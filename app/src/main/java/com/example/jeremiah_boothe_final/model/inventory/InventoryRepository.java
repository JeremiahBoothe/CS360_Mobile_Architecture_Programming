package com.example.jeremiah_boothe_final.model.inventory;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import androidx.lifecycle.LiveData;
import androidx.paging.DataSource;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import com.example.jeremiah_boothe_final.model.AppDatabase;
import com.example.jeremiah_boothe_final.model.notifications.NotificationsEntity;

public class InventoryRepository {

    private final InventoryDao inventoryDao;
    private final Executor executor = Executors.newSingleThreadExecutor();
    private final Handler mainThreadHandler = new Handler(Looper.getMainLooper());
    /**
     * Constructor for InventoryRepository
     * @param context The application context.
     */
    public InventoryRepository(Context context) {
        AppDatabase database = AppDatabase.getDatabase(context);
        inventoryDao = database.inventoryDao();
    }
    /**
     * Deletes an item from the database.
     * @param item The item to be deleted.
     */
    public void deleteItem(InventoryItem item) {
        executor.execute(() -> inventoryDao.deleteItem(item));
    }
    /**
     * Inserts a new item into the database.
     * @param item The item to be inserted.
     */
    public void insertItem(InventoryItem item) {
        executor.execute(() -> inventoryDao.insert(item));
    }
    /**
     * Updates an existing item in the database.
     * @param item The item to be updated.
     */
    public void updateItem(InventoryItem item) {
        executor.execute(() -> inventoryDao.update(item));
    }
    /**
     * Deletes an item from the database.
     * @return The number of items deleted.
     */
    public LiveData<PagedList<InventoryItem>> getAllItems() {
        DataSource.Factory<Integer, InventoryItem> factory = inventoryDao.getAllItems();
        PagedList.Config config = new PagedList.Config.Builder()
                .setEnablePlaceholders(false)
                .setPageSize(20)
                .build();
        return new LivePagedListBuilder<>(factory, config).build();
    }
    /**
     * Retrieves an item from the database based on its ID.
     * @param callback The callback to be invoked when the item is retrieved.
     */
    public void checkItemsWithZeroQuantity(ItemCheckCallback callback) {
        executor.execute(() -> {
            int count = inventoryDao.checkItemsWithZeroQuantity();
            mainThreadHandler.post(() -> callback.onItemCheck(count > 0));
        });
    }
    /**
     * Retrieves a list of items with zero quantity from the database.
     * @param callback The callback to be invoked when the list of items is retrieved.
     */
    public void getItemsWithZeroQuantity(ItemListCallback callback) {
        executor.execute(() -> {
            List<InventoryItem> items = inventoryDao.getItemsWithZeroQuantity();
            mainThreadHandler.post(() -> callback.onItemList(items));
        });
    }
    /**
     * Callback interface for item check operations.
     */
    public interface ItemCheckCallback {
        void onItemCheck(boolean hasItemsWithZeroQuantity);
    }
    /**
     * Callback interface for item list operations.
     */
    public interface ItemListCallback {
        void onItemList(List<InventoryItem> items);
    }
}
