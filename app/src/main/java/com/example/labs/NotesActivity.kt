package com.example.labs

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

/**
 * Лабораторная работа 11: Работа с базой данных SQLite.
 * Добавлена возможность удаления записей через кнопку-крестик.
 */
class NotesActivity : AppCompatActivity() {

    private lateinit var dbHelper: NotesDBHelper
    private lateinit var etNoteContent: EditText
    private lateinit var rvNotes: RecyclerView
    private val notesList = mutableListOf<Note>()

    override fun onCreate(savedInstanceState: Bundle?) {
        val prefs = getSharedPreferences("app_settings", Context.MODE_PRIVATE)
        val isDarkMode = prefs.getBoolean("dark_mode", false)
        if (isDarkMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notes)

        dbHelper = NotesDBHelper(this)

        etNoteContent = findViewById(R.id.etNoteContent)
        rvNotes = findViewById(R.id.rvNotesList)
        val btnSave = findViewById<Button>(R.id.btnSaveNote)
        val btnBack = findViewById<Button>(R.id.btnBackFromNotes)

        rvNotes.layoutManager = LinearLayoutManager(this)
        refreshNotesList()

        btnSave.setOnClickListener {
            val text = etNoteContent.text.toString()
            if (text.isNotEmpty()) {
                dbHelper.addNote("Заметка", text)
                etNoteContent.text.clear()
                refreshNotesList()
                Toast.makeText(this, "Сохранено", Toast.LENGTH_SHORT).show()
            }
        }

        btnBack.setOnClickListener { finish() }
    }

    private fun refreshNotesList() {
        notesList.clear()
        notesList.addAll(dbHelper.getAllNotes())
        updateAdapter()
    }

    private fun updateAdapter() {
        rvNotes.adapter = object : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.item_note, parent, false)
                return object : RecyclerView.ViewHolder(view) {}
            }

            override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
                val tvText = holder.itemView.findViewById<TextView>(R.id.tvNoteItem)
                val btnDelete = holder.itemView.findViewById<TextView>(R.id.btnDeleteNote)
                val currentNote = notesList[position]
                
                tvText.text = currentNote.description
                
                // Нажатие на текст — редактирование
                tvText.setOnClickListener {
                    etNoteContent.setText(currentNote.description)
                    dbHelper.deleteNote(currentNote.id)
                    refreshNotesList()
                }

                // Нажатие на крестик — просто удаление (Запрос пользователя)
                btnDelete.setOnClickListener {
                    dbHelper.deleteNote(currentNote.id)
                    refreshNotesList()
                    Toast.makeText(this@NotesActivity, "Заметка удалена", Toast.LENGTH_SHORT).show()
                }
            }

            override fun getItemCount(): Int = notesList.size
        }
    }
}
