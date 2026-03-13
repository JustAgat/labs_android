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
 * Лабораторная работа 12: Реализация списка пользователей с БД (CRUD).
 */
class ListFragment : Fragment() {

    interface OnUserSelectedListener {
        fun onUserSelected(userName: String)
    }

    private var listener: OnUserSelectedListener? = null
    private lateinit var rvUsers: RecyclerView
    private lateinit var dbHelper: UserDBHelper

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
        
        dbHelper = UserDBHelper(requireContext())

        val btnBack = view.findViewById<Button>(R.id.btnBackFromList)
        btnBack.setOnClickListener {
            activity?.finish()
        }

        val btnAdd = view.findViewById<Button>(R.id.btnAddUser)
        btnAdd.setOnClickListener {
            dbHelper.addUser("Новый пользователь")
            applySettingsAndRefresh()
        }

        rvUsers = view.findViewById<RecyclerView>(R.id.rvUsersFragment)
        rvUsers.layoutManager = LinearLayoutManager(context)

        applySettingsAndRefresh()

        return view
    }

    override fun onResume() {
        super.onResume()
        applySettingsAndRefresh()
    }

    private fun applySettingsAndRefresh() {
        if (!::rvUsers.isInitialized) return

        val prefs = requireActivity().getSharedPreferences("app_settings", Context.MODE_PRIVATE)
        val isReverse = prefs.getBoolean("reverse_sort", false)
        val textSizeBonus = prefs.getInt("text_size_bonus", 0)

        val userList = dbHelper.getAllUsers()

        if (isReverse) {
            userList.reverse()
        }

        rvUsers.adapter = UserAdapter(
            userList, 
            textSizeBonus,
            onUpdate = { user -> 
                dbHelper.updateUser(user.id, user.name)
            },
            onDelete = { id -> 
                dbHelper.deleteUser(id)
                applySettingsAndRefresh() 
            }
        )
    }

    override fun onCreateContextMenu(menu: ContextMenu, v: View, menuInfo: ContextMenu.ContextMenuInfo?) {
        super.onCreateContextMenu(menu, v, menuInfo)
        activity?.menuInflater?.inflate(R.menu.context_menu, menu)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.ctx_edit -> {
                Toast.makeText(context, "Редактирование через контекстное меню", Toast.LENGTH_SHORT).show()
                true
            }
            R.id.ctx_delete -> {
                Toast.makeText(context, "Удаление через контекстное меню", Toast.LENGTH_SHORT).show()
                true
            }
            else -> super.onContextItemSelected(item)
        }
    }
}
