package com.example.aplikasiskripsi.ui

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.aplikasiskripsi.R
import com.example.aplikasiskripsi.adapter.Adapter
import com.example.aplikasiskripsi.databinding.ActivityRecommendationBinding
import com.example.aplikasiskripsi.response.ResponsePostItem
import com.example.aplikasiskripsi.viewmodel.MainViewModel

class RecommendationActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels()
    private lateinit var binding: ActivityRecommendationBinding
    private var dataIntent: ArrayList<ResponsePostItem> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecommendationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Rekomendasi hotel"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val lat = intent.getStringExtra("lat").toString()
        val long = intent.getStringExtra("long").toString()
        val text = intent.getStringExtra("text").toString()
        val loc = intent.getStringExtra("location").toString()

        binding.tvLocation.text = loc

        post(lat = lat, long = long, text = text)
    }

    private fun post(lat: String, long: String, text: String) {
        viewModel.post(lat = lat, long = long, text = text)
        viewModel.error.observe(this) { error ->
            if (!error) {
                Toast.makeText(
                    this@RecommendationActivity,
                    "Sukses",
                    Toast.LENGTH_SHORT
                ).show()

                viewModel.data.observe(this) { data ->

                    dataIntent = data as ArrayList<ResponsePostItem>

                    binding.rvHotel.apply {
                        val itemAdapter = Adapter()
                        itemAdapter.setListHotel(dataIntent)

                        layoutManager = LinearLayoutManager(
                            this@RecommendationActivity,
                            LinearLayoutManager.VERTICAL,
                            false
                        )
                        setHasFixedSize(true)

                        itemAdapter.setOnItemClickCallback(object : Adapter.OnItemClickCallback {
                            override fun onItemClicked(listHotel: ResponsePostItem) {
                                val intent =
                                    Intent(this@RecommendationActivity, DetailActivity::class.java)
                                intent.putExtra("detail", listHotel)
                                startActivity(intent)
                            }
                        })

                        adapter = itemAdapter
                    }
                }
            } else {
                Toast.makeText(
                    this@RecommendationActivity,
                    "Gagal memuat data",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        viewModel.isLoading.observe(this) { loading ->
            binding.progressBar.visibility = if (loading) View.VISIBLE else View.GONE
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.option_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
            R.id.maps -> {
                val intent = Intent(this@RecommendationActivity, MarkerActivity::class.java)
                intent.putParcelableArrayListExtra("data", dataIntent)
                startActivity(intent)
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