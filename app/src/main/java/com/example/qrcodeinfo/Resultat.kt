package com.example.qrcodeinfo

import android.animation.Animator
import android.animation.AnimatorInflater
import android.animation.AnimatorSet
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.qrcodeinfo.service.RetrofitFactory
import com.google.android.material.floatingactionbutton.FloatingActionButton
import retrofit2.Call
import retrofit2.Response
import retrofit2.Callback

class Resultat : AppCompatActivity() {
    private lateinit var current: RelativeLayout
    private lateinit var home: FloatingActionButton
    private lateinit var recycleview: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_resultat)
        val sharedPreferences: SharedPreferences = getSharedPreferences(
            "usersInformation",
            Context.MODE_PRIVATE
        )
        recycleview = findViewById(R.id.recycleview)
        val nom = sharedPreferences.getString("nom", null)
        val prenom = sharedPreferences.getString("prenom", null)
        val date_naissance = sharedPreferences.getString("date_naissance", null)
        val date_vaccination = sharedPreferences.getString("date_vaccination", null)
        val nombre_dose = sharedPreferences.getInt("nombre_dose", 0)
        val type_vaccin = sharedPreferences.getString("type_vaccin", null)

        home = findViewById(R.id.home)
        home.setOnClickListener {
            onBackPressed()
        }
        fetchQrCode()

    }

    fun fetchQrCode(){
        val service = RetrofitFactory.makeRetrofitService()
        val call = service.getqrcode()

        call.enqueue(object : Callback<MutableList<UserInfo>>{
            override fun onResponse(
                call: Call<MutableList<UserInfo>>,
                response: Response<MutableList<UserInfo>>
            ) {
                recycleview.apply {
                    layoutManager = LinearLayoutManager(this@Resultat)
                    adapter = RecyclerAdapter(response.body()!!)
                    adapter = RecyclerAdapter(response.body()!!)
                    println("########################## ${response.body()} ########################################")
                }
            }

            override fun onFailure(call: Call<MutableList<UserInfo>>, t: Throwable) {
                t.printStackTrace()
                Log.e("error", t.message.toString())
            }

        })
    }
}