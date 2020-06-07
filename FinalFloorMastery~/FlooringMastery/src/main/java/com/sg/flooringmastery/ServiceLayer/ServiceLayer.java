/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.ServiceLayer;

import com.sg.flooringmastery.dao.OrderDaoPersistenceException;
import com.sg.flooringmastery.dao.ProductDaoException;
import com.sg.flooringmastery.dao.TaxDaoException;
import com.sg.flooringmastery.dao.ProdPersistenceException;
import com.sg.flooringmastery.dto.Order;
import com.sg.flooringmastery.dto.Product;
import com.sg.flooringmastery.dto.Tax;
import java.util.List;

/**
 *
 * @author nick9
 */
public interface ServiceLayer {
   
    
    Order addOrder(String orderNumber, Order order) throws OrderDaoPersistenceException;
    
    List<Order> getAllOrders() throws OrderDaoPersistenceException;
    
    Order getOrder(String orderNumber) throws OrderDaoPersistenceException;
    
    Order removeOrder(String orderNumber) throws OrderDaoPersistenceException;
    
    void editOrder(String oldOrderNumber,Order OrderToEdit) throws OrderDaoPersistenceException;
    
    void loadOrder() throws OrderDaoPersistenceException;
    
    void writeOrder() throws OrderDaoPersistenceException;
    
    List<Order> getOrdersByDate(String date) throws OrderDaoPersistenceException;
    
    String getHighestOrderNumber() throws OrderDaoPersistenceException;
    
    void writeAllOrdersToCorrectFile() throws OrderDaoPersistenceException,ProdPersistenceException;
    
    //BELOW ARE ALL CRUD METHODS FOR PRODUCTS
    
    Product addProduct(String productType, Product product) throws ProductDaoException;
    
    public List<Product> getAllProducts() throws ProductDaoException;
    
    Product getProduct(String productType) throws ProductDaoException;
    
    Product removeProduct(String productType) throws ProductDaoException;
    
    void editProduct(String productType,Product ProductToEdit) throws ProductDaoException;
    
    void loadProduct() throws ProductDaoException;
    
    void writeProduct() throws ProductDaoException;
    
    //BELOW ARE ALL CRUD METHODS FOR TAXES
    
    Tax addTax(String state, Tax tax) ;
    
    List<Tax> getAllTaxes() ;
    
    Tax getTax(String state) ;
    
    Tax removeTax(String state) ;
    
    void editTax(String state,Tax taxToEdit) ;
    
    void loadTax() throws TaxDaoException;
    
    void writeTax() throws TaxDaoException;
    
    //BELOW ARE ALL Service METHODS FOR Calculations
    
    Order setAllOrderProperties(Order order)  throws OrderDaoPersistenceException, ProductDaoException, ProdPersistenceException, TaxDaoException;
    
    
}
