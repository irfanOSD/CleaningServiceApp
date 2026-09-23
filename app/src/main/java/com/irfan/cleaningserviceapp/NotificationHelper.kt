package com.irfan.cleaningserviceapp

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat

object NotificationHelper {

    const val CHANNEL_ID = "booking_status_channel"
    private const val CHANNEL_NAME = "Booking Status Updates"
    private const val CHANNEL_DESCRIPTION = "তোমার বুকিং-এর status বদলালে notification দেখাবে"

    fun createNotificationChannel(context: Context) {
        // Notification Channel শুধু Android 8.0 (API 26) ও তার পরের ভার্সনে লাগে
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = CHANNEL_DESCRIPTION
            }

            val notificationManager = context.getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
        }
    }

    // status অনুযায়ী কাস্টমার-ফ্রেন্ডলি মেসেজ তৈরি করে
    fun messageForStatus(serviceName: String, status: String): String {
        return when (status) {
            "Accepted" -> "Your booking for $serviceName has been accepted."
            "Completed" -> "Your $serviceName service has been completed."
            "Cancelled" -> "Your booking for $serviceName has been cancelled."
            else -> "Your booking for $serviceName is now $status."
        }
    }

    fun showStatusNotification(context: Context, notificationId: Int, serviceName: String, status: String) {
        // Android 13+ এ permission না থাকলে notification দেখানো যাবে না (crash এড়াতে চেক করা হচ্ছে)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            val hasPermission = ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED

            if (!hasPermission) return
        }

        val message = messageForStatus(serviceName, status)

        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setContentTitle("Booking Update")
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .build()

        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(notificationId, notification)
    }
}