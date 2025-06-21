/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models.Checkout;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * DTO for Checkout
 * Used on server-side only
 * Author: tiozo
 */
public class CheckoutDTO {
    private final int checkoutID;
    private final int orderID;
    private final String paymentMethod;
    private final BigDecimal amount;
    private final String paymentStatus;
    private final LocalDateTime transactionDate;

    private CheckoutDTO(Builder builder) {
        this.checkoutID = builder.checkoutID;
        this.orderID = builder.orderID;
        this.paymentMethod = builder.paymentMethod;
        this.amount = builder.amount;
        this.paymentStatus = builder.paymentStatus;
        this.transactionDate = builder.transactionDate;
    }
    
    // ---------- Getters ----------
    public int getCheckoutID() {
        return checkoutID;
    }
    
    public int getOrderID() {
        return orderID;
    }
    
    public String getPaymentMethod() {
        return paymentMethod;
    }
    
    public BigDecimal getAmount() {
        return amount;
    }
    
    public String getPaymentStatus() {
        return paymentStatus;
    }
    
    public LocalDateTime getTransactionDate() {
        return transactionDate;
    }
    
    // ---------- Builder Pattern ----------
    public static class Builder {
        private int checkoutID; // Optional (set by DB)
        private int orderID;
        private String paymentMethod;
        private BigDecimal amount;
        private String paymentStatus;
        private LocalDateTime transactionDate;
        
        public Builder withCheckoutID(int checkoutID) {
            this.checkoutID = checkoutID;
            return this;
        }
        
        public Builder withOrderID(int orderID) {
            this.orderID = orderID;
            return this;
        }
        
        public Builder withPaymentMethod(String paymentMethod) {
            this.paymentMethod = paymentMethod;
            return this;
        }
        
        public Builder withAmount(BigDecimal amount) {
            this.amount = amount;
            return this;
        }
        
        public Builder withPaymentStatus(String paymentStatus) {
            this.paymentStatus = paymentStatus;
            return this;
        }
        
        public Builder withTransactionDate(LocalDateTime transactionDate) {
            this.transactionDate = transactionDate;
            return this;
        }
        
        public CheckoutDTO build() {
            return new CheckoutDTO(this);
        }
    }
}
