/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author zyz7
 */
public class ConnectionSQL {
    
    private String database = "syspharmacy";
    private String user = "web";
    private String password = "password";
    private String url = "jdbc:mysql://localhost:3306/" + database;
    private String sqlite = "jdbc:sqlite:data/syspharmacy.db";
    Connection conn = null;
    
    public Connection getConnection() {
        
        try {
            //Class.forName("com.mysql.cj.jdbc.Driver");
            //conn = DriverManager.getConnection(url, user, password);
            conn = DriverManager.getConnection(sqlite);
            
        //} catch(ClassNotFoundException e) {
            //System.err.println("ERROR => " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("ERROR => " + e.getMessage());
        }
        
        return conn;
    }
}
