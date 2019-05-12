package gent.zeus.tappb.handlers;

import android.content.SharedPreferences;

import androidx.preference.SwitchPreferenceCompat;

import gent.zeus.tappb.entity.TapUser;
import gent.zeus.tappb.entity.User;
import gent.zeus.tappb.repositories.UserRepository;

public class SettingsKoelkastListener implements SharedPreferences.OnSharedPreferenceChangeListener {

    private SwitchPreferenceCompat isPrivatePref, isFavoriteItemHiddenPref;

    public SettingsKoelkastListener(SwitchPreferenceCompat isPrivatePref, SwitchPreferenceCompat isFavoriteItemHiddenPref) {
        this.isPrivatePref = isPrivatePref;
        this.isFavoriteItemHiddenPref = isFavoriteItemHiddenPref;
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        switch (key) {
            case "ACCOUNT_PRIVATE":
                UserRepository.getInstance().setPrivate(sharedPreferences.getBoolean("ACCOUNT_PRIVATE", false));
                break;
            case "FAVORITE_ITEM_HIDDEN":
                UserRepository.getInstance().setFavoriteItemHidden(sharedPreferences.getBoolean("FAVORITE_ITEM_HIDDEN", false));
                break;
            default:
                break;
        }
    }
}
