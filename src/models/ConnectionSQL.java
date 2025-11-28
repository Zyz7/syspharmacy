/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author zyz7
 */
public class ConnectionSQL {
    
    private Connection conn;
    private final String DBNAME = "syspharmacy.db";
    
    // Main folder of the app according to operating system
    private String getAppDataDir() {
        String os = System.getProperty("os.name").toLowerCase();

        if (os.contains("win")) {
            return System.getenv("APPDATA") + "\\SysPharmacy\\";
        } else if (os.contains("mac")) {
            return System.getProperty("user.home") + "/Library/Application Support/SysPharmacy/";
        } else { 
            return System.getProperty("user.home") + "/.local/share/SysPharmacy/";
        }
    }
    
    // Full path of the database file 
    private String getDBPath() {
        return getAppDataDir() + DBNAME;
    }
    
    // Automatic copy of the database if it does not exists in the user's folder
    private void ensureDatabaseExists() throws IOException {
        
        File dataDir = new File(getAppDataDir());
        if (!dataDir.exists()) {
            dataDir.mkdirs();
        }

        File dbFile = new File(getDBPath());
        if (!dbFile.exists()) {
            System.out.println("Database not found, copying from data folder...");

            try (InputStream is = ConnectionSQL.class.getResourceAsStream("/data/" + DBNAME);
                 FileOutputStream fos = new FileOutputStream(dbFile)) {

                if (is == null) {
                    throw new FileNotFoundException("The file does not exist within the data folder");
                }

                byte[] buffer = new byte[4096];
                int bytesRead;

                while ((bytesRead = is.read(buffer)) != -1) {
                    fos.write(buffer, 0, bytesRead);
                }

                System.out.println("Database successfully copied to: " + getDBPath());
            }
        }
    }
    
    public Connection getConnection() throws SQLException {
        
        if (conn == null || conn.isClosed()) {
            try {
                ensureDatabaseExists();

            } catch(IOException e) {
                throw new SQLException("Error copying database: " + e.getMessage());
            }
            
            conn = DriverManager.getConnection("jdbc:sqlite:" + getDBPath());
        }
        
        return conn;
    }
}
