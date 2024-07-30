package com.example.aplikasiskripsi

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.aplikasiskripsi.databinding.ActivityMainBinding
import com.example.aplikasiskripsi.ui.RecommendationActivity
import com.example.aplikasiskripsi.ui.UserActivity
import com.google.android.gms.maps.model.LatLng
import com.vanillaplacepicker.presentation.builder.VanillaPlacePicker
import com.vanillaplacepicker.utils.MapType
import com.vanillaplacepicker.utils.PickerLanguage
import com.vanillaplacepicker.utils.PickerType
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private var lat = ""
    private var long = ""
    private var text = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        bottomNav()

        val placePickerResultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK && result.data != null) {
                    val vanillaAddress = VanillaPlacePicker.getPlaceResult(result.data)
                    binding.tvLocation.text = vanillaAddress?.formattedAddress
                    lat = vanillaAddress?.latitude.toString()
                    long = vanillaAddress?.longitude.toString()
                }
            }

        binding.btnMaps.setOnClickListener {
            getLoc(placePickerResultLauncher)
        }

        binding.btnVoice.setOnClickListener {
            val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
            intent.putExtra(
                RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
            )

            intent.putExtra(
                RecognizerIntent.EXTRA_LANGUAGE,
                Locale.getDefault()
            )

            intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Start to speak")

            try {
                startActivityForResult(intent, 100)
            } catch (e: Exception) {
                // on below line we are displaying error message in toast
                Toast.makeText(
                    this@MainActivity, " " + e.message,
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        binding.btnSubmit.setOnClickListener {
            text = binding.edText.text.trim().toString()
            if (lat.isEmpty() && long.isEmpty()) {
                Toast.makeText(this, "Lokasi kosong", Toast.LENGTH_SHORT).show()
            } else if (text.isEmpty()) {
                Toast.makeText(this, "Teks kosong", Toast.LENGTH_SHORT).show()
            } else {
                val intent = Intent(this@MainActivity, RecommendationActivity::class.java)
                intent.putExtra("lat", lat)
                intent.putExtra("long", long)
                intent.putExtra("text", text)
                intent.putExtra("location", binding.tvLocation.text)
                startActivity(intent)
            }
        }
    }


    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // in this method we are checking request
        // code with our result code.
        if (requestCode == 100) {
            // on below line we are checking if result code is ok
            if (resultCode == RESULT_OK && data != null) {

                // in that case we are extracting the
                // data from our array list
                val res: ArrayList<String> =
                    data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS) as ArrayList<String>

                // on below line we are setting data
                // to our output text view.
                binding.edText.setText(Objects.requireNonNull(res)[0])
            }
        }
    }

    private fun getLoc(placePickerResultLauncher: ActivityResultLauncher<Intent>) {
        //        Launch caller with Intent
        val intent = VanillaPlacePicker.Builder(this@MainActivity)
            .with(PickerType.MAP) // Select Picker type to enable autocompelte, map or both
            .withLocation(-7.9667370546239, 112.63280849532603)
            .setPickerLanguage(PickerLanguage.ENGLISH) // Apply language to picker
            .setLocationRestriction(
                LatLng(
                    -8.385279606166316,
                    112.3658189542537
                ),  // Southwest coordinates (approximate)
                LatLng(
                    -7.757964766103416,
                    112.94231986416115
                )
            ) // Restrict location bounds in map and autocomplete
//            .setCountry("IN") // Only for Autocomplete
            .enableShowMapAfterSearchResult(true) // To show the map after selecting the place from place picker only for PickerType.MAP_WITH_AUTO_C

//             Configuration for Map UI
            .setMapType(MapType.NORMAL) // Choose map type (Only applicable for map screen)
//            .setMapStyle(R.raw.style_json) // Containing the JSON style declaration for night-mode styling
//            .setMapPinDrawable(android.R.drawable.ic_menu_mylocation) // To give custom pin image for map marker
            .setMapPinDrawable(R.drawable.ic_location)
            .build()

        placePickerResultLauncher.launch(intent)
    }

    private fun bottomNav() {
        val navigation = binding.navView

        navigation.selectedItemId = R.id.navigation_home

        navigation.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.navigation_home -> true
                R.id.navigation_user -> {
                    startActivity(Intent(applicationContext, UserActivity::class.java))
                    overridePendingTransition(0, 0)
                    finish()
                    true
                }
                else -> {
                    true
                }
            }
        }
    }
}