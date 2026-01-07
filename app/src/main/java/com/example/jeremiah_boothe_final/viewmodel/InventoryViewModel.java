package com.example.jeremiah_boothe_final.viewmodel;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.paging.PagedList;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;
import androidx.work.WorkRequest;

import com.example.jeremiah_boothe_final.model.inventory.InventoryItem;
import com.example.jeremiah_boothe_final.model.inventory.InventoryRepository;
import com.example.jeremiah_boothe_final.model.notifications.InventoryNotificationWorker;
import com.example.jeremiah_boothe_final.model.notifications.NotificationScheduler;

import java.util.concurrent.TimeUnit;

public class InventoryViewModel extends AndroidViewModel {

    private final InventoryRepository repository;
    private final LiveData<PagedList<InventoryItem>> itemList;
    /**
     * Constructor for InventoryViewModel.
     * @param application The application context.
     */
    public InventoryViewModel(@NonNull Application application) {
        super(application);
        repository = new InventoryRepository(application);
        itemList = repository.getAllItems();
        NotificationScheduler.scheduleNotificationWorker(application.getApplicationContext());

        // Initialize InventoryNotificationWorker with appropriate WorkerParameters
        // Example initial delay
        WorkRequest inventoryWorkerRequest = new OneTimeWorkRequest.Builder(InventoryNotificationWorker.class)
                .setInitialDelay(5, TimeUnit.SECONDS) // Example initial delay
                .setInputData(new Data.Builder().build())
                .build();

        // Enqueue the worker request
        WorkManager.getInstance(application).enqueue(inventoryWorkerRequest);
    }
    /**
     * This method returns the LiveData list of items.
     * @return The LiveData list of items.
     * @deprecated PagedList in favor of PagingData
     */
    public LiveData<PagedList<InventoryItem>> getItemList() { return itemList; }
    /**
     * This method starts the notification worker.
     */
    public void startNotificationWorker() {
        NotificationScheduler.scheduleNotificationWorker(getApplication().getApplicationContext());
    }
    /**
     * This method stops the notification worker.
     */
    public void stopNotificationWorker() {
        NotificationScheduler.stopNotificationWorker(getApplication().getApplicationContext());
    }
    /**
     * This method adds an item to the database.
     * @param item The item to add.
     */
    public void addItem(InventoryItem item) { repository.insertItem(item); }
    /**
     * This method updates the item in the database and checks if there are any items with zero quantity.
     * @param item The item to update.
     */
    public void updateItem(InventoryItem item) {
        repository.updateItem(item);
        checkNotificationStatus();
        if (item.getQuantity() > 0) {
            removeNotificationForItem(item.getId());
        }
    }
    /**
     * This method deletes an item from the database.
     * @param item The item to delete.
     */
    public void deleteItem(InventoryItem item) {
        repository.deleteItem(item);
        checkNotificationStatus();
        if (item.getQuantity() < 1 ) {
            removeNotificationForItem(item.getId());
        }
    }
    /**
     * This method removes the notification for the specified item.
     * @param itemId The ID of the item to remove the notification for.
     */
    private void removeNotificationForItem(int itemId) {
        // Create a new work request to update or remove notification for the item
        WorkRequest updateNotificationRequest = new OneTimeWorkRequest.Builder(InventoryNotificationWorker.class)
                .setInputData(new Data.Builder().putInt("action", 1).putInt("itemId", itemId).build()) // Using integers for action
                .build();

        // Enqueue the update notification request
        WorkManager.getInstance(getApplication().getApplicationContext()).enqueue(updateNotificationRequest);
    }
    /**
     * This method checks if there are any items with zero quantity in the database.
     */
    private void checkNotificationStatus() {
        repository.checkItemsWithZeroQuantity(hasItemsWithZeroQuantity -> {
            if (hasItemsWithZeroQuantity) {
                startNotificationWorker();
            } else {
                stopNotificationWorker();
            }
        });
    }
}
