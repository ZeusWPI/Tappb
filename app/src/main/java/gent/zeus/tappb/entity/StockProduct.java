package gent.zeus.tappb.entity;

public class StockProduct extends Product{
    private int stock;

    public StockProduct(int id, String name, double price, String imageURL, int stock) {
        super(id, name, price, imageURL);
        this.stock = stock;
    }

    public StockProduct(int id, String name, double price, int stock) {
        super(id, name, price);
        this.stock = stock;
    }

    public StockProduct(Product product, int stock) {
        super(product);
        this.stock = stock;
    }

    public int getStock() {
        return stock;
    }

}
