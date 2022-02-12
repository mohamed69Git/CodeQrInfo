package com.example.qrcodeinfo

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.example.myapplication.showCustomToast
import com.example.qrcodeinfo.service.RetrofitFactory
import kotlinx.android.synthetic.main.confirm.*
import kotlinx.android.synthetic.main.confirm.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DialogConfirm : DialogFragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var rootView: View = inflater.inflate(R.layout.confirm, container, true)
        val sharedPreferences: SharedPreferences? =
            activity?.getSharedPreferences("usersInformation", Context.MODE_PRIVATE)
        val nom = sharedPreferences?.getString("nom", null)
        val prenom = sharedPreferences?.getString("prenom", null)
        val date_naissance = sharedPreferences?.getString("date_naissance", null)
        val date_vaccination = sharedPreferences?.getString("date_vaccination", null)
        val nombre_dose = sharedPreferences?.getInt("nombre_dose", 0)
        val type_vaccin = sharedPreferences?.getString("type_vaccin", null)
        rootView.nom.text = nom
        rootView.prenom.text = prenom
        rootView.date_naissance.text = date_naissance
        rootView.date_vaccination.text = date_vaccination
        rootView.type_vaccin.text = type_vaccin
        rootView.nombre_dose.text = nombre_dose.toString()
        rootView.save.setOnClickListener {
            saveqrcode(
                UserInfo(
                    nom.toString(),
                    prenom.toString(),
                    date_naissance.toString(),
                    nombre_dose,
                    type_vaccin.toString(),
                    date_vaccination.toString()
                )
            )
            Toast(context).showCustomToast(
                "Les informations sont enregistrees avec succes",
                requireActivity()
            )
            dismiss()
        }
        rootView.cancel.setOnClickListener {
            dismiss()
        }
        return rootView
    }

    //Store the qrcode information in the sqlite database
    fun saveqrcode(userInfo: UserInfo) {
        val service = RetrofitFactory.makeRetrofitService()
        val call = service.insertInformation(userInfo)

        call.enqueue(object : Callback<String> {
            //if the request is successfully
            override fun onResponse(call: Call<String>, response: Response<String>) {
                println("Response ${response.body()}")
            }

            //if the request failed
            override fun onFailure(call: Call<String>, t: Throwable) {
                println(t.stackTrace)
            }
        })
    }
}
