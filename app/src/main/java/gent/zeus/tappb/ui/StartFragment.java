package gent.zeus.tappb.ui;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import gent.zeus.tappb.R;
import gent.zeus.tappb.databinding.FragmentStartBinding;
import gent.zeus.tappb.viewmodel.OrderViewModel;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;


/**
 * A simple {@link Fragment} subclass.
 */
public class StartFragment extends Fragment {

    public StartFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentStartBinding binding = FragmentStartBinding.inflate(inflater, container, false);
        binding.setLifecycleOwner(this);

        View view = binding.getRoot();
        
        NavController navController = Navigation.findNavController(view.findViewById(R.id.nav_host_fragment));
        NavigationUI.setupWithNavController(binding.bottomNavigation, navController);

        OrderViewModel orderViewModel = ViewModelProviders.of(getActivity()).get(OrderViewModel.class);
        orderViewModel.init();

        return view;
    }
}
