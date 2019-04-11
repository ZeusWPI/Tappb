package com.example.tappb;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tappb.databinding.FragmentStartBinding;


/**
 * A simple {@link Fragment} subclass.
 */
public class StartFragment extends Fragment {


    public StartFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentStartBinding binding = FragmentStartBinding.inflate(inflater, container, false);
        binding.setLifecycleOwner(this);

        //THIS CRASHES APP
        //FROM
        NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
        NavigationUI.setupWithNavController(binding.bottomNavigation, navController);
        //UNTIL

        return binding.getRoot();
    }
}
