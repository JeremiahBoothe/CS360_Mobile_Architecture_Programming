package com.example.jeremiah_boothe_final.model;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.jeremiah_boothe_final.model.inventory.InventoryDao;
import com.example.jeremiah_boothe_final.model.inventory.InventoryItem;
import com.example.jeremiah_boothe_final.model.notifications.NotificationsDao;
import com.example.jeremiah_boothe_final.model.notifications.NotificationsEntity;
import com.example.jeremiah_boothe_final.model.user.User;
import com.example.jeremiah_boothe_final.model.user.UserDao;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = { InventoryItem.class, User.class, NotificationsEntity.class }, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase
{
    public abstract NotificationsDao notificationDao();
    public abstract InventoryDao inventoryDao();
    public abstract UserDao userDao();

    private static volatile AppDatabase INSTANCE;

    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);
    /**
     * Gets the AppDatabase instance.
     * @param context The application context.
     * @return The AppDatabase instance.
     */
    public static synchronized AppDatabase getDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "app_database")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return INSTANCE;
    }
}

