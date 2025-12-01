/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import models.Employees;
import models.EmployeesDao;
import static models.EmployeesDao.id_user;
import static models.EmployeesDao.role_user;
import views.SystemView;

/**
 *
 * @author zyz7
 */
public class EmployeesController implements ActionListener, MouseListener, KeyListener {
    
    private Employees employee;
    private EmployeesDao employee_dao;
    private SystemView view;
    
    String rol = role_user;
    DefaultTableModel model = new DefaultTableModel();

    public EmployeesController(Employees employee, EmployeesDao employee_dao, SystemView view) {
        this.employee = employee;
        this.employee_dao = employee_dao;
        this.view = view;
        
        this.view.jButton_employee_register.addActionListener(this);
        this.view.jButton_employee_update.addActionListener(this);
        this.view.jButton_employee_delete.addActionListener(this);
        this.view.jButton_employee_clear.addActionListener(this);
        this.view.jButton_profile_update.addActionListener(this);
        
        this.view.jTable_employees.addMouseListener(this);
        this.view.jLabel_employees.addMouseListener(this);
        
        this.view.jText_search_employee.addKeyListener(this);
        
        if (!rol.equals("Administrator")) {
            view.jTabbed.setEnabledAt(0, false);
            view.jLabel_purchases.setEnabled(false);
            view.jTabbed.setEnabledAt(2, false);
            view.jLabel_employees.setEnabled(false);
            view.jTabbed.setEnabledAt(5, false);
            view.jLabel_reports.setEnabled(false);
        } 
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        
        if (e.getSource() == view.jButton_employee_register) {
            
            if (view.jText_employee_fullname.getText().equals("") ||
                    view.jText_employee_address.getText().equals("") || view.jText_employee_email.getText().equals("") ||
                    view.jText_employee_phone.getText().equals("") || view.jText_employee_username.getText().equals("") || 
                    view.jCombo_role.getSelectedItem().toString().equals("Select an option") || String.valueOf(view.jPassword_employee.getPassword()).equals("")) {
                
                JOptionPane.showMessageDialog(null, "No field must be empty");
            } else {
                //employee.setId(Integer.parseInt(view.jText_employee_id.getText().trim()));
                employee.setFull_name(view.jText_employee_fullname.getText().trim());
                employee.setUsername(view.jText_employee_username.getText().trim());
                employee.setAddress(view.jText_employee_address.getText().trim());
                employee.setTelephone(view.jText_employee_phone.getText().trim());
                employee.setEmail(view.jText_employee_email.getText().trim());
                employee.setPassword(String.valueOf(view.jPassword_employee.getPassword()));
                employee.setRol(view.jCombo_role.getSelectedItem().toString());
                
                if (employee_dao.register(employee)) {
                    cleanTable();
                    cleanFields();
                    listEmployees();
                    JOptionPane.showMessageDialog(null, "Successfully registered employee");
                } else {
                    JOptionPane.showMessageDialog(null, "Error registering the employee");
                }
            }
        } else if (e.getSource() == view.jButton_employee_update) {
            
            if (view.jText_employee_id.equals("")) {
                JOptionPane.showMessageDialog(null, "Select an employee to continue");
            } else {
                
                if (view.jText_employee_id.getText().equals("") || view.jText_employee_fullname.getText().equals("")
                        || view.jCombo_role.getSelectedItem().toString().equals("")) {
                    
                    JOptionPane.showMessageDialog(null, "All fields are required");
                } else {
                    employee.setId(Integer.parseInt(view.jText_employee_id.getText().trim()));
                    employee.setFull_name(view.jText_employee_fullname.getText().trim());
                    employee.setUsername(view.jText_employee_username.getText().trim());
                    employee.setAddress(view.jText_employee_address.getText().trim());
                    employee.setTelephone(view.jText_employee_phone.getText().trim());
                    employee.setEmail(view.jText_employee_email.getText().trim());
                    //employee.setPassword(String.valueOf(view.jPassword_employee.getPassword()));
                    employee.setRol(view.jCombo_role.getSelectedItem().toString());
                    
                    if (employee_dao.update(employee)) {
                        cleanTable();
                        cleanFields();
                        listEmployees();
                        view.jPassword_employee.setEnabled(true);
                        view.jButton_employee_register.setEnabled(true);
                        JOptionPane.showMessageDialog(null, "Successfully modified employee");
                    } else {
                        JOptionPane.showMessageDialog(null, "Error modifying the employee");
                    }
                }
            }
            
        } else if (e.getSource() == view.jButton_employee_delete) {
            
            int row = view.jTable_employees.getSelectedRow();
            if (row == -1) {
                
                JOptionPane.showMessageDialog(null, "Select an employee to continue");
            } else if (view.jTable_employees.getValueAt(row, 0).equals(id_user)) {
                
                JOptionPane.showMessageDialog(null, "You cannot delete the same user who logged in");
            } else {
                
                int id = Integer.parseInt(view.jTable_employees.getValueAt(row, 0).toString());
                int question = JOptionPane.showConfirmDialog(null, "¿ Are you sure about eliminating the employee ?");
                
                if (question == 0 && employee_dao.delete(id) != false) {
                    cleanTable();
                    cleanFields();
                    listEmployees();
                    view.jPassword_employee.setEnabled(true);
                    view.jButton_employee_register.setEnabled(true);
                    JOptionPane.showMessageDialog(null, "Successfully eliminated employee");
                }
            }
            
        } else if (e.getSource() == view.jButton_employee_clear) {
            cleanTable();
            cleanFields();
            listEmployees();
            view.jPassword_employee.setEnabled(true);
            view.jButton_employee_register.setEnabled(true);
            
        } else if (e.getSource() == view.jButton_profile_update) {
            
            int id = Integer.parseInt(view.jText_profile_id.getText().trim());
            String pass = String.valueOf(view.jPassword_profile.getPassword());
            String new_pass = String.valueOf(view.jPassword_new_profile.getPassword());
            String confirm = String.valueOf(view.jPassword_confirm_profile.getPassword());
            
            if (!pass.equals("") && !new_pass.equals("") && !confirm.equals("")) {
                if (new_pass.equals(confirm)) {
                    
                    if (employee_dao.verifyPass(pass, id)) {
                        employee.setId(Integer.parseInt(view.jText_profile_id.getText().trim()));
                        employee.setPassword(String.valueOf(view.jPassword_new_profile.getPassword()));
                    
                        if (employee_dao.updatePass(employee) != false) {
                            cleanFields();
                            JOptionPane.showMessageDialog(null, "Successfully modified password");
                        } else {
                            JOptionPane.showMessageDialog(null, "Error modifying the password");
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "The current password is incorrect");
                    }
                    
                } else {
                    JOptionPane.showMessageDialog(null, "Passwords do not match");
                }
                
            } else {
                JOptionPane.showMessageDialog(null, "All fields are required");
            }
        } 
    }
    
