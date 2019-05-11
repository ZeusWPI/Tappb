package gent.zeus.tappb.entity;

import android.annotation.SuppressLint;
import android.util.SparseArray;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class Stock {

    private Map<Integer, StockProduct> products;

    @SuppressLint("UseSparseArrays")
    public Stock() {
        this.products = new HashMap<>();
    }

    public Stock(Map<Integer, StockProduct> products) {
        this.products = products;
    }

    public StockProduct getProductById(Integer id) {
        return products.get(id);
    }

    public Collection<StockProduct> getProducts() {
        return products.values();
    }

    public void addProduct(StockProduct product) {
        products.put(product.getId(), product);
    }
}
