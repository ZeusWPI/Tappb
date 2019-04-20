package gent.zeus.tappb.entity;

import java.util.List;

import gent.zeus.tappb.api.TapAPI;

public class ProductList {
    private static ProductList instance;
    private List<StockProduct> products;

    private ProductList() {}

    public static ProductList getInstance() {
        if (instance == null) {
            instance = new ProductList();
        }
        return instance;
    }

    public List<StockProduct> getStockProducts() {
        if (products == null) {
            updateProducts();
        }
        return products;
    }

    public void updateProducts() {
        products = TapAPI.getStockProducts();
    }

    public StockProduct getProductById(int id) {
        for (StockProduct stockProduct : getStockProducts()) {
            if (stockProduct.getProduct().getId() == id) {
                return stockProduct;
            }
        }
        return null;
    }
}
