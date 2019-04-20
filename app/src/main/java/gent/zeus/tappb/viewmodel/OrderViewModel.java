package gent.zeus.tappb.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import gent.zeus.tappb.entity.OrderProduct;
import gent.zeus.tappb.entity.Product;

public class OrderViewModel extends ViewModel {
    private Map<Product, OrderProduct> orderMap = new HashMap<>();
    private MutableLiveData<List<OrderProduct>> orderProductLive = new MutableLiveData<>();
    private MutableLiveData<ScanningState> scanningState = new MutableLiveData<>();
    private MutableLiveData<OrderState> orderState = new MutableLiveData<>();

    public enum ScanningState {
        NOT_SCANNING,
        SCANNING,
        ERROR,
        EMPTY
    }

    public enum OrderState {
        ORDER_EMPTY,
        ORDER_COMPLETED,
        ORDER_CANCELLED,
        ORDER_ERROR
    }

    public void init() {
        if (scanningState.getValue() == null) {
            scanningState.setValue(ScanningState.NOT_SCANNING);
        }
        if (orderState.getValue() == null) {
            orderState.setValue(OrderState.ORDER_EMPTY);
        }
        setScanningState(scanningState.getValue());
        invalidateOrderList();
    }

    public LiveData<List<OrderProduct>> getOrders() {
        return orderProductLive;
    }

    public LiveData<ScanningState> getScanningState() {
        return scanningState;
    }

    public LiveData<OrderState> getOrderState() {
        return orderState;
    }


    public void setScanningState(ScanningState state) {
        scanningState.setValue(state);
    }


    public void addProduct(Product product, boolean batch) {
        this.orderMap.compute(product, (k, v) -> {
            if (v == null) {
                return new OrderProduct(product, 1);
            }
            v.addCount(1);
            return v;
        });
        if (!batch) {
            invalidateOrderList();
        }
    }

    public void addProduct(Product product) {
        addProduct(product, false);
    }

    public void addOrderProduct(OrderProduct orderProduct, boolean batch) {
        this.orderMap.compute(orderProduct.getProduct(), (k, v) -> {
            if (v == null) {
                return orderProduct;
            }
            v.addCount(orderProduct.getCount());
            return v;
        });
        if (!batch) {
            invalidateOrderList();
        }
    }

    public void addOrderProduct(OrderProduct orderProduct) {
        addOrderProduct(orderProduct, false);
    }

    public void deleteOrderProduct(OrderProduct orderProduct) {
        this.orderMap.remove(orderProduct.getProduct());
        invalidateOrderList();
    }

    public void increaseCount(OrderProduct orderProduct) {
        if (this.orderMap.containsKey(orderProduct.getProduct())) {
            this.orderMap.get(orderProduct.getProduct()).addCount(1);
        }
    }

    public void decreaseCount(OrderProduct orderProduct) {
        if (this.orderMap.containsKey(orderProduct.getProduct())) {
            this.orderMap.get(orderProduct.getProduct()).addCount(-1);
            if (this.orderMap.get(orderProduct.getProduct()).getCount() <= 0) {
                deleteOrderProduct(orderProduct);
            }
        }
    }

    public void invalidateOrderList() {
        this.orderProductLive.setValue(new ArrayList<>(this.orderMap.values()));
    }

    public void makeOrder() {
        clearOrder();
        orderState.setValue(OrderState.ORDER_COMPLETED);
    }

    public void cancelOrder() {
        clearOrder();
        orderState.setValue(OrderState.ORDER_CANCELLED);
    }

    public void clearOrder() {
        this.orderMap.clear();
        invalidateOrderList();
    }
}