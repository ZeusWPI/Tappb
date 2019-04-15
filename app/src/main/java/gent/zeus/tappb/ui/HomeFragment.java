package gent.zeus.tappb.ui;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Objects;

import androidx.viewpager.widget.PagerAdapter;
import gent.zeus.tappb.adapters.HomeViewPagerAdapter;
import gent.zeus.tappb.databinding.FragmentHomeBinding;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {
    PagerAdapter adapter;


    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentHomeBinding binding = FragmentHomeBinding.inflate(inflater, container, false);
        adapter = new HomeViewPagerAdapter(Objects.requireNonNull(this.getActivity()).getSupportFragmentManager());
        binding.viewPager.setAdapter(adapter);
        binding.setLifecycleOwner(this);

        return binding.getRoot();
    }

}
