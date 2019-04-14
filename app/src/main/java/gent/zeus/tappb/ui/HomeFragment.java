package gent.zeus.tappb.ui;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import gent.zeus.tappb.R;
import gent.zeus.tappb.databinding.FragmentHomeBinding;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {


    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentHomeBinding binding = FragmentHomeBinding.inflate(inflater, container, false);
        binding.setLifecycleOwner(this);
        return binding.getRoot();
    }

    public void navShoppingCart(View view) {
        Log.d("test", "test");
        NavController navController = Navigation.findNavController(view.findViewById(R.id.nav_host_fragment));
        navController.navigate(R.id.action_nav_home_to_placeholder);
    }
}
