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
public class SalesDao {
    
    ConnectionSQL cn = new ConnectionSQL();
    Connection conn;
    PreparedStatement pst;
    ResultSet rs;
    
    public boolean register (double total, int customer_id, int employee_id) {
        
        String query = "INSERT INTO sales (date, total, customer_id, employee_id) VALUES (?, ?, ?, ?)";
        Timestamp datetime = new Timestamp(new Date().getTime());
        
        try {
            conn  = cn.getConnection();
            pst = conn.prepareStatement(query);
            pst.setTimestamp(1, datetime);
            pst.setDouble(2, total);
            pst.setInt(3, customer_id);
            pst.setInt(4, employee_id);
            pst.execute();
            
            return true;
        } catch(SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            return false;
        }
    }
    
    public boolean registerDetail (int quantity, double price, double subtotal, int product_id, int sale_id) {
        
        String query = "INSERT INTO sales_details (quantity, price, subtotal, product_id, sale_id) " +
                "VALUES (?, ?, ?, ?, ?)";
        Timestamp datetime = new Timestamp(new Date().getTime());
        
        try {
            conn  = cn.getConnection();
            pst = conn.prepareStatement(query);
            pst.setInt(1, quantity);
            pst.setDouble(2, price);
            pst.setDouble(3, subtotal);
            pst.setInt(4, product_id);
            pst.setInt(5, sale_id);
            pst.execute();
            
            return true;
        } catch(SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            return false;
        }
    }
    
    public int getID () {
        
        int id = 0;
        String query = "SELECT MAX(id) AS id FROM sales";
        
        try {
            conn  = cn.getConnection();
            pst = conn.prepareStatement(query);
            rs = pst.executeQuery();
            
            if (rs.next()) {
                id = rs.getInt("id");
            }

        } catch(SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        
        return id;
    }
    
    public List list () {
        
        List<Sales> list_sale = new ArrayList();
        String query = "SELECT s.*, c.full_name AS customer_name, e.full_name AS employee_name FROM sales s " +
                "INNER JOIN customers c ON s.customer_id = c.id " +
                "INNER JOIN employees e ON s.employee_id = e.id ORDER BY s.id ASC";
        
        try {
            conn  = cn.getConnection();
            pst = conn.prepareStatement(query);
            rs = pst.executeQuery();
            
            while (rs.next()) {
                Sales sale = new Sales();
                sale.setId(rs.getInt("id"));
                sale.setCustomer_name(rs.getString("customer_name"));
                sale.setEmployee_name(rs.getString("employee_name"));
                sale.setTotal(rs.getDouble("total"));
                sale.setDate(rs.getString("date"));
                
                list_sale.add(sale);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString());
        }
        
        return list_sale;
    }
    
     public List listDetails (int id) {
        
        List<Sales> list_sale = new ArrayList();        
        String query = "SELECT s.id, s.date, s.total, sd.price, sd.quantity, sd.subtotal, " +
                "pr.name, pr.code FROM sales s INNER JOIN sales_details sd ON s.id = sd.sale_id " +
                "INNER JOIN products pr ON sd.product_id = pr.id " +
                "INNER JOIN employees e ON s.employee_id = e.id WHERE s.id = ?";
        
        try {
            conn  = cn.getConnection();
            pst = conn.prepareStatement(query);
            pst.setInt(1, id);
            rs = pst.executeQuery();
            
            while (rs.next()) {
                Sales sale = new Sales();
                sale.setId(rs.getInt("id"));
                sale.setDate(rs.getString("date"));
                sale.setTotal(rs.getDouble("total"));
                sale.setCode(rs.getString("code"));
                sale.setProduct_name(rs.getString("name"));
                sale.setPrice(rs.getDouble("price"));
                sale.setQuantity(rs.getInt("quantity"));
                sale.setSubtotal(rs.getDouble("subtotal"));
                
                list_sale.add(sale);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        
        return list_sale;
    }
    
}
