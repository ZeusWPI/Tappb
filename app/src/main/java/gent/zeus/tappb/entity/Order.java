package gent.zeus.tappb.entity;

import android.annotation.SuppressLint;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class Order {

    @SuppressLint("UseSparseArrays")
    private Map<Integer, OrderProduct> orderItems = new HashMap<>();

    public void addItem(Product product, Integer amount) {
        this.orderItems.compute(product.getId(), (k, v) -> {
            if (v == null) {
                return new OrderProduct(product, amount);
            }
            v.addCount(amount);
            return v;
        });
    }

    public void addItem(OrderProduct product) {
        this.orderItems.compute(product.getId(), (k, v) -> {
            if (v == null) {
                return product;
            }
            v.addCount(product.getCount());
            return v;
        });
    }

    public void addItem(Product product) {
        addItem(product, 1);
    }

    public void deleteItem(Product product) {
        this.orderItems.remove(product.getId());
    }

    public void increaseCount(Product product) {
        if (this.orderItems.containsKey(product.getId())) {
            this.orderItems.get(product.getId()).addCount(1);
        }
    }

    public void decreaseCount(Product product) {
        if (this.orderItems.containsKey(product.getId())) {
            this.orderItems.get(product.getId()).addCount(-1);
        }
    }
    public void clear() {
        this.orderItems.clear();
    }

    public Collection<OrderProduct> getProductList() {
        return orderItems.values();
    }
}
