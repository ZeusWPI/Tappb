package gent.zeus.tappb.navigation;

import android.util.Log;
import android.view.View;

import androidx.navigation.Navigation;
import gent.zeus.tappb.R;

public class NavigationHandlers {

    public void navToPlaceholder(View view) {
        Log.d("test", "test");
        Navigation.findNavController(view).navigate(R.id.action_nav_home_to_placeholder);
    }
}
