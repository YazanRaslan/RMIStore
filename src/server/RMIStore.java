/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import app_infrastructure.*;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author Yazan
 */
public class RMIStore extends UnicastRemoteObject implements StoreInterface {

    /**
     * @param args the command line arguments
     */
    ArrayList<Category> categories;
    ArrayList<Model> models;
    Map<Integer, Cart> carts;
    Map<Integer, Bill> bills;
    static int counter = 0;
    static int cart_count = 0;
    int userID;

    public RMIStore() throws RemoteException {

        Model m1 = new Model("J730", 5, 116500);
        Model m2 = new Model("J500", 3, 90000);
        Model m3 = new Model("Sony ZX", 3, 190000);
        Model m4 = new Model("k5550", 2, 250000);
        Model m5 = new Model("Sony202", 1, 125000);
        Model m6 = new Model("syronics", 6, 50000);
        Model m7 = new Model("sony vaio", 2, 250000);

        Product p1 = new Product("Samsung");
        Product p2 = new Product("Sony");
        Product p3 = new Product("Asus");
        Product p4 = new Product("Sony");
        Product p5 = new Product("Syronics");

        Category c1 = new Category("Mobiles");
        Category c2 = new Category("Laptops");
        Category c3 = new Category("Tvs");

        p1.addModel(m1);
        p1.addModel(m2);
        p2.addModel(m3);
        p3.addModel(m4);
        p4.addModel(m5);
        p5.addModel(m6);

        c1.addProduct(p1);
        c1.addProduct(p2);
        c2.addProduct(p3);
        c3.addProduct(p4);
        c3.addProduct(p5);

        categories = new ArrayList<>();
        categories.add(c1);
        categories.add(c2);
        categories.add(c3);

        models = new ArrayList<>();
        models.add(m1);
        models.add(m2);
        models.add(m3);
        models.add(m4);
        models.add(m5);
        models.add(m6);
        models.add(m7);

        carts = new HashMap<>();
        bills = new HashMap<>();

    }

    public static void main(String[] args) throws RemoteException {
        Registry reg = LocateRegistry.createRegistry(Registry.REGISTRY_PORT);
        RMIStore obj = new RMIStore();
        reg.rebind("rmi://localhost/service", obj);
        System.out.println("Server has been initialized");
    }

    @Override
    public int initialize() throws RemoteException {
        counter++;
        userID = counter;
        cart_count++;
        return cart_count;
    }

    @Override
    public ArrayList<String> getCategories() throws RemoteException {
        ArrayList<String> categories_names = new ArrayList<>();
        for (Category c : categories) {
            categories_names.add(c.getName());
        }
        return categories_names;
    }

    @Override
    public ArrayList<String> getProducts(String category) throws RemoteException {
        ArrayList<String> products_names = new ArrayList<>();
        for (Category c : categories) {
            if (c.getName().equals(category)) {
                for (Product p : c.getProducts()) {
                    products_names.add(p.getName());
                }
            }
        }
        return products_names;
    }

    @Override
    public Map<String, List> getModels(String category, String product) throws RemoteException {
        Map<String, List> models = new HashMap<>();
        ArrayList<String> modelInfo = null;
        int count = 0;
        for (Category c : categories) {
            if (c.getName().equals(category)) {
                for (Product p : c.getProducts()) {
                    if (p.getName().equals(product)) {
                        for (Model model : p.getModels()) {
                            modelInfo = new ArrayList<>();
                            modelInfo.add(Integer.toString(model.getId()));
                            modelInfo.add(model.getName());
                            modelInfo.add(Integer.toString(model.getQuantity()));
                            modelInfo.add(Float.toString(model.getPrice()));
                            models.put(Integer.toString(count), modelInfo);
                            count++;
                        }
                    }
                }
            }
        }
        return models;
    }

    @Override
    public boolean addtoCart(int cart_count, int model_id, int quantity) throws RemoteException {
        Cart cart;
        Model model = null;
        boolean added;
        for (Model m : models) {
            if (m.getId() == model_id) {
                model = m;
            }
        }

        if (carts.get(cart_count) == null) {
            cart = new Cart(cart_count);
            added = cart.addtoCart(model, quantity);
            carts.put(cart_count, cart);

        } else {
            cart = carts.get(cart_count);
            added = cart.addtoCart(model, quantity);
            carts.put(cart_count, cart);
        }
        return added;
    }

