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
public class Cart {
    private static int count;
    private final int id;
    private ArrayList<Model> models_inCart;
    private float cartValue;

    public Cart() {
        count++;
        id = count;
        models_inCart = new ArrayList<>();
    }
    
    public int getId() {
        return id;
    }

    public ArrayList<Model> getModels_inCart() {
        return models_inCart;
    }

    public float getCartValue() {
        return cartValue;
    }
    
    public void addtoCart(Model m) {
        models_inCart.add(m);
        cartValue+=m.getPrice();
    }
    
    public void removefromCart(Model m) {
        models_inCart.remove(m);
        cartValue-=m.getPrice();
    }
}
