package com.example.aplikasiskripsi.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.aplikasiskripsi.api.ApiConfig
import com.example.aplikasiskripsi.response.ResponsePostItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {

    private val _error = MutableLiveData<Boolean>()
    val error: LiveData<Boolean> = _error

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _data = MutableLiveData<List<ResponsePostItem>>()
    val data: LiveData<List<ResponsePostItem>> = _data

    fun post(
        lat: String,
        long: String,
        text: String
    ) {
        _isLoading.value = true
        val client = ApiConfig.getApiService()
            .post(
                lat = lat,
                long = long,
                text = text
            )
        client.enqueue(object : Callback<List<ResponsePostItem>> {
            override fun onResponse(
                call: Call<List<ResponsePostItem>>,
                response: Response<List<ResponsePostItem>>
            ) {
                _isLoading.value = false
                val responseBody = response.body()
                if (response.isSuccessful && responseBody != null) {
                    _error.value = false
                    _data.value = response.body()

                } else {
                    _error.value = true
                }
            }

            override fun onFailure(call: Call<List<ResponsePostItem>>, t: Throwable) {
                _isLoading.value = false
                _error.value = true
            }
        })
    }
}