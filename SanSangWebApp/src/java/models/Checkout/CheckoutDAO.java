/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models.Checkout;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import models.Order.OrderDTO;
import models.Order.OrderDAO;
import utils.DBUtils;

/**
 * DAO for CheckoutDTO  
 * Used on server-side only
 * Author: tiozo
 */
public class CheckoutDAO {
    
    // SQL query to insert a new checkout record.
    private static final String CREATE = "INSERT INTO CHECKOUT (OrderID, PaymentMethod, Amount, PaymentStatus) VALUES (?, ?, ?, ?)";
    
    // SQL query to retrieve all checkout records.
    private static final String SELECT_ALL = "SELECT CheckoutID, OrderID, PaymentMethod, Amount, PaymentStatus, TransactionDate FROM CHECKOUT";
    
    /**
     * Inserts a checkout record into the database.
     * @param checkout the CheckoutDTO containing checkout details
     * @return true if creation is successful; false otherwise.
     * @throws SQLException if a database error occurs.
     * @throws ClassNotFoundException if the JDBC driver is not found.
     */
    public boolean create(CheckoutDTO checkout) throws SQLException, ClassNotFoundException {
        boolean result = false;
        Connection conn = null;
        PreparedStatement ptm = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(CREATE);
                ptm.setInt(1, checkout.getOrderID());
                ptm.setString(2, checkout.getPaymentMethod());
                ptm.setBigDecimal(3, checkout.getAmount());
                ptm.setString(4, checkout.getPaymentStatus());
                result = ptm.executeUpdate() > 0;
            } else {
                throw new SQLException("Get Connection failed in CheckoutDAO.create()");
            }
        } finally {
            if (ptm != null) {
                ptm.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
        return result;
    }
    
    /**
     * Retrieves all checkout records from the CHECKOUT table.
     * @return a list of CheckoutDTO objects.
     * @throws SQLException if a database error occurs.
     * @throws ClassNotFoundException if the JDBC driver is not found.
     */
    public List<CheckoutDTO> getAll() throws SQLException, ClassNotFoundException {
        List<CheckoutDTO> result = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(SELECT_ALL);
                rs = ptm.executeQuery();
                while (rs.next()) {
                    CheckoutDTO checkout = new CheckoutDTO.Builder()
                            .withCheckoutID(rs.getInt("CheckoutID"))
                            .withOrderID(rs.getInt("OrderID"))
                            .withPaymentMethod(rs.getString("PaymentMethod"))
                            .withAmount(rs.getBigDecimal("Amount"))
                            .withPaymentStatus(rs.getString("PaymentStatus"))
                            .withTransactionDate(rs.getTimestamp("TransactionDate").toLocalDateTime())
                            .build();
                    result.add(checkout);
                }
            } else {
                throw new SQLException("Get Connection failed in CheckoutDAO.getAll()");
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (ptm != null) {
                ptm.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
        return result;
    }
    
    /**
     * Creates an order record (an element in the ORDERED table) via OrderDAO,
     * then creates a checkout record associated with that order.
     * This method encapsulates the workflow of converting the cart into a confirmed order.
     * 
     * @param order the OrderDTO representing the order to be confirmed.
     * @param checkoutPartial a CheckoutDTO containing payment details (excluding OrderID).
     * @return true if both the order and checkout records are created successfully; false otherwise.
     * @throws SQLException if a database error occurs.
     * @throws ClassNotFoundException if the JDBC driver is not found.
     */
    public boolean createCheckoutAndOrder(OrderDTO order, CheckoutDTO checkoutPartial) throws SQLException, ClassNotFoundException {
        // Create the order record in the ORDERED table using OrderDAO.
        OrderDAO orderDao = new OrderDAO();
        int orderId = orderDao.create(order);  // Assumes OrderDAO.create(OrderDTO order) returns a valid OrderID.
        
        if (orderId <= 0) {
            // Order creation failed.
            return false;
        }
        // Build the complete CheckoutDTO object using the valid OrderID.
        CheckoutDTO checkout = new CheckoutDTO.Builder()
                .withOrderID(orderId)
                .withPaymentMethod(checkoutPartial.getPaymentMethod())
                .withAmount(checkoutPartial.getAmount())
                .withPaymentStatus(checkoutPartial.getPaymentStatus())
                .withTransactionDate(LocalDateTime.now())
                .build();
        
        // Insert the checkout record.
        return create(checkout);
    }
}
