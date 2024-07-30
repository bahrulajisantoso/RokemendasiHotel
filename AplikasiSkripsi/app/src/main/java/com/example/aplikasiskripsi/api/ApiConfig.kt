package com.example.aplikasiskripsi.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class ApiConfig {
    companion object {
        fun getApiService(): ApiService {
            val loggingInterceptor =
                HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
            val client = OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .writeTimeout(5, TimeUnit.MINUTES)  // Timeout untuk menulis data ke server
                .readTimeout(5, TimeUnit.MINUTES)   // Timeout untuk membaca data dari server
                .connectTimeout(5, TimeUnit.MINUTES) // Timeout untuk koneksi ke server
                .build()
            val retrofit = Retrofit.Builder().baseUrl("http://192.168.1.100:5000/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
            return retrofit.create(ApiService::class.java)
        }
    }
}