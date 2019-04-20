package gent.zeus.tappb.api;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.StrictMode;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.security.PrivilegedAction;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import gent.zeus.tappb.entity.Barcode;
import gent.zeus.tappb.entity.Product;
import gent.zeus.tappb.entity.ProductList;
import gent.zeus.tappb.entity.StockProduct;
import gent.zeus.tappb.entity.TapUser;
import gent.zeus.tappb.entity.Transaction;
import gent.zeus.tappb.entity.User;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class TapAPI extends API {

    private final static String endpoint = "https://tap.zeus.gent";

    private static Request.Builder buildRequest(String relativeURL) {
        // TODO remove this code, let api callers call this in another thread
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        return new Request.Builder()
                .url(endpoint + relativeURL)
                .header("Accept", "application/json")
                .header("Authorization", "Token " + User.getInstance().getTapToken());
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

    public static List<StockProduct> getStockProducts() {
        try {
            List<StockProduct> result = new ArrayList<>();
            JSONArray response = new JSONArray(getBody("/products.json"));
            for (int i = 0 ; i < response.length(); i++) {
                JSONObject obj = response.getJSONObject(i);
                int productID = obj.getInt("id");
                String name = obj.getString("name");
                int stock = obj.getInt("stock");
                double price = obj.getInt("price_cents") / 100.0;
                Product p = new Product(productID, name, price);
                StockProduct s = new StockProduct(p, stock);
                result.add(s);

            }
            return result;
        }
        catch (JSONException ex) {
            Log.d("exep", ex.toString());
            throw new APIException("Failed to parse JSON of request");
        }
    }

    public static TapUser getTapUser(User u) {
        try {
            JSONObject response = new JSONObject(getBody("/users/" + u.getUsername() + ".json"));
            String padded = String.format("%09d" , response.getInt("id"));
            List<String> splitID = splitString(padded, 3);

            String path = String.format(endpoint + "/system/users/avatars/%s/%s/%s/small/%s",
                    splitID.get(0),
                    splitID.get(1),
                    splitID.get(2),
                    response.getString("avatar_file_name"));

            URL url = new URL(path);
            Bitmap image = BitmapFactory.decodeStream(url.openConnection().getInputStream());
            String favoriteItemId = response.getString("dagschotel_id");
            Product favoriteItem = null;
            if (!favoriteItemId.equals("null")) {
                favoriteItem = ProductList.getInstance().getProductById(Integer.parseInt(favoriteItemId)).getProduct();
            }
            return new TapUser(response.getInt("id"), image, favoriteItem);
        } catch (JSONException exc) {
            Log.e("TapAPI", "JSON parse failed", exc);
            return null;
        } catch (IOException ex) {
            Log.e("TapAPI", "IO Exception", ex);
            return null;
        }
    }

    private static List<String> splitString(String string, int size) {
        List<String> res = new ArrayList<>();
        for (int i = 0; i < string.length(); i += size) {
            res.add(string.substring(i, Math.min(string.length(), i + size)));
        }
        return res;
    }

    public static List<Barcode> getBarcodes() {
        try {
            List<Barcode> result = new ArrayList<>();
            JSONArray response = new JSONArray(getBody("/barcodes.json"));
            for (int i = 0 ; i < response.length(); i++) {
                JSONObject obj = response.getJSONObject(i);
                Product p = ProductList.getInstance().getProductById(obj.getInt("product_id")).getProduct();
                Barcode s = new Barcode(obj.getString("code"), p);
                result.add(s);

            }
            return result;
        }
        catch (JSONException ex) {
            Log.d("exep", ex.toString());
            throw new APIException("Failed to parse JSON of request");
        }
    }
}
