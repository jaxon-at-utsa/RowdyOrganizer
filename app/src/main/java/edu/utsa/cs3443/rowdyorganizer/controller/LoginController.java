package edu.utsa.cs3443.rowdyorganizer.controller;

import android.content.Context;
import android.content.Intent;

import edu.utsa.cs3443.rowdyorganizer.model.LoginModel;
import edu.utsa.cs3443.rowdyorganizer.view.Welcome;

/**
 * LoginController acts as the Controller in the MVC architecture.
 * It handles login logic and communication between the View (MainActivity)
 * and the Model (LoginModel).
 */
public class LoginController {
    private final Context context;
    private final LoginModel loginModel;

    /**
     * Constructor for LoginController.
     *
     * @param context The application context, typically passed from an activity.
     */
    public LoginController(Context context) {
        this.context = context;
        this.loginModel = new LoginModel();
    }

    /**
     * Attempts to log in using the provided username and password.
     *
     * @param username The username entered by the user.
     * @param password The password entered by the user.
     * @return True if the login is successful, false otherwise.
     */
    public boolean attemptLogin(String username, String password) {
        return loginModel.validateCredentials(username, password);
    }

    /**
     * Navigates to the Welcome screen if login is successful.
     */
    public void navigateToWelcomeScreen() {
        Intent intent = new Intent(context, Welcome.class);
        context.startActivity(intent);
    }
}
