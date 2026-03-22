package com.example.labs

/**
 * Класс для демонстрации Unit-тестирования.
 */
object LoginValidator {
    /**
     * Простой метод для проверки логина и пароля.
     */
    fun validate(login: String, password: String): Boolean {
        return login.length >= 3 && password.length >= 6
    }
}
