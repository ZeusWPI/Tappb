package gent.zeus.tappb.handlers;

import android.content.SharedPreferences;

import androidx.preference.SwitchPreferenceCompat;

import gent.zeus.tappb.entity.TapUser;
import gent.zeus.tappb.entity.User;

public class SettingsKoelkastListener implements SharedPreferences.OnSharedPreferenceChangeListener {

    private SwitchPreferenceCompat isPrivatePref, isFavoriteItemHiddenPref;

    public SettingsKoelkastListener(SwitchPreferenceCompat isPrivatePref, SwitchPreferenceCompat isFavoriteItemHiddenPref) {
        this.isPrivatePref = isPrivatePref;
        this.isFavoriteItemHiddenPref = isFavoriteItemHiddenPref;
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        User.getInstance().updateTapUser();
        TapUser tapUser = User.getInstance().getTapUser();
        switch (key) {
            case "ACCOUNT_PRIVATE":
                tapUser.setPrivate(sharedPreferences.getBoolean("ACCOUNT_PRIVATE", tapUser.isPrivate()));
                break;
            case "FAVORITE_ITEM_HIDDEN":
                tapUser.setFavoriteItemHidden(sharedPreferences.getBoolean("FAVORITE_ITEM_HIDDEN", tapUser.isFavoriteItemHidden()));
                break;
            default:
                break;
        }
        isPrivatePref.setChecked(tapUser.isPrivate());
        isFavoriteItemHiddenPref.setChecked(tapUser.isFavoriteItemHidden());
    }
}
