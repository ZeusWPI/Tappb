package gent.zeus.tappb.viewmodel;

import android.util.Log;

import gent.zeus.tappb.api.APIException;
import gent.zeus.tappb.api.TapAPI;
import gent.zeus.tappb.entity.Product;

import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import gent.zeus.tappb.entity.StockProduct;

public class StockViewModel extends ViewModel {
    private LiveData<List<StockProduct>> stock;

    private boolean isFetched = false;
    public void init() {
        initializeStock();
    }

    public MutableLiveData<Boolean> fetchError;

    public LiveData<List<StockProduct>> getStock() {
        return stock;
    }
    public MutableLiveData<Boolean> getFetchError() {
        return fetchError;
    }


    public void initializeStock() {
        if(stock == null) {
            stock = new MutableLiveData<>();
        }
        if (fetchError == null) {
            fetchError = new MutableLiveData<>();
        }
        List<StockProduct> productList = null;

        try {
            if (!this.isFetched) {
                productList = TapAPI.getStockProducts();
                fetchError.setValue(false);
                this.isFetched = true;
            }
        } catch (APIException ex) {
            this.isFetched = false;
            fetchError.setValue(true);
            productList = new ArrayList<>();
            productList.add(new StockProduct(new Product(0, "Cola", 1.20), 5));
            productList.add(new StockProduct(new Product(1, "Ice-Tea", 1.20), 5));
            productList.add(new StockProduct(new Product(2, "Bier", 1.20), 5));
            productList.add(new StockProduct(new Product(3, "Mate", 1.20), 5));
            productList.add(new StockProduct(new Product(4, "Chips", 1.20), 5));
            productList.add(new StockProduct(new Product(5, "Bueno", 1.20), 5));
            productList.add(new StockProduct(new Product(6, "Sapje", 1.20), 5));
            productList.add(new StockProduct(new Product(7, "Foo", 1.20), 5));
            productList.add(new StockProduct(new Product(8, "Bar", 1.20), 5));
            productList.add(new StockProduct(new Product(9, "Kip", 1.20), 5));
        }

        if (productList != null) {
            ((MutableLiveData<List<StockProduct>>) stock).setValue(productList);
        }
    }
}
