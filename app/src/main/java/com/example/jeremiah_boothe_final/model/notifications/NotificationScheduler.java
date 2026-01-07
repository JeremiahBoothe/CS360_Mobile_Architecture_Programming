package com.example.jeremiah_boothe_final.model.notifications;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.work.BackoffPolicy;
import androidx.work.Constraints;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.NetworkType;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import java.util.concurrent.TimeUnit;

public class NotificationScheduler {

    public static final String WORK_TAG = "inventory_notification_worker";
    /**
     * Schedules a periodic work request to run the InventoryNotificationWorker.
     * @param context The application context.
     */
    public static void scheduleNotificationWorker(@NonNull Context context) {
        Constraints constraints = new Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build();

        PeriodicWorkRequest notificationWorkRequest =
                new PeriodicWorkRequest.Builder(InventoryNotificationWorker.class, 1, TimeUnit.HOURS)
                        .setConstraints(constraints)
                        .setBackoffCriteria(BackoffPolicy.LINEAR, PeriodicWorkRequest.MIN_BACKOFF_MILLIS, TimeUnit.MILLISECONDS)
                        .build();

        WorkManager.getInstance(context)
                .enqueueUniquePeriodicWork(WORK_TAG, ExistingPeriodicWorkPolicy.CANCEL_AND_REENQUEUE, notificationWorkRequest);
        }
    /**
     * Cancels the periodic work request with the given tag.
     * @param context The application context.
     */
    public static void stopNotificationWorker(@NonNull Context context) {
        WorkManager.getInstance(context).cancelUniqueWork(WORK_TAG);
    }
}
