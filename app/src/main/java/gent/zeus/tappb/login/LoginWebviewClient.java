package gent.zeus.tappb.login;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.fragment.NavHostFragment;
import gent.zeus.tappb.R;
import gent.zeus.tappb.entity.User;
import gent.zeus.tappb.api.TabAPI;
import gent.zeus.tappb.repositories.UserRepository;
import gent.zeus.tappb.ui.LoginFragment;
import gent.zeus.tappb.viewmodel.AccountViewModel;

public class LoginWebviewClient extends WebViewClient {
    private static final String TAG = LoginFragment.class.getSimpleName();

    @Override
    public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);
        Log.d(TAG, url);
        if ("https://tabbp.zeus.gent/tokens".equals(url)) {
            Log.d(TAG, "correct url");
            String cookies = CookieManager.getInstance().getCookie(url);
            Log.d(TAG, cookies);
            // This is bad, but HttpCookie.parse only parses the first cookie
            Pattern p = Pattern.compile("X-Auth=+([^\\s;]*)");
            Matcher m = p.matcher(cookies);
            if (m.find()) {
                try {
                    String value = URLDecoder.decode(m.group(1), "UTF-8");
                    Log.d(TAG, value);
                    JSONObject reader = new JSONObject(value);
                    Log.d(TAG, reader.getString("username"));
                    // TODO save user
                    UserRepository.getInstance().load(reader.getString("username"), reader.getString("tab_token"), reader.getString("tap_token"));
                    navigateAway();
                } catch (JSONException | UnsupportedEncodingException ex) {
                    Log.d(TAG, "Error parsing JSON from Tabbp");
                }
            }
        } else {
            Log.d(TAG, "wrong url");
        }
    }

    @Override
    public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
        switch (error.getErrorCode()) {
            case WebViewClient.ERROR_HOST_LOOKUP:
                Toast.makeText(view.getContext(), R.string.no_internet_conn, Toast.LENGTH_LONG).show();
                navigateAway();
        }
    }

    public void navigateAway() {
        Log.d(TAG, "Navigating away from webview");
    }
}
