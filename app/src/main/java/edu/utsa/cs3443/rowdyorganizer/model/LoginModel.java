package edu.utsa.cs3443.rowdyorganizer.model;

/**
 * LoginModel acts as the Model in the MVC architecture.
 * It contains the business logic for validating login credentials.
 */
public class LoginModel {
    // Temporary hardcoded credentials for proof-of-concept
    private final String VALID_USERNAME = "admin";
    private final String VALID_PASSWORD = "abc123";

    /**
     * Validates the provided username and password against stored credentials.
     *
     * @param username The username to validate.
     * @param password The password to validate.
     * @return True if the credentials are valid, false otherwise.
     */
    public boolean validateCredentials(String username, String password) {
        return username.equals(VALID_USERNAME) && password.equals(VALID_PASSWORD);
    }
}
