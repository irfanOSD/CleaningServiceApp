package com.irfan.cleaningserviceapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.irfan.cleaningserviceapp.R
import com.irfan.cleaningserviceapp.model.Service

class ServiceAdapter(
    private val serviceList: List<Service>,
    private val onItemClick: (Service) -> Unit
) : RecyclerView.Adapter<ServiceAdapter.ServiceViewHolder>() {

    class ServiceViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val iconImageView: ImageView = itemView.findViewById(R.id.serviceIconImageView)
        val nameTextView: TextView = itemView.findViewById(R.id.serviceNameTextView)
        val descriptionTextView: TextView = itemView.findViewById(R.id.serviceDescriptionTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ServiceViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.service_item, parent, false)
        return ServiceViewHolder(view)
    }

    override fun onBindViewHolder(holder: ServiceViewHolder, position: Int) {
        val service = serviceList[position]
        holder.iconImageView.setImageResource(service.iconResId)
        holder.nameTextView.text = service.name
        holder.descriptionTextView.text = service.description

        holder.itemView.setOnClickListener {
            onItemClick(service)
        }
    }

    override fun getItemCount(): Int {
        return serviceList.size
    }
}