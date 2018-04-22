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
public class Bill {
    private static int count;
    private final int id;
    private ArrayList<Model> models_inBill;
    private float billValue;
    
    public Bill(ArrayList<Model> models_inBill) {
        count++;
        id = count;
        this.models_inBill = models_inBill;
        for(Model m : models_inBill) {
            billValue+=m.getPrice();
        }
    }

    public int getId() {
        return id;
    }

    public ArrayList<Model> getModels_inBill() {
        return models_inBill;
    }

    public float getBillValue() {
        return billValue;
    }
    
    
    
}
