package gent.zeus.tappb.entity;

public class Product {

    private int id;

    private String name;
    private double price;

    private String imageURL;

    public Product(int id, String name, double price, String imageURL) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.imageURL = imageURL;
    }

    public Product(int id, String name, double price) {
        this.id = id;
        this.name = name;
        this.price = price;
        // MATE
        this.imageURL = "https://tap.zeus.gent/system/products/avatars/000/000/007/medium/bottle-start.png";
    }

    public Product(Product product) {
        this.id = product.getId();
        this.name = product.getName();
        this.price = product.getPrice();
        this.imageURL = product.getImageURL();
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

    public String getImageURL() {
        return imageURL;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product other = (Product) o;
        return other.getName().equals(this.getName()) && other.getId() == this.getId();
    }

    // To make sure items of the same type get combined in a HashMap
    @Override
    public final int hashCode() {
        return (name + id).hashCode();
    }

}
