package com.irfan.cleaningserviceapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.irfan.cleaningserviceapp.R
import com.irfan.cleaningserviceapp.model.PopularService

class PopularServiceAdapter(
    private val serviceList: List<PopularService>,
    private val onServiceClick: (PopularService) -> Unit
) : RecyclerView.Adapter<PopularServiceAdapter.ServiceViewHolder>() {

    class ServiceViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image: ImageView = itemView.findViewById(R.id.serviceImage)
        val name: TextView = itemView.findViewById(R.id.serviceName)
        val price: TextView = itemView.findViewById(R.id.servicePrice)
        val rating: TextView = itemView.findViewById(R.id.serviceRating)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ServiceViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_popular_service, parent, false)
        return ServiceViewHolder(view)
    }

    override fun onBindViewHolder(holder: ServiceViewHolder, position: Int) {
        val service = serviceList[position]
        holder.image.setImageResource(service.imageResId)
        holder.name.text = service.name
        holder.price.text = service.price
        holder.rating.text = "★ ${service.rating}"

        holder.itemView.setOnClickListener {
            onServiceClick(service)
        }
    }

    override fun getItemCount(): Int {
        return serviceList.size
    }
}