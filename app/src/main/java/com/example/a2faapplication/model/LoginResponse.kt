package com.example.a2faapplication.model

data class LoginResponse(
    val userId: String,
    val twoFAEnabled: Boolean,
    val qrCodeUrl: String?
)
