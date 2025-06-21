/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models.Role;

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
public class RoleDAO {
    
//    RoleID INT IDENTITY(1,1) PRIMARY KEY,
//    RoleName VARCHAR(50) NOT NULL UNIQUE
    
    private static final String SEARCH_QUERY = "SELECT RoleID, RoleName FROM ROLE";

    
    public List<RoleDTO> getListRole() throws SQLException, ClassNotFoundException {
        List<RoleDTO> result = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(SEARCH_QUERY);
                rs = ptm.executeQuery();
                while (rs.next()) {
                    result.add(RoleDTO.builder(rs.getInt("RoleID"), rs.getString("RoleName")).build());
                }
            } else {
                throw new SQLException("Get Connection failed at getListRole in RoleDAO");
            }
        } finally {
            if (rs != null) rs.close();
            if (ptm != null) ptm.close();
            if (conn != null) conn.close();
        }
        return result;
    }
}
