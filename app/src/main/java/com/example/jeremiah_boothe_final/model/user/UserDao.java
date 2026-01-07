package com.example.jeremiah_boothe_final.model.user;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public interface UserDao {
    /**
     * Inserts a new user into the database.
     * @param user The user to be inserted.
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(User user);
    /**
     * Retrieves a user from the database based on their username and password.
     * @param username The username of the user to be retrieved.
     * @param password The password of the user to be retrieved.
     * @return The user object if found, or null if not found.
     */
    @Query("SELECT * FROM users WHERE username = :username AND password = :password")
    User login(String username, String password);
    /**
     * Retrieves a user from the database based on their username.
     * @param username The username of the user to be retrieved.
     * @return The user object if found, or null if not found.
     */
    @Query("SELECT * FROM users WHERE username = :username")
    User getUser(String username);
}
