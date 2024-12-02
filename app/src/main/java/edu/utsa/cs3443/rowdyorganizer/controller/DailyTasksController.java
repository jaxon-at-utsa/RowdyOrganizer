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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import edu.utsa.cs3443.rowdyorganizer.R;
import edu.utsa.cs3443.rowdyorganizer.model.TaskManager;
import edu.utsa.cs3443.rowdyorganizer.model.TaskModel;
import edu.utsa.cs3443.rowdyorganizer.view.Welcome;

/**
 * DailyTasksController acts as the Controller in the MVC architecture.
 * Handles navigation and task-related operations for the Daily Tasks screen.
 */
public class DailyTasksController {
    private final Context context;
    private final TaskManager taskManager;

    /**
     * Constructor for DailyTasksController.
     *
     * @param context The application context.
     */
    public DailyTasksController(Context context) {
        this.context = context;
        this.taskManager = new TaskManager(context);
    }

    /**
     * Retrieves tasks for the current day.
     *
     * @return A list of TaskModel objects for today.
     */
    public List<TaskModel> getTasksForToday() {
        String todayDate = getCurrentDate();
        return taskManager.getIncompleteTasksByDate(todayDate);
    }

    /**
     * Gets the TaskManager instance.
     *
     * @return The TaskManager instance.
     */
    public TaskManager getTaskManager() {
        return taskManager;
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
            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(context, (view, year1, month1, dayOfMonth) -> {
                String formattedDate = String.format("%02d/%02d/%04d", month1 + 1, dayOfMonth, year1);
                selectedDate[0] = formattedDate;
                datePickerButton.setText(formattedDate);
            }, year, month, day);
            datePickerDialog.show();
        });

        // Time picker button logic
        timePickerButton.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            int hour = calendar.get(Calendar.HOUR_OF_DAY);
            int minute = calendar.get(Calendar.MINUTE);

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
            dialog.dismiss(); // Close the dialog
            Toast.makeText(context, "Task added successfully", Toast.LENGTH_SHORT).show();
        });

        // Cancel button click listener
        btnCancel.setOnClickListener(v -> dialog.dismiss());

        dialog.show();
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
    private String getCurrentDateInMMDDYYYY() {
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        return sdf.format(new Date());
    }

    /**
     * Gets the current date in YYYY-MM-DD format.
     *
     * @return The current date as a string.
     */
    private String getCurrentDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(new Date());
    }

    /**
     * Navigates to the Welcome screen (Home).
     */
    public void navigateToHome() {
        Intent intent = new Intent(context, Welcome.class);
        context.startActivity(intent);
    }
}
