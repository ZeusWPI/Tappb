package gent.zeus.tappb.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import java.util.ArrayList;
import java.util.List;

import gent.zeus.tappb.api.TapAPI;
import gent.zeus.tappb.entity.Product;
import gent.zeus.tappb.entity.Stock;
import gent.zeus.tappb.entity.StockProduct;

public class StockRepository {
    private static final StockRepository ourInstance = new StockRepository();
    private Stock stock = new Stock();
    private MutableLiveData<List<StockProduct>> productList = new MutableLiveData<>();
    private TapAPI api = new TapAPI();
    private MutableLiveData<Integer> requestedId = new MutableLiveData<>();
    private MutableLiveData<StockProduct> prod = new MutableLiveData<>();
    private LiveData<StockProduct> requestedProduct;

    public static StockRepository getInstance() {
        return ourInstance;
    }

    private StockRepository() {
        LiveData<Stock> apiStock = api.getStockProducts();
        apiStock.observeForever((newstock -> {
            stock = newstock;
            productList.postValue(new ArrayList<>(stock.getProducts()));
        }));

        LiveData<UserRepository.UserStatus> userStatus = UserRepository.getInstance().getStatus();
        userStatus.observeForever((status) -> {
            if (status == UserRepository.UserStatus.LOGGED_IN) {
                api.fetchStockProduct();
            }
        });

        requestedProduct = Transformations.switchMap(requestedId, (id) -> {
            prod.postValue(stock.getProductById(id));
            return prod;
        });
    }

    public LiveData<List<StockProduct>> getProductList() {
        return productList;
    }

    public StockProduct getProductById(Integer id) {
        return stock.getProductById(id);

    }
    public void setRequestedId(Integer id) {
        requestedId.postValue(id);
    }
    public LiveData<StockProduct> getRequestedProduct() {
        return requestedProduct;
    }

    public Product getProductByBarcode(String displayValue) {
        //TODO: implement
        return null;
    }
}
