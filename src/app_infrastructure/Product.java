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
public class Product {
    private String name;
    private static int id;
    private ArrayList<Model> models;

    public Product(String name) {
        id+=1;
        this.name = name;
        models = new ArrayList<>();
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static int getId() {
        return id;
    }

    public ArrayList<Model> getModels() {
        return models;
    }

    public void setModels(ArrayList<Model> models) {
        this.models = models;
    }
    
    public void addModel(Model m) {
        this.models.add(m);
    }
    
    public void removeModel(Model m) {
        this.models.remove(m);
    }
    
}
