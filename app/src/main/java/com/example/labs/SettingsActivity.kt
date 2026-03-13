package com.example.labs

import android.content.Context
import android.os.Bundle
import android.widget.Button
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.google.android.material.switchmaterial.SwitchMaterial

/**
 * Лабораторная работа 9: Работа с SharedPreferences
 */
class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        val switchTheme = findViewById<SwitchMaterial>(R.id.switchTheme)
        val switchSort = findViewById<SwitchMaterial>(R.id.switchSortOrder)
        val seekBarSize = findViewById<SeekBar>(R.id.seekBarTextSize)
        val btnSave = findViewById<Button>(R.id.btnSaveSettings)

        // 1. Загружаем текущие настройки из SharedPreferences
        val prefs = getSharedPreferences("app_settings", Context.MODE_PRIVATE)
        
        val isDarkMode = prefs.getBoolean("dark_mode", false)
        val isReverseSort = prefs.getBoolean("reverse_sort", false)
        val textSizeBonus = prefs.getInt("text_size_bonus", 0)

        // Устанавливаем значения в элементы UI
        switchTheme.isChecked = isDarkMode
        switchSort.isChecked = isReverseSort
        seekBarSize.progress = textSizeBonus

        // 2. Сохраняем настройки при нажатии на кнопку (Задание 9.2)
        btnSave.setOnClickListener {
            val editor = prefs.edit()
            editor.putBoolean("dark_mode", switchTheme.isChecked)
            editor.putBoolean("reverse_sort", switchSort.isChecked)
            editor.putInt("text_size_bonus", seekBarSize.progress)
            editor.apply()

            // Применяем тему мгновенно (Задание 9.3)
            if (switchTheme.isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }

            finish() // Возвращаемся назад
        }
    }
}
