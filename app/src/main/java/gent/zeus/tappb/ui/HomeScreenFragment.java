package gent.zeus.tappb.ui;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.navigation.fragment.NavHostFragment;
import gent.zeus.tappb.R;
import gent.zeus.tappb.databinding.FragmentHomeScreenBinding;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeScreenFragment extends Fragment implements HomeListener {


    public HomeScreenFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        FragmentHomeScreenBinding binding = FragmentHomeScreenBinding.inflate(inflater, container, false);
        binding.setLifecycleOwner(this);
        binding.setHandler(this);
        return binding.getRoot();
    }

    @Override
    public void onCartClicked() {
        NavHostFragment.findNavController(this).navigate(R.id.action_nav_home_to_nav_order);

    }

}
