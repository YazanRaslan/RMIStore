/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app_infrastructure;

import java.util.ArrayList;
import java.util.Map;

/**
 *
 * @author Yazan
 */
public class Bill {
    private static int count;
    private final int id;
    private final int userID;
    private final Cart cart;
    private final float billValue;
    Map<Integer, Integer> models_inBill;
    
    public Bill(int userID, Cart c) {
        count++;
        id = count;
        this.userID = userID;
        this.cart = c;
        models_inBill = c.getModels_inCart();
        billValue = c.getCartValue();
    }

    public int getId() {
        return id;
    }

    public int getUserID() {
        return userID;
    }
    
    public Map<Integer, Integer> getModels_inBill() {
        return models_inBill;
    }

    public float getBillValue() {
        return billValue;
    }
    
    
    
}
