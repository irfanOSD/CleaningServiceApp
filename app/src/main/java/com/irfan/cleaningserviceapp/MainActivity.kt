package com.irfan.cleaningserviceapp

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    // Notification permission-এর ফলাফল (ইউজার Allow/Deny করলে) এখানে ধরা হয়
    private val requestNotificationPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { _ ->
            // ইউজার Allow করুক বা Deny করুক, অ্যাপ স্বাভাবিকভাবেই চলবে।
            // Deny করলে শুধু notification দেখানো যাবে না, অ্যাপ ক্র্যাশ করবে না।
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Notification Channel তৈরি + Permission চাওয়া (STEP 14)
        NotificationHelper.createNotificationChannel(this)
        askNotificationPermission()

        val bottomNavigation = findViewById<BottomNavigationView>(R.id.bottomNavigation)

        // অ্যাপ প্রথম খুললে ডিফল্টভাবে HomeFragment দেখানো
        if (savedInstanceState == null) {
            loadFragment(HomeFragment())
        }

        // Bottom Navigation-এ ট্যাপ করলে সঠিক Fragment দেখানো
        bottomNavigation.setOnItemSelectedListener { menuItem ->
            val selectedFragment: Fragment = when (menuItem.itemId) {
                R.id.nav_home -> HomeFragment()
                R.id.nav_support -> SupportFragment()
                R.id.nav_bookings -> BookingsFragment()
                R.id.nav_profile -> ProfileFragment()
                else -> HomeFragment()
            }
            loadFragment(selectedFragment)
            true
        }
    }

    private fun askNotificationPermission() {
        // এই permission শুধু Android 13 (API 33) ও তার পরের ভার্সনে লাগে
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            val alreadyGranted = ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED

            if (!alreadyGranted) {
                requestNotificationPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }
    }

    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .commit()
    }
}