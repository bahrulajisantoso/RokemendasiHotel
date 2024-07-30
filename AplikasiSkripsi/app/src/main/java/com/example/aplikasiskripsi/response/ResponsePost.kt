package com.example.aplikasiskripsi.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class ResponsePost(

	@field:SerializedName("ResponsePost")
	val responsePost: List<ResponsePostItem?>? = null
)

@Parcelize
data class ResponsePostItem(

	@field:SerializedName("image-src")
	val imageSrc: String? = null,

	@field:SerializedName("jumlah ulasan")
	val jumlahUlasan: String? = null,

	@field:SerializedName("jarak(km)")
	val jarakKm: String? = null,

	@field:SerializedName("deskripsi2")
	val deskripsi2: String? = null,

	@field:SerializedName("fasilitas")
	val fasilitas: String? = null,

	@field:SerializedName("kemiripan")
	val kemiripan: String? = null,

	@field:SerializedName("long")
	val long: String? = null,

	@field:SerializedName("skor1")
	val skor1: String? = null,

	@field:SerializedName("skor2")
	val skor2: String? = null,

	@field:SerializedName("nama")
	val nama: String? = null,

	@field:SerializedName("harga")
	val harga: String? = null,

	@field:SerializedName("deskirpsi1")
	val deskirpsi1: String? = null,

	@field:SerializedName("lokasi")
	val lokasi: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("lat")
	val lat: String? = null,

	@field:SerializedName("bintang")
	val bintang: String? = null
) : Parcelable
