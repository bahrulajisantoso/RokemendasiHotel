package com.example.aplikasiskripsi.api

import com.example.aplikasiskripsi.response.ResponsePostItem
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    @FormUrlEncoded
    @POST("post")
    fun post(
        @Field("lat") lat: String,
        @Field("long") long: String,
        @Field("text") text: String,
    ): Call<List<ResponsePostItem>>
}