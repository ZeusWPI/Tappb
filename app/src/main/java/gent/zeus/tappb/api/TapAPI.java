package gent.zeus.tappb.api;

import android.os.StrictMode;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import gent.zeus.tappb.entity.Barcode;
import gent.zeus.tappb.entity.Product;
import gent.zeus.tappb.entity.Stock;
import gent.zeus.tappb.entity.StockProduct;
import gent.zeus.tappb.entity.TapUser;
import gent.zeus.tappb.entity.User;
import gent.zeus.tappb.repositories.StockRepository;
import gent.zeus.tappb.repositories.UserRepository;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class TapAPI extends API {

    private static final OkHttpClient client = new OkHttpClient();

    private final static String endpoint = "https://tap.zeus.gent";
    private MutableLiveData<Stock> stockProducts = new MutableLiveData<>();
    private MutableLiveData<List<Barcode>> barcodes = new MutableLiveData<>();
    private MutableLiveData<TapUser> user = new MutableLiveData<>();

    private Request.Builder buildRequest(String relativeURL) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        try {

            return new Request.Builder()
                    .url(endpoint + relativeURL)
                    .header("Accept", "application/json")
                    .header("Authorization", "Token " + UserRepository.getInstance().getUser().getValue().getTapToken());
        } catch (RuntimeException e) {
            throw new APIException("Not logged in");
        }
    }

    public MutableLiveData<Stock> getStockProducts() {
        return stockProducts;
    }

    public MutableLiveData<List<Barcode>> getBarcodes() {
        return barcodes;
    }

    public MutableLiveData<TapUser> getTapUser() {
        return user;
    }

    private String getBody(String relativeURL) {
        OkHttpClient client = new OkHttpClient();
        Request request = buildRequest(relativeURL).build();
        try {
            Response response = client.newCall(request).execute();
            return response.body().string();
        } catch (IOException ex) {
            Log.e("TapAPI", "Error", ex);
            throw new APIException("Failed to get body of request: " + relativeURL);
        }
    }


    private String putBody(String relativeURL, String jsondata) {
        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(JSON, jsondata);
        Request request = buildRequest(relativeURL)
                .put(body)
                .header("Content-Type", "application/json")
                .build();
        try {
            Response response = client.newCall(request).execute();
            return response.body().string();
        } catch (IOException ex) {
            throw new APIException("Failed to get body of request: " + relativeURL);
        }
    }

    public void fetchStockProduct() {
        Request request = buildRequest("/products.json").build();


        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                throw new APIException("Failed to fetch stock");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Stock result = new Stock();

                try {
                    JSONArray jsonArray = new JSONArray(response.body().string());
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject obj = jsonArray.getJSONObject(i);
                        int productID = obj.getInt("id");
                        String name = obj.getString("name");
                        int stock = obj.getInt("stock");
                        double price = obj.getInt("price_cents") / 100.0;
                        String pictureName = obj.getString("avatar_file_name");
                        String pictureURL = getImageURL(productID, pictureName);
                        StockProduct p = new StockProduct(productID, name, price, pictureURL, stock);
                        result.addProduct(p);
                    }
                    stockProducts.postValue(result);
                } catch (JSONException e) {
                    throw new APIException("Failed to parse JSON of request");
                }
            }
        });
    }


    public void fetchTapUser(User u) {
        Request request = buildRequest("/users/" + u.getUsername() + ".json").build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@Nullable Call call, IOException e) {
                throw new APIException("Failed to fetch user");
            }

            @Override
            public void onResponse(@Nullable Call call, Response response) throws IOException {
                try {
                    user.postValue(parseTapUser(response.body().string()));
                } catch (Exception ex) {
                    throw new APIException("Failed to get body of request: ");
                }
            }
        });
    }

    private TapUser parseTapUser(String body) throws JSONException {
        JSONObject jsonObject = new JSONObject(body);
        int id = jsonObject.getInt("id");
        String imageURL = getProfileImageUrl(id, jsonObject.getString("avatar_file_name"));
        String favoriteItemId = jsonObject.getString("dagschotel_id");
        boolean isPrivate = jsonObject.getBoolean("private");
        boolean quickPay = jsonObject.getBoolean("quickpay_hidden");
        Integer favId = null;
        if (!favoriteItemId.equals("null")) {
            favId = Integer.parseInt(favoriteItemId);
        }
        return new TapUser(id, imageURL, favId, isPrivate, quickPay);

    }

    public void fetchBarcodes() {
        fetchStockProduct();

        Request request = buildRequest("/barcodes.json").build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@Nullable Call call, IOException e) {
                Log.e("TapAPI", "Could not read barcodes", e);
            }

            @Override
            public void onResponse(@Nullable Call call, Response response) throws IOException {
                try {
                    List<Barcode> result = new ArrayList<>();
                    JSONArray jsonArray = new JSONArray(response.body().string());
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject obj = jsonArray.getJSONObject(i);
                        Product p = StockRepository.getInstance().getProductById(obj.getInt("id"));
                        Barcode s = new Barcode(obj.getString("code"), p);
                        result.add(s);
                    }
                    barcodes.postValue(result);
                } catch (JSONException e) {
                    throw new APIException("Failed to parse JSON of request");
                }
            }
        });
    }

    private String getImageURL(int id, String imageName) {
        String paddedID = String.format("%09d", id);
        List<String> splitID = splitString(paddedID, 3);

        return String.format(endpoint + "/system/products/avatars/%s/%s/%s/medium/%s",
                splitID.get(0),
                splitID.get(1),
                splitID.get(2),
                imageName);
    }

    private String getProfileImageUrl(int id, String imageName) {
        String paddedID = String.format("%09d", id);
        List<String> splitID = splitString(paddedID, 3);

        return String.format(endpoint + "/system/users/avatars/%s/%s/%s/medium/%s",
                splitID.get(0),
                splitID.get(1),
                splitID.get(2),
                imageName);
    }

    private List<String> splitString(String string, int size) {
        List<String> res = new ArrayList<>();
        for (int i = 0; i < string.length(); i += size) {
            res.add(string.substring(i, Math.min(string.length(), i + size)));
        }
        return res;
    }

    public void setFavoriteItem(User user, Product p) {
        putBody("/users/" + user.getUsername() + ".json", String.format("{\"dagschotel_id\":\"%d\"}", p.getId()));
        fetchTapUser(user);
    }

    public void setPrivate(User user, boolean aPrivate) {
        String body = putBody("/users/" + user.getUsername() + ".json", String.format("{\"private\":\"%b\"}", aPrivate));
        try {
            this.user.postValue(parseTapUser(body));
        } catch (Exception ex) {
            throw new APIException("Failed to get body of request: ");
        }
    }

    public void setFavoriteItemHidden(User user, boolean favoriteItemHidden) {
        String body = putBody("/users/" + user.getUsername() + ".json", String.format("{\"quickpay_hidden\":\"%b\"}", favoriteItemHidden));
        try {
            this.user.postValue(parseTapUser(body));
        } catch (Exception ex) {
            throw new APIException("Failed to get body of request: ");
        }
    }
}
