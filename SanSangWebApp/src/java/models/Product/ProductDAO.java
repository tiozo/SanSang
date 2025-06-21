/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import utils.DBUtils;

/**
 * DAO for ProductDTO
 * Used on server-side only
 * @author tiozo
 */
public class ProductDAO {

    private static final String SEARCH_QUERY = 
        "SELECT ProductID, Name, Description, Price, Quantity FROM Product";
    private static final String CREATE = 
        "INSERT INTO Product (Name, Description, Price, Quantity) VALUES (?, ?, ?, ?)";
    private static final String SEARCH_DUPLICATE = 
        "SELECT Name FROM Product WHERE Name = ?";
    private static final String GET_PRODUCT_BY_ID =
        "SELECT ProductID, Name, Description, Price, Quantity FROM Product WHERE ProductID = ?";
    private static final String REDUCE_STOCK = 
            "UPDATE Product SET Quantity = Quantity - ? WHERE ProductID = ? AND Quantity >= ?";

    public List<ProductDTO> getListProducts() throws SQLException, ClassNotFoundException {
        List<ProductDTO> result = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(SEARCH_QUERY);
                rs = ptm.executeQuery();
                while (rs.next()) {
                    result.add(new ProductDTO.Builder(rs.getInt("ProductID"))
                               .withName(rs.getString("Name"))
                               .withDescription(rs.getString("Description"))
                               .withPrice(rs.getDouble("Price"))
                               .withQuantity(rs.getInt("Quantity"))
                               .build());
                }
            } else {
                throw new SQLException("Get Connection failed at getListProducts in ProductDAO");
            }
        } finally {
            if (rs != null) { rs.close(); }
            if (ptm != null) { ptm.close(); }
            if (conn != null) { conn.close(); }
        }
        return result;
    }

    /**
     * Retrieves the ProductDTO by its productID.
     * 
     * @param productId the unique identifier for the product
     * @return the ProductDTO if found, otherwise null
     * @throws SQLException if a database access error occurs
     * @throws ClassNotFoundException if the JDBC driver is not found
     */
    public ProductDTO getProductById(int productId) throws SQLException, ClassNotFoundException {
        ProductDTO product = null;
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(GET_PRODUCT_BY_ID);
                ptm.setInt(1, productId);
                rs = ptm.executeQuery();
                if (rs.next()) {
                    product = new ProductDTO.Builder(rs.getInt("ProductID"))
                              .withName(rs.getString("Name"))
                              .withDescription(rs.getString("Description"))
                              .withPrice(rs.getDouble("Price"))
                              .withQuantity(rs.getInt("Quantity"))
                              .build();
                }
            } else {
                throw new SQLException("Get Connection failed at getProductById in ProductDAO");
            }
        } finally {
            if (rs != null) { rs.close(); }
            if (ptm != null) { ptm.close(); }
            if (conn != null) { conn.close(); }
        }
        return product;
    }

    public boolean checkDuplicate(ProductDTO object) throws SQLException, ClassNotFoundException {
        boolean result = false;
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(SEARCH_DUPLICATE);
                ptm.setInt(1, object.getProductID());
                rs = ptm.executeQuery();
                if (rs.next()) {
                    result = true;
                }
            }
        } finally {
            if (rs != null) { rs.close(); }
            if (ptm != null) { ptm.close(); }
            if (conn != null) { conn.close(); }
        }
        return result;
    }

    public boolean create(ProductDTO object) throws SQLException, ClassNotFoundException {
        boolean result = false;
        Connection conn = null;
        PreparedStatement ptm = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(CREATE);
                ptm.setString(1, object.getName());
                ptm.setString(2, object.getDescription());
                ptm.setDouble(3, object.getPrice());
                ptm.setInt(4, object.getQuantity());
                result = ptm.executeUpdate() > 0;
            }
        } finally {
            if (ptm != null) { ptm.close(); }
            if (conn != null) { conn.close(); }
        }
        return result;
    }
    
    public boolean reduceStock(int productID, int quantity) throws SQLException, ClassNotFoundException {
        boolean result = false;
        Connection conn = null;
        PreparedStatement ptm = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(REDUCE_STOCK);
                ptm.setInt(1, quantity);
                ptm.setInt(2, productID);
                ptm.setInt(3, quantity); // ensures there is enough stock to fulfil the reduction
                result = ptm.executeUpdate() > 0;
            } else {
                throw new SQLException("Get Connection failed at reduceStock in ProductDAO");
            }
        } finally {
            if (ptm != null) ptm.close();
            if (conn != null) conn.close();
        }
        return result;
    }

}
