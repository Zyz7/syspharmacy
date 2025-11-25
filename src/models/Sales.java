/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

/**
 *
 * @author zyz7
 */
public class Sales {
    
    private int id;
    private String code;
    private String date;
    private double total;
    private String product_name;
    private double price;
    private int quantity;
    private double subtotal;
    private int customer_id;
    private String customer_name;
    private int employee_id;
    private String employee_name;

    public Sales() {
    }

    public Sales(int id, String code, String date, double total, String product_name, double price, int quantity, double subtotal, int customer_id, String customer_name, int employee_id, String employee_name) {
        this.id = id;
        this.code = code;
        this.date = date;
        this.total = total;
        this.product_name = product_name;
        this.price = price;
        this.quantity = quantity;
        this.subtotal = subtotal;
        this.customer_id = customer_id;
        this.customer_name = customer_name;
        this.employee_id = employee_id;
        this.employee_name = employee_name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }

    public int getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(int customer_id) {
        this.customer_id = customer_id;
    }

    public String getCustomer_name() {
        return customer_name;
    }

    public void setCustomer_name(String customer_name) {
        this.customer_name = customer_name;
    }

    public int getEmployee_id() {
        return employee_id;
    }

    public void setEmployee_id(int employee_id) {
        this.employee_id = employee_id;
    }

    public String getEmployee_name() {
        return employee_name;
    }

    public void setEmployee_name(String employee_name) {
        this.employee_name = employee_name;
    }
    
    
}
