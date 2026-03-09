package com.example.a2faapplication

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.a2faapplication.model.LoginRequest
import com.example.a2faapplication.services.RetrofitClient
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val usernameEditText = findViewById<TextInputEditText>(R.id.usernameEditText)
        val passwordEditText = findViewById<TextInputEditText>(R.id.passwordEditText)
        val loginButton = findViewById<Button>(R.id.loginButton)
        val progressBar = findViewById<ProgressBar>(R.id.progressBar)

        loginButton.setOnClickListener {
            val email = usernameEditText.text.toString()
            val password = passwordEditText.text.toString()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please enter email and password", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            login(email, password, progressBar)
        }
    }

    private fun login(email: String, password: String, progressBar: ProgressBar) {
        progressBar.visibility = View.VISIBLE
        
        lifecycleScope.launch {
            try {
                val request = LoginRequest(email, password)
                val response = RetrofitClient.apiService.login(request)
                
                progressBar.visibility = View.GONE
                
                if (response.isSuccessful && response.body() != null) {
                    val loginResponse = response.body()!!
                    Toast.makeText(this@MainActivity, "Login successful! 2FA: ${loginResponse.twoFAEnabled}", Toast.LENGTH_LONG).show()
                    // Handle navigation or 2FA flow here
                } else {
                    Toast.makeText(this@MainActivity, "Login failed: ${response.message()}", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                progressBar.visibility = View.GONE
                Toast.makeText(this@MainActivity, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }
}