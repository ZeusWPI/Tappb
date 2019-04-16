package gent.zeus.tappb.entity;

public class StockProduct {
    private final Product product;
    private int stock;

    public StockProduct(Product product, int stock) {
        this.product = product;
        this.stock = stock;
    }

    public Product getProduct() {
        return product;
    }

    public int getStock() {
        return stock;
    }

    public String getName() {
        return product.getName();
    }

    public double getPrice() {
        return product.getPrice();
    }
}
