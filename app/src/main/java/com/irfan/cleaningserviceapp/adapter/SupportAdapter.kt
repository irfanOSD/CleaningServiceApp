package com.irfan.cleaningserviceapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.irfan.cleaningserviceapp.R
import com.irfan.cleaningserviceapp.model.SupportOption

class SupportAdapter(
    private val supportList: List<SupportOption>
) : RecyclerView.Adapter<SupportAdapter.SupportViewHolder>() {

    inner class SupportViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val icon: ImageView = itemView.findViewById(R.id.imgSupportIcon)
        val title: TextView = itemView.findViewById(R.id.txtSupportTitle)
        val subtitle: TextView = itemView.findViewById(R.id.txtSupportSubtitle)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SupportViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.support_item, parent, false)
        return SupportViewHolder(view)
    }

    override fun onBindViewHolder(holder: SupportViewHolder, position: Int) {
        val option = supportList[position]

        holder.title.text = option.title
        holder.subtitle.text = option.subtitle
        holder.icon.setImageResource(option.iconResId)

        // আপাতত সব কার্ডে ট্যাপ করলে "Coming Soon" Toast দেখাবে
        holder.itemView.setOnClickListener {
            Toast.makeText(
                holder.itemView.context,
                "${option.title} - Coming Soon",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    override fun getItemCount(): Int = supportList.size
}