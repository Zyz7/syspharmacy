/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author zyz7
 */
public class CategoriesDao {
    
    ConnectionSQL cn = new ConnectionSQL();
    Connection conn;
    PreparedStatement pst;
    ResultSet rs;
    
    public boolean register (Categories category) {
        
        String query = "INSERT INTO categories (name, created, updated) VALUES (?, ?, ?)";
        Timestamp datetime = new Timestamp(new Date().getTime());
        LocalDateTime now = LocalDateTime.now();
        String isoDate = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        
        try (Connection conn  = cn.getConnection();
            PreparedStatement pst = conn.prepareStatement(query)) {

            pst.setString(1, category.getName());
            pst.setString(2, isoDate);
            pst.setString(3, isoDate);
            pst.execute();
            
            return true;
        } catch(SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            return false;
        }
    }
    
    public List list (String value) {
        
        List<Categories> list_categories = new ArrayList();
        String query = "SELECT * FROM categories";
        String search_categories = "SELECT * FROM categories WHERE name LIKE '%" + value + "%'";
        
        try (Connection conn  = cn.getConnection()) {
            
            if (value.equalsIgnoreCase("")) {
                try (PreparedStatement pst = conn.prepareStatement(query); ResultSet rs = pst.executeQuery()) {
                    while (rs.next()) {
                        Categories category = new Categories();
                        category.setId(rs.getInt("id"));
                        category.setName(rs.getString("name"));
                        list_categories.add(category);
                    }
                }
            } else {
                try (PreparedStatement pst = conn.prepareStatement(search_categories); ResultSet rs = pst.executeQuery()) {
                    while (rs.next()) {
                        Categories category = new Categories();
                        category.setId(rs.getInt("id"));
                        category.setName(rs.getString("name"));
                        list_categories.add(category);
                    }
                }
            }
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString());
        }
        
        return list_categories;
    }
    
    public boolean update (Categories category) {
        
        String query = "UPDATE categories SET name = ?, updated = ? WHERE id = ?";
        Timestamp datetime = new Timestamp(new Date().getTime());
        LocalDateTime now = LocalDateTime.now();
        String isoDate = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        
        try (Connection conn  = cn.getConnection();
            PreparedStatement pst = conn.prepareStatement(query)) {

            pst.setString(1, category.getName());
            pst.setString(2, isoDate);
            pst.setInt(3, category.getId());
            pst.execute();
            
            return true;
        } catch(SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            return false;
        }
    }
    
    public boolean delete (int id) {
        
        String query = "DELETE FROM categories WHERE id = " + id;
        
        try (Connection conn  = cn.getConnection();
            PreparedStatement pst = conn.prepareStatement(query)) {

            pst.execute();
            return true;
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            return false;
        }
    }
}
