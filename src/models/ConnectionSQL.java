/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import com.sun.tools.javac.Main;
import java.io.File;
import java.net.URISyntaxException;
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
    Connection conn = null;
    
    public Connection getConnection() {
        
        String basePath;
        File dbFile;
        
        try {
            String path = Main.class.getProtectionDomain().getCodeSource().getLocation().getPath();
            basePath = new File (path).getParentFile().getAbsolutePath();
            dbFile = new File (basePath + "/data/syspharmacy.db");
            
            if (!dbFile.exists()) {
                basePath = new File(".").getAbsolutePath();
                dbFile = new File(basePath + "/data/syspharmacy.db");
            }
        } catch(Exception e) {
            basePath = new File(".").getAbsolutePath();
            dbFile = new File(basePath + "/data/syspharmacy.db");
        }
    
        String sqlite = "jdbc:sqlite:" + basePath + "/data/syspharmacy.db";
        
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
