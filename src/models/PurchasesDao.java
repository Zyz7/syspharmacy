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
public class PurchasesDao {
    
    ConnectionSQL cn = new ConnectionSQL();
    Connection conn;
    PreparedStatement pst;
    ResultSet rs;
    
    public boolean register (double total, int supplier_id, int employee_id) {
        
        String query = "INSERT INTO purchases (total, created, updated, supplier_id, employee_id) " +
                "VALUES (?, ?, ?, ?, ?)";
        Timestamp datetime = new Timestamp(new Date().getTime());
        LocalDateTime now = LocalDateTime.now();
        String isoDate = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        
        try (Connection conn  = cn.getConnection();
            PreparedStatement pst = conn.prepareStatement(query)) {

            pst.setDouble(1, total);
            pst.setString(2, isoDate);
            pst.setString(3, isoDate);
            pst.setInt(4, supplier_id);
            pst.setInt(5, employee_id);
            pst.execute();
            
            return true;
        } catch(SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            return false;
        }
    }
    
    public boolean registerDetail (double price, int amount, double subtotal, int purchase_id, int product_id) {
        
        String query = "INSERT INTO purchase_details (price, amount, subtotal, purchase_id, product_id) " +
                "VALUES (?, ?, ?, ?, ?)";
        Timestamp datetime = new Timestamp(new Date().getTime());
        
        try (Connection conn  = cn.getConnection();
            PreparedStatement pst = conn.prepareStatement(query)) {

            pst.setDouble(1, price);
            pst.setInt(2, amount);
            pst.setDouble(3, subtotal);
            pst.setInt(4, purchase_id);
            pst.setInt(5, product_id);
            pst.execute();
            
            return true;
        } catch(SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            return false;
        }
    }
    
    public int getID () {
        
        int id = 0;
        String query = "SELECT MAX(id) AS id FROM purchases";
        
        try (Connection conn  = cn.getConnection();
            PreparedStatement pst = conn.prepareStatement(query)) {
            
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    id = rs.getInt("id");
                }
            }        

        } catch(SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        
        return id;
    }
    
    public List list () {
        
        List<Purchases> list_purchase = new ArrayList();
        String query = "SELECT p.*, s.name as supplier_name FROM purchases p, suppliers s WHERE "
                + "p.supplier_id = s.id ORDER BY p.id ASC";
        
        try (Connection conn  = cn.getConnection();
            PreparedStatement pst = conn.prepareStatement(query)) {
            
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    Purchases purchase = new Purchases();
                    purchase.setId(rs.getInt("id"));
                    purchase.setSupplier_name_product(rs.getString("supplier_name"));
                    purchase.setTotal(rs.getDouble("total"));
                    purchase.setCreated(rs.getString("created"));
                    list_purchase.add(purchase);
                }
            }      
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        
        return list_purchase;
    }
    
    public List listDetails (int id) {
        
        List<Purchases> list_purchase = new ArrayList();
        String query = "SELECT p.created, pd.price, pd.amount, pd.subtotal, s.name AS supplier_name, pr.name AS product_name, " +
                "e.full_name, pr.code FROM purchases p INNER JOIN purchase_details pd ON p.id = pd.purchase_id " +
                "INNER JOIN products pr ON pd.product_id = pr.id INNER JOIN suppliers s ON p.supplier_id = s.id " +
                "INNER JOIN employees e ON p.employee_id = e.id WHERE p.id = ?";
        
        try (Connection conn  = cn.getConnection();
            PreparedStatement pst = conn.prepareStatement(query)) {

            pst.setInt(1, id);
            
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    Purchases purchase = new Purchases();
                    purchase.setCode(rs.getString("code"));
                    purchase.setProduct_name(rs.getString("product_name"));
                    purchase.setPurchase_price(rs.getDouble("price"));
                    purchase.setPurchase_amount(rs.getInt("amount"));
                    purchase.setPurchase_subtotal(rs.getDouble("subtotal"));
                    purchase.setSupplier_name_product(rs.getString("supplier_name"));
                    purchase.setCreated(rs.getString("created"));
                    purchase.setPurcharser(rs.getString("full_name"));
                    list_purchase.add(purchase);
                }
            }
                      
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        
        return list_purchase;
    }
    
}
