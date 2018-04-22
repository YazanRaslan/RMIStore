/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app_infrastructure;

/**
 *
 * @author Yazan
 */
public class Model {
    private static int count;
    private String name;
    private final int id;
    private int quantity;
    private float price;

    public Model(String name, int quantity, float price) {
        count++;
        id = count;
        this.name = name;
        this.quantity = quantity;
        this.price = price;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }
    
}
