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
    private MutableLiveData<List<OrderProduct>> orderProductLive;
    private MutableLiveData<ScanningState> scanningState;
    public enum ScanningState {
        NOT_SCANNING,
        SCANNING,
        ERROR
    }

    public void init() {
        orderProductLive = new MutableLiveData<>();
        scanningState = new MutableLiveData<>();
        order.addProduct(Product.fromBarcode("REE"));
        calculateOrderProductLive();
        setScanningState(ScanningState.NOT_SCANNING);
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
}
