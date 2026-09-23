package com.irfan.cleaningserviceapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.irfan.cleaningserviceapp.R
import com.irfan.cleaningserviceapp.model.Booking

class BookingAdapter(
    private val bookingList: List<Booking>,
    private val onItemClick: (Booking) -> Unit
) : RecyclerView.Adapter<BookingAdapter.BookingViewHolder>() {

    class BookingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val serviceNameTextView: TextView = itemView.findViewById(R.id.bookingItemServiceNameTextView)
        val dateTimeTextView: TextView = itemView.findViewById(R.id.bookingItemDateTimeTextView)
        val statusTextView: TextView = itemView.findViewById(R.id.bookingItemStatusTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookingViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.booking_item, parent, false)
        return BookingViewHolder(view)
    }

    override fun onBindViewHolder(holder: BookingViewHolder, position: Int) {
        val booking = bookingList[position]

        holder.serviceNameTextView.text = booking.serviceName
        holder.dateTimeTextView.text = "${booking.date} • ${booking.time}"
        holder.statusTextView.text = booking.status

        holder.itemView.setOnClickListener {
            onItemClick(booking)
        }
    }

    override fun getItemCount(): Int = bookingList.size
}