package com.example.jeremiah_boothe_final.model.inventory;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jeremiah_boothe_final.R;
import com.example.jeremiah_boothe_final.viewmodel.InventoryViewModel;

public class InventoryFragment extends Fragment {

    private InventoryAdapter adapter;
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
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_inventory, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
        adapter = new InventoryAdapter(this::navigateToInventoryItemFragment);
        recyclerView.setAdapter(adapter);

        return view;
    }
    /**
     * Called after {@link #onCreateView(LayoutInflater, ViewGroup, Bundle)}
     * @param view The View returned by {@link #onCreateView(LayoutInflater, ViewGroup, Bundle)}.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     * from a previous saved state as given here.
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        InventoryViewModel viewModel = new ViewModelProvider(requireActivity()).get(InventoryViewModel.class);

        viewModel.getItemList().observe(getViewLifecycleOwner(), items -> {
            if (items != null) {
                adapter.submitList(items);
            } else {
                Toast.makeText(getContext(), "No items available", Toast.LENGTH_SHORT).show();
            }
        });
    }
    /**
     * This method navigates to the InventoryItemFragment with the specified item.
     * @param item The item to navigate to.
     */
    private void navigateToInventoryItemFragment(InventoryItem item) {
        Bundle bundle = new Bundle();
        bundle.putParcelable("inventory_item", item);
        NavHostFragment.findNavController(this).navigate(R.id.navigation_inventory_item, bundle);
    }
}
