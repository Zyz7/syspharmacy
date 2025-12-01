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
import static models.EmployeesDao.id_user;
import static models.EmployeesDao.role_user;
import models.Suppliers;
import models.SuppliersDao;
import views.SystemView;

/**
 *
 * @author zyz7
 */
public class SuppliersController implements ActionListener, MouseListener, KeyListener {
    
    private Suppliers supplier;
    private SuppliersDao supplier_dao;
    private SystemView view;
    
    String rol = role_user;
    DefaultTableModel model = new DefaultTableModel();

    public SuppliersController(Suppliers supplier, SuppliersDao supplier_dao, SystemView view) {
        this.supplier = supplier;
        this.supplier_dao = supplier_dao;
        this.view = view;
        
        this.view.jButton_supplier_register.addActionListener(this);
        this.view.jButton_supplier_update.addActionListener(this);
        this.view.jButton_supplier_delete.addActionListener(this);
        this.view.jButton_supplier_clear.addActionListener(this);
        
        this.view.jTable_suppliers.addMouseListener(this);
        this.view.jLabel_suppliers.addMouseListener(this);
        
        this.view.jText_search_supplier.addKeyListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        
        if (e.getSource() == view.jButton_supplier_register) {
            
            if (view.jText_supplier_name.getText().equals("") || view.jText_supplier_phone.getText().equals("")
                    || view.jText_supplier_email.getText().equals("") || view.jText_supplier_address.getText().equals("")
                    || view.jText_supplier_description.getText().equals("") || view.jCombo_cities.getSelectedItem().toString().equals("Select an option")) {
                
                JOptionPane.showMessageDialog(null, "No field must be empty");
            } else {
                
                supplier.setName(view.jText_supplier_name.getText().trim());
                supplier.setTelephone(view.jText_supplier_phone.getText().trim());
                supplier.setEmail(view.jText_supplier_email.getText().trim());
                supplier.setAddress(view.jText_supplier_address.getText().trim());
                supplier.setDescription(view.jText_supplier_description.getText().trim());
                supplier.setCity(view.jCombo_cities.getSelectedItem().toString());
                
                if (supplier_dao.register(supplier)) {
                    cleanTable();
                    cleanFields();
                    listSuppliers();
                    JOptionPane.showMessageDialog(null, "Successfully registered supplier");
                } else {
                    JOptionPane.showMessageDialog(null, "Error registering the supplier");
                }
            }
            
        } else if (e.getSource() == view.jButton_supplier_update) {
            
            if (view.jText_supplier_id.equals("")) {
                
                JOptionPane.showMessageDialog(null, "Select a supplier to continue");
            } else {
                
                if (view.jText_supplier_name.getText().equals("") || view.jText_supplier_phone.getText().equals("")
                    || view.jText_supplier_email.getText().equals("") || view.jText_supplier_address.getText().equals("")
                    || view.jText_supplier_description.getText().equals("") || view.jCombo_cities.getSelectedItem().toString().equals("")) {
                    
                    JOptionPane.showMessageDialog(null, "All fields are required");
                } else {
                    
                    supplier.setId(Integer.parseInt(view.jText_supplier_id.getText().trim()));
                    supplier.setName(view.jText_supplier_name.getText().trim());
                    supplier.setTelephone(view.jText_supplier_phone.getText().trim());
                    supplier.setEmail(view.jText_supplier_email.getText().trim());
                    supplier.setAddress(view.jText_supplier_address.getText().trim());
                    supplier.setDescription(view.jText_supplier_description.getText().trim());
                    supplier.setCity(view.jCombo_cities.getSelectedItem().toString());
                    
                    if (supplier_dao.update(supplier)) {
                        cleanTable();
                        cleanFields();
                        listSuppliers();
                        view.jButton_supplier_register.setEnabled(true);
                        JOptionPane.showMessageDialog(null, "Successfully modified supplier");
                    } else {
                        JOptionPane.showMessageDialog(null, "Error modifying the supplier");
                    }
                }
            }
            
        } else if (e.getSource() == view.jButton_supplier_delete) {
            
            int row = view.jTable_suppliers.getSelectedRow();
            if (row == -1) {
                
                JOptionPane.showMessageDialog(null, "Select a supplier to continue");
            } else {
                
                int id = Integer.parseInt(view.jTable_suppliers.getValueAt(row, 0).toString());
                int question = JOptionPane.showConfirmDialog(null, "¿ Are you sure about eliminating the supplier ?");
                
                if (question == 0 && supplier_dao.delete(id) != false) {
                    cleanTable();
                    cleanFields();
                    listSuppliers();
                    view.jButton_supplier_register.setEnabled(true);
                    JOptionPane.showMessageDialog(null, "Successfully eliminated supplier");
                }
            }
            
        } else if (e.getSource() == view.jButton_supplier_clear) {
            cleanTable();
            cleanFields();
            listSuppliers();
            view.jButton_supplier_register.setEnabled(true);
            
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        
        if (e.getSource() == view.jTable_suppliers) {
            int row = view.jTable_suppliers.rowAtPoint(e.getPoint());
            view.jText_supplier_id.setText(view.jTable_suppliers.getValueAt(row, 0).toString());
            view.jText_supplier_name.setText(view.jTable_suppliers.getValueAt(row, 1).toString());
            view.jText_supplier_description.setText(view.jTable_suppliers.getValueAt(row, 2).toString());
            view.jText_supplier_address.setText(view.jTable_suppliers.getValueAt(row, 3).toString());
            view.jText_supplier_phone.setText(view.jTable_suppliers.getValueAt(row, 4).toString());
            view.jText_supplier_email.setText(view.jTable_suppliers.getValueAt(row, 5).toString());
            view.jCombo_cities.setSelectedItem(view.jTable_suppliers.getValueAt(row, 6).toString());
            
            view.jButton_supplier_register.setEnabled(false);
            
        } else if (e.getSource() == view.jLabel_suppliers) {
            
            if (rol.equals("Administrator")) {
                view.jTabbed.setSelectedIndex(3);
                cleanTable();
                cleanFields();
                listSuppliers();
            } else {
                view.jTabbed.setEnabledAt(3, false);
                view.jLabel_suppliers.setEnabled(false);
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
        
        if (e.getSource() == view.jText_search_supplier) {
            cleanTable();
            listSuppliers();
        }
    }
    
    public void listSuppliers() {
        
        if (rol.equals("Administrator")) {
            List<Suppliers> list = supplier_dao.list(view.jText_search_supplier.getText());
            model = (DefaultTableModel) view.jTable_suppliers.getModel();
            Object[] row = new Object[7];
            
            for (int i = 0; i < list.size(); i++) {
                row[0] = list.get(i).getId();
                row[1] = list.get(i).getName();
                row[2] = list.get(i).getDescription();
                row[3] = list.get(i).getAddress();
                row[4] = list.get(i).getTelephone();
                row[5] = list.get(i).getEmail();
                row[6] = list.get(i).getCity();
                model.addRow(row);
            }
            view.jTable_suppliers.setModel(model);
        }
    }
    
    public void cleanTable() {
        
        for (int i = 0; i < model.getRowCount(); i++) {
            model.removeRow(i);
            i = i - 1; 
        }
    }
    
    public void cleanFields() {
        
        view.jText_supplier_id.setText("");
        view.jText_supplier_name.setText("");
        view.jText_supplier_description.setText("");
        view.jText_supplier_address.setText("");
        view.jText_supplier_phone.setText("");
        view.jText_supplier_email.setText("");
        view.jCombo_cities.setSelectedIndex(0);
    }
    
    
}
