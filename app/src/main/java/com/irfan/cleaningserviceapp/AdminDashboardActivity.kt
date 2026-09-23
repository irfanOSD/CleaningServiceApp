package com.irfan.cleaningserviceapp

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore

class AdminDashboardActivity : AppCompatActivity() {

    private lateinit var tvTotal: TextView
    private lateinit var tvPending: TextView
    private lateinit var tvAccepted: TextView
    private lateinit var tvCompleted: TextView
    private lateinit var tvCancelled: TextView

    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_dashboard)

        db = FirebaseFirestore.getInstance()

        tvTotal = findViewById(R.id.tvAdminTotalCount)
        tvPending = findViewById(R.id.tvAdminPendingCount)
        tvAccepted = findViewById(R.id.tvAdminAcceptedCount)
        tvCompleted = findViewById(R.id.tvAdminCompletedCount)
        tvCancelled = findViewById(R.id.tvAdminCancelledCount)

        val btnManageBookings = findViewById<android.widget.Button>(R.id.btnManageBookings)
        btnManageBookings.setOnClickListener {
            startActivity(android.content.Intent(this, BookingManagementActivity::class.java))
        }

        loadBookingCounts()
    }

    override fun onResume() {
        super.onResume()
        // Dashboard-এ ফিরে এলে কাউন্ট আবার রিফ্রেশ হবে (যেমন Booking Management থেকে status বদলে ফিরলে)
        loadBookingCounts()
    }

    private fun loadBookingCounts() {
        db.collection("bookings").get()
            .addOnSuccessListener { result ->
                var pendingCount = 0
                var acceptedCount = 0
                var completedCount = 0
                var cancelledCount = 0

                for (document in result) {
                    when (document.getString("status")) {
                        "Pending" -> pendingCount++
                        "Accepted" -> acceptedCount++
                        "Completed" -> completedCount++
                        "Cancelled" -> cancelledCount++
                    }
                }

                val totalCount = result.size()

                tvTotal.text = totalCount.toString()
                tvPending.text = pendingCount.toString()
                tvAccepted.text = acceptedCount.toString()
                tvCompleted.text = completedCount.toString()
                tvCancelled.text = cancelledCount.toString()
            }
            .addOnFailureListener { e ->
                Log.e("AdminDashboardActivity", "Failed to load booking counts", e)
                Toast.makeText(this, "কাউন্ট লোড করতে সমস্যা হয়েছে", Toast.LENGTH_SHORT).show()
            }
    }
}