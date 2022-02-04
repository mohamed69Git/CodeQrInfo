package com.example.qrcodeinfo

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.*
import androidx.activity.result.ActivityResultLauncher
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.edit
import androidx.recyclerview.widget.RecyclerView
import com.budiyev.android.codescanner.AutoFocusMode
import com.budiyev.android.codescanner.CodeScanner
import com.budiyev.android.codescanner.CodeScannerView
import com.budiyev.android.codescanner.DecodeCallback
import com.budiyev.android.codescanner.ErrorCallback
import com.budiyev.android.codescanner.ScanMode
import com.example.qrcodeinfo.service.RetrofitFactory
import com.example.qrcodeinfo.service.RetrofitService
import com.google.zxing.integration.android.IntentIntegrator
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject
import org.json.JSONStringer
import org.json.JSONTokener
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import java.util.regex.Pattern

class MainActivity : AppCompatActivity() {
    private lateinit var liste: Button
    private lateinit var affcher: Button
    private lateinit var scanner: Button
    private lateinit var codescanner: CodeScanner
    private lateinit var mQrResultLauncher: ActivityResultLauncher<Intent>
    private lateinit var textQr: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        liste = findViewById(R.id.liste)
        scanner = findViewById(R.id.scanner)
        liste.setOnClickListener{
            startActivity(Intent(this, Resultat::class.java))
        }
        scanner.setOnClickListener{
            findViewById<RelativeLayout>(R.id.relative).visibility = GONE
            welcome.visibility = VISIBLE
            findViewById<View>(R.id.scanner_view).visibility = VISIBLE
        }
        if (ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.CAMERA) ==
            PackageManager.PERMISSION_DENIED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.CAMERA),
                123
            )

        } else {

            startScanning();
        }


    }


    // Start the QR Scanner
    private fun startScanner() {
        val scanner = IntentIntegrator(this)
        // QR Code Format
        scanner.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE)
        // Set Text Prompt at Bottom of QR code Scanner Activity
        scanner.setPrompt("QR Code Scanner Prompt Text")
        scanner.setBeepEnabled(true)
        scanner.setTimeout(10000)

        // Start Scanner (don't use initiateScan() unless if you want to use OnActivityResult)
        mQrResultLauncher.launch(scanner.createScanIntent())
    }


    fun startScanning() {
        val scannerView: CodeScannerView = findViewById(R.id.scanner_view)
        codescanner = CodeScanner(this, scannerView)
        codescanner.camera = CodeScanner.CAMERA_BACK
        codescanner.formats = CodeScanner.ALL_FORMATS
        codescanner.autoFocusMode = AutoFocusMode.SAFE
        codescanner.scanMode = ScanMode.SINGLE
        codescanner.isAutoFocusEnabled = true
        codescanner.isFlashEnabled = false
        codescanner.decodeCallback = DecodeCallback {
            runOnUiThread {

//                Toast.makeText(this, "${it.text}", Toast.LENGTH_SHORT).show()
                try {

                    afficher.visibility = VISIBLE
                    val jsonObject = JSONTokener(it.text).nextValue() as JSONObject
                    val nom = jsonObject.getString("nom")
                    val prenom = jsonObject.getString("prenom")
                    val date_naissance = jsonObject.getString("date_naissance")
                    val nombre_dose = jsonObject.getString("nombre_dose").toInt()
                    val type_vaccin = jsonObject.getString("type_vaccin")
                    val date_vaccination = jsonObject.getString("date_vaccination")
                    println("$nom, $prenom, $date_naissance, $nombre_dose, $type_vaccin, $date_vaccination")
                    val userInfo = UserInfo(
                        nom,
                        prenom,
                        date_naissance,
                        nombre_dose,
                        type_vaccin,
                        date_vaccination
                    )
                    affcher = findViewById(R.id.afficher)
                    //this is the last recorded qr code
                    val sharedPreferences: SharedPreferences = getSharedPreferences(
                        "usersInformation",
                        Context.MODE_PRIVATE
                    )
                    val editor: SharedPreferences.Editor = sharedPreferences.edit()
                    editor.apply {

                        putString("nom", nom)
                        putString("prenom", prenom)
                        putString("date_naissance", date_naissance)
                        putInt("nombre_dose", nombre_dose)
                        putString("type_vaccin", type_vaccin)
                        putString("date_vaccination", date_vaccination)
                    }.apply()
                    // let's store this information in our database
                    affcher.visibility = VISIBLE
                    affcher.setOnClickListener {
                        var dialog = DialogConfirm()
                        dialog.show(supportFragmentManager,"confirm")
                        afficher.visibility = GONE

                    }
                    welcome.setOnClickListener {
                        startActivity(Intent(this, MainActivity::class.java))
                        finish()
                    }
                }
                catch (e: Exception){
                    print(e.stackTrace)
                    Toast.makeText(
                        this, "Ce code qr n'est pas prise en compte",
                        Toast.LENGTH_LONG
                    )
                        .show()
                }





            }
        }

        codescanner.errorCallback = ErrorCallback {
            runOnUiThread {
                Toast.makeText(this, "Camera initialization: ${it.message}", Toast.LENGTH_SHORT)
                    .show()
            }
        }

        scannerView.setOnClickListener {
            codescanner.startPreview()
        }


    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 123) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                Toast.makeText(this, "Camera permission granted", Toast.LENGTH_SHORT).show()
                startScanning()
            } else {
                Toast.makeText(this, "Camera permission denied", Toast.LENGTH_SHORT).show()
            }
        }

    }

    override fun onResume() {
        super.onResume()
        if (::codescanner.isInitialized) {
            codescanner?.startPreview()
        }
    }

    override fun onPause() {
        super.onPause()
        if (::codescanner.isInitialized) {
            codescanner.releaseResources()
        }

    }

    //let's create function to save data in our datebase



}