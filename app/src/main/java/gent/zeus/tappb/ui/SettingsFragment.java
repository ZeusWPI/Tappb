package gent.zeus.tappb.ui;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import gent.zeus.tappb.R;
import gent.zeus.tappb.databinding.FragmentSettingsBinding;


public class SettingsFragment extends Fragment {

    private FragmentSettingsBinding binding;

    public SettingsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSettingsBinding.inflate(inflater, container, false);
        binding.setLifecycleOwner(this);

        getFragmentManager().beginTransaction().add(R.id.settings_container, new PreferenceFragment()).commit();

        return binding.getRoot();
    }

}
