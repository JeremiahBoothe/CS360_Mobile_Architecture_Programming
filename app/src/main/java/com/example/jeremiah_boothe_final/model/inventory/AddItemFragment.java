package com.example.jeremiah_boothe_final.model.inventory;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.example.jeremiah_boothe_final.R;
import com.example.jeremiah_boothe_final.viewmodel.InventoryViewModel;

public class AddItemFragment extends Fragment {

    private EditText itemNameEditText;
    private EditText itemDescriptionEditText;
    private EditText itemQuantityEditText;
    private InventoryViewModel viewModel;
    /**
     * Called to have the fragment instantiate its user interface view.
     * @param inflater The LayoutInflater object that can be used to inflate
     * any views in the fragment,
     * @param container If non-null, this is the parent view that the fragment's
     * UI should be attached to.  The fragment should not add the view itself,
     * but this can be used to generate the LayoutParams of the view.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     * from a previous saved state as given here.
     * @return Return the View for the fragment's UI, or null.
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_item, container, false);

        itemNameEditText = view.findViewById(R.id.editText_itemName);
        itemDescriptionEditText = view.findViewById(R.id.editText_itemDescription);
        itemQuantityEditText = view.findViewById(R.id.editText_itemQuantity);
        Button saveButton = view.findViewById(R.id.button_addItem);

        saveButton.setOnClickListener(v -> {
            String itemName = itemNameEditText.getText().toString().trim();
            String itemDescription = itemDescriptionEditText.getText().toString().trim();
            String itemQuantityStr = itemQuantityEditText.getText().toString().trim();

            // Check if any of the fields are empty
            if (itemName.isEmpty() || itemDescription.isEmpty() || itemQuantityStr.isEmpty()) {
                // Show a Toast message to the user
                Toast.makeText(requireContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show();
                return; // Exit the method if any field is empty
            }

            try {
                // Parse the itemQuantity from the string
                int itemQuantity = Integer.parseInt(itemQuantityStr);

                // Create a new InventoryItem object
                InventoryItem newItem = new InventoryItem(itemName, itemDescription, "".getBytes(), itemQuantity);

                // Add the new item using the ViewModel
                viewModel.addItem(newItem);

                // Pop the back stack to return to the previous fragment
                requireActivity().getSupportFragmentManager().popBackStack();
            } catch (NumberFormatException e) {
                // Show a Toast message if itemQuantity is not a valid number
                Toast.makeText(requireContext(), "Please enter a valid quantity", Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }
    /**
     * Called when the fragment's activity has been created.
     * @param savedInstanceState If the fragment is being re-created from
     * a previous saved state, this is the state.
     */
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(InventoryViewModel.class);
    }
}
