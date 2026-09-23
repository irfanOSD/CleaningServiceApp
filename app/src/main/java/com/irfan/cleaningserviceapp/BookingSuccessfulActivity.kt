package com.irfan.cleaningserviceapp

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton

class BookingSuccessfulActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_SERVICE_NAME = "extra_service_name"
        const val EXTRA_DATE = "extra_date"
        const val EXTRA_TIME = "extra_time"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_booking_successful)

        // Intent থেকে বুকিং-এর তথ্য নেওয়া হচ্ছে
        val serviceName = intent.getStringExtra(EXTRA_SERVICE_NAME) ?: ""
        val date = intent.getStringExtra(EXTRA_DATE) ?: ""
        val time = intent.getStringExtra(EXTRA_TIME) ?: ""

        // View গুলো খুঁজে বের করা
        val serviceNameTextView = findViewById<TextView>(R.id.successServiceNameTextView)
        val dateTextView = findViewById<TextView>(R.id.successDateTextView)
        val timeTextView = findViewById<TextView>(R.id.successTimeTextView)
        val backToHomeButton = findViewById<MaterialButton>(R.id.backToHomeButton)

        // ডেটা বসানো হচ্ছে
        serviceNameTextView.text = serviceName
        dateTextView.text = date
        timeTextView.text = time

        backToHomeButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            // এই flag গুলো দিয়ে বলা হচ্ছে MainActivity-তে ফিরে যাওয়ার সময়
            // মাঝখানের সব Activity (ServiceDetail, BookingForm) স্ট্যাক থেকে মুছে ফেলতে
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        }
    }
}