package com.example.jeremiah_boothe_final.model.inventory;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import java.util.Arrays;
import java.util.Objects;

@Entity(tableName = "inventory")
public class InventoryItem implements Parcelable
{
    @PrimaryKey(autoGenerate = true)
    private int id;

    private String name;
    private String description;
    private int quantity;
    private byte[] image;
    /**
     * Constructor for InventoryItem
     * @param in Parcel object to read from
     */
    protected InventoryItem(Parcel in)
    {
        id = in.readInt();
        name = in.readString();
        description = in.readString();
        image = in.createByteArray();
        quantity = in.readInt();
    }
    /**
     * This is a Parcelable Creator
     * @param in Parcel object to read from
     * @return InventoryItem object
     */
    public static final Creator<InventoryItem> CREATOR = new Creator<InventoryItem>() {
        @Override
        public InventoryItem createFromParcel(Parcel in) {
            return new InventoryItem(in);
        }

        @Override
        public InventoryItem[] newArray(int size) {
            return new InventoryItem[size];
        }
    };
    /**
     * Writes the object's data to the Parcel.
     * @param dest The Parcel in which the object should be written.
     * @param flags Additional flags about how the object should be written.
     * May be 0 or {@link #PARCELABLE_WRITE_RETURN_VALUE}.
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(description);
        dest.writeInt(quantity);
    }
    /**
     *
     * @return The number of bytes that this object occupies in memory.
     */
    @Override
    public int describeContents() { return 0; }
    /**
     * Constructor for InventoryItem
     * @param name Name of the item
     * @param description Description of the item
     * @param image Image of the item
     * @param quantity Quantity of the item
     */
    public InventoryItem(String name,
                         String description,
                         byte[] image,
                         int quantity)
    {
        this.name = name;
        this.description = description;
        this.image = image;
        this.quantity = quantity;
    }
    /**
     * Getters and Setters
     */
    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }

    public void setDescription(String description) {this.description = description; }

    public byte[] getImageUrl() { return image; }

    public void setImageUrl(byte[] imageUrl) { this.image = imageUrl; }

    public int getQuantity() { return quantity; }

    public void setQuantity(int quantity) { this.quantity = quantity; }

    public byte[] getImage() { return image; }
    /**
     *
     * @param context Context of the application
     * @return Drawable representation of the image
     */
    public Drawable getImageDrawable(Context context) {
        if (image != null) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(image, 0, image.length);
            return new BitmapDrawable(context.getResources(), bitmap);
        }
        return null;
    }
    /**
     *
     * @param image Image data as a byte array
     */
    public void setImage(byte[] image) { this.image = image; }
    /**
     *
     * @param obj Object to compare with
     * @return True if the objects are equal, false otherwise
     */
    @Override
    public boolean equals(Object obj)
    {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        InventoryItem other = (InventoryItem) obj;
        return id == other.id &&
                Objects.equals(name, other.name) &&
                Objects.equals(description, other.description) &&
                Arrays.equals(image, other.image) &&
                Objects.equals(quantity, other.quantity);
    }
    /**
     *
     * @return Hash code of the object
     * @see Object#hashCode()
     */
    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, Arrays.hashCode(image), quantity);
    }
}
