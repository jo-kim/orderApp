package org.example.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;

public class Jdbc {
    private static Logger log = LoggerFactory.getLogger(Jdbc.class);
    public static Connection getConnection() {
        Connection conn = null;
        try{
            DriverManager.registerDriver(new org.h2.Driver());
            conn = DriverManager.getConnection("jdbc:h2:tcp://localhost/~/test", "sa", "");
        } catch (SQLException e) {
            log.error("Error : {}", e.getMessage());
        }
        return conn;
    }

    public static void close(PreparedStatement pstmt, Connection conn) {
        try {
            pstmt.close();
        } catch (SQLException e) {
            log.error("Error : {}", e.getMessage());
        }
        try{
            conn.close();
        } catch (SQLException e) {
            log.error("Error : {}", e.getMessage());
        }
    }
    public static void close(ResultSet rs, PreparedStatement pstmt, Connection conn) {
        try{
            rs.close();
        } catch (SQLException e) {
            log.error("Error : {}", e.getMessage());
        }
        try {
            pstmt.close();
        } catch (SQLException e) {
            log.error("Error : {}", e.getMessage());
        }
        try{
            conn.close();
        } catch (SQLException e) {
            log.error("Error : {}", e.getMessage());
        }
    }
}
