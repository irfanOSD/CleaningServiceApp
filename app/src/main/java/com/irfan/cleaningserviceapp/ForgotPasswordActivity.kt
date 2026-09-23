package com.irfan.cleaningserviceapp

import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth

class ForgotPasswordActivity : AppCompatActivity() {

    private lateinit var resetEmailInputLayout: TextInputLayout
    private lateinit var resetEmailEditText: TextInputEditText

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)

        // Firebase Auth ইনস্ট্যান্স নেওয়া হচ্ছে
        auth = FirebaseAuth.getInstance()

        // View গুলো খুঁজে বের করা
        resetEmailInputLayout = findViewById(R.id.resetEmailInputLayout)
        resetEmailEditText = findViewById(R.id.resetEmailEditText)

        val resetPasswordButton = findViewById<com.google.android.material.button.MaterialButton>(R.id.resetPasswordButton)
        val backToLoginText = findViewById<android.widget.TextView>(R.id.backToLoginText)

        // Reset Password বাটনে ক্লিক করলে
        resetPasswordButton.setOnClickListener {
            if (validateEmail()) {
                sendResetEmail(resetPasswordButton)
            }
        }

        // Back to Login ক্লিক করলে
        backToLoginText.setOnClickListener {
            finish() // আগের স্ক্রিনে (Login) ফিরে যাবে
        }
    }

    private fun sendResetEmail(resetPasswordButton: com.google.android.material.button.MaterialButton) {
        val email = resetEmailEditText.text.toString().trim()

        // বাটন সাময়িকভাবে বন্ধ রাখা হচ্ছে, যাতে ইউজার একবারের বেশি ক্লিক করতে না পারে
        resetPasswordButton.isEnabled = false

        auth.sendPasswordResetEmail(email)
            .addOnCompleteListener { task ->
                resetPasswordButton.isEnabled = true

                if (task.isSuccessful) {
                    Toast.makeText(
                        this,
                        "Password reset email sent! Please check your inbox.",
                        Toast.LENGTH_LONG
                    ).show()
                    finish() // Login স্ক্রিনে ফিরে যাওয়া
                } else {
                    Toast.makeText(
                        this,
                        "Failed: ${task.exception?.message}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
    }

    private fun validateEmail(): Boolean {
        val email = resetEmailEditText.text.toString().trim()

        if (email.isEmpty()) {
            resetEmailInputLayout.error = "Email is required"
            return false
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            resetEmailInputLayout.error = "Enter a valid email address"
            return false
        }

        resetEmailInputLayout.error = null
        return true
    }
}