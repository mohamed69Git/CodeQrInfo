package com.example.qrcodeinfo.service

import com.example.qrcodeinfo.UserInfo
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface RetrofitService {
    //this is the route for inserting a new information in the database
    @POST("insertcodeqr")
    fun insertInformation(@Body codeQrInfo: UserInfo): Call<String>
    //this is the route to retrieve all the information recorded in the database
    @GET("getqrinfo")
    fun getqrcode(): Call<MutableList<UserInfo>>


}