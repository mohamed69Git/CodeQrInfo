package com.example.qrcodeinfo

//this is the model of the qrcode information
data class UserInfo(
    val nom: String? = null,
    val prenom: String? = null,
    val date_naissance: String? = null,
    val nombre_dose: Int? = null,
    val type_vaccin: String? = null,
    val date_vaccination: String? = null
)
