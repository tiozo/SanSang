/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models.Order;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import utils.DBUtils;

/**
 * DAO for OrderDTO
 * Used on server-side only
 * Author: tiozo
 */
public class OrderDAO {

    private static final String GET_CART_QUERY = 
        "SELECT TOP 1 OrderID, Date, Total FROM ORDERED WHERE UserID = ? ORDER BY Date DESC";
    private static final String GET_ORDER_DETAILS_QUERY = 
        "SELECT ProductID, Quantity, Price FROM ORDER_DETAIL WHERE OrderID = ?";
    private static final String CREATE_ORDER = 
        "INSERT INTO ORDERED (UserID, Total) VALUES (?, ?)";
    private static final String CREATE_ORDER_DETAIL = 
        "INSERT INTO ORDER_DETAIL (OrderID, ProductID, Quantity, Price) VALUES (?, ?, ?, ?)";
    private static final String GET_ALL = 
        "SELECT OrderID FROM ORDERED";
    
    public OrderDTO getCart(String userID) throws SQLException, ClassNotFoundException {
        OrderDTO result = null;
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(GET_CART_QUERY);
                ptm.setString(1, userID);
                rs = ptm.executeQuery();
                
                if (rs.next()) {
                    OrderDTO.Builder orderBuilder = new OrderDTO.Builder()
                        .withOrderID(rs.getInt("OrderID"))
                        .withUserID(userID)
                        .withDate(rs.getTimestamp("Date").toLocalDateTime())
                        .withTotal(rs.getBigDecimal("Total"));
                    
                    List<OrderDetailDTO> details = getOrderDetails(rs.getInt("OrderID"));
                    orderBuilder.withOrderDetails(details);
                    
                    result = orderBuilder.build();
                }
            } else {
                throw new SQLException("Get Connection failed at getCart in OrderDAO");
            }
        } finally {
            if (rs != null) { rs.close(); }
            if (ptm != null) { ptm.close(); }
            if (conn != null) { conn.close(); }
        }
        return result;
    }
    
    private List<OrderDetailDTO> getOrderDetails(int orderID) throws SQLException, ClassNotFoundException {
        List<OrderDetailDTO> result = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(GET_ORDER_DETAILS_QUERY);
                ptm.setInt(1, orderID);
                rs = ptm.executeQuery();
                
                while (rs.next()) {
                    OrderDetailDTO detail = new OrderDetailDTO.Builder()
                        .withOrderID(orderID)
                        .withProductID(rs.getInt("ProductID"))
                        .withQuantity(rs.getInt("Quantity"))
                        .withPrice(rs.getBigDecimal("Price"))
                        .build();
                    
                    result.add(detail);
                }
            } else {
                throw new SQLException("Get Connection failed at getOrderDetails in OrderDAO");
            }
        } finally {
            if (rs != null) { rs.close(); }
            if (ptm != null) { ptm.close(); }
            if (conn != null) { conn.close(); }
        }
        return result;
    }
    
    /**
     * Creates an order (header and its order details) in a single transaction.
     * This method inserts a record into the ORDERED table and then each
     * OrderDetail into the ORDER_DETAIL table.
     * 
     * @param order the OrderDTO object representing the order to be created.
     * @return the generated OrderID if creation is successful; otherwise, returns -1.
     * @throws SQLException if a database access error occurs.
     * @throws ClassNotFoundException if the JDBC driver is not found.
     */
    public int create(OrderDTO order) throws SQLException, ClassNotFoundException {
        int orderID = -1;
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                // Begin transaction
                conn.setAutoCommit(false);
                
                // Insert order header
                ptm = conn.prepareStatement(CREATE_ORDER, PreparedStatement.RETURN_GENERATED_KEYS);
                ptm.setString(1, order.getUserID());
                ptm.setBigDecimal(2, order.getTotal());
                
                int affectedRows = ptm.executeUpdate();
                if (affectedRows > 0) {
                    rs = ptm.getGeneratedKeys();
                    if (rs.next()) {
                        orderID = rs.getInt(1);
                    }
                    
                    // Insert order details
                    for (OrderDetailDTO detail : order.getOrderDetails()) {
                        ptm = conn.prepareStatement(CREATE_ORDER_DETAIL);
                        ptm.setInt(1, orderID);
                        ptm.setInt(2, detail.getProductID());
                        ptm.setInt(3, detail.getQuantity());
                        ptm.setBigDecimal(4, detail.getPrice());
                        ptm.executeUpdate();
                    }
                    conn.commit();
                }
            } else {
                throw new SQLException("Get Connection failed at create in OrderDAO");
            }
        } catch (SQLException ex) {
            if (conn != null) {
                conn.rollback();
            }
            throw ex;
        } finally {
            if (rs != null) { rs.close(); }
            if (ptm != null) { ptm.close(); }
            if (conn != null) {
                conn.setAutoCommit(true);
                conn.close();
            }
        }
        return orderID;
    }
    
    public List<OrderDTO> getAllOrderDTO() throws SQLException, ClassNotFoundException {
        List<OrderDTO> result = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(GET_ALL);
                rs = ptm.executeQuery();
                
                while (rs.next()) {
                    OrderDTO order = new OrderDTO.Builder()
                        .withOrderID(rs.getInt("OrderID"))
                        .build();
                    
                    result.add(order);
                }
            } else {
                throw new SQLException("Get Connection failed at getAllOrderDTO in OrderDAO");
            }
        } finally {
            if (rs != null) { rs.close(); }
            if (ptm != null) { ptm.close(); }
            if (conn != null) { conn.close(); }
        }
        return result;
    } 
}
