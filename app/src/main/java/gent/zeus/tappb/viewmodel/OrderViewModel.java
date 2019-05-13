package gent.zeus.tappb.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

import gent.zeus.tappb.entity.OrderProduct;
import gent.zeus.tappb.entity.Product;
import gent.zeus.tappb.repositories.OrderRepository;

public class OrderViewModel extends ViewModel {
    private LiveData<List<OrderProduct>> orderProductList;
    private OrderRepository repo = OrderRepository.getInstance();
    private MutableLiveData<ScanningState> scanningState = new MutableLiveData<>();

    public enum ScanningState {
        NOT_SCANNING,
        SCANNING,
        ERROR,
        EMPTY
    }

    public void init() {
        orderProductList = Transformations.map(repo.getLiveOrder(), newOrder ->
                new ArrayList<>(newOrder.getProductList())
        );
    }

    public LiveData<List<OrderProduct>> getOrders() {
        return orderProductList;
    }

    public LiveData<ScanningState> getScanningState() {
        return scanningState;
    }
    public void setScanningState(ScanningState scanning) {
        scanningState.postValue(scanning);
    }

    public void addProduct(Product product) {
        repo.addItem(product);
    }

    public void increaseCount(OrderProduct orderProduct) {
        repo.increaseCount(orderProduct);
    }

    public void decreaseCount(OrderProduct orderProduct) {
        repo.decreasecount(orderProduct);
    }

    public void clearOrder() {
        repo.clearOrder();
    }


    public void makeOrder() {
        repo.makeOrder();
    }

    public LiveData<Double> getTotalPrice() {
        return Transformations.map(orderProductList, list ->
                list.stream().mapToDouble(op -> op.getCount() * op.getPrice()).sum());
    }
}