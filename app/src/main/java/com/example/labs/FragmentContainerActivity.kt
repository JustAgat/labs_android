package com.example.labs

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.Toolbar
import com.google.android.material.snackbar.Snackbar

class FragmentContainerActivity : AppCompatActivity(), ListFragment.OnUserSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        val prefs = getSharedPreferences("app_settings", Context.MODE_PRIVATE)
        val isDarkMode = prefs.getBoolean("dark_mode", false)
        if (isDarkMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fragment_container)

        // Инициализируем Toolbar
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        // Убираем текст заголовка, чтобы оставить место (три точки будут в углу)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        val showWelcome = intent.getBooleanExtra("SHOW_WELCOME", false)
        val login = intent.getStringExtra("EXTRA_LOGIN") ?: ""
        
        if (showWelcome && login.isNotEmpty()) {
            Snackbar.make(
                findViewById(R.id.fragment_container),
                "Приветствую, $login!",
                Snackbar.LENGTH_LONG
            ).show()
        }

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, ListFragment())
                .commit()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_notes -> {
                val intent = Intent(this, NotesActivity::class.java)
                startActivity(intent)
                true
            }
            R.id.action_settings -> {
                val intent = Intent(this, SettingsActivity::class.java)
                startActivity(intent)
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
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun showInfoDialog() {
        AlertDialog.Builder(this)
            .setTitle("О программе")
            .setMessage("Это учебное приложение.")
            .setPositiveButton("Понятно") { dialog, _ -> dialog.dismiss() }
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
