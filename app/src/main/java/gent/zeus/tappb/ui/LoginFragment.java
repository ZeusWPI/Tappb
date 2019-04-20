package gent.zeus.tappb.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
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
import gent.zeus.tappb.login.LoginWebviewClient;

public class LoginFragment extends Fragment {

    private WebView webView;
    private NavController navController;

    public LoginFragment() {
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentLoginBinding binding = FragmentLoginBinding.inflate(inflater, container, false);
        navController = NavHostFragment.findNavController(this);
        webView = binding.loginwebviewid;
        webView.setWebViewClient(new LoginWebviewClient() {
            @Override
            public void navigateAway() {
                navController.navigate(R.id.action_nav_login_to_nav_home);
            }
        });
        CookieManager.getInstance().removeAllCookie();
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl("https://tabbp.zeus.gent/login");
        return binding.getRoot();
    }
}
