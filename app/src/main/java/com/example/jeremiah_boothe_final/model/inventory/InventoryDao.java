package com.example.jeremiah_boothe_final.model.inventory;

import androidx.paging.DataSource;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import java.util.List;

@Dao
public interface InventoryDao {
    /**
     * Get all items from the inventory table.
     * @return A DataSource.Factory that provides a PagedList of InventoryItem objects.
     */
    @Query("SELECT * FROM inventory")
    DataSource.Factory<Integer, InventoryItem> getAllItems();
    /**
     * Insert an item into the inventory table.
     * @param item The InventoryItem object to be inserted.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(InventoryItem item);
    /**
     * Delete an item from the inventory table.
     * @param item The InventoryItem object to be deleted.
     */
    @Delete()
    void deleteItem(InventoryItem item);
    /**
     * Update an item in the inventory table.
     * @param item The InventoryItem object to be updated.
     */
    @Update(onConflict = OnConflictStrategy.REPLACE)
    void update(InventoryItem item);
    /**
     * Check for items with zero quantity.
     * @return The number of items with zero quantity.
     */
    @Query("SELECT COUNT(*) FROM inventory WHERE quantity = 0")
    int checkItemsWithZeroQuantity();
    /**
     * Get items with zero quantity.
     * @return A list of InventoryItem objects with zero quantity.
     */
    @Query("SELECT * FROM inventory WHERE quantity = 0")
    List<InventoryItem> getItemsWithZeroQuantity();
}
