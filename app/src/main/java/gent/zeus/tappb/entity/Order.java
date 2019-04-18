package gent.zeus.tappb.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;

import androidx.annotation.Nullable;

public class Order extends Observable {

    private Map<Product, OrderProduct> products;

    public Order() {
        super();
        products = new HashMap<>();
    }

    public void addProducts(Product p, int count) {
        if (products.containsKey(p)) {
            products.get(p).addCount(count);
        } else {
            products.put(p, new OrderProduct(p, count));
        }
        setChanged();
        notifyObservers();
    }

    public void addProduct(Product p) {
        addProducts(p, 1);
    }

    public void addOrderProduct(OrderProduct orderProduct) {
        addProducts(orderProduct.getProduct(), orderProduct.getCount());
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
        setChanged();
        notifyObservers();
    }

    public List<OrderProduct> getOrderProducts() {
        return new ArrayList<>(products.values());
    }

    public void updateCount(Product p, int amount) {
        products.computeIfPresent(p, (k, v) -> {
            if (amount == 0) {
                return null;
            }
            v.setCount(amount);
            return v;
        });
        setChanged();
        notifyObservers();
    }
}
