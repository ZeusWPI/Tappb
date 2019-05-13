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

import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import gent.zeus.tappb.databinding.FragmentLoginBinding;
import gent.zeus.tappb.login.LoginWebviewClient;
import gent.zeus.tappb.repositories.BarcodeRepository;
import gent.zeus.tappb.repositories.StockRepository;
import gent.zeus.tappb.repositories.UserRepository;

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
            UserRepository.getInstance().load(username, tabToken, tapToken);
            getFragmentManager().popBackStack();
            loadData();
            return binding.getRoot();
        }

        webView = binding.loginwebviewid;
        webView.setWebViewClient(new LoginWebviewClient() {
            @Override
            public void navigateAway() {
                // save preferences
                SharedPreferences.Editor tokenEditor = tokenPreferences.edit();
                tokenEditor.putString(USERNAME_KEY, UserRepository.getInstance().getUser().getValue().getUsername());
                tokenEditor.putString(TAB_TOKEN_KEY, UserRepository.getInstance().getUser().getValue().getTabToken());
                tokenEditor.putString(TAP_TOKEN_KEY, UserRepository.getInstance().getUser().getValue().getTapToken());
                tokenEditor.apply();
                loadData();
                // upload device registration token
                Task<InstanceIdResult> task = FirebaseInstanceId.getInstance().getInstanceId();
                task.addOnSuccessListener(authResult -> {
                    String deviceRegistrationToken = authResult.getToken();
                    Log.i("registrationToken", deviceRegistrationToken);
                    UserRepository.getInstance().uploadDeviceRegistrationToken(deviceRegistrationToken);
                });
                getFragmentManager().popBackStack();
            }
        });
        CookieManager.getInstance().removeAllCookie();
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl("https://tabbp.zeus.gent/login");
        return binding.getRoot();
    }

    private void loadData() {
        // Initiate Stock
        StockRepository.getInstance();

        // Initiate User
        UserRepository.getInstance().fetchAll();

        BarcodeRepository.getInstance().fetchAll();
    }
}
