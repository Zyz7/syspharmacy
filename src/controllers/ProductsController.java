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
import models.DynamicCombo;
import static models.EmployeesDao.role_user;
import models.Products;
import models.ProductsDao;
import views.SystemView;

/**
 *
 * @author zyz7
 */
public class ProductsController implements ActionListener, MouseListener, KeyListener {
    
    private Products product;
    private ProductsDao product_dao;
    private SystemView view;
    
    String rol = role_user;
    DefaultTableModel model = new DefaultTableModel();

    public ProductsController(Products product, ProductsDao product_dao, SystemView view) {
        this.product = product;
        this.product_dao = product_dao;
        this.view = view;
        
        this.view.jButton_product_register.addActionListener(this);
        this.view.jButton_product_update.addActionListener(this);
        this.view.jButton_product_delete.addActionListener(this);
        this.view.jButton_product_clear.addActionListener(this);
        
        this.view.jTable_products.addMouseListener(this);
        this.view.jLabel_products.addMouseListener(this);
        
        this.view.jText_search_product.addKeyListener(this);
        
        view.jText_product_quantity.setText("0");
        view.jText_product_price.setText("0");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        
        if (e.getSource() == view.jButton_product_register) {
            
            if (view.jText_product_code.getText().equals("") || view.jText_product_name.getText().equals("") || 
                    view.jText_product_description.getText().equals("") || view.jCombo_categories.getSelectedItem().toString().equals("")) {
                
                JOptionPane.showMessageDialog(null, "No field must be empty");
            } else {
                
                product.setCode(view.jText_product_code.getText());
                product.setName(view.jText_product_name.getText().trim());
                product.setUnit_price(Double.parseDouble(view.jText_product_price.getText()));
                product.setQuantity(Integer.parseInt(view.jText_product_quantity.getText()));
                product.setDescription(view.jText_product_description.getText().trim());
                DynamicCombo category_id = (DynamicCombo) view.jCombo_categories.getSelectedItem();
                product.setCategory_id(category_id.getId());
                
                if (product_dao.register(product)) {
                    cleanTable();
                    cleanFields();
                    listProducts();
                    JOptionPane.showMessageDialog(null, "Successfully registered product");
                } else {
                    JOptionPane.showMessageDialog(null, "Error registering the product");
                }
            }
            
        } else if (e.getSource() == view.jButton_product_update) {
            
            if (view.jText_product_id.equals("")) {
                JOptionPane.showMessageDialog(null, "Select a product to continue");
            } else {
                
                if (view.jText_product_code.getText().equals("") || view.jText_product_name.getText().equals("") ||
                    view.jText_product_description.getText().equals("") || view.jCombo_categories.getSelectedItem().toString().equals("")) {
                    
                    JOptionPane.showMessageDialog(null, "All fields are required");
                } else {
                    product.setId(Integer.parseInt(view.jText_product_id.getText().toString()));
                    product.setCode(view.jText_product_code.getText());
                    product.setName(view.jText_product_name.getText().trim());
                    product.setUnit_price(Double.parseDouble(view.jText_product_price.getText()));
                    product.setQuantity(Integer.parseInt(view.jText_product_quantity.getText()));
                    product.setDescription(view.jText_product_description.getText().trim());
                    DynamicCombo category_id = (DynamicCombo) view.jCombo_categories.getSelectedItem();
                    product.setCategory_id(category_id.getId());
                    
                    if (product_dao.update(product)) {
                        cleanTable();
                        cleanFields();
                        listProducts();
                        view.jButton_product_register.setEnabled(true);
                        JOptionPane.showMessageDialog(null, "Successfully modified product");
                    } else {
                        JOptionPane.showMessageDialog(null, "Error modifying the product");
                    }
                }
            }
            
        } else if (e.getSource() == view.jButton_product_delete) {
            
            int row = view.jTable_products.getSelectedRow();
            if (row == -1) {
                
                JOptionPane.showMessageDialog(null, "Select a product to continue");
            } else {
                
                int id = Integer.parseInt(view.jTable_products.getValueAt(row, 0).toString());
                int question = JOptionPane.showConfirmDialog(null, "¿ Are you sure about eliminating the product ?");
                
                if (question == 0 && product_dao.delete(id) != false) {
                    cleanTable();
                    cleanFields();
                    listProducts();
                    view.jButton_product_register.setEnabled(true);
                    JOptionPane.showMessageDialog(null, "Successfully eliminated product");
                }
            }
            
        } else if (e.getSource() == view.jButton_product_clear) {
            cleanTable();
            cleanFields();
            listProducts();
            view.jButton_product_register.setEnabled(true);
            
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        
        if (e.getSource() == view.jTable_products) {
            int row = view.jTable_products.rowAtPoint(e.getPoint());
            view.jText_product_id.setText(view.jTable_products.getValueAt(row, 0).toString());
            view.jText_product_code.setText(view.jTable_products.getValueAt(row, 1).toString());
            view.jText_product_name.setText(view.jTable_products.getValueAt(row, 2).toString());
            view.jText_product_description.setText(view.jTable_products.getValueAt(row, 3).toString());
            view.jText_product_price.setText(view.jTable_products.getValueAt(row, 4).toString());
            view.jText_product_quantity.setText(view.jTable_products.getValueAt(row, 5).toString());
            
            product = product_dao.searchCategory(view.jTable_products.getValueAt(row, 6).toString());
            view.jCombo_categories.setSelectedItem(new DynamicCombo(product.getId(), product.getCategory_name()));
            
            view.jButton_product_register.setEnabled(false);
            
        } else if (e.getSource() == view.jLabel_products) {
            
            view.jTabbed.setSelectedIndex(7);
            cleanTable();
            cleanFields();
            listProducts();
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
        
        if (e.getSource() == view.jText_search_product) {
            cleanTable();
            listProducts();
        }
    }
    
    public void cleanTable() {
        
        for (int i = 0; i < model.getRowCount(); i++) {
            model.removeRow(i);
            i = i - 1; 
        }
    }
    
    public void cleanFields() {
        
        view.jText_product_id.setText("");
        view.jText_product_name.setText("");
        view.jText_product_price.setText("0.0");
        view.jText_product_quantity.setText("0");
        view.jText_product_description.setText("");
        view.jText_product_code.setText("");
        view.jCombo_categories.setSelectedIndex(0);
    }
    
    public void listProducts() {
        
        List<Products> list = product_dao.list(view.jText_search_product.getText());
        model = (DefaultTableModel) view.jTable_products.getModel();
        Object[] row = new Object[7];

        for (int i = 0; i < list.size(); i++) {
            row[0] = list.get(i).getId();
            row[1] = list.get(i).getCode();
            row[2] = list.get(i).getName();
            row[3] = list.get(i).getDescription();
            row[4] = list.get(i).getUnit_price();
            row[5] = list.get(i).getQuantity();
            row[6] = list.get(i).getCategory_name();
            model.addRow(row);
        }
        view.jTable_products.setModel(model);
    }
    
}
