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
public class CustomersDao {
    
    ConnectionSQL cn = new ConnectionSQL();
    Connection conn;
    PreparedStatement pst;
    ResultSet rs;
    
    public boolean register (Customers customer) {
        
        String query = "INSERT INTO customers (full_name, address, telephone, email, created, updated) " +
                "VALUES (?, ?, ?, ?, ?, ?)";
        Timestamp datetime = new Timestamp(new Date().getTime());
        LocalDateTime now = LocalDateTime.now();
        String isoDate = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        
        try (Connection conn  = cn.getConnection();
            PreparedStatement pst = conn.prepareStatement(query)) {

            pst.setString(1, customer.getFull_name());
            pst.setString(2, customer.getAddress());
            pst.setString(3, customer.getTelephone());
            pst.setString(4, customer.getEmail());
            pst.setString(5, isoDate);
            pst.setString(6, isoDate);
            pst.execute();
            
            return true;
        } catch(SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            return false;
        }
    }
    
    public List list (String value) {
        
        List<Customers> list_customers = new ArrayList();
        String query = "SELECT * FROM customers";
        String search_customer = "SELECT * FROM customers WHERE full_name LIKE '%" + value + "%'";
        
        try (Connection conn  = cn.getConnection()) {

            if (value.equalsIgnoreCase("")) {
                try (PreparedStatement pst = conn.prepareStatement(query); ResultSet rs = pst.executeQuery()) {
                    while (rs.next()) {
                        Customers customer = new Customers();
                        customer.setId(rs.getInt("id"));
                        customer.setFull_name(rs.getString("full_name"));
                        customer.setAddress(rs.getString("address"));
                        customer.setTelephone(rs.getString("telephone"));
                        customer.setEmail(rs.getString("email"));
                        list_customers.add(customer);
                    }
                }
            } else {
                try (PreparedStatement pst = conn.prepareStatement(search_customer); ResultSet rs = pst.executeQuery()) {
                    while (rs.next()) {
                        Customers customer = new Customers();
                        customer.setId(rs.getInt("id"));
                        customer.setFull_name(rs.getString("full_name"));
                        customer.setAddress(rs.getString("address"));
                        customer.setTelephone(rs.getString("telephone"));
                        customer.setEmail(rs.getString("email"));
                        list_customers.add(customer);
                    }
                }
            }
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString());
        }
        
        return list_customers;
    }
    
    public boolean update (Customers customer) {
        
        String query = "UPDATE customers SET full_name = ?, address = ?, telephone = ?, email = ?, updated = ? WHERE id = ?";
        Timestamp datetime = new Timestamp(new Date().getTime());
        LocalDateTime now = LocalDateTime.now();
        String isoDate = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        
        try (Connection conn  = cn.getConnection();
            PreparedStatement pst = conn.prepareStatement(query)) {

            pst.setString(1, customer.getFull_name());
            pst.setString(2, customer.getAddress());
            pst.setString(3, customer.getTelephone());
            pst.setString(4, customer.getEmail());
            pst.setString(5, isoDate);
            pst.setInt(6, customer.getId());
            pst.execute();
            
            return true;
        } catch(SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString());
            return false;
        }
    }
    
    public boolean delete (int id) {
        
        String query = "DELETE FROM customers WHERE id = " + id;
        
        try (Connection conn  = cn.getConnection();
            PreparedStatement pst = conn.prepareStatement(query)) {

            pst.execute();
            
            return true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            return false;
        }
    }
    
    public Customers search (int id) {
        
        String query = "SELECT * FROM customers WHERE id = ?";
        Customers customer = new Customers();
        
        try (Connection conn  = cn.getConnection();
            PreparedStatement pst = conn.prepareStatement(query)) {

            pst.setInt(1, id);
            
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    customer.setFull_name(rs.getString("full_name"));
                }
            }
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        
        return customer;
    }
    
}
