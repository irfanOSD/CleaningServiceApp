package com.irfan.cleaningserviceapp

import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class ForgotPasswordActivity : AppCompatActivity() {

    private lateinit var resetEmailInputLayout: TextInputLayout
    private lateinit var resetEmailEditText: TextInputEditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)

        // View গুলো খুঁজে বের করা
        resetEmailInputLayout = findViewById(R.id.resetEmailInputLayout)
        resetEmailEditText = findViewById(R.id.resetEmailEditText)

        val resetPasswordButton = findViewById<com.google.android.material.button.MaterialButton>(R.id.resetPasswordButton)
        val backToLoginText = findViewById<android.widget.TextView>(R.id.backToLoginText)

        // Reset Password বাটনে ক্লিক করলে
        resetPasswordButton.setOnClickListener {
            if (validateEmail()) {
                // এখানে পরে Firebase Password Reset logic বসবে
                Toast.makeText(this, "Validation passed! (Firebase reset coming soon)", Toast.LENGTH_SHORT).show()
            }
        }

        // Back to Login ক্লিক করলে
        backToLoginText.setOnClickListener {
            finish() // আগের স্ক্রিনে (Login) ফিরে যাবে
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