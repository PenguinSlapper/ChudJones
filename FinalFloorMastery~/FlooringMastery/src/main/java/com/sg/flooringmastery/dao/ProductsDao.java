/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.dao;

import com.sg.flooringmastery.dto.Product;
import java.util.List;

/**
 *
 * @author nick9
 */
public interface ProductsDao {
    
    Product addProduct(String productType, Product product) throws ProductDaoException;
    
    List<Product> getAllProducts() throws ProductDaoException;
    
    Product getProduct(String productType) throws ProductDaoException;
    
    Product removeProduct(String productType) throws ProductDaoException;
    
    void editProduct(String productType,Product ProductToEdit) throws ProductDaoException;
    
    void loadProduct() throws ProductDaoException;
    
    void writeProduct() throws ProductDaoException;
}
