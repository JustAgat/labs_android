package com.example.labs

import android.content.Context
import android.os.Bundle
import android.view.ContextMenu
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

/**
 * Лабораторная работа 8: Контекстное меню для элементов списка.
 */
class ListFragment : Fragment() {

    interface OnUserSelectedListener {
        fun onUserSelected(userName: String)
    }

    private var listener: OnUserSelectedListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnUserSelectedListener) {
            listener = context
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_list, container, false)
        
        val btnBack = view.findViewById<Button>(R.id.btnBackFromList)
        btnBack.setOnClickListener {
            activity?.finish()
        }

        val rvUsers = view.findViewById<RecyclerView>(R.id.rvUsersFragment)
        val userList = mutableListOf<String>()
        for (i in 1..25) {
            userList.add("Пользователь #$i")
        }

        rvUsers.layoutManager = LinearLayoutManager(context)
        rvUsers.adapter = UserAdapter(userList) { selectedUser ->
            listener?.onUserSelected(selectedUser)
        }

        // Регистрируем RecyclerView для контекстного меню (Задание 8.1)
        registerForContextMenu(rvUsers)

        return view
    }

    // 1. Создание контекстного меню (Задание 8.1)
    override fun onCreateContextMenu(menu: ContextMenu, v: View, menuInfo: ContextMenu.ContextMenuInfo?) {
        super.onCreateContextMenu(menu, v, menuInfo)
        activity?.menuInflater?.inflate(R.menu.context_menu, menu)
    }

    // 2. Обработка выбора в контекстном меню (Задание 8.2)
    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.ctx_edit -> {
                Toast.makeText(context, "Редактирование", Toast.LENGTH_SHORT).show()
                true
            }
            R.id.ctx_delete -> {
                Toast.makeText(context, "Удаление", Toast.LENGTH_SHORT).show()
                true
            }
            else -> super.onContextItemSelected(item)
        }
    }
}
