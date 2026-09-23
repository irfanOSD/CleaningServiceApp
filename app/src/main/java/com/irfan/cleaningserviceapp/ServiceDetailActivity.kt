package com.irfan.cleaningserviceapp

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton

class ServiceDetailActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_SERVICE_NAME = "extra_service_name"
        const val EXTRA_SERVICE_DESCRIPTION = "extra_service_description"
        const val EXTRA_SERVICE_ICON = "extra_service_icon"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_service_detail)

        // Intent থেকে ডেটা নেওয়া হচ্ছে
        val serviceName = intent.getStringExtra(EXTRA_SERVICE_NAME) ?: ""
        val serviceDescription = intent.getStringExtra(EXTRA_SERVICE_DESCRIPTION) ?: ""
        val serviceIconResId = intent.getIntExtra(EXTRA_SERVICE_ICON, R.drawable.ic_app_logo)

        // View গুলো খুঁজে বের করা
        val iconImageView = findViewById<ImageView>(R.id.detailIconImageView)
        val nameTextView = findViewById<TextView>(R.id.detailNameTextView)
        val descriptionTextView = findViewById<TextView>(R.id.detailDescriptionTextView)
        val bookThisServiceButton = findViewById<MaterialButton>(R.id.bookThisServiceButton)

        // ডেটা বসানো হচ্ছে
        iconImageView.setImageResource(serviceIconResId)
        nameTextView.text = serviceName
        descriptionTextView.text = serviceDescription

        bookThisServiceButton.setOnClickListener {
            val intent = Intent(this, BookingFormActivity::class.java)
            intent.putExtra(BookingFormActivity.EXTRA_SERVICE_NAME, serviceName)
            startActivity(intent)
        }
    }
}