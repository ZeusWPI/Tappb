package gent.zeus.tappb.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.CookieManager;
import android.webkit.WebView;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import gent.zeus.tappb.R;
import gent.zeus.tappb.databinding.FragmentLoginBinding;
import gent.zeus.tappb.entity.User;
import gent.zeus.tappb.login.LoginWebviewClient;

import static android.content.Context.MODE_PRIVATE;

public class LoginFragment extends Fragment {

    private WebView webView;
    private NavController navController;
    private static String USERNAME_KEY = "username";
    private static String TAB_TOKEN_KEY = "tab_token";
    private static String TAP_TOKEN_KEY = "tap_token";

    public LoginFragment() {
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentLoginBinding binding = FragmentLoginBinding.inflate(inflater, container, false);
        navController = NavHostFragment.findNavController(this);
        SharedPreferences tokenPreferences = getContext().getSharedPreferences("tokens", MODE_PRIVATE);
        if (tokenPreferences.contains(USERNAME_KEY)) {
            String username = tokenPreferences.getString(USERNAME_KEY, null);
            String tabToken = tokenPreferences.getString(TAB_TOKEN_KEY, null);
            String tapToken = tokenPreferences.getString(TAP_TOKEN_KEY, null);
            User.getInstance().load(username, tabToken, tapToken);
            navController.navigate(R.id.action_nav_login_to_nav_home);
            return binding.getRoot();
        }

        webView = binding.loginwebviewid;
        webView.setWebViewClient(new LoginWebviewClient() {
            @Override
            public void navigateAway() {
                // save preferences
                SharedPreferences.Editor tokenEditor = tokenPreferences.edit();
                tokenEditor.putString(USERNAME_KEY, User.getInstance().getUsername());
                tokenEditor.putString(TAB_TOKEN_KEY, User.getInstance().getTabToken());
                tokenEditor.putString(TAP_TOKEN_KEY, User.getInstance().getTapToken());
                tokenEditor.apply();
                navController.navigate(R.id.action_nav_login_to_nav_home);
            }
        });
        CookieManager.getInstance().removeAllCookie();
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl("https://tabbp.zeus.gent/login");
        return binding.getRoot();
    }
}
