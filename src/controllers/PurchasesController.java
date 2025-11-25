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
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import models.DynamicCombo;
import static models.EmployeesDao.id_user;
import static models.EmployeesDao.role_user;
import models.Products;
import models.ProductsDao;
import models.Purchases;
import models.PurchasesDao;
import models.Suppliers;
import models.SuppliersDao;
import views.PrintView;
import views.SystemView;

/**
 *
 * @author zyz7
 */
public class PurchasesController implements ActionListener, MouseListener, KeyListener {
    
    private Purchases purchase;
    private PurchasesDao purchase_dao;
    private SystemView view;
    
    Products product = new Products();
    ProductsDao product_dao = new ProductsDao();
    SuppliersDao supplier_dao = new SuppliersDao();
    String role = role_user;
    
    private int getIdSupplier = 0;
    private int item = 0;
    DefaultTableModel model = new DefaultTableModel();
    DefaultTableModel tempModel = new DefaultTableModel();

    public PurchasesController(Purchases purchase, PurchasesDao purchase_dao, SystemView view) {
        this.purchase = purchase;
        this.purchase_dao = purchase_dao;
        this.view = view;
        
        this.view.jButton_purchase_add.addActionListener(this);
        this.view.jButton_purchase_buy.addActionListener(this);
        this.view.jButton_purchase_remove.addActionListener(this);
        this.view.jButton_purchase_clear.addActionListener(this);
        this.view.jButton_purchase_view.addActionListener(this);
        
        this.view.jLabel_purchases.addMouseListener(this);
        this.view.jLabel_reports.addMouseListener(this);
        
        this.view.jText_purchase_code.addKeyListener(this);
        this.view.jText_purchase_price.addKeyListener(this);
        
        listSupplierName();
        
        if (!role.equals("Administrator")) {
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
     
        if (e.getSource() == view.jButton_purchase_add) {
            
            if (view.jText_purchase_code.getText().equals("") || view.jText_purchase_id.getText().equals("") || 
                    view.jText_purchase_name.getText().equals("") || view.jText_purchase_amount.getText().equals("") ||
                    view.jText_purchase_price.getText().equals("") || view.jText_purchase_subtotal.getText().equals("") || 
                    view.jCombo_purchase_supplier.getSelectedItem().toString().equals("Select an option")) {
                
                JOptionPane.showMessageDialog(null, "No field must be empty");
            } else {
                
                int amount = Integer.parseInt(view.jText_purchase_amount.getText());
                String name = view.jText_purchase_name.getText();
                double price = Double.parseDouble(view.jText_purchase_price.getText());
                double subtotal = Double.parseDouble(view.jText_purchase_subtotal.getText());
                String code = view.jText_purchase_code.getText();
                String supplier = view.jCombo_purchase_supplier.getSelectedItem().toString();
                int id_product = Integer.parseInt(view.jText_purchase_id.getText().toString());
                
                if (amount > 0) {
                    
                    tempModel = (DefaultTableModel) view.jTable_purchases.getModel();
                    for (int i = 0; i < view.jTable_purchases.getRowCount(); i++) {
                        
                        if (view.jTable_purchases.getValueAt(i, 1).equals(view.jText_purchase_code.getText())) {
                            JOptionPane.showMessageDialog(null, "The product is already in the table");
                            return;
                        }
                    }
                    
                    ArrayList listProducts = new ArrayList();
                    item = item + 1;
                    listProducts.add(item);
                    listProducts.add(code);
                    listProducts.add(name);
                    listProducts.add(amount);
                    listProducts.add(price);
                    listProducts.add(subtotal);
                    listProducts.add(id_product);
                    
                    Object[] row = new Object[7];
                    row[0] = listProducts.get(0);
                    row[1] = listProducts.get(1);
                    row[2] = listProducts.get(2);
                    row[3] = listProducts.get(3);
                    row[4] = listProducts.get(4);
                    row[5] = listProducts.get(5);
                    row[6] = listProducts.get(6);
                    tempModel.addRow(row);
                    view.jTable_purchases.setModel(tempModel);
                    cleanFields();
                    calculateTotal();
                    view.jCombo_purchase_supplier.setEnabled(false);
                    view.jText_purchase_code.requestFocus();
                } else {
                    JOptionPane.showMessageDialog(null, "The quantity must be greater than zero");
                }
            }
            
        } else if (e.getSource() == view.jButton_purchase_buy) {
            
            if (view.jTable_purchases.getRowCount() == 0) {
                
                JOptionPane.showMessageDialog(null, "You must add rows to the table");
            } else {
                if (role.equals("Administrator")) {
                    insertPurchase();
                } else {
                    JOptionPane.showMessageDialog(null, "You do not have administrator privileges");
                }
            }
            
        } else if (e.getSource() == view.jButton_purchase_remove) {
            
            item = item - 1;
            try {
                model = (DefaultTableModel) view.jTable_purchases.getModel();
                model.removeRow(view.jTable_purchases.getSelectedRow());
                calculateTotal();
                view.jText_purchase_code.requestFocus();
            } catch(ArrayIndexOutOfBoundsException z) {
                JOptionPane.showMessageDialog(null, "There is no row to remove");
            }
            
            
        } else if (e.getSource() == view.jButton_purchase_clear) {
            
            try {
                cleanFields();
                cleanTable();
                calculateTotal();
                view.jCombo_purchase_supplier.setSelectedIndex(0);
                view.jCombo_purchase_supplier.setEnabled(true);
            } catch(NullPointerException x){
                
            }
            
        } else if (e.getSource() == view.jButton_purchase_view) {
            
            PrintView print = new PrintView(1);
            print.setVisible(true);
        }
    }
    
    private void insertPurchase() {
        
        DynamicCombo supplier_id = (DynamicCombo) view.jCombo_purchase_supplier.getSelectedItem();
        double total = Double.parseDouble(view.jText_purchase_total.getText());
        int user_id = id_user;
        
        if (purchase_dao.register(total, supplier_id.getId(), user_id)) {
            
            int purchase_id = purchase_dao.getID();
            for (int i = 0; i < view.jTable_purchases.getRowCount(); i++) {
                
                double price = Double.parseDouble(view.jTable_purchases.getValueAt(i, 4).toString());
                int amount = Integer.parseInt(view.jTable_purchases.getValueAt(i, 3).toString());
                double subtotal = Double.parseDouble(view.jTable_purchases.getValueAt(i, 5).toString());
                int id_product = Integer.parseInt(view.jTable_purchases.getValueAt(i, 6).toString());
                
                purchase_dao.registerDetail(price, amount, subtotal, purchase_id, id_product);
                product = product_dao.search(id_product);
                int quantityTotal = product.getQuantity() + amount;
                product_dao.updateProduct(price, quantityTotal, id_product);
            }
            
            cleanTable();
            cleanFields();
            view.jCombo_purchase_supplier.setSelectedIndex(0);
            view.jCombo_purchase_supplier.setEnabled(true);
            JOptionPane.showMessageDialog(null, "Successfully generated purchase");
            
            PrintView print = new PrintView(purchase_id);
            print.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(null, "Error generating the purchase");
        }
        
    }
    
    public void listPurchases() {
        
        List<Purchases> list = purchase_dao.list();
        model = (DefaultTableModel) view.jTable_reports_purchases.getModel();
        Object[] row = new Object[4];
        
        for (int i = 0; i < list.size(); i++) {
            row[0] = list.get(i).getId();
            row[1] = list.get(i).getSupplier_name_product();
            row[2] = list.get(i).getTotal();
            row[3] = list.get(i).getCreated();
            model.addRow(row);
        }
        view.jTable_reports_purchases.setModel(model);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        
        if (e.getSource() == view.jLabel_purchases) {
            
            if (role.equals("Administrator")) {
                view.jTabbed.setSelectedIndex(0);
                cleanTable();
                cleanFields();
                calculateTotal();
            } else {
                view.jTabbed.setEnabledAt(0, false);
                view.jLabel_purchases.setEnabled(false);
                JOptionPane.showMessageDialog(null, "You do not have administrator privileges");
            }
        } 
        
        if (e.getSource() == view.jLabel_reports) {
            
            if (role.equals("Administrator")) {
                view.jTabbed.setSelectedIndex(5);
                cleanTableP();
                listPurchases();
            } else {
                view.jTabbed.setEnabledAt(5, false);
                view.jLabel_reports.setEnabled(false);
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
        
        if (e.getSource() == view.jText_purchase_code) {
            if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                if (view.jText_purchase_code.getText().equals("")) {
                    
                    JOptionPane.showMessageDialog(null, "Enter the product code");
                } else {
                    String code = view.jText_purchase_code.getText();
                    product = product_dao.searchByCode(code);
                    view.jText_purchase_name.setText(product.getName());
                    view.jText_purchase_id.setText("" + product.getId());
                    view.jText_purchase_amount.requestFocus();
                }
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
       
        if (e.getSource() == view.jText_purchase_price) {
            int quantity;
            //double price = Double.parseDouble(view.jText_purchase_price.getText());
            double price = 0.0;
            
            if (view.jText_purchase_amount.getText().equals("")) {
                quantity = 1;
                view.jText_purchase_price.setText("" + price);
            } else {
                quantity = Integer.parseInt(view.jText_purchase_amount.getText());
                price = Double.parseDouble(view.jText_purchase_price.getText());
                view.jText_purchase_subtotal.setText("" + quantity * price);
            }
        }
    }
    
    public void listSupplierName(){
        
        List<Suppliers> list = supplier_dao.list(view.jText_search_supplier.getText());
        for (int i = 0; i < list.size(); i++) {
            int id = list.get(i).getId();
            String name = list.get(i).getName();
            view.jCombo_purchase_supplier.addItem(new DynamicCombo(id, name));
        }
    }
    
    public void cleanTable() {
        
        try {
            for (int i = 0; i < tempModel.getRowCount(); i++) {
                tempModel.removeRow(i);
                i = i - 1; 
            }
        } catch(NullPointerException e) {
            
        }
        
    }
    
    public void cleanTableP() {
        
        for (int i = 0; i < model.getRowCount(); i++) {
            model.removeRow(i);
            i = i - 1; 
        }
    }
    
    public void cleanFields() {
        
        view.jText_purchase_code.setText("");
        view.jText_purchase_name.setText("");
        view.jText_purchase_amount.setText("");
        view.jText_purchase_price.setText("");
        view.jText_purchase_subtotal.setText("");
        view.jText_purchase_id.setText("");
        view.jText_purchase_total.setText("");
    }
    
    private void calculateTotal() {
        double total = 0.0;
        int rows = view.jTable_purchases.getRowCount();
        
        for (int i = 0; i < rows; i++) {
            total = total + Double.parseDouble(String.valueOf(view.jTable_purchases.getValueAt(i, 5)));
        }
        view.jText_purchase_total.setText("" + String.format("%.2f", total));
    }
    
}
