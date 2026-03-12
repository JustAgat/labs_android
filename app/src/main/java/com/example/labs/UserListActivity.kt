package com.example.labs

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

/**
 * Лабораторная работа 6: Навигация между Activity. Intent
 */
class UserListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_list)

        // 1. Получаем данные, переданные с первого экрана (Задание 6.2)
        val login = intent.getStringExtra("EXTRA_LOGIN") ?: "Гость"
        
        // Показываем переданный логин в заголовке или через Toast (для примера выведем в лог/заголовок)
        title = "Список для: $login"

        val rvUsers = findViewById<RecyclerView>(R.id.rvUsers)

        val userList = listOf(
            "Иван Иванов", "Мария Петрова", "Александр Сидоров",
            "Елена Смирнова", "Дмитрий Кузнецов", "Анна Попова",
            "Сергей Васильев", "Ольга Соколова", "Андрей Михайлов",
            "Татьяна Новикова", "Игорь Федоров", "Наталья Морозова"
        )

        // 3. Создаем адаптер с обработкой клика (Задание 6.3)
        val adapter = UserAdapter(userList) { selectedUser ->
            // При клике на элемент списка возвращаем его как результат
            val resultIntent = Intent()
            resultIntent.putExtra("SELECTED_USER", selectedUser)
            
            // Устанавливаем результат и закрываем экран
            setResult(RESULT_OK, resultIntent)
            finish() 
        }

        rvUsers.adapter = adapter
        rvUsers.layoutManager = LinearLayoutManager(this)
    }
}
