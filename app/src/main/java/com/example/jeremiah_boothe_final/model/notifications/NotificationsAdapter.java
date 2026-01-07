package com.example.jeremiah_boothe_final.model.notifications;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jeremiah_boothe_final.R;

public class NotificationsAdapter extends ListAdapter<NotificationsEntity, NotificationsAdapter.NotificationViewHolder> {
    /**
     * Constructor for NotificationsAdapter
     */
    protected NotificationsAdapter() {
        super(DIFF_CALLBACK);
    }
    /**
     * DiffUtil callback for NotificationsEntity
     */
    private static final DiffUtil.ItemCallback<NotificationsEntity> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<NotificationsEntity>() {
                @Override
                public boolean areItemsTheSame(NotificationsEntity oldItem,
                                               NotificationsEntity newItem)
                {
                    return oldItem.getId() == newItem.getId();
                }

                @Override
                public boolean areContentsTheSame(NotificationsEntity oldItem,
                                                  @NonNull NotificationsEntity newItem) {
                    return oldItem.equals(newItem);
                }
            };
    /**
     *
     * @param parent   The ViewGroup into which the new View will be added after it is bound to
     *                 an adapter position.
     * @param viewType The view type of the new View.
     * @return A new NotificationViewHolder that holds a View of the given view type.
     */
    @NonNull
    @Override
    public NotificationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_layout, parent, false);
        return new NotificationViewHolder(view);
    }
    /**
     *
     * @param holder   The ViewHolder which should be updated to represent the contents of the
     *                 item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(@NonNull NotificationViewHolder holder, int position) {
        NotificationsEntity notification = getItem(position);
        if (notification != null) {
            holder.bind(notification);
        }
    }

    static class NotificationViewHolder extends RecyclerView.ViewHolder {

        TextView itemName;
        TextView itemDescription;
        /**
         * Constructor for NotificationViewHolder
         * @param itemView The View that will be used to display the data
         */
        NotificationViewHolder(@NonNull View itemView) {
            super(itemView);
            itemName = itemView.findViewById(R.id.item_name);
            itemDescription = itemView.findViewById(R.id.item_description);
        }
        /**
         * Method to bind the data to the view
         * @param notification The notification to be bound
         */
        void bind(NotificationsEntity notification) {
            itemName.setText(notification.getTitle());
            itemDescription.setText(notification.getDescription());
        }
    }
}
