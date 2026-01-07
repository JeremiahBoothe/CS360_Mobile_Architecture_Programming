package com.example.jeremiah_boothe_final.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.jeremiah_boothe_final.model.notifications.NotificationsEntity;
import com.example.jeremiah_boothe_final.model.notifications.NotificationsRepository;

import java.util.List;

public class NotificationsViewModel extends AndroidViewModel {

    private final NotificationsRepository repository;
    private final LiveData<List<NotificationsEntity>> allNotifications;
    /**
     * Constructor for NotificationsViewModel.
     * @param application Application object
     */
    public NotificationsViewModel(@NonNull Application application) {
        super(application);
        repository = new NotificationsRepository(application);
        allNotifications = repository.getAllNotifications();
    }
    /**
     * Get all notifications from the repository.
     * @return LiveData list of notifications
     */
    public LiveData<List<NotificationsEntity>> getAllNotifications() { return allNotifications; }
    /**
     * Insert a notification into the repository.
     * @param notification Notification object
     */
    public void insert(NotificationsEntity notification) { repository.insert(notification); }
    /**
     * Delete a notification from the repository.
     * @param notification Notification object
     */
    public void delete(NotificationsEntity notification) { repository.delete(notification); }
}