    @Override
    public Map<Integer, List> getModelsNcart(int cartcount) throws RemoteException {
        Map<Integer, List> modelsNcart = new HashMap<>();
        Cart c = carts.get(cartcount);
        Set<Integer> keySet = c.getModels_inCart().keySet();
        Iterator itr = keySet.iterator();
        int key;
        ArrayList<String> modelInfo;
        while (itr.hasNext()) {
            key = (int) itr.next();
            for (Model m : models) {
                if (m.getId() == key) {
                    modelInfo = new ArrayList<>();
                    modelInfo.add(m.getName());
                    modelInfo.add(Integer.toString(c.getModels_inCart().get(key)));
                    modelInfo.add(Float.toString(m.getPrice()));
                    modelInfo.add(Float.toString(m.getPrice() * c.getModels_inCart().get(key)));
                    System.out.println(modelInfo.toString());
                    modelsNcart.put(m.getId(), modelInfo);
                }
            }
        }
        return modelsNcart;
    }

    @Override
    public boolean removeFromCart(int cart_count, int model_id, int quantity) throws RemoteException {
        boolean removed = false;
        Cart cart;
        Model model = null;

        for (Model m : models) {
            if (m.getId() == model_id) {
                model = m;
            }
        }
        cart = carts.get(cart_count);

        if (cart.getModels_inCart().get(model_id) >= quantity) {
            removed = cart.removefromCart(model, quantity);
            return removed;
        } else {
            return false;
        }
    }

    @Override
    public int checkOut(int cart_count) throws RemoteException {
        Cart cart;
        cart = carts.get(cart_count);

        Bill bill = new Bill(userID, cart);
        System.out.println("bill ID: " + bill.getId());
        System.out.println("bill models " + bill.getModels_inBill().toString());
        bills.put(bill.getId(), bill);
        carts.remove(cart_count);
        return bill.getId();
    }

    @Override
    public Map<Integer, List> getModelsNBill(int billID) throws RemoteException {
        Map<Integer, List> modelsNbill = new HashMap<>();
        Bill b = bills.get(billID);
        Set<Integer> keySet = b.getModels_inBill().keySet();
        Iterator itr = keySet.iterator();
        int key;
        ArrayList<String> modelInfo;
        while (itr.hasNext()) {
            key = (int) itr.next();
            for (Model m : models) {
                if (m.getId() == key) {
                    modelInfo = new ArrayList<>();
                    modelInfo.add(m.getName());
                    modelInfo.add(Integer.toString(b.getModels_inBill().get(key)));
                    modelInfo.add(Float.toString(m.getPrice()));
                    modelInfo.add(Float.toString(m.getPrice() * b.getModels_inBill().get(key)));
                    System.out.println(modelInfo.toString());
                    modelsNbill.put(m.getId(), modelInfo);
                }
            }
        }
        return modelsNbill;
    }

    @Override
    public int getUserID() throws RemoteException {
        return this.userID;
    }

    @Override
    public ArrayList<Integer> getUserbills(int userID) throws RemoteException {
        ArrayList<Integer> userbills = new ArrayList<>();

        Set<Integer> keySet = bills.keySet();

        Iterator itr = keySet.iterator();
        int key;
        Bill b;
        while (itr.hasNext()) {
            key = (int) itr.next();
            b = bills.get(key);
            if (b.getUserID() == userID) {
                userbills.add(b.getId());
            }
        }
        return userbills;
    }

    @Override
    public int getModelsCount() throws RemoteException {
        System.out.println("models size is : " + models.size());
        return models.size();
    }

    @Override
    public ArrayList<Integer> getModelIDsNcart(int cartcount) throws RemoteException {
        ArrayList<Integer> modelsIDsNcart = new ArrayList<>();
        Cart cart = carts.get(cartcount);
        Map<Integer, Integer> models_inCart = cart.getModels_inCart();

        Set<Integer> keySet = models_inCart.keySet();
        Iterator itr = keySet.iterator();

        while (itr.hasNext()) {
            modelsIDsNcart.add((Integer) itr.next());
        }
        return modelsIDsNcart;
    }

    @Override
    public boolean cartisEmpty(int cartcount) throws RemoteException {
        if (carts.containsKey(cartcount)) {
            if (carts.get(cartcount).getModels_inCart().isEmpty()) {
                return true;
            } else {
                return false;
            }
        } else {
            return true;
        }
    }

    @Override
    public void destroy(int cartcount) throws RemoteException {
        if (carts.containsKey(cartcount)) {
            Cart c = carts.get(cartcount);
            Map<Integer, Integer> models_inCart = c.getModels_inCart();

            Set<Integer> keySet = models_inCart.keySet();
            Iterator itr = keySet.iterator();

            Model model = null;

            int key;

            while (itr.hasNext()) {
                key = (int) itr.next();

                for (Model m : models) {
                    if (m.getId() == key) {
                        model = m;
                    }
                }
                c.removefromCart(model, models_inCart.get(key));
            }

        }
    }

}
