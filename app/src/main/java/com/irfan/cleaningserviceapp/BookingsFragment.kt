package com.irfan.cleaningserviceapp

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.Query
import com.irfan.cleaningserviceapp.adapter.BookingAdapter
import com.irfan.cleaningserviceapp.model.Booking

class BookingsFragment : Fragment() {

    private var listenerRegistration: ListenerRegistration? = null

    // প্রতিটা বুকিং-এর সর্বশেষ জানা status মনে রাখার জন্য (docId -> status)
    private val lastKnownStatus = mutableMapOf<String, String>()
    private var isFirstLoad = true

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_bookings, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bookingsRecyclerView = view.findViewById<RecyclerView>(R.id.bookingsRecyclerView)
        val emptyBookingsTextView = view.findViewById<View>(R.id.emptyBookingsTextView)

        bookingsRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        val currentUser = FirebaseAuth.getInstance().currentUser
        val userId = currentUser?.uid ?: ""

        val db = FirebaseFirestore.getInstance()

        listenerRegistration = db.collection("bookings")
            .whereEqualTo("userId", userId)
            .orderBy("timestamp", Query.Direction.DESCENDING)
            .addSnapshotListener { result, error ->

                if (error != null) {
                    Log.e("BookingsFragment", "Error listening to bookings", error)
                    return@addSnapshotListener
                }

                if (result == null) return@addSnapshotListener

                val bookingList = mutableListOf<Booking>()

                for (document in result) {
                    val booking = document.toObject(Booking::class.java)
                    bookingList.add(booking)

                    // প্রথমবার লোড হওয়ার সময় notification দেখানো হবে না,
                    // শুধু পরবর্তী কোনো পরিবর্তনে (status বদলালে) দেখানো হবে
                    if (!isFirstLoad) {
                        val previousStatus = lastKnownStatus[document.id]
                        if (previousStatus != null && previousStatus != booking.status) {
                            // Status বদলেছে — আসল notification দেখানো হচ্ছে
                            NotificationHelper.showStatusNotification(
                                requireContext(),
                                notificationId = document.id.hashCode(),
                                serviceName = booking.serviceName,
                                status = booking.status
                            )
                        }
                    }

                    lastKnownStatus[document.id] = booking.status
                }

                isFirstLoad = false

                if (bookingList.isEmpty()) {
                    emptyBookingsTextView.visibility = View.VISIBLE
                    bookingsRecyclerView.visibility = View.GONE
                } else {
                    emptyBookingsTextView.visibility = View.GONE
                    bookingsRecyclerView.visibility = View.VISIBLE

                    val adapter = BookingAdapter(bookingList) { clickedBooking ->
                        showBookingDetailsDialog(clickedBooking)
                    }
                    bookingsRecyclerView.adapter = adapter
                }
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        // Fragment বন্ধ হয়ে গেলে listener-ও বন্ধ করে দেওয়া হচ্ছে, নাহলে মেমোরি লিক হতে পারে
        listenerRegistration?.remove()
    }

    private fun showBookingDetailsDialog(booking: Booking) {
        val message = """
            Name: ${booking.name}
            Phone: ${booking.phone}
            Address: ${booking.address}
            Note: ${if (booking.note.isEmpty()) "N/A" else booking.note}
        """.trimIndent()

        AlertDialog.Builder(requireContext())
            .setTitle(booking.serviceName)
            .setMessage(message)
            .setPositiveButton("Close", null)
            .show()
    }
}