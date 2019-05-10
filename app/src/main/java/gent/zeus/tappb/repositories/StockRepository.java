package gent.zeus.tappb.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;

import java.util.ArrayList;
import java.util.List;

import gent.zeus.tappb.api.TapAPI;
import gent.zeus.tappb.entity.Product;
import gent.zeus.tappb.entity.StockProduct;
import gent.zeus.tappb.entity.StockState;

public class StockRepository {
    private static final StockRepository ourInstance = new StockRepository();
    private LiveData<StockState> stock;

    public static StockRepository getInstance() {
        return ourInstance;
    }

    private StockRepository() {
    }

    public LiveData<StockState> getStock() {
        if (stock == null) {
            stock = TapAPI.getStockProducts();
        }
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
