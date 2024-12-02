package edu.utsa.cs3443.rowdyorganizer.model;

/**
 * TaskModel represents a single task with its details.
 */
public class TaskModel {
    private int id;
    private String task;
    private String date;
    private String time;
    private boolean completed; // New field for task completion status

    /**
     * Constructor for TaskModel.
     *
     * @param id        The unique ID of the task.
     * @param task      The task description.
     * @param date      The date of the task.
     * @param time      The time of the task.
     * @param completed The completion status of the task.
     */
    public TaskModel(int id, String task, String date, String time, boolean completed) {
        this.id = id;
        this.task = task;
        this.date = date;
        this.time = time;
        this.completed = completed;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public String getTask() {
        return task;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}