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
public class ProdPersistenceException extends Exception {
    
    public ProdPersistenceException(String message) {
        super(message);
    }
    public ProdPersistenceException(String message, Throwable cause){
        super(message);
    }
}
