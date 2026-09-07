package com.irfan.cleaningserviceapp

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class LoginActivity : AppCompatActivity() {

    private lateinit var emailInputLayout: TextInputLayout
    private lateinit var passwordInputLayout: TextInputLayout
    private lateinit var emailEditText: TextInputEditText
    private lateinit var passwordEditText: TextInputEditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

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
                // এখানে পরে Firebase Login logic বসবে
                Toast.makeText(this, "Validation passed! (Firebase login coming soon)", Toast.LENGTH_SHORT).show()
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