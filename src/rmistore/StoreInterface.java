/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rmistore;

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
    public ArrayList<String> getCategories() throws RemoteException;
    public ArrayList<String> getProducts(String category) throws RemoteException;
    public Map<String,List> getModels(String category, String product) throws RemoteException;
}
