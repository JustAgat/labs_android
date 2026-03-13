package com.example.labs

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

/**
<<<<<<< Updated upstream
 * Новое Activity для отображения списка.
=======
 * Лабораторная работа 6: Навигация между Activity.
 * Файл обновлен для совместимости с новым UserAdapter из ЛР12.
>>>>>>> Stashed changes
 */
class UserListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_list)

<<<<<<< Updated upstream
        // 1. Инициализируем RecyclerView из макета
        val rvUsers = findViewById<RecyclerView>(R.id.rvUsers)

        // 2. Тестовые данные (список имен)
        val userList = listOf(
            "Иван ватафа", "Мария Пепе", "Александр Шнене",
            "Елена Сиа", "Дмитрий джумба", "Анна Попа",
            "Сергей Васильев", "Ольга Соколова", "Андрей Михайлов",
            "Татьяна Новикова", "Игорь Федоров", "Наталья Морозова"
=======
        val login = intent.getStringExtra("EXTRA_LOGIN") ?: "Гость"
        title = "Список для: $login"

        val rvUsers = findViewById<RecyclerView>(R.id.rvUsers)

        // Конвертируем список строк в список объектов UserData для совместимости
        val userStrings = listOf(
            "Иван Иванов", "Мария Петрова", "Александр Сидоров",
            "Елена Смирнова", "Дмитрий Кузнецов", "Анна Попова"
>>>>>>> Stashed changes
        )
        val userList = userStrings.mapIndexed { index, name -> UserData(index, name) }.toMutableList()

<<<<<<< Updated upstream
        // 3. Создаем адаптер и передаем в него данные
        val adapter = UserAdapter(userList)
=======
        // Передаем все 4 параметра, требуемые новым конструктором UserAdapter
        val adapter = UserAdapter(
            userList, 
            0,
            onUpdate = { /* Ничего не делаем в старой лабе */ },
            onDelete = { /* Ничего не делаем в старой лабе */ }
        )
>>>>>>> Stashed changes

        // 4. Устанавливаем адаптер в RecyclerView
        rvUsers.adapter = adapter

        // 5. Указываем способ расположения элементов (вертикальный список)
        rvUsers.layoutManager = LinearLayoutManager(this)
    }
}
