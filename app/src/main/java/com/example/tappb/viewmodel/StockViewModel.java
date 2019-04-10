package com.example.tappb.viewmodel;

import com.example.tappb.entity.Product;

import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class StockViewModel extends ViewModel {
    //TODO: implement TAP api

    private LiveData<List<Product>> stock;

    public void init() {
        initializeStock();
    }

    public LiveData<List<Product>> getStock() {
        return stock;
    }

    public void initializeStock() {
        stock = new MutableLiveData<>();

        List<Product> productList = new ArrayList<>();
        productList.add(new Product(0, "Cola", 1.20, 5));
        productList.add(new Product(1, "Ice-Tea", 1.20, 5));
        productList.add(new Product(2, "Bier", 1.20, 5));
        productList.add(new Product(3, "Mate", 1.20, 5));
        productList.add(new Product(4, "Chips", 1.20, 5));
        productList.add(new Product(5, "Bueno", 1.20, 5));
        productList.add(new Product(6, "Sapje", 1.20, 5));
        productList.add(new Product(7, "Foo", 1.20, 5));
        productList.add(new Product(8, "Bar", 1.20, 5));
        productList.add(new Product(9, "Kip", 1.20, 5));

        ((MutableLiveData<List<Product>>) stock).setValue(productList);
    }
}
