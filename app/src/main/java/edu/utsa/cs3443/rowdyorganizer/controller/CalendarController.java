package edu.utsa.cs3443.rowdyorganizer.controller;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

import edu.utsa.cs3443.rowdyorganizer.R;
import edu.utsa.cs3443.rowdyorganizer.model.TaskManager;
import edu.utsa.cs3443.rowdyorganizer.model.TaskModel;
import edu.utsa.cs3443.rowdyorganizer.view.MainActivity;
import edu.utsa.cs3443.rowdyorganizer.view.TaskAdapter;
import edu.utsa.cs3443.rowdyorganizer.view.Welcome;

/**
 * CalendarController acts as the Controller in the MVC architecture.
 * Handles navigation and task-related operations for the Calendar screen.
 */
public class CalendarController {
    private final Context context;
    private final TaskManager taskManager;
    private final TaskAdapter taskAdapter;
    private final List<TaskModel> taskList;

    /**
     * Constructor for CalendarController.
     *
     * @param context  The application context.
     * @param taskList The task list to display in the RecyclerView.
     */
    public CalendarController(Context context, List<TaskModel> taskList) {
        this.context = context;
        this.taskList = taskList;
        this.taskManager = new TaskManager(context);
        this.taskAdapter = new TaskAdapter(taskList, taskManager);
    }

    /**
     * Retrieves the TaskAdapter for the RecyclerView.
     *
     * @return The TaskAdapter instance.
     */
    public TaskAdapter getTaskAdapter() {
        return taskAdapter;
    }

    /**
     * Loads tasks for a specific date into the task list and updates the adapter.
     *
     * @param date The selected date in YYYY-MM-DD format.
     */
    public void loadTasksForDate(String date) {
        taskList.clear();
        taskList.addAll(taskManager.getIncompleteTasksByDate(date));
        taskAdapter.notifyDataSetChanged();
    }

    /**
     * Displays a dialog for adding a new task.
     */
    public void showAddTaskDialog() {
        // Inflate the dialog layout
        View dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_add_task, null);

        // Initialize dialog views
        EditText taskEditText = dialogView.findViewById(R.id.editTask);
        Button datePickerButton = dialogView.findViewById(R.id.datePickerButton);
        Button timePickerButton = dialogView.findViewById(R.id.timePickerButton);
        Button btnSave = dialogView.findViewById(R.id.btnSave);
        Button btnCancel = dialogView.findViewById(R.id.btnCancel);

        // Initialize default date and time values
        final String[] selectedDate = {getCurrentDateInMMDDYYYY()}; // Default to today's date
        final String[] selectedTime = {"11:59 PM"}; // Default to end of the day

        // Set default date and time on the buttons
        datePickerButton.setText(selectedDate[0]);
        timePickerButton.setText(selectedTime[0]);

        // Date picker button logic
        datePickerButton.setOnClickListener(v -> {
            java.util.Calendar calendar = java.util.Calendar.getInstance();
            int year = calendar.get(java.util.Calendar.YEAR);
            int month = calendar.get(java.util.Calendar.MONTH);
            int day = calendar.get(java.util.Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(context, (view, year1, month1, dayOfMonth) -> {
                String formattedDate = String.format("%02d/%02d/%04d", month1 + 1, dayOfMonth, year1);
                selectedDate[0] = formattedDate;
                datePickerButton.setText(formattedDate);
            }, year, month, day);
            datePickerDialog.show();
        });

        // Time picker button logic
        timePickerButton.setOnClickListener(v -> {
            java.util.Calendar calendar = java.util.Calendar.getInstance();
            int hour = calendar.get(java.util.Calendar.HOUR_OF_DAY);
            int minute = calendar.get(java.util.Calendar.MINUTE);

            TimePickerDialog timePickerDialog = new TimePickerDialog(context, (view, hourOfDay, minute1) -> {
                String period = hourOfDay >= 12 ? "PM" : "AM";
                int hour12Format = hourOfDay > 12 ? hourOfDay - 12 : (hourOfDay == 0 ? 12 : hourOfDay);
                String formattedTime = String.format("%02d:%02d %s", hour12Format, minute1, period);
                selectedTime[0] = formattedTime;
                timePickerButton.setText(formattedTime);
            }, hour, minute, false);
            timePickerDialog.show();
        });

        // Create and show the dialog
        android.app.AlertDialog dialog = new android.app.AlertDialog.Builder(context)
                .setView(dialogView)
                .create();

        // Save button click listener
        btnSave.setOnClickListener(v -> {
            String task = taskEditText.getText().toString();

            if (TextUtils.isEmpty(task)) {
                Toast.makeText(context, "Task name is required", Toast.LENGTH_SHORT).show();
                return;
            }

            // Convert date to database format (YYYY-MM-DD)
            String databaseDate = convertDateToDatabaseFormat(selectedDate[0]);

            // Add the task to the database
            taskManager.addTask(new TaskModel(0, task, databaseDate, selectedTime[0], false));
            loadTasksForDate(databaseDate); // Reload tasks for the current date
            dialog.dismiss(); // Close the dialog
            Toast.makeText(context, "Task added successfully", Toast.LENGTH_SHORT).show();
        });

        // Cancel button click listener
        btnCancel.setOnClickListener(v -> dialog.dismiss());

        dialog.show();
    }


    /**
     * Validates if a given date matches the MM/DD/YYYY format.
     *
     * @param date The date string to validate.
     * @return True if the date is valid, otherwise false.
     */
    private boolean isValidDate(String date) {
        // Regular expression for MM/DD/YYYY format
        String dateRegex = "^(0[1-9]|1[0-2])/([0-2][0-9]|3[0-1])/\\d{4}$";
        return date != null && date.matches(dateRegex);
    }

    /**
     * Validates if a given time matches the hh:mm AM/PM format.
     *
     * @param time The time string to validate.
     * @return True if the time is valid, otherwise false.
     */
    private boolean isValidTime(String time) {
        // Regular expression for hh:mm AM/PM format
        String timeRegex = "^(0[1-9]|1[0-2]):[0-5][0-9] (AM|PM)$";
        return time != null && time.matches(timeRegex);
    }

    /**
     * Converts a date from MM/DD/YYYY format to YYYY-MM-DD format.
     *
     * @param date The date string in MM/DD/YYYY format.
     * @return The date string in YYYY-MM-DD format.
     */
    private String convertDateToDatabaseFormat(String date) {
        String[] parts = date.split("/");
        return parts[2] + "-" + parts[0] + "-" + parts[1];
    }

    /**
     * Gets the current date in MM/DD/YYYY format.
     *
     * @return The current date as a string.
     */
    public String getCurrentDateInMMDDYYYY() {
        java.util.Calendar calendar = java.util.Calendar.getInstance();
        int year = calendar.get(java.util.Calendar.YEAR);
        int month = calendar.get(java.util.Calendar.MONTH) + 1;
        int day = calendar.get(java.util.Calendar.DAY_OF_MONTH);
        return String.format("%02d/%02d/%04d", month, day, year);
    }

    /**
     * Navigates to the Welcome screen (Home).
     */
    public void navigateToHome() {
        Intent intent = new Intent(context, Welcome.class);
        context.startActivity(intent);
    }

}
