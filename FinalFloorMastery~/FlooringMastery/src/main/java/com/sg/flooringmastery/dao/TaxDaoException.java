/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.dao;

/**
 *
 * @author nick9
 */
public class TaxDaoException extends Exception {
    
    public TaxDaoException(String message) {
        super(message);
    }
    public TaxDaoException(String message, Throwable cause){
        super(message);
    }
}
