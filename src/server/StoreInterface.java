/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Yazan
 */
public interface StoreInterface extends Remote {
    public int initialize() throws RemoteException;
    public ArrayList<String> getCategories() throws RemoteException;
    public ArrayList<String> getProducts(String category) throws RemoteException;
    public Map<String,List> getModels(String category, String product) throws RemoteException;
    public int getModelsCount() throws RemoteException;
    public boolean addtoCart(int cart_conut, int model_id, int quantity) throws RemoteException;
    public Map<Integer,List> getModelsNcart(int cartcount) throws RemoteException;
    public ArrayList<Integer> getModelIDsNcart(int cartcount) throws RemoteException;
    public boolean removeFromCart(int cart_count, int model_id, int quantity) throws RemoteException;
    public int checkOut(int cart_count) throws RemoteException;
    public Map<Integer, List> getModelsNBill(int billID) throws RemoteException;
    public int getUserID() throws RemoteException;
    public ArrayList<Integer> getUserbills(int userID) throws RemoteException;
    public boolean cartisEmpty(int cartcount) throws RemoteException ;
    public void destroy(int cartcount) throws RemoteException;
}
