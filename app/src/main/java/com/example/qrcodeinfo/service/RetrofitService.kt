package com.example.qrcodeinfo.service

import com.example.qrcodeinfo.UserInfo
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface RetrofitService {
    @POST("insertcodeqr")
    fun insertInformation(@Body codeQrInfo: UserInfo): Call<String>

    @GET("getqrinfo")
    fun getqrcode(): Call<MutableList<UserInfo>>


}