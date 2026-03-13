package com.example.labs

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

/**
 * Лабораторная работа 6: Навигация между Activity.
 */
class UserListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_list)

        val login = intent.getStringExtra("EXTRA_LOGIN") ?: "Гость"
        title = "Список для: $login"

        val rvUsers = findViewById<RecyclerView>(R.id.rvUsers)

        // Подготовка данных
        val userStrings = listOf(
            "Иван Иванов", "Мария Петрова", "Александр Сидоров",
            "Елена Смирнова", "Дмитрий Кузнецов", "Анна Попова"
        )
        val userList = userStrings.mapIndexed { index, name -> UserData(index, name) }.toMutableList()

        // Использование актуального конструктора UserAdapter (из ЛР12)
        val adapter = UserAdapter(
            userList, 
            0,
            onUpdate = { /* Логика обновления для старой лабы не требуется */ },
            onDelete = { /* Логика удаления для старой лабы не требуется */ }
        )

        rvUsers.adapter = adapter
        rvUsers.layoutManager = LinearLayoutManager(this)
    }
}
