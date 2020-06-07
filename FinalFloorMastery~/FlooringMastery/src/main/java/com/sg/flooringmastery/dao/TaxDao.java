/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.dao;

import com.sg.flooringmastery.dto.Tax;
import java.util.List;

/**
 *
 * @author nick9
 */
public interface TaxDao {
    
    Tax addTax(String state, Tax tax);
    
    List<Tax> getAllTaxes();
    
    Tax getTax(String state);
    
    Tax removeTax(String state);
    
    void editTax(String state,Tax taxToEdit);
    
    void loadTax() throws TaxDaoException;
    
    void writeTax() throws TaxDaoException;
}
