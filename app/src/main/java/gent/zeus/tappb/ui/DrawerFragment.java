package gent.zeus.tappb.ui;


import android.os.Bundle;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import gent.zeus.tappb.R;
import gent.zeus.tappb.databinding.FragmentDrawerBinding;


public class DrawerFragment extends Fragment {

    DrawerLayout drawerLayout;

    public DrawerFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentDrawerBinding binding = FragmentDrawerBinding.inflate(inflater, container, false);
        binding.setLifecycleOwner(this);

        View view = binding.getRoot();

        NavController navController = Navigation.findNavController(view.findViewById(R.id.nav_host_fragment));
        NavigationUI.setupWithNavController(binding.navView, navController);

        Toolbar toolbar = binding.toolbar;
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);

        drawerLayout = binding.drawerLayout;
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(getActivity(), drawerLayout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        return view;
    }
}
