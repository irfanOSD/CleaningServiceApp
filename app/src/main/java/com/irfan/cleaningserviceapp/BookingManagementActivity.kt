package com.irfan.cleaningserviceapp

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import com.irfan.cleaningserviceapp.adapter.AdminBookingAdapter
import com.irfan.cleaningserviceapp.model.AdminBookingItem
import com.irfan.cleaningserviceapp.model.Booking

class BookingManagementActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var emptyTextView: TextView
    private lateinit var db: FirebaseFirestore

    private val bookingItems = mutableListOf<AdminBookingItem>()
    private lateinit var adapter: AdminBookingAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_booking_management)

        db = FirebaseFirestore.getInstance()

        recyclerView = findViewById(R.id.adminBookingsRecyclerView)
        emptyTextView = findViewById(R.id.emptyAdminBookingsTextView)

        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = AdminBookingAdapter(bookingItems) { docId, newStatus ->
            updateBookingStatus(docId, newStatus)
        }
        recyclerView.adapter = adapter

        loadAllBookings()
    }

    private fun loadAllBookings() {
        db.collection("bookings")
            .orderBy("timestamp", com.google.firebase.firestore.Query.Direction.DESCENDING)
            .get()
            .addOnSuccessListener { result ->
                bookingItems.clear()
                for (document in result) {
                    val booking = document.toObject(Booking::class.java)
                    bookingItems.add(AdminBookingItem(docId = document.id, booking = booking))
                }
                adapter.notifyDataSetChanged()

                if (bookingItems.isEmpty()) {
                    emptyTextView.visibility = android.view.View.VISIBLE
                    recyclerView.visibility = android.view.View.GONE
                } else {
                    emptyTextView.visibility = android.view.View.GONE
                    recyclerView.visibility = android.view.View.VISIBLE
                }
            }
            .addOnFailureListener { e ->
                Log.e("BookingManagementActivity", "Failed to load bookings", e)
                Toast.makeText(this, "বুকিং লোড করতে সমস্যা হয়েছে", Toast.LENGTH_SHORT).show()
            }
    }

    private fun updateBookingStatus(docId: String, newStatus: String) {
        db.collection("bookings").document(docId)
            .update("status", newStatus)
            .addOnSuccessListener {
                Toast.makeText(this, "Status updated to $newStatus", Toast.LENGTH_SHORT).show()
                loadAllBookings()
            }
            .addOnFailureListener { e ->
                Log.e("BookingManagementActivity", "Failed to update status", e)
                Toast.makeText(this, "Status আপডেট করতে সমস্যা হয়েছে", Toast.LENGTH_SHORT).show()
            }
    }
}