package com.example.jeremiah_boothe_final.model.notifications;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import java.util.Objects;

@Entity(tableName = "notifications")
public class NotificationsEntity {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String title;
    private String description;
    /**
     * Constructor for NotificationsEntity
     * @param title Notification title
     * @param description Notification description
     */
    public NotificationsEntity(String title, String description) {
        this.title = title;
        this.description = description;
    }
    /**
     * Getters and Setters
     */
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    /**
     * Override equals and hashCode methods
     * @param o Object to compare with
     * @return True if the objects are equal, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        NotificationsEntity that = (NotificationsEntity) o;

        if (id != that.id) return false;
        if (!Objects.equals(title, that.title)) return false;
        return Objects.equals(description, that.description);
    }
    /**
     * Override hashCode method
     * @return Hash code of the object
     */
    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        return result;
    }
}
