package gent.zeus.tappb.viewmodel;

import android.util.Log;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import gent.zeus.tappb.entity.Order;
import gent.zeus.tappb.entity.OrderProduct;
import gent.zeus.tappb.entity.Product;

public class OrderViewModel extends ViewModel implements Observer {
    private Order order = new Order();
    private MutableLiveData<List<OrderProduct>> orderProductLive = new MutableLiveData<>();
    private MutableLiveData<ScanningState> scanningState = new MutableLiveData<>();

    public enum ScanningState {
        NOT_SCANNING,
        SCANNING,
        ERROR,
        EMPTY
    }

    public void init() {
        if (scanningState.getValue() == null) {
            scanningState.setValue(ScanningState.NOT_SCANNING);
        }
        setScanningState(scanningState.getValue());
        order.addObserver(this);
    }

    @Override
    public void update(Observable o, Object arg) {
        Order newOrder = (Order) o;
        Log.d("update", "observable updated");
        orderProductLive.setValue(newOrder.getOrderProducts());
    }

    public LiveData<List<OrderProduct>> getOrders() {
        return orderProductLive;
    }

    public LiveData<ScanningState> getScanningState() {
        return scanningState;
    }

    public void addOrder(Order o) {
        this.order.combine(o);
    }

    public void setScanningState(ScanningState state) {
        scanningState.setValue(state);
    }

    public void updateCount(Product p, int amount) {
        order.updateCount(p, amount);
    }

    public void addProduct(Product product) {
        this.order.addProduct(product);
    }
}