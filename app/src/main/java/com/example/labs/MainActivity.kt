package com.example.labs

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private val getUserResult = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val selectedUser = result.data?.getStringExtra("SELECTED_USER")
            Toast.makeText(this, "Выбран пользователь: $selectedUser", Toast.LENGTH_SHORT).show()
        }
    }

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
                // Переход на страницу со списком (FragmentContainerActivity)
                val intent = Intent(this, FragmentContainerActivity::class.java)
                intent.putExtra("EXTRA_LOGIN", loginText)
                
                // Флаг для вывода приветствия на следующем экране
                intent.putExtra("SHOW_WELCOME", true)
                
                getUserResult.launch(intent)
            } else {
                Toast.makeText(this, "Пожалуйста, введите логин и пароль", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
