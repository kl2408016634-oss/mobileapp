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

class EditTaskActivity : AppCompatActivity() {

    private lateinit var etEditTaskTitle: EditText
    private lateinit var etEditTaskDescription: EditText
    private lateinit var etEditTaskDueDate: EditText
    private lateinit var btnUpdateTask: Button
    private lateinit var dbHelper: DatabaseHelper
    private var taskId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_task)

        // Setup Toolbar with Back Button
        val toolbar: MaterialToolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        dbHelper = DatabaseHelper(this)

        etEditTaskTitle = findViewById(R.id.etEditTaskTitle)
        etEditTaskDescription = findViewById(R.id.etEditTaskDescription)
        etEditTaskDueDate = findViewById(R.id.etEditTaskDueDate)
        btnUpdateTask = findViewById(R.id.btnUpdateTask)

        // Get task data from intent
        val task = intent.getSerializableExtra("TASK_DATA") as? TaskModel
        if (task != null) {
            taskId = task.id
            etEditTaskTitle.setText(task.title)
            etEditTaskDescription.setText(task.description)
            etEditTaskDueDate.setText(task.dueDate)
        }

        btnUpdateTask.setOnClickListener {
            updateTask()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    private fun updateTask() {
        val title = etEditTaskTitle.text.toString().trim()
        val description = etEditTaskDescription.text.toString().trim()
        val dueDate = etEditTaskDueDate.text.toString().trim()

        if (title.isEmpty()) {
            etEditTaskTitle.error = "Title is required"
            return
        }

        val updatedTask = TaskModel(
            id = taskId,
            title = title,
            description = description,
            dueDate = dueDate,
            status = 0 // Initially pending
        )

        val result = dbHelper.updateTask(updatedTask)

        if (result > 0) {
            Toast.makeText(this, "Task updated successfully!", Toast.LENGTH_SHORT).show()
            finish()
        } else {
            Toast.makeText(this, "Failed to update task", Toast.LENGTH_SHORT).show()
        }
    }
}
