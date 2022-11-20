package com.mechastudios.speechjammer.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mechastudios.speechjammer.R
import com.mechastudios.speechjammer.databinding.ItemSpeechJammerDashboardBinding
import com.mechastudios.speechjammer.model.dashboard.HomeData
import com.mechastudios.speechjammer.model.dashboard.SpeechJammerData

class DashboardAdapter : ListAdapter<HomeData,RecyclerView.ViewHolder>(HomeDataDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            R.layout.item_speech_jammer_dashboard -> {
                SpeechJammerViewHolder(ItemSpeechJammerDashboardBinding.inflate(LayoutInflater.from(parent.context),parent,false))
            }
            else -> TODO()
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    class HomeDataDiffUtil : DiffUtil.ItemCallback<HomeData>() {
        override fun areItemsTheSame(oldItem: HomeData, newItem: HomeData): Boolean {
            return oldItem.type == newItem.type
        }

        override fun areContentsTheSame(oldItem: HomeData, newItem: HomeData): Boolean {
            if (oldItem is SpeechJammerData && newItem is SpeechJammerData)
                return oldItem.name == newItem.name
            return false
        }
    }

    inner class SpeechJammerViewHolder(val binding: ItemSpeechJammerDashboardBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind() {

        }
    }

}