package gent.zeus.tappb.entity;

import java.util.HashMap;
import java.util.Map;

public class Order {
    private Map<Product, Integer> products;
    public Order() {
        products = new HashMap<>();
    }

    public void addProduct(Product p) {
        products.put(p, getProductCount(p) + 1);
    }

    public int getProductCount(Product p) {
        return products.getOrDefault(p, 0);
    }
}
