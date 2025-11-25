/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

/**
 *
 * @author zyz7
 */
public class DynamicCombo {
    
    private int id;
    private String name;

    public DynamicCombo(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    @Override
    public String toString() {
        return this.getName();
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DynamicCombo)) return false;
        DynamicCombo other = (DynamicCombo) o;
        return id == other.id; 
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(id);
    }
    
}
