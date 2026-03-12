package com.example.labs

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

/**
 * Обновленный адаптер с поддержкой кликов для возврата результата.
 * Мы добавляем лямбда-выражение onItemClick, которое будет срабатывать при нажатии на строку.
 */
class UserAdapter(
    private val users: List<String>,
    private val onItemClick: (String) -> Unit // Функция-слушатель клика
) : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvUserName: TextView = itemView.findViewById(R.id.tvUserName)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_user, parent, false)
        return UserViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val userName = users[position]
        holder.tvUserName.text = userName

        // Устанавливаем слушатель клика на всю строку списка
        holder.itemView.setOnClickListener {
            onItemClick(userName)
        }
    }

    override fun getItemCount(): Int = users.size
}
