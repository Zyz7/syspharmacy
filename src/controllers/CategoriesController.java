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
import models.Categories;
import models.CategoriesDao;
import models.DynamicCombo;
import static models.EmployeesDao.id_user;
import static models.EmployeesDao.role_user;
import views.SystemView;

/**
 *
 * @author zyz7
 */
public class CategoriesController implements ActionListener, MouseListener, KeyListener {
    
    private Categories categorie;
    private CategoriesDao categorie_dao;
    private SystemView view;
    
    String rol = role_user;
    DefaultTableModel model = new DefaultTableModel();

    public CategoriesController(Categories categorie, CategoriesDao categorie_dao, SystemView view) {
        this.categorie = categorie;
        this.categorie_dao = categorie_dao;
        this.view = view;
        
        this.view.jButton_categorie_register.addActionListener(this);
        this.view.jButton_categorie_update.addActionListener(this);
        this.view.jButton_categorie_delete.addActionListener(this);
        this.view.jButton_categorie_clear.addActionListener(this);
        
        this.view.jTable_categories.addMouseListener(this);
        this.view.jLabel_categories.addMouseListener(this);
        
        this.view.jText_search_categories.addKeyListener(this);
        
        listCategoryName();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        
        if (e.getSource() == view.jButton_categorie_register) {
            
            if (view.jText_categorie_name.getText().equals("")) {
                
                JOptionPane.showMessageDialog(null, "No field must be empty");
            } else {
                categorie.setName(view.jText_categorie_name.getText().trim());
                
                if (categorie_dao.register(categorie)) {
                    cleanTable();
                    cleanFields();
                    listCategories();
                    JOptionPane.showMessageDialog(null, "Successfully registered categorie");
                } else {
                    JOptionPane.showMessageDialog(null, "Error registering the categorie");
                }
            }
            
        } else if (e.getSource() == view.jButton_categorie_update) {
            
            if (view.jText_categorie_id.equals("")) {
                JOptionPane.showMessageDialog(null, "Select a categorie to continue");
            } else {
                
                if (view.jText_categorie_id.getText().equals("") || view.jText_categorie_name.getText().equals("") ) {
                    
                    JOptionPane.showMessageDialog(null, "All fields are required");
                } else {
                    categorie.setId(Integer.parseInt(view.jText_categorie_id.getText().trim()));
                    categorie.setName(view.jText_categorie_name.getText().trim());
                    
                    if (categorie_dao.update(categorie)) {
                        cleanTable();
                        cleanFields();
                        listCategories();
                        view.jButton_categorie_register.setEnabled(true);
                        JOptionPane.showMessageDialog(null, "Successfully modified categorie");
                    } else {
                        JOptionPane.showMessageDialog(null, "Error modifying the categorie");
                    }
                }
            }
            
        } else if (e.getSource() == view.jButton_categorie_delete) {
            
            int row = view.jTable_categories.getSelectedRow();
            if (row == -1) {
                
                JOptionPane.showMessageDialog(null, "Select a categorie to continue");
            } else {
                
                int id = Integer.parseInt(view.jTable_categories.getValueAt(row, 0).toString());
                int question = JOptionPane.showConfirmDialog(null, "¿ Are you sure about eliminating the categorie ?");
                
                if (question == 0 && categorie_dao.delete(id) != false) {
                    cleanTable();
                    cleanFields();
                    listCategories();
                    view.jButton_categorie_register.setEnabled(true);
                    JOptionPane.showMessageDialog(null, "Successfully eliminated categorie");
                }
            }
            
        } else if (e.getSource() == view.jButton_categorie_clear) {
            
            cleanTable();
            cleanFields();
            listCategories();
            view.jButton_categorie_register.setEnabled(true);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        
        if (e.getSource() == view.jTable_categories) {
            int row = view.jTable_categories.rowAtPoint(e.getPoint());
            view.jText_categorie_id.setText(view.jTable_categories.getValueAt(row, 0).toString());
            view.jText_categorie_name.setText(view.jTable_categories.getValueAt(row, 1).toString());
            
            view.jButton_categorie_register.setEnabled(false);
        } else if (e.getSource() == view.jLabel_categories) {
            
            view.jTabbed.setSelectedIndex(4);
            cleanTable();
            cleanFields();
            listCategories();
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
        
        if (e.getSource() == view.jText_search_categories) {
            cleanTable();
            listCategories();
        }
    }
    
    public void cleanTable() {
        
        for (int i = 0; i < model.getRowCount(); i++) {
            model.removeRow(i);
            i = i - 1; 
        }
    }
    
    public void cleanFields() {
        
        view.jText_categorie_id.setText("");
        view.jText_categorie_name.setText("");
    }
    
    public void listCategories() {
        
        List<Categories> list = categorie_dao.list(view.jText_search_categories.getText());
        model = (DefaultTableModel) view.jTable_categories.getModel();
        Object[] row = new Object[2];
            
        for (int i = 0; i < list.size(); i++) {
            row[0] = list.get(i).getId();
            row[1] = list.get(i).getName();
            model.addRow(row);
        }
    }
    
    public void listCategoryName(){
        
        List<Categories> list = categorie_dao.list(view.jText_search_categories.getText());
        for (int i = 0; i < list.size(); i++) {
            int id = list.get(i).getId();
            String name = list.get(i).getName();
            view.jCombo_categories.addItem(new DynamicCombo(id, name));
        }
    }
    
}
