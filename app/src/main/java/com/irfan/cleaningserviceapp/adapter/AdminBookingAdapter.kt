package com.irfan.cleaningserviceapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AdapterView
import android.widget.Spinner
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.irfan.cleaningserviceapp.R
import com.irfan.cleaningserviceapp.model.AdminBookingItem

class AdminBookingAdapter(
    private val bookingList: List<AdminBookingItem>,
    private val onStatusChange: (docId: String, newStatus: String) -> Unit
) : RecyclerView.Adapter<AdminBookingAdapter.AdminBookingViewHolder>() {

    private val statusOptions = listOf("Pending", "Accepted", "Completed", "Cancelled")

    class AdminBookingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val serviceNameTextView: TextView = itemView.findViewById(R.id.adminBookingServiceNameTextView)
        val dateTimeTextView: TextView = itemView.findViewById(R.id.adminBookingDateTimeTextView)
        val nameTextView: TextView = itemView.findViewById(R.id.adminBookingNameTextView)
        val phoneTextView: TextView = itemView.findViewById(R.id.adminBookingPhoneTextView)
        val addressTextView: TextView = itemView.findViewById(R.id.adminBookingAddressTextView)
        val noteTextView: TextView = itemView.findViewById(R.id.adminBookingNoteTextView)
        val statusTextView: TextView = itemView.findViewById(R.id.adminBookingStatusTextView)
        val statusSpinner: Spinner = itemView.findViewById(R.id.adminBookingStatusSpinner)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdminBookingViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.admin_booking_item, parent, false)
        return AdminBookingViewHolder(view)
    }

    override fun onBindViewHolder(holder: AdminBookingViewHolder, position: Int) {
        val item = bookingList[position]
        val booking = item.booking

        holder.serviceNameTextView.text = booking.serviceName
        holder.dateTimeTextView.text = "${booking.date} • ${booking.time}"
        holder.nameTextView.text = "Name: ${booking.name}"
        holder.phoneTextView.text = "Phone: ${booking.phone}"
        holder.addressTextView.text = "Address: ${booking.address}"
        holder.noteTextView.text = if (booking.note.isNotEmpty()) "Note: ${booking.note}" else "Note: (none)"
        holder.statusTextView.text = "Status: ${booking.status}"

        // Spinner-এ status options বসানো হচ্ছে
        val spinnerAdapter = ArrayAdapter(
            holder.itemView.context,
            android.R.layout.simple_spinner_item,
            statusOptions
        )
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        holder.statusSpinner.adapter = spinnerAdapter

        // RecyclerView রিসাইকেল হওয়ার সময় যেন ভুল করে পুরনো listener ট্রিগার না হয়,
        // তাই আগে listener সরিয়ে, সঠিক position সেট করে, তারপর listener বসানো হচ্ছে
        holder.statusSpinner.onItemSelectedListener = null
        val currentIndex = statusOptions.indexOf(booking.status)
        holder.statusSpinner.setSelection(if (currentIndex >= 0) currentIndex else 0, false)

        holder.statusSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, pos: Int, id: Long) {
                val selectedStatus = statusOptions[pos]
                if (selectedStatus != booking.status) {
                    onStatusChange(item.docId, selectedStatus)
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }

    override fun getItemCount(): Int = bookingList.size
}