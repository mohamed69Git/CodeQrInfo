package com.example.qrcodeinfo
import org.json.JSONObject

class Response(json: String) : JSONObject(json) {

    val nom: String? = this.optString("nom")
    val prenom: String? = this.optString("prenom")
    val date_naissance: String? = this.optString("date_naissance")
    val nombre_dose: String? = this.optString("nombre_dose")
    val type_vaccin: String? = this.optString("type_vaccin")
    val date_vaccination: String? = this.optString("date_vaccination")

    init {
        println("$nom, $prenom, $date_naissance, $nombre_dose")
    }
//    val data = this.optJSONArray("data")
//        ?.let { 0.until(it.length()).map { i -> it.optJSONObject(i) } } // returns an array of JSONObject
//        ?.map { Foo(it.toString()) } // transforms each JSONObject of the array into Foo
}

class Foo(json: String) : JSONObject(json) {
    val id = this.optInt("id")
    val title: String? = this.optString("title")
}

fun main(){
    Response("{\"nom\":\"Dieng\",\"prenom\":\"Awa\",\"date_naissance\":\"1999-11-12\",\"nombre_dose\":1,\"type_vaccin\":\"Johnson Johnson\",\"date_vaccination\":\"2021-12-22\"}")
}