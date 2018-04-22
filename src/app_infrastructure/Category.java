/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app_infrastructure;

import java.util.ArrayList;

/**
 *
 * @author Yazan
 */
public class Category {
    private String name;
    private static int id;
    private ArrayList<Product> products;

    public Category(String name) {
        id+=1;
        this.name = name;
        products = new ArrayList<>();
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

    public ArrayList<Product> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<Product> products) {
        this.products = products;
    }
    
    public void addProduct(Product p) {
        this.products.add(p);
    }
    
    public void removeProduct(Product p) {
        this.products.remove(p);
    }
    
}
