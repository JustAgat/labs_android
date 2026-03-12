package com.example.labs

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar

class FragmentContainerActivity : AppCompatActivity(), ListFragment.OnUserSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fragment_container)

        // Вывод приветствия при входе (Задание пользователя)
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
            R.id.action_info -> {
                showInfoDialog()
                true
            }
            R.id.action_settings -> {
                showCustomDialog()
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
            .setMessage("Это учебное приложение для Лабораторной работы №8.")
            .setPositiveButton("Понятно") { dialog, _ -> dialog.dismiss() }
            .show()
    }

    private fun showCustomDialog() {
        val dialogView = layoutInflater.inflate(R.layout.dialog_custom, null)
        val dialog = AlertDialog.Builder(this)
            .setView(dialogView)
            .create()

        val etNote = dialogView.findViewById<EditText>(R.id.etCustomNote)
        val btnOk = dialogView.findViewById<Button>(R.id.btnCustomOk)

        btnOk.setOnClickListener {
            val note = etNote.text.toString()
            Toast.makeText(this, "Заметка сохранена: $note", Toast.LENGTH_SHORT).show()
            dialog.dismiss()
        }
        dialog.show()
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
