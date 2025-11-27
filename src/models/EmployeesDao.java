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
public class EmployeesDao {
    
    ConnectionSQL cn = new ConnectionSQL();
    Connection conn;
    PreparedStatement pst;
    ResultSet rs;
    
    public static int id_user = 0;
    public static String full_name_user = "";
    public static String username_user = "";
    public static String address_user = "";
    public static String role_user = "";
    public static String email_user = "";
    public static String telephone_user = "";
    
    public Employees login (String user, String password) {
        
        String query = "SELECT * FROM employees WHERE username = ? AND password = ?";
        Employees employee = new Employees();
        
        try (Connection conn  = cn.getConnection();
            PreparedStatement pst = conn.prepareStatement(query)) {
            
            pst.setString(1, user);
            pst.setString(2, password);
            
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    employee.setId(rs.getInt("id"));
                    id_user = employee.getId();
                    employee.setFull_name(rs.getString("full_name"));
                    full_name_user = employee.getFull_name();
                    employee.setUsername(rs.getString("username"));
                    username_user = employee.getUsername();
                    employee.setAddress(rs.getString("address"));
                    address_user = employee.getAddress();
                    employee.setTelephone(rs.getString("telephone"));
                    telephone_user = employee.getTelephone();
                    employee.setEmail(rs.getString("email"));
                    email_user = employee.getEmail();
                    employee.setRol(rs.getString("rol"));
                    role_user = employee.getRol();
                }
            }
            
        } catch(SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        
        return employee;
    }
    
    public boolean register (Employees employee) {
        
        String query = "INSERT INTO employees (full_name, username, address, telephone, email, password, rol, created, updated) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        Timestamp datetime = new Timestamp(new Date().getTime());
        
        try {
            conn  = cn.getConnection();
            pst = conn.prepareStatement(query);
            pst.setString(1, employee.getFull_name());
            pst.setString(2, employee.getUsername());
            pst.setString(3, employee.getAddress());
            pst.setString(4, employee.getTelephone());
            pst.setString(5, employee.getEmail());
            pst.setString(6, employee.getPassword());
            pst.setString(7, employee.getRol());
            pst.setTimestamp(8, datetime);
            pst.setTimestamp(9, datetime);
            pst.execute();
            
            return true;
        } catch(SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            return false;
        }
    }
    
    public List list (String value) {
        
        List<Employees> list_employees = new ArrayList();
        String query = "SELECT * FROM employees ORDER BY rol ASC";
        String search_employee = "SELECT * FROM employees WHERE full_name LIKE '%" + value + "%'";
        
        try {
            conn  = cn.getConnection();
            if (value.equalsIgnoreCase("")) {
                pst = conn.prepareStatement(query);
                rs = pst.executeQuery();
            } else {
                pst = conn.prepareStatement(search_employee);
                rs = pst.executeQuery();
            }
            
            while (rs.next()) {
                Employees employee = new Employees();
                employee.setId(rs.getInt("id"));
                employee.setFull_name(rs.getString("full_name"));
                employee.setUsername(rs.getString("username"));
                employee.setAddress(rs.getString("address"));
                employee.setTelephone(rs.getString("telephone"));
                employee.setEmail(rs.getString("email"));
                employee.setRol(rs.getString("rol"));
                list_employees.add(employee);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        
        return list_employees;
    }
    
    public boolean update (Employees employee) {
        
        String query = "UPDATE employees SET full_name = ?, username = ?, address = ?, telephone = ?, email = ?, rol = ?, updated = ? " +
                "WHERE id = ?";
        Timestamp datetime = new Timestamp(new Date().getTime());
        
        try {
            conn  = cn.getConnection();
            pst = conn.prepareStatement(query);
            pst.setString(1, employee.getFull_name());
            pst.setString(2, employee.getUsername());
            pst.setString(3, employee.getAddress());
            pst.setString(4, employee.getTelephone());
            pst.setString(5, employee.getEmail());
            pst.setString(6, employee.getRol());
            pst.setTimestamp(7, datetime);
            pst.setInt(8, employee.getId());
            pst.execute();

            return true;
        } catch(SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            return false;
        }
    }
    
    public boolean verifyPass(String pass, int id) {
        
        String query = "SELECT * FROM employees WHERE password = ? AND id = ?";
        Employees employee = new Employees();
        
        try {
            conn  = cn.getConnection();
            pst = conn.prepareStatement(query);
            pst.setString(1, pass);
            pst.setInt(2, id);
            rs = pst.executeQuery();
            
            if (rs.next()) {
                return true;
            } else {
                return false;
            }
            
        } catch(SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            return false;
        }
        
    }
    
    public boolean updatePass (Employees employee) {
        
        String query = "UPDATE employees SET password = ? WHERE id = ?";
        try {
            conn  = cn.getConnection();
            pst = conn.prepareStatement(query);
            pst.setString(1, employee.getPassword());
            pst.setInt(2, employee.getId());
            pst.executeUpdate();
            
            return true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            return false;
        }
    }
    
    public boolean delete (int id) {
        
        String query = "DELETE FROM employees WHERE id = " + id;
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
    
}
