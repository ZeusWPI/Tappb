package gent.zeus.tappb.ui;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.CookieManager;
import android.webkit.WebView;

import androidx.fragment.app.Fragment;

import gent.zeus.tappb.databinding.FragmentLoginBinding;
import gent.zeus.tappb.entity.User;
import gent.zeus.tappb.login.LoginWebviewClient;

import static android.content.Context.MODE_PRIVATE;

public class LoginFragment extends Fragment {

    private WebView webView;
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
        SharedPreferences tokenPreferences = getContext().getSharedPreferences("tokens", MODE_PRIVATE);
        if (tokenPreferences.contains(USERNAME_KEY)) {
            String username = tokenPreferences.getString(USERNAME_KEY, null);
            String tabToken = tokenPreferences.getString(TAB_TOKEN_KEY, null);
            String tapToken = tokenPreferences.getString(TAP_TOKEN_KEY, null);
            User.getInstance().load(username, tabToken, tapToken);
            Log.i("LOGIN", "succeeeded automatically REEEEEEEEEEEEEEEEEEEEEE");
            getFragmentManager().popBackStack();
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
                getFragmentManager().popBackStack();
            }
        });
        CookieManager.getInstance().removeAllCookie();
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl("https://tabbp.zeus.gent/login");
        return binding.getRoot();
    }
}
