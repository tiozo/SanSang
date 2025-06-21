/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models.Order;

import java.math.BigDecimal;

/**
 *
 * @author tiozo
 */
public class OrderDetailDTO {
    private final int orderDetailID;
    private final int orderID;
    private final int productID;
    private final int quantity;
    private final BigDecimal price;

    private OrderDetailDTO(Builder builder) {
        this.orderDetailID = builder.orderDetailID;
        this.orderID = builder.orderID;
        this.productID = builder.productID;
        this.quantity = builder.quantity;
        this.price = builder.price;
    }

    public static class Builder {
        private int orderDetailID;
        private int orderID;
        private int productID;
        private int quantity;
        private BigDecimal price;

        public Builder withOrderDetailID(int orderDetailID) {
            this.orderDetailID = orderDetailID;
            return this;
        }

        public Builder withOrderID(int orderID) {
            this.orderID = orderID;
            return this;
        }

        public Builder withProductID(int productID) {
            this.productID = productID;
            return this;
        }

        public Builder withQuantity(int quantity) {
            this.quantity = quantity;
            return this;
        }

        public Builder withPrice(BigDecimal price) {
            this.price = price;
            return this;
        }

        public OrderDetailDTO build() {
            return new OrderDetailDTO(this);
        }
    }

    // Getters
    public int getOrderDetailID() { return orderDetailID; }
    public int getOrderID() { return orderID; }
    public int getProductID() { return productID; }
    public int getQuantity() { return quantity; }
    public BigDecimal getPrice() { return price; }
}