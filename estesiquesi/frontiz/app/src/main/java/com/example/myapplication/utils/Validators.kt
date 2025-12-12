package com.example.myapplication.utils

object Validators {

    fun isValidEmail(email: String): Boolean {
        // Simple regex for unit testing
        val emailRegex = "^.+@.+\\..+$"
        return email.matches(emailRegex.toRegex())
    }

    fun isValidPassword(password: String): Boolean {
        return password.length >= 4
    }

    fun isValidRUT(rut: String): Boolean {
        // Basic RUT validation logic (simplified for this example)
        // Format: 12345678-9
        val rutClean = rut.replace(".", "").replace("-", "")
        if (rutClean.length < 2) return false
        val dv = rutClean.last()
        val body = rutClean.substring(0, rutClean.length - 1)
        
        return try {
            val num = body.toInt()
            // Here we could implement full Mod11 check, but for now we check structure
            true
        } catch (e: NumberFormatException) {
            false
        }
    }
}
