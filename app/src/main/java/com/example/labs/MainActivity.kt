package com.example.labs

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

/**
 * MainActivity - связующее звено всех лабораторных работ.
 */
class MainActivity : AppCompatActivity() {

    private val TAG = "LifecycleDemo"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_constraint)

        val tvTitle = findViewById<TextView>(R.id.tvTitle)
        val etLogin = findViewById<EditText>(R.id.etLogin)
        val btnLogin = findViewById<Button>(R.id.btnLogin)

        btnLogin.setOnClickListener {
            val loginText = etLogin.text.toString()

            if (loginText.isNotEmpty()) {
                // 1. Показываем приветствие ()
                val welcomeMessage = getString(R.string.welcome_message, loginText)
                Toast.makeText(this, welcomeMessage, Toast.LENGTH_SHORT).show()
                tvTitle.text = welcomeMessage

                // 2. Выполняем переход на экран списка ()
                // Intent - это "намерение" перейти от текущего экрана к другому
                val intent = Intent(this, UserListActivity::class.java)
                startActivity(intent)
                
            } else {
                Toast.makeText(this, getString(R.string.error_empty_login), Toast.LENGTH_SHORT).show()
            }
        }
    }
}
