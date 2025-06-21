/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.DriverManager;

/**
 *
 * @author tiozo
 */
public class DBUtils {
    
    private static final String DB_NAME = "DBShopping";
    private static final String USER_NAME = "sa";
    private static final String PASSWORD = "12345";    
    
    public static Connection getConnection() throws ClassNotFoundException, SQLException {
        Connection result = null;
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        String url = "jdbc:sqlserver://localhost:1433;databaseName=" + DB_NAME;
        result = DriverManager.getConnection(url, USER_NAME, PASSWORD);
        return result;
    }
}
