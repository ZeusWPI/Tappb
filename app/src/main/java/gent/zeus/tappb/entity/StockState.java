package gent.zeus.tappb.entity;

import java.util.Collection;

public interface StockState {
    StockProduct getProductById(Integer id);
    Collection<StockProduct> getProducts();

    void addProduct(StockProduct product);
}
