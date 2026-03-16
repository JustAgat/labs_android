package com.example.labs

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.snackbar.Snackbar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class FragmentContainerActivity : AppCompatActivity(), ListFragment.OnUserSelectedListener {

    private val TAG_RETROFIT = "RetrofitDemo"

    override fun onCreate(savedInstanceState: Bundle?) {
        // 1. Применяем тему из настроек ДО отрисовки
        val prefs = getSharedPreferences("app_settings", Context.MODE_PRIVATE)
        val isDarkMode = prefs.getBoolean("dark_mode", false)
        if (isDarkMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fragment_container)

        // 2. Настройка Toolbar напрямую
        val toolbar = findViewById<MaterialToolbar>(R.id.toolbar)
        toolbar.menu.clear()
        toolbar.inflateMenu(R.menu.main_menu)

        toolbar.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.action_posts -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, PostListFragment())
                        .addToBackStack(null)
                        .commit()
                    true
                }
                R.id.action_notes -> {
                    startActivity(Intent(this, NotesActivity::class.java))
                    true
                }
                R.id.action_settings -> {
                    startActivity(Intent(this, SettingsActivity::class.java))
                    true
                }
                R.id.action_info -> {
                    showInfoDialog()
                    true
                }
                R.id.action_logout -> {
                    finish()
                    true
                }
                else -> false
            }
        }

        // Приветствие
        val showWelcome = intent.getBooleanExtra("SHOW_WELCOME", false)
        val login = intent.getStringExtra("EXTRA_LOGIN") ?: ""
        if (showWelcome && login.isNotEmpty()) {
            Snackbar.make(findViewById(R.id.fragment_container), "Приветствую, $login!", Snackbar.LENGTH_LONG).show()
        }

        // Загрузка начального фрагмента
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, ListFragment())
                .commit()
        }
    }

    private fun showInfoDialog() {
        AlertDialog.Builder(this)
            .setTitle("О программе")
            .setMessage("Учебное приложение.\n\nФункции:\n- Список пользователей (БД)\n- Личные заметки\n- Настройки темы и шрифта\n- Сетевые запросы (Retrofit)")
            .setPositiveButton("Понятно", null)
            .show()
    }

    override fun onUserSelected(userName: String) {
        val resultIntent = Intent()
        resultIntent.putExtra("SELECTED_USER", userName)
        setResult(Activity.RESULT_OK, resultIntent)

        val detailsFragment = DetailsFragment.newInstance(userName)
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, detailsFragment)
            .addToBackStack(null)
            .commit()
    }
}
