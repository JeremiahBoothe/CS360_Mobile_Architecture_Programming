package com.example.jeremiah_boothe_final.model.notifications;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.example.jeremiah_boothe_final.R;
import com.example.jeremiah_boothe_final.model.inventory.InventoryItem;
import com.example.jeremiah_boothe_final.model.inventory.InventoryRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class InventoryNotificationWorker extends Worker {

    private final Context mContext;
    private final InventoryRepository mRepository;
    private final Handler mainThreadHandler = new Handler(Looper.getMainLooper());
    private final Set<Long> notifiedItemIds = new HashSet<>(); // Store IDs of items already notified
    /**
     * Constructor for InventoryNotificationWorker
     * @param context Context of the application
     * @param workerParams Worker parameters
     */
    public InventoryNotificationWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        mContext = context;
        mRepository = new InventoryRepository(context);
    }
    /**
     * Method to perform the work
     * @return Result of the work
     */
    @NonNull
    @Override
    public Result doWork() {
        try {
            int action = getInputData().getInt("action", 0);
            if (action == 1) { // Check for the integer action value
                int itemId = getInputData().getInt("itemId", 0);
                if (itemId != 0) {
                    removeNotificationForItem(itemId);
                }
            } else {
                // Handle regular notification logic here
                // For example, trigger notifications for items with zero quantity
                mRepository.checkItemsWithZeroQuantity(hasItemsWithZeroQuantity -> {
                    if (hasItemsWithZeroQuantity) {
                        mRepository.getItemsWithZeroQuantity(itemsWithZeroQuantity -> {
                            if (itemsWithZeroQuantity != null && !itemsWithZeroQuantity.isEmpty()) {
                                mainThreadHandler.post(() -> triggerNotification(itemsWithZeroQuantity));
                            }
                        });
                    }
                });
            }
            return Result.success();
        } catch (Throwable throwable) {
            Log.e("InventoryNotification", "Error executing work", throwable);
            return Result.failure();
        }
    }
    /**
     * Method to trigger notifications for items with zero quantity
     * @param itemsWithZeroQuantity List of items with zero quantity
     */
    private void triggerNotification(List<InventoryItem> itemsWithZeroQuantity) {
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(mContext);
        String channelId = "inventory_channel"; // Notification Channel ID
        String channelName = "Inventory Channel"; // Notification Channel Name

        // Create a notification channel if Android version is Oreo or higher
        NotificationChannel channel = new NotificationChannel(
                channelId,
                channelName,
                NotificationManager.IMPORTANCE_DEFAULT);
        notificationManager.createNotificationChannel(channel);

        // Build individual notifications for each item with zero quantity
        for (InventoryItem item : itemsWithZeroQuantity) {
            long itemId = item.getId();

            // Check if notification for this item has already been sent
            if (notifiedItemIds.contains(itemId)) {
                continue; // Skip this item, notification already sent
            }

            // Use item.getId() as the notification ID to ensure uniqueness
            int notificationId = (int) itemId;

            /**
             * TODO: Grouping doesn't appear to be working correctly although notifications post normally
             **/
            NotificationCompat.Builder builder = new NotificationCompat.Builder(mContext, channelId)
                    .setContentTitle("Inventory Update")
                    .setContentText(item.getName() + " has zero quantity.")
                    .setSmallIcon(R.drawable.placeholder_image)
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .setGroup("inventory_group"); // Group key to associate with the summary notification

            if (ActivityCompat.checkSelfPermission(mContext,
                    Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED) {
                notificationManager.notify(notificationId, builder.build());
                notifiedItemIds.add(itemId); // Mark item as notified
                } else {
                Log.e("InventoryNotification", "Permissions not granted for notifications");
            }
        }
    }
    /**
     * Method to remove notification for the specified item
     * @param itemId ID of the item to remove notification for
     */
    private void removeNotificationForItem(int itemId) {
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(mContext);
        notificationManager.cancel(itemId);
    }
}

