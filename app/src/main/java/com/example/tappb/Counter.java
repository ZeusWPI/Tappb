package com.example.tappb;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tappb.databinding.FragmentCounterBinding;



/**
 * A simple {@link Fragment} subclass.
 */
public class Counter extends Fragment {

    private static final String TAG = Counter.class.getSimpleName();

    public Counter() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        FragmentCounterBinding binding = FragmentCounterBinding.inflate(inflater, container, false);
        binding.setHandler(this);

        return binding.getRoot();

    }
    public View doTheThing(View view) {
        Log.d(TAG, "doTheThing: boi");
        return view;
    }
}
