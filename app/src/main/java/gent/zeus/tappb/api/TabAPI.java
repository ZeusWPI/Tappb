package gent.zeus.tappb.api;

import android.os.StrictMode;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import gent.zeus.tappb.User;
import gent.zeus.tappb.entity.Transaction;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class TabAPI {
    public TabAPI() {
        Log.d("TAB", "hi");
    }

    private JSONArray getArray(String relativeURL) throws JSONException {
        OkHttpClient client = new OkHttpClient();

        // TODO remove this code, let api callers call this in another thread
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        Request request = new Request.Builder()
                .url("https://tab.zeus.gent" + relativeURL)
                .header("Accept", "application/json")
                .header("Authorization", "Token " + User.getInstance().getTabToken())
                .build();
        Log.d("apiurl", request.url().toString());
        try {
            Response response = client.newCall(request).execute();
            String body = response.body().string();
            Log.d("body", body);
            return new JSONArray(body);
        } catch (IOException ex) {
            throw new APIException("Failed to get body of request: " + relativeURL);
        }
    }

    public List<Transaction> getTransactions() {
        try {
            List<Transaction> result = new ArrayList<>();
            JSONArray response = getArray("/users/" + User.getInstance().getUsername() + "/transactions");
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
                Transaction t = new Transaction(transactionID, d, debtor, creditor, message, amount * 100.0);
            }
            return result;
        }
        catch (JSONException ex) {
            Log.d("exep", ex.toString());
            throw new APIException("Failed to parse JSON of request");
        }
    }
}
