package com.irfan.cleaningserviceapp

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import java.util.Calendar
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import android.widget.Toast
import android.content.Intent
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.irfan.cleaningserviceapp.model.Booking

class BookingFormActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_SERVICE_NAME = "extra_service_name"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_booking_form)

        // Intent থেকে নির্বাচিত service-এর নাম নেওয়া হচ্ছে
        val serviceName = intent.getStringExtra(EXTRA_SERVICE_NAME) ?: "Selected Service"

        // View গুলো খুঁজে বের করা
        val selectedServiceNameTextView = findViewById<TextView>(R.id.selectedServiceNameTextView)
        val selectedServicePriceTextView = findViewById<TextView>(R.id.selectedServicePriceTextView)

        val nameEditText = findViewById<TextInputEditText>(R.id.bookingNameEditText)
        val phoneEditText = findViewById<TextInputEditText>(R.id.bookingPhoneEditText)
        val addressEditText = findViewById<TextInputEditText>(R.id.bookingAddressEditText)
        val dateEditText = findViewById<TextInputEditText>(R.id.bookingDateEditText)
        val timeEditText = findViewById<TextInputEditText>(R.id.bookingTimeEditText)
        val noteEditText = findViewById<TextInputEditText>(R.id.bookingNoteEditText)

        val confirmBookingButton = findViewById<MaterialButton>(R.id.confirmBookingButton)

        // নির্বাচিত service-এর নাম ও (সাময়িক) দাম দেখানো হচ্ছে
        selectedServiceNameTextView.text = serviceName
        selectedServicePriceTextView.text = "Price: To be updated"

        // Date field-এ ক্লিক করলে DatePickerDialog খোলার জন্য
        dateEditText.setOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(
                this,
                { _, selectedYear, selectedMonth, selectedDay ->
                    val formattedDate = "%02d/%02d/%04d".format(selectedDay, selectedMonth + 1, selectedYear)
                    dateEditText.setText(formattedDate)
                },
                year, month, day
            )

            datePickerDialog.datePicker.minDate = calendar.timeInMillis
            datePickerDialog.show()
        }

        // Time field-এ ক্লিক করলে TimePickerDialog খোলার জন্য
        timeEditText.setOnClickListener {
            val calendar = Calendar.getInstance()
            val hour = calendar.get(Calendar.HOUR_OF_DAY)
            val minute = calendar.get(Calendar.MINUTE)

            val timePickerDialog = TimePickerDialog(
                this,
                { _, selectedHour, selectedMinute ->
                    val amPm = if (selectedHour < 12) "AM" else "PM"
                    val hourIn12Format = when {
                        selectedHour == 0 -> 12
                        selectedHour > 12 -> selectedHour - 12
                        else -> selectedHour
                    }
                    val formattedTime = "%02d:%02d %s".format(hourIn12Format, selectedMinute, amPm)
                    timeEditText.setText(formattedTime)
                },
                hour, minute, false
            )

            timePickerDialog.show()
        }

        // Confirm Booking বাটনে ক্লিক করলে Firestore-এ ডেটা সেভ করা
        confirmBookingButton.setOnClickListener {
            val name = nameEditText.text.toString().trim()
            val phone = phoneEditText.text.toString().trim()
            val address = addressEditText.text.toString().trim()
            val date = dateEditText.text.toString().trim()
            val time = timeEditText.text.toString().trim()
            val note = noteEditText.text.toString().trim()

            // Validation — সব দরকারি ফিল্ড ঠিকমতো পূরণ হয়েছে কিনা চেক করা হচ্ছে
            if (name.isEmpty()) {
                Toast.makeText(this, "Please enter your name", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (phone.isEmpty()) {
                Toast.makeText(this, "Please enter your phone number", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (address.isEmpty()) {
                Toast.makeText(this, "Please enter your address", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (date.isEmpty()) {
                Toast.makeText(this, "Please select a date", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (time.isEmpty()) {
                Toast.makeText(this, "Please select a time", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val currentUser = FirebaseAuth.getInstance().currentUser
            val userId = currentUser?.uid ?: ""

            val booking = Booking(
                serviceName = serviceName,
                name = name,
                phone = phone,
                address = address,
                date = date,
                time = time,
                note = note,
                userId = userId
            )

            val db = FirebaseFirestore.getInstance()

            confirmBookingButton.isEnabled = false

            db.collection("bookings")
                .add(booking)
                .addOnSuccessListener {
                    confirmBookingButton.isEnabled = true

                    // Booking Successful স্ক্রিনে পাঠানো হচ্ছে, সাথে সারসংক্ষেপের তথ্যও
                    val successIntent = Intent(this, BookingSuccessfulActivity::class.java)
                    successIntent.putExtra(BookingSuccessfulActivity.EXTRA_SERVICE_NAME, serviceName)
                    successIntent.putExtra(BookingSuccessfulActivity.EXTRA_DATE, date)
                    successIntent.putExtra(BookingSuccessfulActivity.EXTRA_TIME, time)
                    startActivity(successIntent)
                    finish()
                }
                .addOnFailureListener { exception ->
                    Toast.makeText(this, "Failed: ${exception.message}", Toast.LENGTH_SHORT).show()
                    confirmBookingButton.isEnabled = true
                }
        }
    }
}