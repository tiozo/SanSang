/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models.Order;


import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;


/**
 *
 * @author tiozo
 */
public class OrderDTO {
    private final int orderID;
    private final String userID;
    private final LocalDateTime date;
    private final BigDecimal total;
    private final List<OrderDetailDTO> orderDetails;

    private OrderDTO(Builder builder) {
        this.orderID = builder.orderID;
        this.userID = builder.userID;
        this.date = builder.date;
        this.total = builder.total;
        this.orderDetails = builder.orderDetails;
    }

    public static class Builder {
        private int orderID;
        private String userID;
        private LocalDateTime date;
        private BigDecimal total = BigDecimal.ZERO;
        private List<OrderDetailDTO> orderDetails;

        public Builder withOrderID(int orderID) {
            this.orderID = orderID;
            return this;
        }

        public Builder withUserID(String userID) {
            this.userID = userID;
            return this;
        }

        public Builder withDate(LocalDateTime date) {
            this.date = date;
            return this;
        }

        public Builder withTotal(BigDecimal total) {
            this.total = total;
            return this;
        }

        public Builder withOrderDetails(List<OrderDetailDTO> orderDetails) {
            this.orderDetails = orderDetails;
            return this;
        }

        public OrderDTO build() {
            return new OrderDTO(this);
        }
    }

    // Getters
    public int getOrderID() { return orderID; }
    public String getUserID() { return userID; }
    public LocalDateTime getDate() { return date; }
    public BigDecimal getTotal() { return total; }
    public List<OrderDetailDTO> getOrderDetails() { return orderDetails; }
}