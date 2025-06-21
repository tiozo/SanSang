/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import utils.DBUtils;

/**
 *
 * @author tiozo
 */
public class UserDAO {
    
    
//    UserID NVARCHAR(50) PRIMARY KEY, -- Thay đổi từ INT sang NVARCHAR
//    Name NVARCHAR(100) NOT NULL,
//    RoleID INT NOT NULL,
//    Phone NVARCHAR(15),
//    AddressID INT NOT NULL,
//    Password NVARCHAR(255) NOT NULL,
//    FOREIGN KEY (RoleID) REFERENCES ROLE(RoleID),
//    FOREIGN KEY (AddressID) REFERENCES ADDRESS(AddressID)
    
    private static final String LOGIN_QUERY = "SELECT Name, RoleID, Phone, AddressID FROM USERS WHERE userID = ? AND password = ?";
    private static final String SEARCH_QUERY = "SELECT userID, Name, roleID, Phone FROM USERS WHERE Name LIKE ? OR userID like ?";
    private static final String SEARCH_DUPLICATE = "SELECT userID FROM USERS WHERE userID = ?";
    private static final String CREATE_QUERY = "INSERT INTO USERS (UserID, Name, RoleID, Phone, AddressID, Password) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String UPDATE_QUERY = "UPDATE USERS SET Name = ?, roleID = ?, Phone = ?  WHERE userID = ?";
    private static final String DELETE_QUERY = "DELETE USERS WHERE userID = ?";
    
    /**
     *
     * @param userID
     * @param password
     * @return UserDTO
     * @throws SQLException
     */
    public UserDTO getCredential(String userID, String password) throws SQLException, ClassNotFoundException {
        UserDTO user = null;
        
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(LOGIN_QUERY);
                ptm.setString(1, userID);
                ptm.setString(2, password);
                rs = ptm.executeQuery();
                if (rs.next()) {
//                    userId, String name, int roleId, int addressId, String password
                    user = new UserDTO.Builder(userID, rs.getString("name"), 
                                               rs.getInt("roleID"), rs.getInt("addressID"), 
                                               "***")
                                               .build();
                }
            } else {
                throw new SQLException("Get Connection failed at getCredential in UserDAO");
            }
        } finally {
            if (rs != null) rs.close();
            if (ptm != null) ptm.close();
            if (conn != null) conn.close();
        }
        
        return user;
    }
    
    public boolean checkDuplicate(String userID) throws SQLException, ClassNotFoundException {
        boolean result = false;
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(SEARCH_DUPLICATE);
                ptm.setString(1, userID);
                rs = ptm.executeQuery();
                if (rs.next()) 
                    result = true;
            } else {
                throw new SQLException("Get Connection failed at getCredential in UserDAO");
            }
        } finally {
            if (rs != null) rs.close();
            if (ptm != null) ptm.close();
            if (conn != null) conn.close();
        }
        return result;
    }
    
    public List<UserDTO> getListUser(String search) throws SQLException, ClassNotFoundException {
        List<UserDTO> result = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(SEARCH_QUERY);
                ptm.setString(1, "%" + search + "%");
                ptm.setString(2, "%" + search + "%");
                rs = ptm.executeQuery();
                while (rs.next()) {
                    UserDTO user = new UserDTO.Builder(rs.getString("userID"), 
                                                rs.getString("Name"), rs.getInt("roleID"), 0, "***")
                                                .withPhone(rs.getString("phone")).build();
                    result.add(user);
                }
            } else {
                throw new SQLException("DB connection failed.");
            }
        } finally {
            if (rs != null) rs.close();
            if (ptm != null) ptm.close();
            if (conn != null) conn.close();
        }
        return result;
    }
    
    public boolean create(UserDTO object) throws SQLException, ClassNotFoundException {
        boolean result = false;
        Connection conn = null;
        PreparedStatement ptm = null;
        try {
//                private static final String CREATE = "INSERT INTO USERS (UserID, Name, RoleID, Phone, AddressID, Password) VALUES (?, ?, ?, ?, ?, ?)";

            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(CREATE_QUERY);
                ptm.setString(1, object.getUserId());
                ptm.setString(2, object.getName());
                ptm.setInt(3, object.getRoleId());
                ptm.setString(4, object.getPhone());
                ptm.setInt(5, object.getAddressId());
                ptm.setString(6, object.getPassword());
                result = ptm.executeUpdate() > 0;
            } else {
                throw new SQLException("DB connection failed.");
            }
        } finally {
            if (ptm != null) ptm.close();
            if (conn != null) conn.close();
        }
        return result;
    }
    
    public boolean update(UserDTO user) throws SQLException, ClassNotFoundException {
        boolean result = false;
        Connection conn = null;
        PreparedStatement ptm = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(UPDATE_QUERY);
                ptm.setString(1, user.getName());
                ptm.setInt(2, user.getRoleId());
                ptm.setString(3, user.getPhone());
                ptm.setString(4, user.getUserId());
                result = ptm.executeUpdate() > 0;
            } else {
                throw new SQLException("DB connection failed.");
            }
        } finally {
            if (ptm != null) ptm.close();
            if (conn != null) conn.close();
        }
        return result;
    }
    
    public boolean delete(String userID) throws SQLException, ClassNotFoundException {
        boolean result = false;
        Connection conn = null;
        PreparedStatement ptm = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(DELETE_QUERY);
                ptm.setString(1, userID);
                result = ptm.executeUpdate() > 0;
            } else {
                throw new SQLException("DB connection failed.");
            }
        } finally {
            if (ptm != null) ptm.close();
            if (conn != null) conn.close();
        }
        return result;
    }
}
