package edu.utsa.cs3443.rowdyorganizer.view;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import edu.utsa.cs3443.rowdyorganizer.R;
import edu.utsa.cs3443.rowdyorganizer.controller.WelcomeController;

/**
 * Welcome activity serves as the View in the MVC architecture.
 * Displays the welcome screen with buttons for navigation.
 */
public class Welcome extends AppCompatActivity {
    private Button dailyTasksButton;
    private Button calendarButton;
    private ImageButton settingsButton; // Changed to ImageButton
    private Button logoutButton;
    private WelcomeController welcomeController;

    /**
     * Called when the activity is created.
     * Initializes UI components and sets up the controller.
     *
     * @param savedInstanceState The saved instance state.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        // Initialize buttons
        dailyTasksButton = findViewById(R.id.DailyTasksButton);
        calendarButton = findViewById(R.id.CalendarButton);
        settingsButton = findViewById(R.id.settingsButton); // Initialize ImageButton
        logoutButton = findViewById(R.id.LogoutButton);

        // Initialize the controller
        welcomeController = new WelcomeController(this);

        // Set up button click listeners
        dailyTasksButton.setOnClickListener(view -> welcomeController.navigateToDailyTasks());
        calendarButton.setOnClickListener(view -> welcomeController.navigateToCalendar());
        settingsButton.setOnClickListener(view -> welcomeController.navigateToSettings()); // Updated listener
        logoutButton.setOnClickListener(view -> welcomeController.logout());
    }
}
