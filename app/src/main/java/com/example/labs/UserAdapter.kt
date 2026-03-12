package com.example.labs

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

/**
 * Адаптер - это "мост" между данными и списком на экране.
 * Он знает, как превратить один элемент данных в одну строку списка.
 */
class UserAdapter(private val users: List<String>) : 
    RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    // ViewHolder хранит ссылки на элементы интерфейса одной строки
    class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvUserName: TextView = itemView.findViewById(R.id.tvUserName)
    }

    // Создает новый макет строки (вызывается системой)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_user, parent, false)
        return UserViewHolder(view)
    }

    // Привязывает данные к элементам интерфейса (вызывается системой)
    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.tvUserName.text = users[position]
    }

    // Возвращает общее количество элементов в списке
    override fun getItemCount(): Int = users.size
}
