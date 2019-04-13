package gent.zeus.tappb.entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Order {
    private Map<Product, OrderProduct> products;
    public Order() {
        products = new HashMap<>();
    }

    public void addProduct(Product p) {
        if (products.containsKey(p)) {
            products.get(p).changeCount(1);
        } else {
            products.put(p, new OrderProduct(p, 1));
        }
    }

    public int getProductCount(Product p) {
        if (products.containsKey(p)) {
            return products.get(p).getCount();
        } else {
            return 0;
        }
    }

    public List<OrderProduct> getOrderProducts() {
        return new ArrayList<>(products.values());
    }
}
