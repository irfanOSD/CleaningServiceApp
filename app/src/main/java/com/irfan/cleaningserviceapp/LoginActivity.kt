package com.irfan.cleaningserviceapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class LoginActivity : AppCompatActivity() {

    private lateinit var emailInputLayout: TextInputLayout
    private lateinit var passwordInputLayout: TextInputLayout
    private lateinit var emailEditText: TextInputEditText
    private lateinit var passwordEditText: TextInputEditText

    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Firebase Auth ও Firestore ইনস্ট্যান্স নেওয়া হচ্ছে
        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        // View গুলো খুঁজে বের করা
        emailInputLayout = findViewById(R.id.emailInputLayout)
        passwordInputLayout = findViewById(R.id.passwordInputLayout)
        emailEditText = findViewById(R.id.emailEditText)
        passwordEditText = findViewById(R.id.passwordEditText)

        val loginButton = findViewById<com.google.android.material.button.MaterialButton>(R.id.loginButton)
        val forgotPasswordText = findViewById<android.widget.TextView>(R.id.forgotPasswordText)
        val registerRedirectText = findViewById<android.widget.TextView>(R.id.registerRedirectText)

        // Login বাটনে ক্লিক করলে
        loginButton.setOnClickListener {
            if (validateInputs()) {
                loginUser(loginButton)
            }
        }

        // Forgot Password ক্লিক করলে
        forgotPasswordText.setOnClickListener {
            startActivity(Intent(this, ForgotPasswordActivity::class.java))
        }

        // Register redirect ক্লিক করলে
        registerRedirectText.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }

    private fun loginUser(loginButton: com.google.android.material.button.MaterialButton) {
        val email = emailEditText.text.toString().trim()
        val password = passwordEditText.text.toString().trim()

        // বাটন সাময়িকভাবে বন্ধ রাখা হচ্ছে, যাতে ইউজার একবারের বেশি ক্লিক করতে না পারে
        loginButton.isEnabled = false

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Login সফল হয়েছে — এবার চেক করা হচ্ছে ইউজার Admin কিনা
                    checkIfAdminAndNavigate(loginButton)
                } else {
                    // Login ব্যর্থ হয়েছে
                    loginButton.isEnabled = true
                    Toast.makeText(
                        this,
                        "Login failed: ${task.exception?.message}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
    }

    private fun checkIfAdminAndNavigate(loginButton: com.google.android.material.button.MaterialButton) {
        val uid = auth.currentUser?.uid

        if (uid == null) {
            loginButton.isEnabled = true
            Toast.makeText(this, "Something went wrong. Please try again.", Toast.LENGTH_SHORT).show()
            return
        }

        db.collection("admins").document(uid).get()
            .addOnSuccessListener { document ->
                loginButton.isEnabled = true

                if (document.exists()) {
                    // এই ইউজার Admin — Admin Dashboard-এ পাঠানো হচ্ছে
                    startActivity(Intent(this, AdminDashboardActivity::class.java))
                } else {
                    // সাধারণ Customer — MainActivity-তে পাঠানো হচ্ছে
                    startActivity(Intent(this, MainActivity::class.java))
                }
                finish()
            }
            .addOnFailureListener { e ->
                loginButton.isEnabled = true
                Log.e("LoginActivity", "Admin check failed", e)

                // চেক করতে ব্যর্থ হলেও ইউজারকে আটকে না রেখে সাধারণ MainActivity-তে পাঠানো হচ্ছে
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
    }

    private fun validateInputs(): Boolean {
        var isValid = true

        val email = emailEditText.text.toString().trim()
        val password = passwordEditText.text.toString().trim()

        // Email ফাঁকা কিনা চেক
        if (email.isEmpty()) {
            emailInputLayout.error = "Email is required"
            isValid = false
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailInputLayout.error = "Enter a valid email address"
            isValid = false
        } else {
            emailInputLayout.error = null
        }

        // Password ফাঁকা কিনা এবং length চেক
        if (password.isEmpty()) {
            passwordInputLayout.error = "Password is required"
            isValid = false
        } else if (password.length < 6) {
            passwordInputLayout.error = "Password must be at least 6 characters"
            isValid = false
        } else {
            passwordInputLayout.error = null
        }

        return isValid
    }
}