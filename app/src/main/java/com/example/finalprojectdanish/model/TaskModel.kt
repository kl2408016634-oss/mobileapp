package com.example.finalprojectdanish.model

import java.io.Serializable

// Task model to hold task data
// Implementing Serializable so we can pass it between activities via Intent
data class TaskModel(
    val id: Int = 0,
    val title: String,
    val description: String,
    val dueDate: String,
    val status: Int // 0 for pending, 1 for completed
) : Serializable
