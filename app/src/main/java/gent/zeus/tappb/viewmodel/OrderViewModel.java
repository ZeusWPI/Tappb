package gent.zeus.tappb.viewmodel;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import gent.zeus.tappb.entity.Order;
import gent.zeus.tappb.entity.OrderProduct;
import gent.zeus.tappb.entity.Product;

public class OrderViewModel extends ViewModel {
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
        calculateOrderProductLive();
        setScanningState(scanningState.getValue());
    }

    public LiveData<List<OrderProduct>> getOrders() {
        return orderProductLive;
    }
    
    public LiveData<ScanningState> getScanningState() {
        return scanningState;
    }

    public void addOrder(Order o) {
        this.order.combine(o);
        calculateOrderProductLive();
    }

    private void calculateOrderProductLive() {
        orderProductLive.setValue(this.order.getOrderProducts());
    }

    public void setScanningState(ScanningState state) {
        scanningState.setValue(state);
    }

    public void updateCount(Product p, int amount) {
        order.updateCount(p, amount);
        calculateOrderProductLive();
    }
}
