package com.example.headsup

import retrofit2.Call
import retrofit2.http.*

interface ApiInterface {
    @GET("celebrities/")
    fun getData(): Call<List<CelebrityDataItem>>

    @POST("celebrities/")
    fun postData(@Body cData: CelebrityDataItem): Call<CelebrityData>

    @PUT("celebrities/{id}")
    fun updateData(@Path("id") id: Int, @Body cData: CelebrityDataItem): Call<CelebrityData>

    @DELETE("celebrities/{id}")
    fun deleteData(@Path("id") id: Int): Call<Void>
}