package gent.zeus.tappb.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import gent.zeus.tappb.entity.StockProduct;
import gent.zeus.tappb.repositories.StockRepository;

public class StockViewModel extends ViewModel {

    private StockRepository stockRepository = StockRepository.getInstance();


    public StockViewModel(){
        super();
    }
    public void init() {

    }

    public MutableLiveData<Boolean> fetchError;

    public LiveData<List<StockProduct>> getStock() {
        return stockRepository.getProductList();
    }
    public MutableLiveData<Boolean> getFetchError() {
        return fetchError;
    }


//    public void initializeStock() {
//        if (fetchError == null) {
//            fetchError = new MutableLiveData<>();
//        }
//
//
//        try {
//            if (!this.isFetched) {
//                stock = TapAPI.fetchStockProduct();
//                fetchError.setValue(false);
//                this.isFetched = true;
//            }
//        } catch (APIException ex) {
//            this.isFetched = false;
//            fetchError.setValue(true);
//            List<StockProduct> productList = new ArrayList<>();
//            productList.add(new StockProduct(new Product(0, "Cola", 1.20), 5));
//            productList.add(new StockProduct(new Product(1, "Ice-Tea", 1.20), 5));
//            productList.add(new StockProduct(new Product(2, "Bier", 1.20), 5));
//            productList.add(new StockProduct(new Product(3, "Mate", 1.20), 5));
//            productList.add(new StockProduct(new Product(4, "Chips", 1.20), 5));
//            productList.add(new StockProduct(new Product(5, "Bueno", 1.20), 5));
//            productList.add(new StockProduct(new Product(6, "Sapje", 1.20), 5));
//            productList.add(new StockProduct(new Product(7, "Foo", 1.20), 5));
//            productList.add(new StockProduct(new Product(8, "Bar", 1.20), 5));
//            productList.add(new StockProduct(new Product(9, "Kip", 1.20), 5));
//            stock = new MutableLiveData<>();
//            ((MutableLiveData<List<StockProduct>>) stock).setValue(productList);
//        }
//    }
}
