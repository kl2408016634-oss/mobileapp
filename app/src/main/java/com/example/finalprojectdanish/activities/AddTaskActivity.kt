package com.example.finalprojectdanish.activities

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.finalprojectdanish.R
import com.example.finalprojectdanish.database.DatabaseHelper
import com.example.finalprojectdanish.model.TaskModel
import com.google.android.material.appbar.MaterialToolbar

class AddTaskActivity : AppCompatActivity() {

    private lateinit var etTaskTitle: EditText
    private lateinit var etTaskDescription: EditText
    private lateinit var etTaskDueDate: EditText
    private lateinit var btnSaveTask: Button
    private lateinit var dbHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_task)

        // Setup Toolbar with Back Button
        val toolbar: MaterialToolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        dbHelper = DatabaseHelper(this)

        etTaskTitle = findViewById(R.id.etTaskTitle)
        etTaskDescription = findViewById(R.id.etTaskDescription)
        etTaskDueDate = findViewById(R.id.etTaskDueDate)
        btnSaveTask = findViewById(R.id.btnSaveTask)

        btnSaveTask.setOnClickListener {
            saveTask()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    private fun saveTask() {
        val title = etTaskTitle.text.toString().trim()
        val description = etTaskDescription.text.toString().trim()
        val dueDate = etTaskDueDate.text.toString().trim()

        // Validation
        if (title.isEmpty()) {
            etTaskTitle.error = "Title is required"
            return
        }

        val newTask = TaskModel(
            title = title,
            description = description,
            dueDate = dueDate,
            status = 0 // Initially pending
        )

        val result = dbHelper.insertTask(newTask)

        if (result != -1L) {
            Toast.makeText(this, "Task added successfully!", Toast.LENGTH_SHORT).show()
            finish() // Close activity and return to MainActivity
        } else {
            Toast.makeText(this, "Failed to add task", Toast.LENGTH_SHORT).show()
        }
    }
}
