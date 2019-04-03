package com.example.tappb;

import android.util.Log;
import android.webkit.CookieManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.HttpCookie;
import java.net.URLDecoder;
import java.util.List;

public class LoginWebviewClient extends WebViewClient {
    private static final String TAG = LoginActivity.class.getSimpleName();

    @Override
    public void onPageFinished(WebView view, String url){
        Log.d(TAG, url);
        if ("https://tabbp.zeus.gent/tokens".equals(url)) {
            Log.d(TAG, "correct url");
            String cookies = CookieManager.getInstance().getCookie(url);
            List<HttpCookie> cookielist = HttpCookie.parse(cookies);
            Log.d(TAG, cookielist.toString());
            for (HttpCookie c : cookielist) {
                Log.d(TAG, c.getName());
                if ("X-Auth".equals(c.getName())) {
                    try {
                        String value = URLDecoder.decode(c.getValue(),  "UTF-8");
                        Log.d(TAG, value);
                        JSONObject reader = new JSONObject(value);
                        Log.d(TAG, reader.getString("username"));
                        // TODO set global creds
                    } catch (JSONException | UnsupportedEncodingException ex) {
                        Log.d(TAG, "Error parsing JSON from Tabbp");
                    }
                }
            }
        } else {
            Log.d(TAG, "wrong url");
        }
    }
}
