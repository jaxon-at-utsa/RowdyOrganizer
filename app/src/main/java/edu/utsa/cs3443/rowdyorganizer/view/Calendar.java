package edu.utsa.cs3443.rowdyorganizer.view;

import android.os.Bundle;
import android.widget.Button;
import android.widget.CalendarView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import edu.utsa.cs3443.rowdyorganizer.R;
import edu.utsa.cs3443.rowdyorganizer.controller.CalendarController;
import edu.utsa.cs3443.rowdyorganizer.model.TaskModel;

/**
 * Calendar activity serves as the View in the MVC architecture.
 * Displays the calendar screen and manages task display for selected dates.
 */
public class Calendar extends AppCompatActivity {
    private Button homeButton;
    private Button addTaskButton;
    private CalendarView calendarView;
    private RecyclerView recyclerView;
    private CalendarController calendarController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        // Initialize UI components
        calendarView = findViewById(R.id.calendarView);
        recyclerView = findViewById(R.id.recyclerView);
        homeButton = findViewById(R.id.HomeButton);
        addTaskButton = findViewById(R.id.addTaskButton);

        // Setup RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<TaskModel> taskList = new ArrayList<>(); // Empty list initially
        calendarController = new CalendarController(this, taskList);
        recyclerView.setAdapter(calendarController.getTaskAdapter());

        // Set up button listeners
        homeButton.setOnClickListener(view -> calendarController.navigateToHome());
        addTaskButton.setOnClickListener(view -> calendarController.showAddTaskDialog());

        // Set default date as current date and load tasks
        String currentDate = calendarController.getCurrentDateInMMDDYYYY();
        calendarController.loadTasksForDate(calendarController.convertDateToDatabaseFormat(currentDate));

        // Handle date selection changes
        calendarView.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
            String selectedDate = String.format("%d-%02d-%02d", year, month + 1, dayOfMonth);
            calendarController.loadTasksForDate(selectedDate);
        });
    }
}
