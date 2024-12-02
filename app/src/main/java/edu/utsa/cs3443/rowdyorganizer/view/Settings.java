package edu.utsa.cs3443.rowdyorganizer.view;

import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import edu.utsa.cs3443.rowdyorganizer.R;
import edu.utsa.cs3443.rowdyorganizer.controller.SettingsController;

/**
 * Settings activity serves as the View in the MVC architecture.
 * Displays the settings screen and handles navigation to Home or Logout.
 */
public class Settings extends AppCompatActivity {
    private Button homeButton;
    private Button logoutButton;
    private SettingsController settingsController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        // Enable edge-to-edge layout
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize buttons
        homeButton = findViewById(R.id.HomeButton);
        logoutButton = findViewById(R.id.LogoutButton);

        // Initialize the controller
        settingsController = new SettingsController(this);

        // Set up button listeners
        homeButton.setOnClickListener(view -> settingsController.navigateToHome());
        logoutButton.setOnClickListener(view -> settingsController.logout());
    }
}
