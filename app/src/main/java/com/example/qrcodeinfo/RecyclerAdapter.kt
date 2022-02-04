package com.example.qrcodeinfo

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.qrcodeinfo.UserInfo

class RecyclerAdapter(val userInfo: MutableList<UserInfo>):RecyclerView.Adapter<ScannedUserHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScannedUserHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_layout, parent,false)
        return ScannedUserHolder(view)
    }

    override fun onBindViewHolder(holder: ScannedUserHolder, position: Int) {
        return holder.bindView(userInfo[position])
    }

    override fun getItemCount(): Int {
        return userInfo.size
    }
}

class ScannedUserHolder(itemView: View): RecyclerView.ViewHolder(itemView){
   private val nom: TextView = itemView.findViewById(R.id.nom)
    private val prenom: TextView = itemView.findViewById(R.id.prenom)
    private val date_naissance: TextView = itemView.findViewById(R.id.date_naissance)
    private val date_vaccination: TextView = itemView.findViewById(R.id.date_vaccination)
    private val type_vaccin: TextView = itemView.findViewById(R.id.type_vaccin)
    private val nombre_dose: TextView = itemView.findViewById(R.id.nombre_dose)
    private val imageView: ImageView = itemView.findViewById(R.id.iconshow)

    fun bindView(userInfo: UserInfo){
        nom.text = userInfo.nom
        prenom.text = userInfo.prenom
        date_naissance.text = userInfo.date_naissance
        date_vaccination.text = userInfo.date_vaccination
        type_vaccin.text = userInfo.type_vaccin
        nombre_dose.text = userInfo.nombre_dose.toString()
        imageView.setImageResource(R.drawable.ic_baseline_account_circle_24)



    }
}