package com.example.jeremiah_boothe_final;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import android.Manifest;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.jeremiah_boothe_final.databinding.ActivityMainBinding;
import com.example.jeremiah_boothe_final.viewmodel.InventoryViewModel;
import com.example.jeremiah_boothe_final.viewmodel.NotificationsViewModel;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CODE = 1;
    private AppBarConfiguration appBarConfiguration;
    private static final String KEY_NAVIGATION_STATE = "navigation_state";
    /**
     * Called when the activity is starting.
     * @param savedInstanceState If the activity is being re-initialized after
     *     previously being shut down then this Bundle contains the data it most
     *     recently supplied in {@link #onSaveInstanceState}.  <b><i>Note: Otherwise it is null.</i></b>
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Check if the user is logged in
        boolean isLoggedIn = getSharedPreferences("app_prefs", MODE_PRIVATE).getBoolean("is_logged_in", false);
        if (!isLoggedIn) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
            return;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            requestPermissions();
        }

        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Initialize ViewModel
        InventoryViewModel viewModel = new ViewModelProvider(this).get(InventoryViewModel.class);
        NotificationsViewModel notificationsViewModel = new ViewModelProvider(this).get(NotificationsViewModel.class);

        // Initialize NavHostFragment and NavController
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.nav_host_fragment_activity_main);
        assert navHostFragment != null;
        NavController navController = navHostFragment.getNavController();

        // Set up AppBarConfiguration
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();

        // Set up ActionBar and NavigationUI
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);
        if (savedInstanceState != null) {
            int navState = savedInstanceState.getInt(KEY_NAVIGATION_STATE);
            navController.navigate(navState);
        }
    }
    /**
     * Requests permissions for Tiramisu, which was used to test notifications.
     */
    @RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
    private void requestPermissions() {
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.POST_NOTIFICATIONS},
                REQUEST_CODE);
    }
    /**
     * Called when a permission request has been completed.
     * @param requestCode The request code passed in
     * @param permissions The requested permissions. Never null.
     * @param grantResults The grant results for the corresponding permissions
     *     which is either {@link android.content.pm.PackageManager#PERMISSION_GRANTED}
     *     or {@link android.content.pm.PackageManager#PERMISSION_DENIED}. Never null.
     *
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, handle your logic here
                handlePermissionGranted();
            } else {
                // Permission denied, handle accordingly
                handlePermissionDenied();
            }
        }
    }
    /**
     * Method to handle permission granted
     */
    private void handlePermissionGranted() {
        // Perform actions when permission is granted
        Toast.makeText(this, "Permission granted!", Toast.LENGTH_SHORT).show();
        Log.d("MainActivity", "Permission granted!");
        // Example: Start a service, enable a feature, etc.
    }
    /**
     * Method to handle permission denied
     */
    private void handlePermissionDenied() {
        // Perform actions when permission is denied
        Toast.makeText(this, "Permission denied!", Toast.LENGTH_SHORT).show();
        Log.d("MainActivity", "Permission denied!");
    }
    /**
     * Called when the activity has detected the user's press of the back
     * @return true if the activity handled the back press, false otherwise
     */
    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }
}
