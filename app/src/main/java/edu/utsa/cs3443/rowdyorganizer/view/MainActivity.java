package edu.utsa.cs3443.rowdyorganizer.view;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.google.android.material.textfield.TextInputEditText;

import androidx.appcompat.app.AppCompatActivity;

import edu.utsa.cs3443.rowdyorganizer.R;
import edu.utsa.cs3443.rowdyorganizer.controller.LoginController;

/**
 * MainActivity serves as the View in the MVC architecture.
 * It manages the user interface and captures user inputs for the login process.
 */
public class MainActivity extends AppCompatActivity {
    private TextInputEditText usernameInput;
    private TextInputEditText passwordInput;
    private Button loginButton;
    private LoginController loginController;

    /**
     * Called when the activity is created.
     * Initializes UI components and sets up listeners.
     *
     * @param savedInstanceState The saved instance state.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize UI components
        usernameInput = findViewById(R.id.usernameInput);
        passwordInput = findViewById(R.id.passwordInput);
        loginButton = findViewById(R.id.LoginButton);

        // Initialize the controller
        loginController = new LoginController(this);

        // Set up login button click listener
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = usernameInput.getText().toString();
                String password = passwordInput.getText().toString();

                // Delegate login attempt to the controller
                if (loginController.attemptLogin(username, password)) {
                    loginController.navigateToWelcomeScreen();
                } else {
                    Toast.makeText(MainActivity.this, "Invalid username or password", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
