package com.example.labs

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import java.io.*

/**
 * Лабораторная работа 10: Работа с файловой системой
 */
class FileWorkActivity : AppCompatActivity() {

    private val INTERNAL_FILENAME = "internal_data.txt"
    private val EXTERNAL_FILENAME = "external_data.txt"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_file_work)

        val etData = findViewById<EditText>(R.id.etFileData)
        val tvContent = findViewById<TextView>(R.id.tvFileContent)

        // --- ВНУТРЕННЕЕ ХРАНИЛИЩЕ ---
        
        findViewById<Button>(R.id.btnSaveInternal).setOnClickListener {
            val text = etData.text.toString()
            try {
                val fos: FileOutputStream = openFileOutput(INTERNAL_FILENAME, Context.MODE_PRIVATE)
                fos.write(text.toByteArray())
                fos.close()
                Toast.makeText(this, "Сохранено во внутр. память", Toast.LENGTH_SHORT).show()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        findViewById<Button>(R.id.btnReadInternal).setOnClickListener {
            try {
                val fis: FileInputStream = openFileInput(INTERNAL_FILENAME)
                val reader = BufferedReader(InputStreamReader(fis))
                val sb = StringBuilder()
                var line: String?
                while (reader.readLine().also { line = it } != null) {
                    sb.append(line).append("\n")
                }
                fis.close()
                tvContent.text = sb.toString()
            } catch (e: Exception) {
                tvContent.text = "Файл не найден"
            }
        }

        // --- ВНЕШНЕЕ ХРАНИЛИЩЕ ---

        findViewById<Button>(R.id.btnSaveExternal).setOnClickListener {
            saveToExternal(etData.text.toString())
        }

        findViewById<Button>(R.id.btnReadExternal).setOnClickListener {
            readFromExternal(tvContent)
        }

        findViewById<Button>(R.id.btnBackFromFile).setOnClickListener { finish() }
    }

    private fun saveToExternal(text: String) {
        // getExternalFilesDir не требует разрешений в новых Android для своей папки
        val file = File(getExternalFilesDir(null), EXTERNAL_FILENAME)
        try {
            val fos = FileOutputStream(file)
            fos.write(text.toByteArray())
            fos.close()
            Toast.makeText(this, "Сохранено во внешнюю память", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) { e.printStackTrace() }
    }

    private fun readFromExternal(tv: TextView) {
        val file = File(getExternalFilesDir(null), EXTERNAL_FILENAME)
        if (!file.exists()) {
            tv.text = "Файл не найден во внешней памяти"
            return
        }
        try {
            val fis = FileInputStream(file)
            val reader = BufferedReader(InputStreamReader(fis))
            val text = reader.readText()
            fis.close()
            tv.text = text
        } catch (e: Exception) { e.printStackTrace() }
    }
}
