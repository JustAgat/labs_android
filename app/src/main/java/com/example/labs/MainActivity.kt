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
        val etPassword = findViewById<EditText>(R.id.etPassword) 
        val btnLogin = findViewById<Button>(R.id.btnLogin)

        btnLogin.setOnClickListener {
            val loginText = etLogin.text.toString()
            val passwordText = etPassword.text.toString()

            if (loginText.isNotEmpty() && passwordText.isNotEmpty()) {
                val intent = Intent(this, FragmentContainerActivity::class.java)
                intent.putExtra("EXTRA_LOGIN", loginText)
                intent.putExtra("SHOW_WELCOME", true)
                startActivity(intent)
            } else {
                Toast.makeText(this, "Введите логин и пароль", Toast.LENGTH_SHORT).show()
            }
        }
    }

        override fun onStart() {
        super.onStart()
        println("MY_LOG: Экран Входа - Я запускаюсь (onStart)")
    }

    override fun onResume() {
        super.onResume()
        println("MY_LOG: Экран Входа - Я на экране! (onResume)")
    }

    override fun onPause() {
        super.onPause()
        println("MY_LOG: Экран Входа - Меня перекрыли (onPause)")
    }

    override fun onStop() {
        super.onStop()
        println("MY_LOG: Экран Входа - Я спрятался (onStop)")
    }
}
