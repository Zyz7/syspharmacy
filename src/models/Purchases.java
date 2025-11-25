/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

/**
 *
 * @author zyz7
 */
public class Purchases {
    
    private int id;
    private String code;
    private String product_name;
    private int purchase_amount;
    private double purchase_price;
    private double purchase_subtotal;
    private String supplier_name_product;
    private String purcharser;
    private String created;
    private double total;

    public Purchases() {
    }

    public Purchases(int id, String code, String product_name, int purchase_amount, double purchase_price, double purchase_subtotal, String supplier_name_product, String purcharser, String created, double total) {
        this.id = id;
        this.code = code;
        this.product_name = product_name;
        this.purchase_amount = purchase_amount;
        this.purchase_price = purchase_price;
        this.purchase_subtotal = purchase_subtotal;
        this.supplier_name_product = supplier_name_product;
        this.purcharser = purcharser;
        this.created = created;
        this.total = total;
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

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public int getPurchase_amount() {
        return purchase_amount;
    }

    public void setPurchase_amount(int purchase_amount) {
        this.purchase_amount = purchase_amount;
    }

    public double getPurchase_price() {
        return purchase_price;
    }

    public void setPurchase_price(double purchase_price) {
        this.purchase_price = purchase_price;
    }

    public double getPurchase_subtotal() {
        return purchase_subtotal;
    }

    public void setPurchase_subtotal(double purchase_subtotal) {
        this.purchase_subtotal = purchase_subtotal;
    }

    public String getSupplier_name_product() {
        return supplier_name_product;
    }

    public void setSupplier_name_product(String supplier_name_product) {
        this.supplier_name_product = supplier_name_product;
    }

    public String getPurcharser() {
        return purcharser;
    }

    public void setPurcharser(String purcharser) {
        this.purcharser = purcharser;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
    
    
}
