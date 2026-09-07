package com.irfan.cleaningserviceapp

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class RegisterActivity : AppCompatActivity() {

    private lateinit var nameInputLayout: TextInputLayout
    private lateinit var emailInputLayout: TextInputLayout
    private lateinit var passwordInputLayout: TextInputLayout
    private lateinit var confirmPasswordInputLayout: TextInputLayout

    private lateinit var nameEditText: TextInputEditText
    private lateinit var emailEditText: TextInputEditText
    private lateinit var passwordEditText: TextInputEditText
    private lateinit var confirmPasswordEditText: TextInputEditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        // View গুলো খুঁজে বের করা
        nameInputLayout = findViewById(R.id.nameInputLayout)
        emailInputLayout = findViewById(R.id.registerEmailInputLayout)
        passwordInputLayout = findViewById(R.id.registerPasswordInputLayout)
        confirmPasswordInputLayout = findViewById(R.id.confirmPasswordInputLayout)

        nameEditText = findViewById(R.id.nameEditText)
        emailEditText = findViewById(R.id.registerEmailEditText)
        passwordEditText = findViewById(R.id.registerPasswordEditText)
        confirmPasswordEditText = findViewById(R.id.confirmPasswordEditText)

        val registerButton = findViewById<com.google.android.material.button.MaterialButton>(R.id.registerButton)
        val loginRedirectText = findViewById<android.widget.TextView>(R.id.loginRedirectText)

        // Register বাটনে ক্লিক করলে
        registerButton.setOnClickListener {
            if (validateInputs()) {
                // এখানে পরে Firebase Register logic বসবে
                Toast.makeText(this, "Validation passed! (Firebase register coming soon)", Toast.LENGTH_SHORT).show()
            }
        }

        // Login redirect ক্লিক করলে
        loginRedirectText.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }

    private fun validateInputs(): Boolean {
        var isValid = true

        val name = nameEditText.text.toString().trim()
        val email = emailEditText.text.toString().trim()
        val password = passwordEditText.text.toString().trim()
        val confirmPassword = confirmPasswordEditText.text.toString().trim()

        // Full Name চেক
        if (name.isEmpty()) {
            nameInputLayout.error = "Full name is required"
            isValid = false
        } else {
            nameInputLayout.error = null
        }

        // Email চেক
        if (email.isEmpty()) {
            emailInputLayout.error = "Email is required"
            isValid = false
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailInputLayout.error = "Enter a valid email address"
            isValid = false
        } else {
            emailInputLayout.error = null
        }

        // Password চেক
        if (password.isEmpty()) {
            passwordInputLayout.error = "Password is required"
            isValid = false
        } else if (password.length < 6) {
            passwordInputLayout.error = "Password must be at least 6 characters"
            isValid = false
        } else {
            passwordInputLayout.error = null
        }

        // Confirm Password চেক (Password-এর সাথে মিলছে কিনা)
        if (confirmPassword.isEmpty()) {
            confirmPasswordInputLayout.error = "Please confirm your password"
            isValid = false
        } else if (confirmPassword != password) {
            confirmPasswordInputLayout.error = "Passwords do not match"
            isValid = false
        } else {
            confirmPasswordInputLayout.error = null
        }

        return isValid
    }
}