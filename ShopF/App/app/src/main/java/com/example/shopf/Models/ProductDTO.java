package com.example.shopf.Models;

public class ProductDTO {
    private int productId;
    private String productName;
    private int quantity;
    private String description;
    private Double price;
    private Double discount;
    private String image;
    private int categoryId;
    private int sold;

    public ProductDTO() {
    }

    public ProductDTO(int productId, String productName, int quantity, String description, Double price, Double discount, String image, int categoryId, int sold) {
        this.productId = productId;
        this.productName = productName;
        this.quantity = quantity;
        this.description = description;
        this.price = price;
        this.discount = discount;
        this.image = image;
        this.categoryId = categoryId;
        this.sold = sold;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public int getSold() {
        return sold;
    }

    public void setSold(int sold) {
        this.sold = sold;
    }
}
