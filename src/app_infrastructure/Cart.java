/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app_infrastructure;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Yazan
 */
public class Cart {

    private int id;
    private Map<Integer, Integer> models_inCart;
    private float cartValue;

    public Cart(int id) {
        this.id = id;
        models_inCart = new HashMap<>();
    }

    public int getId() {
        return id;
    }

    public Map<Integer, Integer> getModels_inCart() {
        return models_inCart;
    }

    public float getCartValue() {
        return cartValue;
    }

    public boolean addtoCart(Model m, int quantity) {
        if (m.getQuantity() >= quantity) {
            if (models_inCart.containsKey(m.getId())) {
                models_inCart.put(m.getId(), models_inCart.get(m.getId()) + quantity);
                cartValue += m.getPrice() * quantity;
                m.setQuantity(m.getQuantity()-quantity);
            } else {
                models_inCart.put(m.getId(), quantity);
                cartValue += m.getPrice() * quantity;
                m.setQuantity(m.getQuantity()-quantity);
            }
            return true;
        } else return false;
    }

    public boolean removefromCart(Model m, int quantity) {

        if (models_inCart.get(m.getId()) > quantity) {
            models_inCart.put(m.getId(), models_inCart.get(m.getId()) - quantity);
            m.setQuantity(m.getQuantity()+quantity);
            return true;
        } else if(models_inCart.get(m.getId()) == quantity) {
            models_inCart.remove(m.getId());
            m.setQuantity(m.getQuantity()+quantity);
            return true;
        } else return false;
    }
}
