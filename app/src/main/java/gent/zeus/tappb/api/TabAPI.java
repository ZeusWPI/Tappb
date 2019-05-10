package gent.zeus.tappb.api;

import android.os.StrictMode;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import gent.zeus.tappb.entity.User;
import gent.zeus.tappb.entity.Transaction;
import gent.zeus.tappb.repositories.UserRepository;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class TabAPI extends API {

    private static final OkHttpClient client = new OkHttpClient();

    private final static String endpoint = "https://tab.zeus.gent";

    private static Request.Builder buildRequest(String relativeURL) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        return new Request.Builder()
                .url(endpoint + relativeURL)
                .header("Accept", "application/json")
                .header("Authorization", "Token " + UserRepository.getInstance().getTabToken());
    }

    public static LiveData<List<Transaction>> getTransactions() {
        Request request = buildRequest("/users/" + UserRepository.getInstance().getUsername() + "/transactions").build();

        final MutableLiveData<List<Transaction>> transactions = new MutableLiveData<>();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                throw new APIException("Failed to fetch transactions");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                List<Transaction> result = new ArrayList<>();

                try {
                    JSONArray jsonArray = new JSONArray(response.body().string());
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject obj = jsonArray.getJSONObject(i);
                        int transactionID = obj.getInt("id");
                        String debtor = obj.getString("debtor");
                        String creditor = obj.getString("creditor");
                        String message = obj.getString("message");
//                        String issuer = obj.getString("issuer");
                        String timestring = obj.getString("time");
                        int amount = obj.getInt("amount");
                        OffsetDateTime d = OffsetDateTime.parse(timestring);
                        Transaction t = new Transaction(transactionID, d, debtor, creditor, message, amount * 100.0);
                        result.add(t);
                    }
                    transactions.postValue(result);
                } catch (JSONException ex) {
                    throw new APIException("Failed to parse JSON of request");
                }
            }
        });

        return transactions;
    }

    public static LiveData<Integer> getBalanceInCents() {
        Request request = buildRequest("/users/" + UserRepository.getInstance().getUsername()).build();

        final MutableLiveData<Integer> balance = new MutableLiveData<>();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                throw new APIException("Failed to fetch balance");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    if (UserRepository.getInstance().getUser().getValue() != null) {
                        balance.postValue(0);
                    } else {
                        JSONObject jsonObject = new JSONObject(response.body().string());
                        balance.postValue(jsonObject.getInt("balance"));
                    }
                }
                catch (JSONException ex) {
                    throw new APIException("Failed to parse JSON of request");
                }

            }
        });

        return balance;
    }

    public static LiveData<Boolean> createTransaction(String debtor, String creditor, int cents, String message) {
        final MutableLiveData<Boolean> isSucceeded = new MutableLiveData<>();

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

        RequestBody body = RequestBody.create(JSON, data.toString());
        Request request = buildRequest("/transactions")
                .post(body)
                .build();


        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                isSucceeded.postValue(response.isSuccessful());
            }
        });

        return isSucceeded;
    }
}
