package com.example.phoenixheadphones;

public class Cart {
    // CodeByFarnoodID
    private String name;
    private String price;
    private String image;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Cart() {

    }

    public Cart(String name, String price, String image) {
        this.name = name;
        this.price = price;
        this.image = image;
    }
}
