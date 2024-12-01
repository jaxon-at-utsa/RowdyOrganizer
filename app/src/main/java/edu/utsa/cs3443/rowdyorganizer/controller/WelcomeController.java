package edu.utsa.cs3443.rowdyorganizer.controller;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import edu.utsa.cs3443.rowdyorganizer.view.Calendar;
import edu.utsa.cs3443.rowdyorganizer.view.DailyTasks;
import edu.utsa.cs3443.rowdyorganizer.view.MainActivity;
import edu.utsa.cs3443.rowdyorganizer.view.Settings;
import edu.utsa.cs3443.rowdyorganizer.view.Welcome;

/**
 * WelcomeController acts as the Controller in the MVC architecture.
 * Handles button actions and navigation for the Welcome screen.
 */
public class WelcomeController {
    private final Context context;

    /**
     * Constructor for WelcomeController.
     *
     * @param context The application context, typically passed from an activity.
     */
    public WelcomeController(Context context) {
        this.context = context;
    }

    /**
     * Navigates to the Daily Tasks screen.
     */
    public void navigateToDailyTasks() {
        Intent intent = new Intent(context, DailyTasks.class);
        context.startActivity(intent);
    }

    /**
     * Navigates to the Calendar screen.
     */
    public void navigateToCalendar() {
        Intent intent = new Intent(context, Calendar.class);
        context.startActivity(intent);
    }

    /**
     * Navigates to the Settings screen.
     */
    public void navigateToSettings() {
        Intent intent = new Intent(context, Settings.class);
        context.startActivity(intent);
    }

    /**
     * Logs the user out and navigates to the MainActivity (Login screen).
     */
    public void logout() {
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }
}
