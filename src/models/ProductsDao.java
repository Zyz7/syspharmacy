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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author zyz7
 */
public class ProductsDao {
    
    ConnectionSQL cn = new ConnectionSQL();
    Connection conn;
    PreparedStatement pst;
    ResultSet rs;
    
    public boolean register (Products product) {
        
        String query = "INSERT INTO products (code, name, description, unit_price, quantity, created, updated, category_id) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        Timestamp datetime = new Timestamp(new Date().getTime());
        
        try {
            conn  = cn.getConnection();
            pst = conn.prepareStatement(query);
            pst.setString(1, product.getCode());
            pst.setString(2, product.getName());
            pst.setString(3, product.getDescription());
            pst.setDouble(4, product.getUnit_price());
            pst.setInt(5, product.getQuantity());
            pst.setTimestamp(6, datetime);
            pst.setTimestamp(7, datetime);
            pst.setInt(8, product.getCategory_id());
            pst.execute();
            
            return true;
        } catch(SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            return false;
        }
    }
    
    public List list (String value) {
        
        List<Products> list_products = new ArrayList();
        String query = "SELECT p.*, c.name AS category_name FROM products p, categories c WHERE p.category_id = c.id";
        String search_product = "SELECT p.*, c.name AS category_name FROM products p INNER JOIN categories c ON p.category_id " +
                "= c.id WHERE p.name LIKE '%" + value + "%'";
        
        try {
            conn  = cn.getConnection();
            if (value.equalsIgnoreCase("")) {
                pst = conn.prepareStatement(query);
                rs = pst.executeQuery();
            } else {
                pst = conn.prepareStatement(search_product);
                rs = pst.executeQuery();
            }
            
            while (rs.next()) {
                Products product = new Products();
                product.setId(rs.getInt("id"));
                product.setCode(rs.getString("code"));
                product.setName(rs.getString("name"));
                product.setDescription(rs.getString("description"));
                product.setUnit_price(rs.getDouble("unit_price"));
                product.setQuantity(rs.getInt("quantity"));
                product.setCategory_name(rs.getString("category_name"));
                list_products.add(product);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString());
        }
        
        return list_products;
    }
    
    public boolean update (Products product) {
        
        String query = "UPDATE products SET code = ?, name = ?, description = ?, unit_price = ?, quantity = ?, updated = ?, category_id = ? " +
                "WHERE id = ?";
        Timestamp datetime = new Timestamp(new Date().getTime());
        
        try {
            conn  = cn.getConnection();
            pst = conn.prepareStatement(query);
            pst.setString(1, product.getCode());
            pst.setString(2, product.getName());
            pst.setString(3, product.getDescription());
            pst.setDouble(4, product.getUnit_price());
            pst.setInt(5, product.getQuantity());
            pst.setTimestamp(6, datetime);
            pst.setInt(7, product.getCategory_id());
            pst.setInt(8, product.getId());
            pst.execute();
            
            return true;
        } catch(SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            return false;
        }
    }
    
    public boolean delete (int id) {
        
        String query = "DELETE FROM products WHERE id = " + id;
        try {
            conn  = cn.getConnection();
            pst = conn.prepareStatement(query);
            pst.execute();
            
            return true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            return false;
        }
    }
    
    public Products searchCategory (String name) {
        
        String query = "SELECT * FROM categories WHERE name = ?";
        Products product = new Products();
        
        try {
            conn  = cn.getConnection();
            pst = conn.prepareStatement(query);
            pst.setString(1, name);
            rs = pst.executeQuery();
            
            if (rs.next()) {
                
                product.setId(rs.getInt("id"));
                product.setCategory_name(rs.getString("name"));
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        
        return product;
    }
    
    public Products searchById (int id) {
        
        String query = "SELECT p.*, c.name AS category_name FROM products p INNER JOIN categories c " + 
                "ON p.category_id = c.id WHERE p.category_id = ?";
        Products product = new Products();
        
        try {
            conn  = cn.getConnection();
            pst = conn.prepareStatement(query);
            pst.setInt(1, id);
            rs = pst.executeQuery();
            
            if (rs.next()) {
                
                product.setId(rs.getInt("id"));
                product.setCode(rs.getString("code"));
                product.setName(rs.getString("name"));
                product.setDescription(rs.getString("description"));
                product.setUnit_price(rs.getDouble("unit_price"));
                product.setQuantity(rs.getInt("quantity"));
                product.setCategory_id(rs.getInt("category_id"));
                product.setCategory_name(rs.getString("category_name"));
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        
        return product;
    }
    
    public Products searchByCode (String code) {
        
        String query = "SELECT id, name, unit_price, quantity FROM products WHERE code = ?";
        Products product = new Products();
        
        try {
            conn  = cn.getConnection();
            pst = conn.prepareStatement(query);
            pst.setString(1, code);
            rs = pst.executeQuery();
            
            if (rs.next()) {
                
                product.setId(rs.getInt("id"));
                product.setName(rs.getString("name"));
                product.setUnit_price(rs.getDouble("unit_price"));
                product.setQuantity(rs.getInt("quantity"));
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        
        return product;
    }
    
    public Products search (int id) {
        
        String query = "SELECT quantity FROM products WHERE id = ?";
        Products product = new Products();
        
        try {
            conn  = cn.getConnection();
            pst = conn.prepareStatement(query);
            pst.setInt(1, id);
            rs = pst.executeQuery();
            
            if (rs.next()) {
                
                product.setQuantity(rs.getInt("quantity"));
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        
        return product;
    }
    
    public boolean updateProduct (double price, int amount, int product_id) {
        
        String query = "UPDATE products SET unit_price = ?, quantity = ? WHERE id = ?";
        
        try {
            conn  = cn.getConnection();
            pst = conn.prepareStatement(query);
            pst.setDouble(1, price);
            pst.setInt(2, amount);
            pst.setInt(3, product_id);
            pst.execute();
            
            return true;
        } catch(SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            return false;
        }
    }
    
}
