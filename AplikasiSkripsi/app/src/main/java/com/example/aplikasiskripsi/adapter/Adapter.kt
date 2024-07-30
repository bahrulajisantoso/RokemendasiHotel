package com.example.aplikasiskripsi.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.aplikasiskripsi.databinding.ItemRecommendationBinding
import com.example.aplikasiskripsi.response.ResponsePostItem

class Adapter : RecyclerView.Adapter<Adapter.ListViewHolder>() {

    private val listHotel = ArrayList<ResponsePostItem>()

    fun setListHotel(newListHotel: List<ResponsePostItem>?) {
        if (newListHotel == null) return
        listHotel.clear()
        listHotel.addAll(newListHotel)
    }

    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding =
            ItemRecommendationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val adjustedPosition = position + 1
        val hotel = listHotel[adjustedPosition]
        holder.itemView.setOnClickListener { onItemClickCallback.onItemClicked(listHotel[adjustedPosition]) }
        holder.bind(hotel)
    }

    override fun getItemCount(): Int {
        return if (listHotel.size > 1) listHotel.size - 1 else 0
    }


    class ListViewHolder(private val binding: ItemRecommendationBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(hotel: ResponsePostItem) {

            binding.apply {
                Glide.with(itemView.context).load(hotel.imageSrc).into(imgItem)
                tvItemName.text = hotel.nama
                tvItemLoc.text = hotel.lokasi
                tvItemPrice.text = hotel.harga
                val distance = hotel.jarakKm?.let { String.format("%.1f", it.toDouble()) }
                tvItemDistance.text = "Jarak: $distance km"
                val similarity = hotel.kemiripan?.let { String.format("%.2f", it.toDouble()) }
                tvItemSimilarity.text = "Kemiripan: $similarity"

            }
        }
    }

    interface OnItemClickCallback {
        fun onItemClicked(listHotel: ResponsePostItem)
    }
}