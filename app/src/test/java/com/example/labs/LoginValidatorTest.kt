package com.example.labs

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

/**
 * Лабораторная работа 17: Unit-тест для LoginValidator.
 */
class LoginValidatorTest {

    @Test
    fun loginValidator_CorrectLoginAndPassword_ReturnsTrue() {
        // Логин >= 3 символов, пароль >= 6 символов
        assertTrue(LoginValidator.validate("admin", "123456"))
    }

    @Test
    fun loginValidator_ShortLogin_ReturnsFalse() {
        assertFalse(LoginValidator.validate("ad", "123456"))
    }

    @Test
    fun loginValidator_ShortPassword_ReturnsFalse() {
        assertFalse(LoginValidator.validate("admin", "12345"))
    }

    @Test
    fun loginValidator_EmptyFields_ReturnsFalse() {
        assertFalse(LoginValidator.validate("", ""))
    }
}
