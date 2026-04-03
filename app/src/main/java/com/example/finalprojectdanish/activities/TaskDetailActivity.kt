package com.example.finalprojectdanish.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.finalprojectdanish.R
import com.example.finalprojectdanish.database.DatabaseHelper
import com.example.finalprojectdanish.model.TaskModel
import com.google.android.material.appbar.MaterialToolbar

class TaskDetailActivity : AppCompatActivity() {

    private lateinit var tvDetailTitle: TextView
    private lateinit var tvDetailDueDate: TextView
    private lateinit var tvDetailDescription: TextView
    private lateinit var btnMarkComplete: Button
    private lateinit var btnEditTask: Button
    private lateinit var btnDeleteTask: Button
    private lateinit var dbHelper: DatabaseHelper
    private var currentTask: TaskModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_detail)

        // Setup Toolbar with Back Button
        val toolbar: MaterialToolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        dbHelper = DatabaseHelper(this)

        tvDetailTitle = findViewById(R.id.tvDetailTitle)
        tvDetailDueDate = findViewById(R.id.tvDetailDueDate)
        tvDetailDescription = findViewById(R.id.tvDetailDescription)
        btnMarkComplete = findViewById(R.id.btnMarkComplete)
        btnEditTask = findViewById(R.id.btnEditTask)
        btnDeleteTask = findViewById(R.id.btnDeleteTask)

        // Receive task object from Intent
        currentTask = intent.getSerializableExtra("TASK_DATA") as? TaskModel
        displayTaskDetails()

        btnMarkComplete.setOnClickListener {
            markAsComplete()
        }

        btnEditTask.setOnClickListener {
            val intent = Intent(this, EditTaskActivity::class.java)
            intent.putExtra("TASK_DATA", currentTask)
            startActivity(intent)
        }

        btnDeleteTask.setOnClickListener {
            deleteTask()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    private fun displayTaskDetails() {
        currentTask?.let {
            tvDetailTitle.text = it.title
            tvDetailDueDate.text = "Due: ${it.dueDate}"
            tvDetailDescription.text = it.description
            
            if (it.status == 1) {
                btnMarkComplete.text = "Task Completed"
                btnMarkComplete.isEnabled = false
            } else {
                btnMarkComplete.text = "Mark as Completed"
                btnMarkComplete.isEnabled = true
            }
        }
    }

    private fun markAsComplete() {
        currentTask?.let {
            val updatedTask = it.copy(status = 1)
            val result = dbHelper.updateTask(updatedTask)
            if (result > 0) {
                Toast.makeText(this, "Task marked as completed", Toast.LENGTH_SHORT).show()
                currentTask = updatedTask
                displayTaskDetails()
            }
        }
    }

    private fun deleteTask() {
        currentTask?.let {
            val result = dbHelper.deleteTask(it.id)
            if (result > 0) {
                Toast.makeText(this, "Task deleted", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        // Re-fetch task details in case it was edited in EditTaskActivity
        // For a more robust app, you might fetch by ID from the database here
    }
}
