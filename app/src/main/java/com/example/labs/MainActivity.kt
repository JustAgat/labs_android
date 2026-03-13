package com.example.labs

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

/**
 * Главный экран входа.
 * Отсюда мы переходим в FragmentContainerActivity, где реализованы все основные лабы (7-12).
 */
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_constraint)

        val etLogin = findViewById<EditText>(R.id.etLogin)
        val etPassword = findViewById<EditText>(R.id.etPassword) // Добавили поле пароля для проверки
        val btnLogin = findViewById<Button>(R.id.btnLogin)

        btnLogin.setOnClickListener {
            val loginText = etLogin.text.toString()
            val passwordText = etPassword.text.toString()

            if (loginText.isNotEmpty() && passwordText.isNotEmpty()) {
                // ПЕРЕХОД НА ГЛАВНУЮ ЧАСТЬ ПРИЛОЖЕНИЯ (Фрагменты + БД + Меню)
                val intent = Intent(this, FragmentContainerActivity::class.java)
                intent.putExtra("EXTRA_LOGIN", loginText)
                intent.putExtra("SHOW_WELCOME", true)
                startActivity(intent)
            } else {
                Toast.makeText(this, "Введите логин и пароль", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
