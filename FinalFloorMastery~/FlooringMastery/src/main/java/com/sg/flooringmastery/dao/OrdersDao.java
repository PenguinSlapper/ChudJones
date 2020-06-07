/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.dao;

import com.sg.flooringmastery.dto.Order;
import java.util.List;

/**
 *
 * @author nick9
 */
public interface OrdersDao {
    
    Order addOrder(String orderNumber, Order order) throws OrderDaoPersistenceException;
    
    List<Order> getAllOrders() throws OrderDaoPersistenceException;
    
    Order getOrder(String orderNumber) throws OrderDaoPersistenceException;
    
    Order removeOrder(String orderNumber) throws OrderDaoPersistenceException;
    
    void editOrder(String oldOrderNumber,Order OrderToEdit) throws OrderDaoPersistenceException;
    
    void loadOrder() throws OrderDaoPersistenceException;
    
    void writeOrder() throws OrderDaoPersistenceException;
    
    public List<Order> getOrdersByDate(String date) throws OrderDaoPersistenceException;
    
    String getHighestOrderNumber() throws OrderDaoPersistenceException;
    
    void writeAllOrdersToCorrectFile() throws OrderDaoPersistenceException,ProdPersistenceException;
    
}