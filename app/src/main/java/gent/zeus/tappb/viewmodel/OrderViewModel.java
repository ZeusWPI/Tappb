package gent.zeus.tappb.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

import gent.zeus.tappb.entity.OrderProduct;
import gent.zeus.tappb.entity.Product;
import gent.zeus.tappb.repositories.BarcodeRepository;
import gent.zeus.tappb.repositories.OrderRepository;
import gent.zeus.tappb.repositories.StockRepository;

public class OrderViewModel extends ViewModel {
    private LiveData<List<OrderProduct>> orderProductList;
    private OrderRepository orderRepo = OrderRepository.getInstance();
    private BarcodeRepository barcodeRepo = BarcodeRepository.getInstance();
    private MutableLiveData<ScanningState> scanningState = new MutableLiveData<>();

    public enum ScanningState {
        NOT_SCANNING,
        SCANNING,
        ERROR,
        EMPTY
    }

    public void init() {
        orderProductList = Transformations.map(orderRepo.getLiveOrder(), newOrder ->
                new ArrayList<>(newOrder.getProductList())
        );
        barcodeRepo.getRequestedBarcode().observeForever(code ->
        {
            if (code != null) {
                Product p = StockRepository.getInstance().getProductById(code.getProductId());
                if (p != null) {
                    addProduct(p);
                }
            }
        });

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
        orderRepo.addItem(product);
    }

    public void addProductByBarcode(String barcode) {
        BarcodeRepository.getInstance().requestBarcodeByCode(barcode);
    }

    public void increaseCount(OrderProduct orderProduct) {
        orderRepo.increaseCount(orderProduct);
    }

    public void decreaseCount(OrderProduct orderProduct) {
        orderRepo.decreasecount(orderProduct);
    }

    public void clearOrder() {
        orderRepo.clearOrder();
    }

    public void refresh() {
        barcodeRepo.fetchAll();
    }


    public void makeOrder() {
        orderRepo.makeOrder();
    }

    public LiveData<Double> getTotalPrice() {
        return Transformations.map(orderProductList, list ->
                list.stream().mapToDouble(op -> op.getCount() * op.getPrice()).sum());
    }
}