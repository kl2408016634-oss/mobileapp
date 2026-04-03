package com.example.finalprojectdanish.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.finalprojectdanish.model.TaskModel

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "StudentTaskManager.db"
        private const val DATABASE_VERSION = 1

        private const val TABLE_TASKS = "tasks"
        private const val COLUMN_ID = "id"
        private const val COLUMN_TITLE = "title"
        private const val COLUMN_DESCRIPTION = "description"
        private const val COLUMN_DUE_DATE = "dueDate"
        private const val COLUMN_STATUS = "status"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTable = ("CREATE TABLE $TABLE_TASKS ("
                + "$COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "$COLUMN_TITLE TEXT,"
                + "$COLUMN_DESCRIPTION TEXT,"
                + "$COLUMN_DUE_DATE TEXT,"
                + "$COLUMN_STATUS INTEGER)")
        db?.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_TASKS")
        onCreate(db)
    }

    // CRUD - Create
    fun insertTask(task: TaskModel): Long {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(COLUMN_TITLE, task.title)
        values.put(COLUMN_DESCRIPTION, task.description)
        values.put(COLUMN_DUE_DATE, task.dueDate)
        values.put(COLUMN_STATUS, task.status)

        val result = db.insert(TABLE_TASKS, null, values)
        db.close()
        return result
    }

    // CRUD - Read
    fun getAllTasks(): ArrayList<TaskModel> {
        val taskList = ArrayList<TaskModel>()
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_TASKS", null)

        if (cursor.moveToFirst()) {
            do {
                val task = TaskModel(
                    cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DESCRIPTION)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DUE_DATE)),
                    cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_STATUS))
                )
                taskList.add(task)
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return taskList
    }

    // CRUD - Update
    fun updateTask(task: TaskModel): Int {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(COLUMN_TITLE, task.title)
        values.put(COLUMN_DESCRIPTION, task.description)
        values.put(COLUMN_DUE_DATE, task.dueDate)
        values.put(COLUMN_STATUS, task.status)

        val result = db.update(TABLE_TASKS, values, "$COLUMN_ID = ?", arrayOf(task.id.toString()))
        db.close()
        return result
    }

    // CRUD - Delete
    fun deleteTask(id: Int): Int {
        val db = this.writableDatabase
        val result = db.delete(TABLE_TASKS, "$COLUMN_ID = ?", arrayOf(id.toString()))
        db.close()
        return result
    }
}
