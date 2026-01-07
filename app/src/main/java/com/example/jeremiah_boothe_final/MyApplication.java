/** This acts as the entry point to the application, initializing the database to support app functionality.
 * @author Jeremiah Boothe
 * @date 06/24/2024
 */

package com.example.jeremiah_boothe_final;

import android.app.Application;
import com.example.jeremiah_boothe_final.model.AppDatabase;

public class MyApplication extends Application {
    /**
     * Called when the application is starting, before any other application objects have been created.
     */
    @Override
    public void onCreate() {
        super.onCreate();
        // Initialize the AppDatabase
        AppDatabase.getDatabase(this);
    }
}
