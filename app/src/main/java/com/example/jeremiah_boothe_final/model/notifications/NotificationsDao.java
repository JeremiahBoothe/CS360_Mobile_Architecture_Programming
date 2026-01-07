package com.example.jeremiah_boothe_final.model.notifications;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface NotificationsDao {
    /**
     * Retrieves all notifications from the database.
     * @return LiveData list of notifications.
     */
    @Query("SELECT * FROM notifications ORDER BY id DESC")
    LiveData<List<NotificationsEntity>> getAllNotifications();
    /**
     * Inserts a new notification into the database.
     * @param notification The notification to be inserted.
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(NotificationsEntity notification);
    /**
     * Deletes a notification from the database.
     * @param notification The notification to be deleted.
     */
    @Delete()
    void deleteItem(NotificationsEntity notification);

}
