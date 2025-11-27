/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import com.sun.tools.javac.Main;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
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
    private String path = "jdbc:sqlite:data/syspharmacy.db";
    Connection conn = null;
    
    private String getDbPath() {
        try {
            // Carpeta donde está el jar
            String basePath = new File(
            getClass().getProtectionDomain().getCodeSource().getLocation().toURI()
            ).getParent();
            return basePath + File.separator + "data" + File.separator + "syspharmacy.db";
        } catch (URISyntaxException e) {
            throw new RuntimeException("No se pudo resolver la ruta del jar", e);
        }
    }
    
    public Connection getConnection() {
        
        String dbPath = getDbPath();
        //path = "jdbc:sqlite:" + dbPath;
        
        try {
            //Class.forName("com.mysql.cj.jdbc.Driver");
            //conn = DriverManager.getConnection(url, user, password);
            conn = DriverManager.getConnection(path);
            
        //} catch(ClassNotFoundException e) {
            //System.err.println("ERROR => " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("ERROR SQL => " + e.getMessage());
        }
        
        return conn;
    }
}
