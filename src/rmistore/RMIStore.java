/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rmistore;

import app_infrastructure.*;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 *
 * @author Yazan
 */
public class RMIStore extends UnicastRemoteObject implements StoreInterface {

    /** 
     * @param args the command line arguments
     */
    
    
    ArrayList<Category> categories;
    
    public RMIStore() throws RemoteException {
        
        Model m1 = new Model("J730",5,116500);
        Model m2 = new Model("J500",3,90000);
        Model m3 = new Model("Sony ZX",3,190000);
        Model m4 = new Model("k5550",2,250000);
        Model m5 = new Model("Sony202",1,125000);
        Model m6 = new Model("syronics",6,50000);
        Model m7 = new Model("sony vaio",2,250000);
  
        Product p1 =new Product("Samsung");
        Product p2 =new Product("Sony");
        Product p3 =new Product("Asus");
        Product p4 =new Product("Sony");
        Product p5 =new Product("Syronics");
        
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
        
    }
    
    public static void main(String[] args) throws RemoteException {
        Registry reg = LocateRegistry.createRegistry(Registry.REGISTRY_PORT);
        RMIStore obj = new RMIStore();
        reg.rebind("rmi://localhost/service", obj);
        System.out.println("Server has been initialized");
    }
    
    @Override
    public ArrayList<String> getCategories() throws RemoteException {
        ArrayList<String> categories_names = new ArrayList<>();
        for(Category c : categories) {
            categories_names.add(c.getName());
        }
        return categories_names;
    }

    @Override
    public ArrayList<String> getProducts(String category) throws RemoteException {
       ArrayList<String> products_names = new ArrayList<>();
       for(Category c : categories) {
           if(c.getName().equals(category)) {
                for(Product p : c.getProducts()){
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
        int count=0;
        for(Category c : categories) {
           if(c.getName().equals(category)) {
                for(Product p : c.getProducts()){
                    if(p.getName().equals(product)) {
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

}
