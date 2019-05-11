package gent.zeus.tappb.ui;

import android.os.Bundle;

import androidx.preference.PreferenceFragmentCompat;

import gent.zeus.tappb.R;

public class PreferenceFragment extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.preferences, rootKey);
    }
}