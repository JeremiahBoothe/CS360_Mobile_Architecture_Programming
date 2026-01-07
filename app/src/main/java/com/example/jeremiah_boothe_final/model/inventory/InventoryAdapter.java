package com.example.jeremiah_boothe_final.model.inventory;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jeremiah_boothe_final.R;

public class InventoryAdapter
        extends PagedListAdapter<InventoryItem,
        InventoryAdapter.InventoryViewHolder> {

    private final OnItemClickListener listener;
    /**
     * Interface for handling item clicks.
     */
    public interface OnItemClickListener { void onItemClick(InventoryItem item); }
    /**
     * Constructor for InventoryAdapter.
     * @param listener OnItemClickListener to handle item clicks
     */
    public InventoryAdapter(OnItemClickListener listener) {
        super(DIFF_CALLBACK);
        this.listener = listener;
    }
    /**
     * DiffUtil callback for InventoryItem.
     */
    private static final DiffUtil.ItemCallback<InventoryItem> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<InventoryItem>() {
                @Override
                public boolean areItemsTheSame(InventoryItem oldItem, InventoryItem newItem) {
                    return oldItem.getId() == newItem.getId();
                }

                @Override
                public boolean areContentsTheSame(InventoryItem oldItem, InventoryItem newItem) {
                    return oldItem.equals(newItem);
                }
            };
    /**
     * Called when RecyclerView needs a new ViewHolder of the given view type to represent an item.
     * @param parent The ViewGroup into which the new View will be added after it is bound to an adapter position.
     * @param viewType The view type of the new View.
     * @return A new InventoryViewHolder that holds a View of the given view type.
     */
    @NonNull
    @Override
    public InventoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_layout, parent, false);
        return new InventoryViewHolder(view);
    }
    /**
     * Called by RecyclerView to display the data at the specified position.
     * @param holder The ViewHolder which should be updated to represent the contents of the
     *                 item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(@NonNull InventoryViewHolder holder, int position) {
        InventoryItem item = getItem(position);
        if (item != null) {
            holder.bind(item, listener);
        }
    }

    static class InventoryViewHolder extends RecyclerView.ViewHolder {

        TextView itemName;
        TextView itemDescription;
        TextView itemQuantity;
        /**
         * Constructor for InventoryViewHolder.
         * @param itemView The View used to display the data
         */
        InventoryViewHolder(@NonNull View itemView) {
            super(itemView);
            itemName = itemView.findViewById(R.id.item_name);
            itemDescription = itemView.findViewById(R.id.item_description);
            itemQuantity = itemView.findViewById(R.id.item_quantity);
        }
        /**
         * Binds the data to the view holder.
         * @param item       The InventoryItem to bind
         * @param listener   The OnItemClickListener to handle item clicks
         */
        void bind(final InventoryItem item, final OnItemClickListener listener) {
            itemName.setText(item.getName());
            itemDescription.setText(item.getDescription());
            itemQuantity.setText(String.valueOf(item.getQuantity()));

            // Handle item click
            itemView.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onItemClick(item);
                }
            });
        }
    }
}

