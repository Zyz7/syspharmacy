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
import models.Customers;
import models.CustomersDao;
import views.SystemView;

/**
 *
 * @author zyz7
 */
public class CustomersController implements ActionListener, MouseListener, KeyListener {
    
    private Customers customer;
    private CustomersDao customer_dao;
    private SystemView view;
    
    DefaultTableModel model = new DefaultTableModel();

    public CustomersController(Customers customer, CustomersDao customer_dao, SystemView view) {
        this.customer = customer;
        this.customer_dao = customer_dao;
        this.view = view;
        
        this.view.jButton_customer_register.addActionListener(this);
        this.view.jButton_customer_update.addActionListener(this);
        this.view.jButton_customer_delete.addActionListener(this);
        this.view.jButton_customer_clear.addActionListener(this);
        
        this.view.jTable_customers.addMouseListener(this);
        this.view.jLabel_customers.addMouseListener(this);
        
        this.view.jText_search_customer.addKeyListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        
        if (e.getSource() == view.jButton_customer_register) {
            
            if (view.jText_customer_fullname.getText().equals("") || view.jText_customer_address.getText().equals("") 
                    || view.jText_customer_phone.getText().equals("")
                    || view.jText_customer_email.getText().equals("")) {
                
                JOptionPane.showMessageDialog(null, "No field must be empty");
            } else {
                //customer.setId(Integer.parseInt(view.jText_customer_id.getText().trim()));
                customer.setFull_name(view.jText_customer_fullname.getText().trim());
                customer.setAddress(view.jText_customer_address.getText().trim());
                customer.setTelephone(view.jText_customer_phone.getText().trim());
                customer.setEmail(view.jText_customer_email.getText().trim());
                
                if (customer_dao.register(customer)) {
                    cleanFields();
                    cleanTable();
                    listCustomers();
                    JOptionPane.showMessageDialog(null, "Successfully registered customer");
                } else {
                    JOptionPane.showMessageDialog(null, "Error registering the customer");
                }
            }
            
        } else if (e.getSource() == view.jButton_customer_update) {
            
            if (view.jText_customer_id.equals("")) {
                JOptionPane.showMessageDialog(null, "Select a customer to continue");
            } else {
                
                if (view.jText_customer_id.getText().equals("") || view.jText_customer_fullname.getText().equals("")
                        || view.jText_customer_address.getText().equals("") || view.jText_customer_phone.getText().equals("")
                        || view.jText_customer_email.getText().equals("")) {
                    
                    JOptionPane.showMessageDialog(null, "All fields are required");
                } else {
                    customer.setId(Integer.parseInt(view.jText_customer_id.getText().trim()));
                    customer.setFull_name(view.jText_customer_fullname.getText().trim());
                    customer.setAddress(view.jText_customer_address.getText().trim());
                    customer.setTelephone(view.jText_customer_phone.getText().trim());
                    customer.setEmail(view.jText_customer_email.getText().trim());
                    
                    if (customer_dao.update(customer)) {
                        cleanFields();
                        cleanTable();
                        listCustomers();
                        view.jButton_customer_register.setEnabled(true);
                        JOptionPane.showMessageDialog(null, "Successfully modofied customer");
                    } else {
                        JOptionPane.showMessageDialog(null, "Error modifying the customer");
                    }
                }
            }
            
        } else if (e.getSource() == view.jButton_customer_delete) {
            
            int row = view.jTable_customers.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(null, "Select a customer to continue");
            } else {
                int id = Integer.parseInt(view.jTable_customers.getValueAt(row, 0).toString());
                int question = JOptionPane.showConfirmDialog(null, "¿ Are you sure about eliminating the customer ?");
                
                if (question == 0 && customer_dao.delete(id) != false) {
                    cleanTable();
                    cleanFields();
                    listCustomers();
                    view.jButton_customer_register.setEnabled(true);
                    JOptionPane.showMessageDialog(null, "Successfully eliminated customer");
                }
            }
            
        } else if (e.getSource() == view.jButton_customer_clear) {
            cleanFields();
            cleanTable();
            listCustomers();
            view.jButton_customer_register.setEnabled(true);
        }
    }
    
    public void listCustomers() {
        
        List<Customers> list = customer_dao.list(view.jText_search_customer.getText());
        model = (DefaultTableModel) view.jTable_customers.getModel();
        Object[] row = new Object[5];
        
        for (int i = 0; i <list.size(); i++) {
            row[0] = list.get(i).getId();
            row[1] = list.get(i).getFull_name();
            row[2] = list.get(i).getAddress();
            row[3] = list.get(i).getTelephone();
            row[4] = list.get(i).getEmail();
            model.addRow(row);
        }
        
        view.jTable_customers.setModel(model);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        
        if (e.getSource() == view.jTable_customers) {
            int row = view.jTable_customers.rowAtPoint(e.getPoint());
            view.jText_customer_id.setText(view.jTable_customers.getValueAt(row, 0).toString());
            view.jText_customer_fullname.setText(view.jTable_customers.getValueAt(row, 1).toString());
            view.jText_customer_address.setText(view.jTable_customers.getValueAt(row, 2).toString());
            view.jText_customer_phone.setText(view.jTable_customers.getValueAt(row, 3).toString());
            view.jText_customer_email.setText(view.jTable_customers.getValueAt(row, 4).toString());
            
            view.jButton_customer_register.setEnabled(false);
            
        } else if (e.getSource() == view.jLabel_customers) {
            view.jTabbed.setSelectedIndex(1);
            cleanTable();
            cleanFields();
            listCustomers();
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
        
        if (e.getSource() == view.jText_search_customer) {
            cleanTable();
            listCustomers();
        }
    }
    
    public void cleanTable() {
        
        for (int i = 0; i < model.getRowCount(); i++) {
            model.removeRow(i);
            i = i - 1; 
        }
    }
    
    public void cleanFields() {
        
        view.jText_customer_id.setText("");
        view.jText_customer_fullname.setText("");
        view.jText_customer_address.setText("");
        view.jText_customer_phone.setText("");
        view.jText_customer_email.setText("");
    }
    
}
