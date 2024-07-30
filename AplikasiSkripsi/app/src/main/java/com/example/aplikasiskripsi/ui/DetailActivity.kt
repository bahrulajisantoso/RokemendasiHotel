package com.example.aplikasiskripsi.ui

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.example.aplikasiskripsi.R
import com.example.aplikasiskripsi.databinding.ActivityDetailBinding
import com.example.aplikasiskripsi.response.ResponsePostItem

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Detail"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val data = intent.getParcelableExtra<ResponsePostItem>("detail")

        data?.let { showDetail(it) }

        val textViews = getAllTextViews(findViewById(android.R.id.content))

        for (textView in textViews) {
            textView.setTextColor(ContextCompat.getColor(this, R.color.gray))
        }
    }

    private fun getAllTextViews(view: android.view.View): List<TextView> {
        val views = ArrayList<TextView>()

        if (view is TextView) {
            views.add(view)
        } else if (view is android.view.ViewGroup) {
            for (i in 0 until view.childCount) {
                views.addAll(getAllTextViews(view.getChildAt(i)))
            }
        }

        return views
    }

    private fun showDetail(data: ResponsePostItem) {
        binding.apply {
            Glide.with(this@DetailActivity).load(data.imageSrc).into(imgDetail)
            tvName.text = data.nama
            tvLocation.text = data.lokasi
            tvPrice.text = data.harga
            tvStar.text = data.bintang
            tvDesc.text = data.deskripsi2
            tvFacilities.text = data.fasilitas
            if (data.skor1.isNullOrEmpty()) {
                tvScore1.text = "Rating: -"
            } else {
                tvScore1.text = "Rating: ${data.skor1}"
            }

            if (data.skor2.isNullOrEmpty()) {
                tvScore2.text = ""
            } else {
                tvScore2.text = data.skor2
            }

            tvScore2.text = data.skor2
            if (data.jumlahUlasan.isNullOrEmpty()) {
                tvReviews.text = "Jumlah ulasan: -"
            } else {
                tvReviews.text = "Jumlah ulasan: ${data.jumlahUlasan}"
            }
        }
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