package gent.zeus.tappb.ui;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import gent.zeus.tappb.entity.OrderProduct;

public class OrderPageItem {
    public String productName;
    public LiveData<Integer> count;
    public LiveData<Integer> price;

    public OrderPageItem(OrderProduct orderProduct) {
        productName = orderProduct.getProduct().getName();
        count = new MutableLiveData<>();
        price = new MutableLiveData<>();
    }
}

