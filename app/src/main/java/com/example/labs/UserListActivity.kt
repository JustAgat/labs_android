package com.example.labs

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

/**
 * Новое Activity для отображения списка.
 */
class UserListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_list)

        // 1. Инициализируем RecyclerView из макета
        val rvUsers = findViewById<RecyclerView>(R.id.rvUsers)

        // 2. Тестовые данные (список имен)
        val userList = listOf(
            "Иван ватафа", "Мария Пепе", "Александр Шнене",
            "Елена Сиа", "Дмитрий джумба", "Анна Попа",
            "Сергей Васильев", "Ольга Соколова", "Андрей Михайлов",
            "Татьяна Новикова", "Игорь Федоров", "Наталья Морозова"
        )

        // 3. Создаем адаптер и передаем в него данные
        val adapter = UserAdapter(userList)

        // 4. Устанавливаем адаптер в RecyclerView
        rvUsers.adapter = adapter

        // 5. Указываем способ расположения элементов (вертикальный список)
        rvUsers.layoutManager = LinearLayoutManager(this)
    }
}
