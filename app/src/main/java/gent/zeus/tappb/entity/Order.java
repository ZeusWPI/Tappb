package gent.zeus.tappb.entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.Nullable;

public class Order {

    private Map<Product, OrderProduct> products;

    public Order() {
        products = new HashMap<>();
    }

    public void addProducts(Product p, int count) {
        if (products.containsKey(p)) {
            products.get(p).changeCount(count);
        } else {
            products.put(p, new OrderProduct(p, count));
        }
    }

    public void addProduct(Product p) {
        addProducts(p, 1);
    }

    public int getProductCount(Product p) {
        if (products.containsKey(p)) {
            return products.get(p).getCount();
        } else {
            return 0;
        }
    }

    public void combine(@Nullable Order o) {
        if (o == null) {
            return;
        }
        for (OrderProduct orderProduct : o.getOrderProducts()) {
            addProducts(orderProduct.getProduct(), orderProduct.getCount());
        }
    }

    public List<OrderProduct> getOrderProducts() {
        return new ArrayList<>(products.values());
    }

    public void updateCount(Product p, int amount) {
        OrderProduct orderProduct = products.get(p);
        products.remove(p);
        if (amount != 0) {
            products.put(p, new OrderProduct(p, amount));
        }
    }
}
