package com.example.finalprojectdanish.adapter

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.finalprojectdanish.R
import com.example.finalprojectdanish.model.TaskModel

class TaskAdapter(
    private var taskList: ArrayList<TaskModel>,
    private val onItemClick: (TaskModel) -> Unit
) : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvTaskTitle: TextView = itemView.findViewById(R.id.tvTaskTitle)
        val tvTaskDueDate: TextView = itemView.findViewById(R.id.tvTaskDueDate)
        val ivStatus: ImageView = itemView.findViewById(R.id.ivStatus)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_task, parent, false)
        return TaskViewHolder(view)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = taskList[position]
        holder.tvTaskTitle.text = task.title
        holder.tvTaskDueDate.text = "Due: ${task.dueDate}"

        // Update UI based on completion status
        if (task.status == 1) {
            holder.ivStatus.setImageResource(android.R.drawable.checkbox_on_background)
            // Strike-through title if completed
            holder.tvTaskTitle.paintFlags = holder.tvTaskTitle.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
        } else {
            holder.ivStatus.setImageResource(android.R.drawable.checkbox_off_background)
            holder.tvTaskTitle.paintFlags = holder.tvTaskTitle.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
        }

        // Handle item click to open details
        holder.itemView.setOnClickListener {
            onItemClick(task)
        }
    }

    override fun getItemCount(): Int = taskList.size

    // Update list data
    fun updateData(newList: ArrayList<TaskModel>) {
        this.taskList = newList
        notifyDataSetChanged()
    }
}
