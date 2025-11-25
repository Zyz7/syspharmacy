/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import static models.EmployeesDao.address_user;
import static models.EmployeesDao.email_user;
import static models.EmployeesDao.full_name_user;
import static models.EmployeesDao.id_user;
import static models.EmployeesDao.telephone_user;
import views.SystemView;

/**
 *
 * @author zyz7
 */
public class SettingsController implements MouseListener {

    private SystemView views;
    
    public SettingsController(SystemView views) {
        this.views = views;
        this.views.jLabel_sales.addMouseListener(this);
        this.views.jLabel_categories.addMouseListener(this);
        this.views.jLabel_customers.addMouseListener(this);
        this.views.jLabel_employees.addMouseListener(this);
        this.views.jLabel_products.addMouseListener(this);
        this.views.jLabel_purchases.addMouseListener(this);
        this.views.jLabel_reports.addMouseListener(this);
        this.views.jLabel_profile.addMouseListener(this);
        this.views.jLabel_suppliers.addMouseListener(this);
        Profile();
    }
    
    public void Profile() {
        this.views.jText_profile_id.setText(String.valueOf(id_user));
        this.views.jText_profile_fullname.setText(full_name_user);
        this.views.jText_profile_address.setText(address_user);
        this.views.jText_profile_phone.setText(telephone_user);
        this.views.jText_profile_email.setText(email_user);
    }
    
    @Override
    public void mouseClicked(MouseEvent e) {
        
        if (e.getSource() == views.jLabel_profile) {
            
            views.jTabbed.setSelectedIndex(6);
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
        if (e.getSource() == views.jLabel_categories) {
            views.jPanel_categories.setBackground(new Color(152, 202, 63));
        } else if (e.getSource() == views.jLabel_customers) {
            views.jPanel_customers.setBackground(new Color(152, 202, 63));
        } else if (e.getSource() == views.jLabel_employees) {
            views.jPanel_employees.setBackground(new Color(152, 202, 63));
        } else if (e.getSource() == views.jLabel_products) {
            views.jPanel_products.setBackground(new Color(152, 202, 63));
        } else if (e.getSource() == views.jLabel_purchases) {
            views.jPanel_purchases.setBackground(new Color(152, 202, 63));
        } else if (e.getSource() == views.jLabel_reports) {
            views.jPanel_reports.setBackground(new Color(152, 202, 63));
        } else if (e.getSource() == views.jLabel_profile) {
            views.jPanel_settings.setBackground(new Color(152, 202, 63));
        } else if (e.getSource() == views.jLabel_suppliers) {
            views.jPanel_suppliers.setBackground(new Color(152, 202, 63));
        } else if (e.getSource() == views.jLabel_sales) {
            views.jPanel_sales.setBackground(new Color(152, 202, 63));
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        if (e.getSource() == views.jLabel_categories) {
            views.jPanel_categories.setBackground(new Color(18, 45, 61));
        } else if (e.getSource() == views.jLabel_customers) {
            views.jPanel_customers.setBackground(new Color(18, 45, 61));
        } else if (e.getSource() == views.jLabel_employees) {
            views.jPanel_employees.setBackground(new Color(18, 45, 61));
        } else if (e.getSource() == views.jLabel_products) {
            views.jPanel_products.setBackground(new Color(18, 45, 61));
        } else if (e.getSource() == views.jLabel_purchases) {
            views.jPanel_purchases.setBackground(new Color(18, 45, 61));
        } else if (e.getSource() == views.jLabel_reports) {
            views.jPanel_reports.setBackground(new Color(18, 45, 61));
        } else if (e.getSource() == views.jLabel_profile) {
            views.jPanel_settings.setBackground(new Color(18, 45, 61));
        } else if (e.getSource() == views.jLabel_suppliers) {
            views.jPanel_suppliers.setBackground(new Color(18, 45, 61));
        } else if (e.getSource() == views.jLabel_sales) {
            views.jPanel_sales.setBackground(new Color(18, 45, 61));
        }
    }
    
}
