package gent.zeus.tappb.login;

import android.util.Log;
import android.webkit.CookieManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import gent.zeus.tappb.entity.User;

public class LoginWebviewClient extends WebViewClient {
    private static final String TAG = LoginActivity.class.getSimpleName();

    @Override
    public void onPageFinished(WebView view, String url){
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
                    // TODO tap token
                    User u = new User(reader.getString("username"), reader.getString("tab_token"), "");
                    navigateAway();
                } catch (JSONException | UnsupportedEncodingException ex) {
                    Log.d(TAG, "Error parsing JSON from Tabbp");
                }
            }
        } else {
            Log.d(TAG, "wrong url");
        }
    }

    public void navigateAway() {
        Log.d(TAG, "Navigating away from webview");
    }
}
