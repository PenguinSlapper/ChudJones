/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.ServiceLayer;

import com.sg.flooringmastery.dao.OrdersDao;
import com.sg.flooringmastery.dao.OrdersDaoImpl;
import com.sg.flooringmastery.dao.ProductsDao;
import com.sg.flooringmastery.dao.ProductsDaoImpl;
import com.sg.flooringmastery.dao.TaxDao;
import com.sg.flooringmastery.dao.TaxDaoImpl;
import com.sg.flooringmastery.dto.Order;
import java.math.BigDecimal;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author nick9
 */
public class ServiceLayerTest {
    
    private ServiceLayer service;
    Order onlyOrder;
    public ServiceLayerTest() {
        OrdersDao orderDao = new OrdersDaoImpl();
        ProductsDao productDao = new ProductsDaoImpl();
        TaxDao taxDao = new TaxDaoImpl();
        service = new ServiceLayerImpl(orderDao,productDao,taxDao);
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void testSetAllOrderProperties() throws Exception {
        Order onlyOrder = new Order("2");
        onlyOrder.setCustomerName("jane");
        onlyOrder.setState("Tx");
        onlyOrder.setTaxRate(new BigDecimal("4.45"));
        onlyOrder.setProductType("Bamboo");
        onlyOrder.setArea(new BigDecimal("50"));
        onlyOrder.setCostPerSquareFoot(BigDecimal.TEN);
        onlyOrder.setLaborCostPerSquareFoot(new BigDecimal("1.50"));
        onlyOrder.setMaterialCost(new BigDecimal("12"));
        onlyOrder.setLaborCost(new BigDecimal("9.50"));
        onlyOrder.setTax(new BigDecimal("6.00"));
        onlyOrder.setTotal(new BigDecimal("260.00"));
        onlyOrder.setDate("02/28/1997");
        service.setAllOrderProperties(onlyOrder);
    }

    
}
