package gent.zeus.tappb.entity;

public class Product {

    private int id;

    private String name;
    private double price;
    private int stock;

    static Product fromId(int id) {
        return new Product(id, "AAAA", 1.24, 12);
    }

    public Product(int id, String name, double price, int stock) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
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
