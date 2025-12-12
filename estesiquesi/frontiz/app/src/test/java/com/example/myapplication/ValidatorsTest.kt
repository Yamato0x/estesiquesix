package com.example.myapplication

import com.example.myapplication.utils.Validators
import org.junit.Assert.*
import org.junit.Test

class ValidatorsTest {

    @Test
    fun emailValidator_CorrectEmailSimple_ReturnsTrue() {
        assertTrue(Validators.isValidEmail("name@email.com"))
    }

    @Test
    fun emailValidator_CorrectEmailSubDomain_ReturnsTrue() {
        assertTrue(Validators.isValidEmail("name@email.co.uk"))
    }

    @Test
    fun emailValidator_InvalidEmailNoTld_ReturnsFalse() {
        assertFalse(Validators.isValidEmail("name@email"))
    }



    @Test
    fun emailValidator_InvalidEmailNoUsername_ReturnsFalse() {
        assertFalse(Validators.isValidEmail("@email.com"))
    }

    @Test
    fun emailValidator_EmptyString_ReturnsFalse() {
        assertFalse(Validators.isValidEmail(""))
    }

    @Test
    fun passwordValidator_ValidPassword_ReturnsTrue() {
        assertTrue(Validators.isValidPassword("1234"))
        assertTrue(Validators.isValidPassword("password"))
    }

    @Test
    fun passwordValidator_ShortPassword_ReturnsFalse() {
        assertFalse(Validators.isValidPassword("123"))
        assertFalse(Validators.isValidPassword(""))
    }

    @Test
    fun rutValidator_ValidFormat_ReturnsTrue() {
       // Simplified logic test
       assertTrue(Validators.isValidRUT("12345678-9"))
       assertTrue(Validators.isValidRUT("1-9"))
    }
    
    @Test
    fun rutValidator_InvalidFormat_ReturnsFalse() {
       assertFalse(Validators.isValidRUT("k"))
       assertFalse(Validators.isValidRUT("-"))
    }
}
