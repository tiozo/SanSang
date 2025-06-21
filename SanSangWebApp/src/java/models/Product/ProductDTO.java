/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models.Product;

/**
 *
 * @author tiozo
 */
public class ProductDTO {
    private final int productID;
    private String name;
    private String description;
    private double price;
    private int quantity;

    // Private constructor to enforce the use of the Builder
    private ProductDTO(Builder builder) {
        this.productID = builder.productID;
        this.name = builder.name;
        this.description = builder.description;
        this.price = builder.price;
        this.quantity = builder.quantity;
    }

    // Static inner Builder class
    public static class Builder {
        private int productID;
        private String name;
        private String description = "";
        private double price = 0.0;
        private int quantity = 0;

        public Builder(int productID) {
            if (productID < 0) 
                throw new IllegalArgumentException("Product ID should be greater than 0");
            this.productID = productID;
        }
        
        public Builder withProductID(int productID) {
            this.productID = productID;
            return this;
        }

        public Builder withName(String name) {
            this.name = name;
            return this;
        }

        public Builder withDescription(String description) {
            this.description = description;
            return this;
        }

        public Builder withPrice(double price) {
            this.price = price;
            return this;
        }

        public Builder withQuantity(int quantity) {
            this.quantity = quantity;
            return this;
        }

        // Build method to create a ProductDTO instance
        public ProductDTO build() {
            return new ProductDTO(this);
        }
    }

    @Override
    public String toString() {
        return "ProductDTO{" +
               "productID=" + productID +
               ", name='" + name + '\'' +
               ", description='" + description + '\'' +
               ", price=" + price +
               ", quantity=" + quantity + '}';
    }

    // Getters
    public int getProductID() { return productID; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public double getPrice() { return price; }
    public int getQuantity() { return quantity; }
}

