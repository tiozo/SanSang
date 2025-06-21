/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models.Address;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import models.Role.RoleDTO;
import utils.DBUtils;

/**
 * Được dùng trên server-side only
 * @author tiozo
 */
public class AddressDAO {
    
    private static final String SEARCH_QUERY = "SELECT AddressID, Street, City, State, PostalCode, Country FROM ADDRESS";
    private static final String CREATE = "INSERT INTO ADDRESS (street, city, state, postalcode, country) VALUES(?, ?, ?, ?, ?)";
    private static final String SEARCH_DUPLICATE = "SELECT street, city, country FROM ADDRESS WHERE street = ? AND city = ? AND country = ?";
    private static final String GET_ADDRESS_BY_ID = "SELECT AddressID, Street, City, State, PostalCode, Country FROM ADDRESS WHERE AddressID = ?";
    
    public List<AddressDTO> getListAddress() throws SQLException, ClassNotFoundException {
        List<AddressDTO> result = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(SEARCH_QUERY);
                rs = ptm.executeQuery();
                while (rs.next()) {
                    result.add(AddressDTO.builder(rs.getString("Street"), 
                                         rs.getString("City"), rs.getString("Country"))
                                         .withAddressID(rs.getInt("AddressID"))
                                         .withState(rs.getString("State"))
                                         .withPostalCode(rs.getString("PostalCode"))
                                         .build());
                }
            } else {
                throw new SQLException("Get Connection failed at getListAddress in AddressDAO");
            }
        } finally {
            if (rs != null) rs.close();
            if (ptm != null) ptm.close();
            if (conn != null) conn.close();
        }
        return result;
    }
    
    public AddressDTO getAddressByID(int addressID) throws SQLException, ClassNotFoundException {
        AddressDTO address = null;
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(GET_ADDRESS_BY_ID);
                ptm.setInt(1, addressID);
                rs = ptm.executeQuery();
                if (rs.next()) {
                    address = AddressDTO.builder(rs.getString("Street"), 
                                  rs.getString("City"), rs.getString("Country"))
                               .withAddressID(rs.getInt("AddressID"))
                               .withState(rs.getString("State"))
                               .withPostalCode(rs.getString("PostalCode"))
                               .build();
                }
            } else {
                throw new SQLException("Get Connection failed at getAddressByID in AddressDAO");
            }
        } finally {
            if (rs != null) rs.close();
            if (ptm != null) ptm.close();
            if (conn != null) conn.close();
        }
        return address;
    }
    
    public boolean checkDuplicate(AddressDTO object) throws SQLException, ClassNotFoundException {
        boolean result = false;
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(SEARCH_DUPLICATE);
                ptm.setString(1, object.getStreet());
                ptm.setString(2, object.getCity());
                ptm.setString(3, object.getCountry());
                rs = ptm.executeQuery();
                if (rs.next()) 
                    result = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (rs != null) rs.close();
            if (ptm != null) ptm.close();
            if (conn != null) conn.close();
        }
        return result;
    }
    
    public boolean create(AddressDTO object) throws SQLException, ClassNotFoundException {
        boolean result = false;
        Connection conn = null;
        PreparedStatement ptm = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(CREATE);
                ptm.setString(1, object.getStreet());
                ptm.setString(2, object.getCity());
                ptm.setString(3, object.getState());
                ptm.setString(4, object.getPostalCode());
                ptm.setString(5, object.getCountry());
                result = ptm.executeUpdate() > 0;
            }
        } finally {
            if (ptm != null) ptm.close();
            if (conn != null) conn.close();
        }
        return result;
    }
}
