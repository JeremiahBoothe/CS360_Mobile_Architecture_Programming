package com.example.jeremiah_boothe_final.model.notifications;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jeremiah_boothe_final.R;
import com.example.jeremiah_boothe_final.viewmodel.NotificationsViewModel;

public class NotificationsFragment extends Fragment {

    private NotificationsAdapter adapter;
    /**
     * Constructor for NotificationsFragment.
     */
    public NotificationsFragment() {}
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
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(
                R.layout.fragment_notifications,
                container,
                false);

        RecyclerView recyclerView = view.findViewById(R.id.recycler_view_notifications);

        // Setup RecyclerView
        adapter = new NotificationsAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        // Initialize ViewModel
        NotificationsViewModel notificationsViewModel = new ViewModelProvider(this).get(NotificationsViewModel.class);

        // Observe ViewModel data
        notificationsViewModel.getAllNotifications().observe(getViewLifecycleOwner(), notifications -> {
            // Update UI with new list of notifications
            adapter.submitList(notifications);
        });
        return view;
    }
}
