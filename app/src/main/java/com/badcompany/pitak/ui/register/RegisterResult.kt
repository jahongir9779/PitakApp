package com.badcompany.pitak.ui.register

/**
 * Authentication result : success (user details) or error message.
 */
data class RegisterResult(
    val success: RegisterUserView? = null,
    val error: Int? = null
)