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
import models.Customers;
import models.CustomersDao;
import models.DynamicCombo;
import static models.EmployeesDao.id_user;
import static models.EmployeesDao.role_user;
import models.Products;
import models.ProductsDao;
import models.Purchases;
import models.Sales;
import models.SalesDao;
import views.PrintView;
import views.SystemView;

/**
 *
 * @author zyz7
 */
public class SalesController implements ActionListener, MouseListener, KeyListener {
    
    private Sales sale;
    private SalesDao sale_dao;
    private SystemView view;
    
    Products product = new Products();
    ProductsDao product_dao = new ProductsDao();
    Customers customer = new Customers();
    CustomersDao customer_dao = new CustomersDao();
    
    private int item = 0;
    String role = role_user;
    DefaultTableModel model = new DefaultTableModel();
    DefaultTableModel tempModel = new DefaultTableModel();

    public SalesController(Sales sale, SalesDao sale_dao, SystemView view) {
        this.sale = sale;
        this.sale_dao = sale_dao;
        this.view = view;
        
        this.view.jButton_sales_add.addActionListener(this);
        this.view.jButton_sales_sell.addActionListener(this);
        this.view.jButton_sales_remove.addActionListener(this);
        this.view.jButton_sales_clear.addActionListener(this);
        this.view.jButton_sales_view.addActionListener(this);
        
        this.view.jLabel_sales.addMouseListener(this);
        this.view.jLabel_reports.addMouseListener(this);
        
        this.view.jText_sales_code.addKeyListener(this);
        this.view.jText_sales_idcustomer.addKeyListener(this);
        this.view.jText_sales_amount.addKeyListener(this);
        
        view.jText_sales_amount.requestFocus();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        
        if (e.getSource() == view.jButton_sales_add) {
            
            if (view.jText_sales_id.getText().equals("") || view.jText_sales_code.getText().equals("") || 
                    view.jText_sales_name.getText().equals("") || view.jText_sales_amount.getText().equals("") ||
                    view.jText_sales_price.getText().equals("") || view.jText_sales_stock.getText().equals("") || 
                    view.jText_sales_idcustomer.getText().equals("") || view.jText_sales_namecustomer.getText().equals("") ||
                    view.jText_sales_subtotal.getText().equals("")) {
                
                JOptionPane.showMessageDialog(null, "No field must be empty");
            } else {
                
                int id_product = Integer.parseInt(view.jText_sales_id.getText().toString());
                String code = view.jText_sales_code.getText();
                String name = view.jText_sales_name.getText();
                int amount = Integer.parseInt(view.jText_sales_amount.getText());
                double price = Double.parseDouble(view.jText_sales_price.getText());
                double subtotal = Double.parseDouble(view.jText_sales_subtotal.getText());
                int stock = Integer.parseInt(view.jText_sales_stock.getText());
                
                if (amount <= 0) {
                    
                    JOptionPane.showMessageDialog(null, "The quantity must be greater than zero");
                } else if (amount > stock) {
                    
                    JOptionPane.showMessageDialog(null, "The quantity must be less that the stock");
                } else {
                    
                    tempModel = (DefaultTableModel) view.jTable_sales.getModel();
                    for (int i = 0; i < view.jTable_sales.getRowCount(); i++) {
                        
                        if (view.jTable_sales.getValueAt(i, 1).equals(view.jText_sales_code.getText())) {
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
                    view.jTable_sales.setModel(tempModel);
                    cleanFields();
                    calculateTotal();
                    view.jText_sales_idcustomer.setEditable(false);
                    view.jText_sales_code.requestFocus();
                }
                
            }
            
        } else if (e.getSource() == view.jButton_sales_remove) {
            
            item = item - 1;
            try {
                model = (DefaultTableModel) view.jTable_sales.getModel();
                model.removeRow(view.jTable_sales.getSelectedRow());
                calculateTotal();
                view.jText_sales_code.requestFocus();
            } catch(ArrayIndexOutOfBoundsException z) {
                JOptionPane.showMessageDialog(null, "There is no row to remove");
            }
            
            
        } else if (e.getSource() == view.jButton_sales_sell) {
            
            if (view.jTable_sales.getRowCount() == 0) {
                
                JOptionPane.showMessageDialog(null, "You must add rows to the table");
            } else {
                insertSale();
                view.jText_sales_idcustomer.setText("");
                view.jText_sales_namecustomer.setText("");
                view.jText_sales_idcustomer.setEditable(true);
            }
            
        } else if (e.getSource() == view.jButton_sales_clear) {
            
            try {
                cleanFields();
                cleanTable();
                view.jText_sales_idcustomer.setText("");
                view.jText_sales_namecustomer.setText("");
                view.jText_sales_idcustomer.setEditable(true);
            } catch(NullPointerException x){
                
            }
            
        } else if (e.getSource() == view.jButton_sales_view) {
            
            PrintView print = new PrintView("sale", 1);
            print.setVisible(true);
        }
        
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        
        if (e.getSource() == view.jLabel_sales) {
            
            view.jTabbed.setSelectedIndex(8);
            cleanTable();
            cleanFields();
        }
        
        if (e.getSource() == view.jLabel_reports) {
            
            view.jTabbed.setSelectedIndex(5);
            cleanTableS();
            listSales();
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
        
        if (e.getSource() == view.jText_sales_code) {
            if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                if (view.jText_sales_code.getText().equals("")) {
                    
                    JOptionPane.showMessageDialog(null, "Enter the product code");
                } else {
                    String code = view.jText_sales_code.getText();
                    product = product_dao.searchByCode(code);
                    
                    if (product.getName() != null) {
                        view.jText_sales_name.setText(product.getName());
                        view.jText_sales_id.setText("" + product.getId());
                        view.jText_sales_price.setText("" + product.getUnit_price());
                        view.jText_sales_stock.setText("" + product.getQuantity());
                    
                        view.jText_sales_amount.requestFocus();
                    } else {
                        JOptionPane.showMessageDialog(null, "There is no product with that code");
                    }
                    
                }
            }
            
        } 
        
        if (e.getSource() == view.jText_sales_idcustomer) {
            if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                if (view.jText_sales_idcustomer.getText().equals("")) {
                    
                    JOptionPane.showMessageDialog(null, "Enter the customer id");
                } else {
                    int id_client = Integer.parseInt(view.jText_sales_idcustomer.getText());
                    customer = customer_dao.search(id_client);
                    
                    if (customer.getFull_name() != null) {
                        view.jText_sales_namecustomer.setText(customer.getFull_name());
                    } else {
                        JOptionPane.showMessageDialog(null, "There is no client with that ID");
                    }
                }
            }
            
        }
        
    }

    @Override
    public void keyReleased(KeyEvent e) {
        
        if (e.getSource() == view.jText_sales_amount) {
            int quantity;
            double price = 0.0;
            
            if (view.jText_sales_amount.getText().equals("")) {
                quantity = 1;
                //view.jText_sales_price.setText("" + price);
            } else {
                quantity = Integer.parseInt(view.jText_sales_amount.getText());
                price = Double.parseDouble(view.jText_sales_price.getText());
                view.jText_sales_subtotal.setText("" + quantity * price);
            }
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
    
    public void cleanTableS() {
        
        model = (DefaultTableModel) view.jTable_reports_sales.getModel();
        for (int i = 0; i < model.getRowCount(); i++) {
            model.removeRow(i);
            i = i - 1; 
        }
    }
    
    public void cleanFields() {
        
        view.jText_sales_id.setText("");
        view.jText_sales_code.setText("");
        view.jText_sales_name.setText("");
        view.jText_sales_amount.setText("");
        view.jText_sales_price.setText("");
        view.jText_sales_stock.setText("");
        //view.jText_sales_idcustomer.setText("");
        //view.jText_sales_namecustomer.setText("");
        view.jText_sales_subtotal.setText("");
        view.jText_sales_total.setText("");
    }
    
    private void calculateTotal() {
        double total = 0.0;
        int rows = view.jTable_sales.getRowCount();
        
        for (int i = 0; i < rows; i++) {
            total = total + Double.parseDouble(String.valueOf(view.jTable_sales.getValueAt(i, 5)));
        }
        view.jText_sales_total.setText("" + String.format("%.2f", total));
    }
    
    private void insertSale() {
        
        double total = Double.parseDouble(view.jText_sales_total.getText());
        int customer_id = Integer.parseInt(view.jText_sales_idcustomer.getText());
        int user_id = id_user;
        
        if (sale_dao.register(total, customer_id, user_id)) {
            
            int sale_id = sale_dao.getID();
            for (int i = 0; i < view.jTable_sales.getRowCount(); i++) {
                
                int amount = Integer.parseInt(view.jTable_sales.getValueAt(i, 3).toString());
                double price = Double.parseDouble(view.jTable_sales.getValueAt(i, 4).toString());
                double subtotal = Double.parseDouble(view.jTable_sales.getValueAt(i, 5).toString());
                int product_id = Integer.parseInt(view.jTable_sales.getValueAt(i, 6).toString());
                
                sale_dao.registerDetail(amount, price, subtotal, product_id, sale_id);
                product = product_dao.search(product_id);
                int quantityTotal = product.getQuantity() - amount;
                product_dao.updateProduct(price, quantityTotal, product_id);
            }
            
            cleanTable();
            cleanFields();
            JOptionPane.showMessageDialog(null, "Successfully generated sell");
            
            PrintView print = new PrintView("sale", sale_id);
            print.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(null, "Error generating the sell");
        }
        
    }
    
    public void listSales() {
        
        List<Sales> list = sale_dao.list();
        model = (DefaultTableModel) view.jTable_reports_sales.getModel();
        Object[] row = new Object[5];
        
        for (int i = 0; i < list.size(); i++) {
            row[0] = list.get(i).getId();
            row[1] = list.get(i).getCustomer_name();
            row[2] = list.get(i).getEmployee_name();
            row[3] = list.get(i).getTotal();
            row[4] = list.get(i).getDate();
            model.addRow(row);
        }
        view.jTable_reports_sales.setModel(model);
    }
    
}
