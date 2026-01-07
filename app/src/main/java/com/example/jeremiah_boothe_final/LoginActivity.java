/** This class is responsible for handling user login and registration. It also functions as a navigation blocker.
 * to the navigation components supported by the app.  In an earlier iteration the navigation bar was on the login screen, and could be used to bypass the login/registration process.
 * @author Jeremiah Boothe
 * @date 06/24/2023
 */
package com.example.jeremiah_boothe_final;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;

import com.example.jeremiah_boothe_final.model.user.User;
import com.example.jeremiah_boothe_final.model.user.UserRepository;

public class LoginActivity extends AppCompatActivity implements UserRepository.LoginCallback {

    private EditText usernameEditText;
    private EditText passwordEditText;
    private UserRepository userRepository;
    /**
     *
     * @param savedInstanceState If the activity is being re-initialized after
     *     previously being shut down then this Bundle contains the data it most
     *     recently supplied in {@link #onSaveInstanceState}.  <b><i>Note: Otherwise it is null.</i></b>
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_login);

        usernameEditText = findViewById(R.id.username);
        passwordEditText = findViewById(R.id.password);
        Button loginButton = findViewById(R.id.login);
        Button registerButton = findViewById(R.id.register);

        userRepository = new UserRepository(getApplication());

        loginButton.setOnClickListener(v -> loginUser());

        registerButton.setOnClickListener(v -> registerUser());

        // Observe logged-in user
        LiveData<User> loggedInUser = userRepository.getLoggedInUser();
        loggedInUser.observe(this, user -> {
            if (user != null) {
                navigateToMain();
            }
        });
    }
    /**
     * Called when the activity has detected the user's press of the back
     */
    private void loginUser() {
        String username = usernameEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();

        if (TextUtils.isEmpty(username)) {
            Toast.makeText(this, "Please enter a username", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Please enter a password", Toast.LENGTH_SHORT).show();
            return;
        }

        userRepository.login(username, password, this);
    }
    /**
     * Called when the activity has detected the user's press of the back
     *
     */
    private void registerUser() {
        String username = usernameEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();

        if (TextUtils.isEmpty(username)) {
            Toast.makeText(this, "Please enter a username", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Please enter a password", Toast.LENGTH_SHORT).show();
            return;
        }

        User newUser = new User(username, password);
        newUser.setUsername(username);
        newUser.setPassword(password);

        userRepository.register(newUser);
        Toast.makeText(this, "User registered successfully", Toast.LENGTH_SHORT).show();
    }
    /**
     * Called when the activity has detected the user's press of the back
     */
    private void navigateToMain() {
        getSharedPreferences("app_prefs", MODE_PRIVATE).edit().putBoolean("is_logged_in", true).apply();
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
    /**
     * Called when the activity has detected the user's press of the back
     * @param user The user object returned from the login callback
     */
    @Override
    public void onLoginResult(User user) {
        if (user != null) {
            Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show();
            userRepository.getLoggedInUser().getValue(); // Set the logged-in user
            navigateToMain();
        } else {
            Toast.makeText(this, "Login failed", Toast.LENGTH_SHORT).show();
        }
    }
}
