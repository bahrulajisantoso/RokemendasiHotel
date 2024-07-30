package com.example.aplikasiskripsi.ui

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.aplikasiskripsi.R
import com.example.aplikasiskripsi.databinding.ActivityMarkerBinding
import com.example.aplikasiskripsi.response.ResponsePostItem
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*

class MarkerActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMarkerBinding
    private var dataIntent: ArrayList<ResponsePostItem> = ArrayList()

    private val boundsBuilder = LatLngBounds.Builder()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportActionBar?.title = "Peta hotel"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val data = intent.getParcelableArrayListExtra<ResponsePostItem>("data")
        dataIntent = data as ArrayList<ResponsePostItem>

        binding = ActivityMarkerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.mapsView) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.uiSettings.isZoomControlsEnabled = true
        mMap.uiSettings.isIndoorLevelPickerEnabled = true
        mMap.uiSettings.isCompassEnabled = true
        mMap.uiSettings.isMapToolbarEnabled = true

        addManyMarker(data = dataIntent)
    }

    private fun addManyMarker(data: ArrayList<ResponsePostItem>) {

        data.forEach {
            val latLng = LatLng(it.lat?.toDouble() ?: 0.0, it.long?.toDouble() ?: 0.0)
            val markerIcon = if (it.nama == "user input") {
                bitmapDescriptorFromVector(this, R.drawable.ic_location, 124, 124)
            } else {
                bitmapDescriptorFromVector(this, R.drawable.ic_location_blue, 124, 124)
            }

            val markerOptions = MarkerOptions()
                .position(latLng)
                .title(it.nama)
                .icon(markerIcon)

            mMap.addMarker(markerOptions)
            boundsBuilder.include(latLng)
        }

        val bounds: LatLngBounds = boundsBuilder.build()
        mMap.animateCamera(
            CameraUpdateFactory.newLatLngBounds(
                bounds,
                resources.displayMetrics.widthPixels,
                resources.displayMetrics.heightPixels, 300
            )
        )
    }

    private fun bitmapDescriptorFromVector(
        context: Context,
        vectorResId: Int,
        width: Int,
        height: Int
    ): BitmapDescriptor {
        val vectorDrawable = ContextCompat.getDrawable(context, vectorResId)
        vectorDrawable?.setBounds(0, 0, width, height)
        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        vectorDrawable?.draw(canvas)
        return BitmapDescriptorFactory.fromBitmap(bitmap)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        val intent = Intent()
        setResult(RESULT_CANCELED, intent)
        finish()
        super.onBackPressed()
    }
}