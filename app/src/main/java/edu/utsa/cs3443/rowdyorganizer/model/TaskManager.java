package edu.utsa.cs3443.rowdyorganizer.model;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * TaskManager class handles all database operations related to tasks.
 * This class centralizes CRUD (Create, Read, Update, Delete) logic for task data.
 */
public class TaskManager {
    private SQLiteDatabase database;

    /**
     * Constructor for TaskManager. Initializes the database.
     *
     * @param context The application context.
     */
    public TaskManager(Context context) {
        // Open or create the database
        database = context.openOrCreateDatabase("TaskDB", Context.MODE_PRIVATE, null);
        // Create the tasks table if it doesn't exist
        database.execSQL("CREATE TABLE IF NOT EXISTS tasks(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "task TEXT, " +
                "date TEXT, " +
                "time TEXT, " +
                "completed INTEGER DEFAULT 0)"); // Added 'completed' column
    }

    /**
     * Retrieves incomplete tasks for a specific date.
     *
     * @param date The date for which incomplete tasks are to be retrieved.
     * @return A list of TaskModel objects.
     */
    public List<TaskModel> getIncompleteTasksByDate(String date) {
        List<TaskModel> tasks = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT * FROM tasks WHERE date = ? AND completed = 0", new String[]{date});
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String task = cursor.getString(1);
                String taskDate = cursor.getString(2);
                String time = cursor.getString(3);
                boolean completed = cursor.getInt(4) == 1; // Read 'completed' column
                tasks.add(new TaskModel(id, task, taskDate, time, completed));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return tasks;
    }

    /**
     * Marks a task as completed in the database.
     *
     * @param taskId The ID of the task to mark as completed.
     */
    public void markTaskAsCompleted(int taskId) {
        database.execSQL("UPDATE tasks SET completed = 1 WHERE id = ?", new Object[]{taskId});
    }

    /**
     * Retrieves all tasks (including completed ones).
     *
     * @return A list of TaskModel objects.
     */
    public List<TaskModel> getAllTasks() {
        List<TaskModel> tasks = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT * FROM tasks", null);
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String task = cursor.getString(1);
                String date = cursor.getString(2);
                String time = cursor.getString(3);
                boolean completed = cursor.getInt(4) == 1; // Read 'completed' column
                tasks.add(new TaskModel(id, task, date, time, completed));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return tasks;
    }

    /**
     * Adds a new task to the database.
     *
     * @param taskModel The TaskModel object containing task details.
     */
    public void addTask(TaskModel taskModel) {
        database.execSQL("INSERT INTO tasks(task, date, time, completed) VALUES(?, ?, ?, ?)",
                new Object[]{taskModel.getTask(), taskModel.getDate(), taskModel.getTime(), taskModel.isCompleted() ? 1 : 0});
    }

    /**
     * Deletes a task from the database by ID.
     *
     * @param taskId The ID of the task to be deleted.
     */
    public void deleteTask(int taskId) {
        database.execSQL("DELETE FROM tasks WHERE id = ?", new Object[]{taskId});
    }
}
