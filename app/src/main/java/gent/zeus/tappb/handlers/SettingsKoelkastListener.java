package gent.zeus.tappb.handlers;

import android.content.SharedPreferences;

import gent.zeus.tappb.entity.TapUser;
import gent.zeus.tappb.entity.User;

public class SettingsKoelkastListener implements SharedPreferences.OnSharedPreferenceChangeListener {
    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
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
    }
}
