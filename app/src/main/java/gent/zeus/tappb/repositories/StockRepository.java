package gent.zeus.tappb.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import java.util.ArrayList;
import java.util.List;

import gent.zeus.tappb.api.TapAPI;
import gent.zeus.tappb.entity.Product;
import gent.zeus.tappb.entity.StockProduct;
import gent.zeus.tappb.entity.StockState;

public class StockRepository {
    private static final StockRepository ourInstance = new StockRepository();
    private MutableLiveData<StockState> stock = new MutableLiveData<>();
    private TapAPI api = new TapAPI();

    public static StockRepository getInstance() {
        return ourInstance;
    }

    private StockRepository() {
        LiveData<StockState> apiStock = api.getStockProducts();
        apiStock.observeForever((stockState -> stock.setValue(stockState)));
        LiveData<UserRepository.UserStatus> userStatus = UserRepository.getInstance().getStatus();
        userStatus.observeForever((status) -> {
            if (status == UserRepository.UserStatus.LOGGED_IN) {
                api.fetchStockProduct();
            }
        });
    }

    public LiveData<StockState> getStock() {
        return stock;
    }

    public LiveData<List<StockProduct>> getProductList() {
        return Transformations.map(stock, (stock) -> new ArrayList<>(stock.getProducts()));
    }

    public StockProduct getProductById(Integer id) {
        return stock.getValue().getProductById(id);
    }

    public Product getProductByBarcode(String displayValue) {
        //TODO: implement
        return null;
    }
}
