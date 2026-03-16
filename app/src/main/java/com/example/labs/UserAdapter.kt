package com.example.labs

import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

/**
 * Адаптер - это "мост" между данными и списком на экране.
 * Он знает, как превратить один элемент данных в одну строку списка.
 */
class UserAdapter(
    private val users: MutableList<UserData>,
    private val textSizeBonus: Int,
    private val onUpdate: (UserData) -> Unit,
    private val onDelete: (Int) -> Unit
) : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    // ViewHolder хранит ссылки на элементы интерфейса одной строки
    class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val etName: EditText = itemView.findViewById(R.id.etUserName)
        val btnEdit: TextView = itemView.findViewById(R.id.btnEditUser)
        val btnSave: TextView = itemView.findViewById(R.id.btnSaveUser)
        val btnDelete: TextView = itemView.findViewById(R.id.btnDeleteUser)
    }

    // Создает новый макет строки (вызывается системой)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false)
        return UserViewHolder(view)
    }

    // Привязывает данные к элементам интерфейса (вызывается системой)
    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = users[position]
        holder.etName.setText(user.name)
        
        holder.etName.setTextSize(TypedValue.COMPLEX_UNIT_SP, (16 + textSizeBonus).toFloat())

        // Редактирование (Желтые точки - теперь больше и жирнее)
        holder.btnEdit.setOnClickListener {
            holder.etName.isEnabled = true
            
            // Если это стандартное имя "Новый пользователь", стираем его (Задание пользователя)
            if (holder.etName.text.toString() == "Новый пользователь") {
                holder.etName.text.clear()
            }
            
            holder.etName.requestFocus()
            holder.btnEdit.visibility = View.GONE
            holder.btnSave.visibility = View.VISIBLE
        }

        holder.btnSave.setOnClickListener {
            val newName = holder.etName.text.toString()
            user.name = if (newName.isEmpty()) "Без имени" else newName
            onUpdate(user)
            
            holder.etName.isEnabled = false
            holder.btnSave.visibility = View.GONE
            holder.btnEdit.visibility = View.VISIBLE
        }

        holder.btnDelete.setOnClickListener {
            onDelete(user.id)
        }

    }

    // Возвращает общее количество элементов в списке
    override fun getItemCount(): Int = users.size
}
