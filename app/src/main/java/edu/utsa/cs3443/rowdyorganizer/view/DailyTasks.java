package edu.utsa.cs3443.rowdyorganizer.view;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.utsa.cs3443.rowdyorganizer.R;
import edu.utsa.cs3443.rowdyorganizer.controller.DailyTasksController;
import edu.utsa.cs3443.rowdyorganizer.model.TaskModel;

/**
 * DailyTasks activity serves as the View in the MVC architecture.
 * Displays the daily tasks screen and handles user navigation to Home or Logout.
 */
public class DailyTasks extends AppCompatActivity {
    private Button homeButton;
    private Button addTaskButton; // Add task button
    private RecyclerView recyclerView;
    private DailyTasksController dailyTasksController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dailytasks);

        // Initialize UI components
        recyclerView = findViewById(R.id.recyclerViewDailyTasks);
        homeButton = findViewById(R.id.HomeButton);
        addTaskButton = findViewById(R.id.addTaskButton); // Initialize add task button

        // Initialize controller
        dailyTasksController = new DailyTasksController(this);

        // Set up RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<TaskModel> taskList = dailyTasksController.getTasksForToday();
        TaskAdapter adapter = new TaskAdapter(taskList, dailyTasksController.getTaskManager());
        recyclerView.setAdapter(adapter);

        // Set up button listeners
        homeButton.setOnClickListener(view -> dailyTasksController.navigateToHome());

        addTaskButton.setOnClickListener(view -> {
            dailyTasksController.showAddTaskDialog(() -> {
                // Refresh the task list and RecyclerView after adding a task
                List<TaskModel> updatedTasks = dailyTasksController.getTasksForToday();
                adapter.updateTaskList(updatedTasks);
            });
        });

    }
}