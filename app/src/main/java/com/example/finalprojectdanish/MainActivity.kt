package com.example.finalprojectdanish

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.finalprojectdanish.activities.AddTaskActivity
import com.example.finalprojectdanish.activities.TaskDetailActivity
import com.example.finalprojectdanish.adapter.TaskAdapter
import com.example.finalprojectdanish.database.DatabaseHelper
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    private lateinit var rvTasks: RecyclerView
    private lateinit var fabAddTask: FloatingActionButton
    private lateinit var dbHelper: DatabaseHelper
    private lateinit var adapter: TaskAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        dbHelper = DatabaseHelper(this)
        rvTasks = findViewById(R.id.rvTasks)
        fabAddTask = findViewById(R.id.fabAddTask)

        setupRecyclerView()

        fabAddTask.setOnClickListener {
            // Navigate to Add Task Screen
            val intent = Intent(this, AddTaskActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setupRecyclerView() {
        adapter = TaskAdapter(dbHelper.getAllTasks()) { task ->
            // Navigate to Task Detail Screen
            val intent = Intent(this, TaskDetailActivity::class.java)
            intent.putExtra("TASK_DATA", task)
            startActivity(intent)
        }
        rvTasks.layoutManager = LinearLayoutManager(this)
        rvTasks.adapter = adapter
    }

    override fun onResume() {
        super.onResume()
        // Refresh task list whenever we return to this screen
        adapter.updateData(dbHelper.getAllTasks())
    }
}
