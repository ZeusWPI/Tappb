package gent.zeus.tappb.entity;

public class Product {

    private int id;

    private String name;
    private double price;
    private int stock;

    public Product(int id, String name, double price, int stock) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
    }

    public static Product fromBarcode(String barcode) {
        //TODO: preform API request to get actual data
        return new Product(0, barcode, 13.37, 1);
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getStock() {
        return stock;
    }
}
