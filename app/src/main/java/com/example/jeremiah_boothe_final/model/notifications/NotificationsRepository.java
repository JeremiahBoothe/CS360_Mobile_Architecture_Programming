package com.example.jeremiah_boothe_final.model.notifications;

import android.app.Application;
import androidx.lifecycle.LiveData;
import java.util.List;

import com.example.jeremiah_boothe_final.model.AppDatabase;

public class NotificationsRepository {

    private final NotificationsDao notificationsDao;
    private final LiveData<List<NotificationsEntity>> allNotifications;
    /**
     * Constructor for NotificationsRepository.
     * @param application Application object
     */
    public NotificationsRepository(Application application) {
        AppDatabase database = AppDatabase.getDatabase(application);
        notificationsDao = database.notificationDao();
        allNotifications = notificationsDao.getAllNotifications();
    }
    /**
     * Get all notifications from the database.
     * @return LiveData list of notifications
     */
    public LiveData<List<NotificationsEntity>> getAllNotifications() {
        return allNotifications;
    }
    /**
     * Insert a notification into the database.
     * @param notification Notification object
     */
    public void insert(NotificationsEntity notification) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            notificationsDao.insert(notification);
        });
    }
    /**
     * Delete a notification from the database.
     * @param notification Notification object
     */
    public void delete(NotificationsEntity notification) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            notificationsDao.deleteItem(notification);
        });
    }
}