    public void listEmployees() {
        
        if (rol.equals("Administrator")) {
            List<Employees> list = employee_dao.list(view.jText_search_employee.getText());
            model = (DefaultTableModel) view.jTable_employees.getModel();
            Object[] row = new Object[7];
            
            for (int i = 0; i < list.size(); i++) {
                row[0] = list.get(i).getId();
                row[1] = list.get(i).getFull_name();
                row[2] = list.get(i).getUsername();
                row[3] = list.get(i).getAddress();
                row[4] = list.get(i).getTelephone();
                row[5] = list.get(i).getEmail();
                row[6] = list.get(i).getRol();
                model.addRow(row);
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        
        if (e.getSource() == view.jTable_employees) {
            int row = view.jTable_employees.rowAtPoint(e.getPoint());
            view.jText_employee_id.setText(view.jTable_employees.getValueAt(row, 0).toString());
            view.jText_employee_fullname.setText(view.jTable_employees.getValueAt(row, 1).toString());
            view.jText_employee_username.setText(view.jTable_employees.getValueAt(row, 2).toString());
            view.jText_employee_address.setText(view.jTable_employees.getValueAt(row, 3).toString());
            view.jText_employee_phone.setText(view.jTable_employees.getValueAt(row, 4).toString());
            view.jText_employee_email.setText(view.jTable_employees.getValueAt(row, 5).toString());
            view.jCombo_role.setSelectedItem(view.jTable_employees.getValueAt(row, 6).toString());
            
            view.jPassword_employee.setEnabled(false);
            view.jButton_employee_register.setEnabled(false);
            
        } else if (e.getSource() == view.jLabel_employees) {
            
            if (rol.equals("Administrator")) {
                view.jTabbed.setSelectedIndex(2);
                cleanTable();
                cleanFields();
                listEmployees();
            } else {
                view.jTabbed.setEnabledAt(2, false);
                view.jLabel_employees.setEnabled(false);
                JOptionPane.showMessageDialog(null, "You do not have administrator privileges");
            }
            
        } 
        
    }

    @Override
    public void mousePressed(MouseEvent e) {
        
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        
    }

    @Override
    public void mouseExited(MouseEvent e) {
        
    }

    @Override
    public void keyTyped(KeyEvent e) {
        
    }

    @Override
    public void keyPressed(KeyEvent e) {
        
    }

    @Override
    public void keyReleased(KeyEvent e) {
        
        if (e.getSource() == view.jText_search_employee) {
            cleanTable();
            listEmployees();
        }
    }
    
    public void cleanTable() {
        
        for (int i = 0; i < model.getRowCount(); i++) {
            model.removeRow(i);
            i = i - 1; 
        }
    }
    
    public void cleanFields() {
        
        view.jText_employee_id.setText("");
        view.jText_employee_fullname.setText("");
        view.jText_employee_username.setText("");
        view.jText_employee_address.setText("");
        view.jText_employee_phone.setText("");
        view.jText_employee_email.setText("");
        view.jPassword_employee.setText("");
        view.jCombo_role.setSelectedIndex(0);
        
        view.jPassword_profile.setText("");
        view.jPassword_new_profile.setText("");
        view.jPassword_confirm_profile.setText("");
    }
    
}
