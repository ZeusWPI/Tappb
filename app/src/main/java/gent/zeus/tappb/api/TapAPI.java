package gent.zeus.tappb.api;

import android.os.StrictMode;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import gent.zeus.tappb.entity.Barcode;
import gent.zeus.tappb.entity.Stock;
import gent.zeus.tappb.entity.StockProduct;
import gent.zeus.tappb.entity.StockState;
import gent.zeus.tappb.entity.TapUser;
import gent.zeus.tappb.entity.User;
import gent.zeus.tappb.repositories.StockRepository;
import gent.zeus.tappb.repositories.UserRepository;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class TapAPI extends API {

    private static final OkHttpClient client = new OkHttpClient();

    private final static String endpoint = "https://tap.zeus.gent";
    private MutableLiveData<StockState> stockProducts = new MutableLiveData<>();
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

    public MutableLiveData<StockState> getStockProducts() {
        return stockProducts;
    }

    public MutableLiveData<List<Barcode>> getBarcodes() {
        return barcodes;
    }

    public MutableLiveData<TapUser> getTapUser() {
        return user;
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
                StockState result = new Stock();

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
                    JSONObject jsonObject = new JSONObject(response.body().string());
                    int id = jsonObject.getInt("id");
                    String imageURL = getImageURL(id, jsonObject.getString("avatar_file_name"));
                    String favoriteItemId = jsonObject.getString("dagschotel_id");

                    if (!favoriteItemId.equals("null")) {
                        user.setValue(new TapUser(id, imageURL, StockRepository.getInstance().getProductById(Integer.parseInt(favoriteItemId))));
                    }
                } catch (JSONException e) {
                    throw new APIException("Failed to parse JSON of request");
                }
            }
        });

    }

    public void fetchBarcodes() {
        Request request = buildRequest("/barcodes.json").build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@Nullable Call call, IOException e) {

            }

            @Override
            public void onResponse(@Nullable Call call, Response response) throws IOException {
                try {
                    List<Barcode> result = new ArrayList<>();
                    JSONArray jsonArray = new JSONArray(response.body().string());
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject obj = jsonArray.getJSONObject(i);
                        // TODO
//                        Product p = StockRepository.getInstance().getStock().getValue().getProductById()
//                        Barcode s = new Barcode(obj.getString("code"), p);
//                        result.add(s);
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

        return String.format(endpoint + "/system/products/avatars/%s/%s/%s/small/%s",
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
}
