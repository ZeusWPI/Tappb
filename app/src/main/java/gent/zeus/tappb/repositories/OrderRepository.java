package gent.zeus.tappb.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import gent.zeus.tappb.api.TapAPI;
import gent.zeus.tappb.entity.Order;
import gent.zeus.tappb.entity.OrderProduct;
import gent.zeus.tappb.entity.Product;

public class OrderRepository {
    private static final OrderRepository ourInstance = new OrderRepository();
    private MutableLiveData<Order> liveOrder = new MutableLiveData<>();
    private Order order = new Order();
    private TapAPI tapAPI = new TapAPI();

    public static OrderRepository getInstance() {
        return ourInstance;
    }

    private OrderRepository() {
    }

    public LiveData<Order> getLiveOrder() {
        return liveOrder;
    }

    public void addItem(Product product) {
        order.addItem(product);
        invalidateLiveOrder();
    }

    public void addItem(OrderProduct product) {
        order.addItem(product);
        invalidateLiveOrder();

    }

    public void clearOrder() {
        order.clear();
        invalidateLiveOrder();
    }

    private void invalidateLiveOrder() {
        liveOrder.postValue(order);
    }


    public void increaseCount(OrderProduct orderProduct) {
        order.increaseCount(orderProduct);
        invalidateLiveOrder();
    }

    public void decreasecount(OrderProduct orderProduct) {
        order.decreaseCount(orderProduct);
        invalidateLiveOrder();
    }

    public void makeOrder() {
        tapAPI.createOrder(order);
        order = new Order();
        invalidateLiveOrder();
    }
}
