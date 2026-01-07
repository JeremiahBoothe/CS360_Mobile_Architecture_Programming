package com.example.jeremiah_boothe_final.model.inventory;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.example.jeremiah_boothe_final.R;
import com.example.jeremiah_boothe_final.viewmodel.InventoryViewModel;

public class InventoryItemFragment extends Fragment {

    private TextView itemQuantityTextView;
    private InventoryViewModel viewModel;
    private InventoryItem currentItem;
    /**
     * Called to have the fragment instantiate its user interface view.
     * @param inflater The LayoutInflater object that can be used to inflate
     * any views in the fragment,
     * @param container If non-null, this is the parent view that the fragment's
     * UI should be attached to.  The fragment should not add the view itself,
     * but this can be used to generate the LayoutParams of the view.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     * from a previous saved state as given here.
     *
     * @return Return the View for the fragment's UI, or null.
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_inventory_item, container, false);

        TextView itemNameTextView = view.findViewById(R.id.item_name);
        TextView itemDescriptionTextView = view.findViewById(R.id.item_description);
        itemQuantityTextView = view.findViewById(R.id.item_quantity);
        ImageView itemImageView = view.findViewById(R.id.item_image);

        Button increaseButton = view.findViewById(R.id.button_increase_quantity);
        Button decreaseButton = view.findViewById(R.id.button_decrease_quantity);
        Button deleteButton = view.findViewById(R.id.button_delete_item);

        increaseButton.setOnClickListener(v -> updateQuantity(1));
        decreaseButton.setOnClickListener(v -> updateQuantity(-1));
        deleteButton.setOnClickListener(v -> deleteItem(currentItem));

        Bundle arguments = getArguments();
        if (arguments != null && arguments.containsKey("inventory_item")) {
            currentItem = arguments.getParcelable("inventory_item");
            if (currentItem != null) {
                itemNameTextView.setText(currentItem.getName());
                itemDescriptionTextView.setText(currentItem.getDescription());
                itemQuantityTextView.setText(String.valueOf(currentItem.getQuantity()));
            }
        }
        viewModel = new ViewModelProvider(requireActivity()).get(InventoryViewModel.class);

        return view;
    }
    /**
     * Deletes current item and returns to inventory screen
     * @param item The item to delete
     */
    private void deleteItem(InventoryItem item) {
        if (item != null) {
            viewModel.deleteItem(item);
            Toast.makeText(getContext(), "Item deleted", Toast.LENGTH_SHORT).show();
            NavHostFragment.findNavController(this).navigate(R.id.navigation_inventory);
        }
    }
    /**
     *
     * @param increment The amount to update the quantity by
     */
    private void updateQuantity(int increment) {
        if (currentItem != null) {
            int newQuantity = currentItem.getQuantity() + increment;
            currentItem.setQuantity(newQuantity);
            itemQuantityTextView.setText(String.valueOf(newQuantity));
            viewModel.updateItem(currentItem);
            Toast.makeText(getContext(), "Quantity updated", Toast.LENGTH_SHORT).show();
        }
    }
}
