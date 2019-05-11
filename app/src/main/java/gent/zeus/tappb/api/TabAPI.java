package gent.zeus.tappb.api;

import android.os.StrictMode;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import gent.zeus.tappb.entity.User;
import gent.zeus.tappb.entity.Transaction;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class TabAPI extends API {
    public TabAPI() {
        Log.d("TAB", "hi");
    }

    private final static String endpoint = "https://tab.zeus.gent";

    private static Request.Builder buildRequest(String relativeURL) {
        // TODO remove this code, let api callers call this in another thread
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        return new Request.Builder()
                .url(endpoint + relativeURL)
                .header("Accept", "application/json")
                .header("Authorization", "Token " + User.getInstance().getTabToken());
    }

    private static String getBody(String relativeURL) {
        OkHttpClient client = new OkHttpClient();
        Request request = buildRequest(relativeURL).build();
        try {
            Response response = client.newCall(request).execute();
            return response.body().string();
        } catch (IOException ex) {
            throw new APIException("Failed to get body of request: " + relativeURL);
        }
    }

    private static String postBody(String relativeURL, String jsondata) {
        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(JSON, jsondata);
        Request request = buildRequest(relativeURL)
                .post(body)
                .build();
        try {
            Response response = client.newCall(request).execute();
            return response.body().string();
        } catch (IOException ex) {
            throw new APIException("Failed to get body of request: " + relativeURL);
        }
    }

    public static List<Transaction> getTransactions() {
        try {
            List<Transaction> result = new ArrayList<>();
            JSONArray response = new JSONArray(getBody("/users/" + User.getInstance().getUsername() + "/transactions"));
            for (int i = 0 ; i < response.length(); i++) {
                JSONObject obj = response.getJSONObject(i);
                int transactionID = obj.getInt("id");
                String debtor = obj.getString("debtor");
                String creditor = obj.getString("creditor");
                String message = obj.getString("message");
                String issuer = obj.getString("issuer");
                String timestring = obj.getString("time");
                int amount = obj.getInt("amount");
                OffsetDateTime d = OffsetDateTime.parse(timestring);
                Transaction t = new Transaction(transactionID, d, debtor, creditor, message, amount / 100.0);
                result.add(t);
            }
            Collections.reverse(result);
            return result;
        }
        catch (JSONException ex) {
            Log.d("exep", ex.toString());
            throw new APIException("Failed to parse JSON of request");
        }
    }

    public static int getBalanceInCents() {
        try {
            if (!User.getInstance().isLoaded()) {
                return 0;
            }
            JSONObject response = new JSONObject(getBody("/users/" + User.getInstance().getUsername()));
            return response.getInt("balance");
        }
        catch (JSONException ex) {
            Log.d("exep", ex.toString());
            throw new APIException("Failed to parse JSON of request");
        }
    }

    public static boolean createTransaction(String debtor, String creditor, int cents, String message) {
        JSONObject data = new JSONObject();
        JSONObject transaction = new JSONObject();
        try {
            transaction.put("debtor", debtor);
            transaction.put("creditor", creditor);
            transaction.put("cents", cents);
            transaction.put("message", message);
            data.put("transaction", transaction);
        } catch (JSONException ex) {
            throw new APIException("Failed to create JSON");
        }

        String response = postBody("/transactions", data.toString());
        Log.d("TabAPI","Got response" + response);
        // TODO check if transaction succeeded
        return true;
    }
}
