package gent.zeus.tappb.ui;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.SwitchPreferenceCompat;

import gent.zeus.tappb.R;
import gent.zeus.tappb.entity.TapUser;
import gent.zeus.tappb.entity.User;
import gent.zeus.tappb.handlers.SettingsKoelkastListener;
import gent.zeus.tappb.repositories.UserRepository;

public class PreferenceFragment extends PreferenceFragmentCompat {

    private SharedPreferences.OnSharedPreferenceChangeListener listener;

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.preferences, rootKey);


        SwitchPreferenceCompat isPrivatePref = (SwitchPreferenceCompat) findPreference("ACCOUNT_PRIVATE");
        SwitchPreferenceCompat isFavoriteItemHiddenPref = (SwitchPreferenceCompat) findPreference("FAVORITE_ITEM_HIDDEN");
        UserRepository.getInstance().getIsPrivate().observe(this, (flag) -> {

            getPreferenceScreen().getSharedPreferences().edit()
                    .putBoolean("ACCOUNT_PRIVATE",flag)
                    .apply();
            isPrivatePref.setChecked(flag);
        });
        UserRepository.getInstance().getIsFavoriteItemHidden().observe(this, (flag) -> {
            getPreferenceScreen().getSharedPreferences().edit()
                    .putBoolean("FAVORITE_ITEM_HIDDEN", flag)
                    .apply();

            isFavoriteItemHiddenPref.setChecked(flag);
        });
        listener  = new SettingsKoelkastListener(isPrivatePref, isFavoriteItemHiddenPref);
    }

    @Override
    public void onResume() {
        super.onResume();

        getPreferenceScreen().getSharedPreferences()
                .registerOnSharedPreferenceChangeListener(listener);
    }

    @Override
    public void onPause() {
        super.onPause();

        getPreferenceScreen().getSharedPreferences()
                .unregisterOnSharedPreferenceChangeListener(listener);
    }
}