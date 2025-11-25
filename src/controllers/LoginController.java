/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import models.Employees;
import models.EmployeesDao;
import views.LoginView;
import views.SystemView;

/**
 *
 * @author zyz7
 */
public class LoginController implements ActionListener {
    
    private Employees employee;
    private EmployeesDao employee_dao;
    private LoginView login_view;

    public LoginController(Employees employee, EmployeesDao employee_dao, LoginView login_view) {
        this.employee = employee;
        this.employee_dao = employee_dao;
        this.login_view = login_view;
        this.login_view.btn_login.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        
        String user = login_view.txt_username.getText().trim();
        String pass = String.valueOf(login_view.txt_password.getPassword());
        
        if (e.getSource() == login_view.btn_login) {
            
            if (!user.equals("") || !pass.equals("")) {
                
                employee = employee_dao.login(user, pass);
                if (employee.getUsername() != null) {
                    
                    if (employee.getRol().equals("Administrator")) {
                        
                        SystemView admin = new SystemView();
                        admin.setVisible(true);
                    } else {
                        SystemView aux = new SystemView();
                        aux.setVisible(true);
                        
                    }
                    this.login_view.dispose();
                } else {
                    JOptionPane.showMessageDialog(null, "Incorrect username or password");
                }
            } else {
                JOptionPane.showMessageDialog(null, "The fields cannot be empty");
            }
        }
    }
    
    
}
