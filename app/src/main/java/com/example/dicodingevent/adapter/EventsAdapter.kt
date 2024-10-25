package com.example.dicodingevent.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.dicodingevent.data.network.response.ListEventsItem
import com.example.dicodingevent.databinding.ItemEventsBinding
import com.example.dicodingevent.ui.detail.DetailActivity

class EventsAdapter: ListAdapter<ListEventsItem, EventsAdapter.ViewHolder>(DIFF_CALLBACK) {
    class ViewHolder(private val binding: ItemEventsBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(item: ListEventsItem){
            binding.tvName.text = item.name
            Glide.with(itemView.context)
                .load(item.mediaCover)
                .into(binding.eventImage)
            val goDetail = Intent(itemView.context, DetailActivity::class.java)
            goDetail.putExtra(DetailActivity.EXTRA_ID, item)
            itemView.setOnClickListener {
                itemView.context.startActivity(goDetail)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemEventsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val events = getItem(position)
        holder.bind(events)
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ListEventsItem>() {
            override fun areItemsTheSame(oldItem: ListEventsItem, newItem: ListEventsItem): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: ListEventsItem, newItem: ListEventsItem): Boolean {
                return oldItem == newItem
            }
        }
    }
}