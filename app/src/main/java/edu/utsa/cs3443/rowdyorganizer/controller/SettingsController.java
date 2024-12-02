package edu.utsa.cs3443.rowdyorganizer.controller;

import android.content.Context;
import android.content.Intent;

import edu.utsa.cs3443.rowdyorganizer.view.MainActivity;
import edu.utsa.cs3443.rowdyorganizer.view.Welcome;

/**
 * SettingsController acts as the Controller in the MVC architecture.
 * Handles button actions and navigation for the Settings screen.
 */
public class SettingsController {
    private final Context context;

    /**
     * Constructor for SettingsController.
     *
     * @param context The application context, typically passed from an activity.
     */
    public SettingsController(Context context) {
        this.context = context;
    }

    /**
     * Navigates to the Welcome screen (Home).
     */
    public void navigateToHome() {
        Intent intent = new Intent(context, Welcome.class);
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
