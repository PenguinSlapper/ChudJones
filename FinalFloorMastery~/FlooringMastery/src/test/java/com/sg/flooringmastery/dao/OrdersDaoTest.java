/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.dao;

import com.sg.flooringmastery.dto.Order;
import java.math.BigDecimal;
import java.util.List;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author nick9
 */
public class OrdersDaoTest {

    private OrdersDao dao = new OrdersDaoImpl();

    public OrdersDaoTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() throws Exception {
        List<Order> orderList = dao.getAllOrders();
        for (Order order : orderList) {
            dao.removeOrder(order.getOrderNumber());
        }
    }

    @Test
    public void testAddOrder() throws Exception {
        Order order = new Order("1");
        order.setCustomerName("bob");
        order.setState("PA");
        order.setTaxRate(new BigDecimal("4.45"));
        order.setProductType("bamboo");
        order.setArea(new BigDecimal("100"));
        order.setCostPerSquareFoot(BigDecimal.ONE);
        order.setLaborCostPerSquareFoot(new BigDecimal("5"));
        order.setMaterialCost(new BigDecimal("10"));
        order.setLaborCost(new BigDecimal("7.50"));
        order.setTax(new BigDecimal("15.00"));
        order.setTotal(new BigDecimal("200.00"));
        order.setDate("02/28/1997");

        dao.addOrder(order.getOrderNumber(), order);

        Order fromDao = dao.getOrder(order.getOrderNumber());

        assertEquals(order, fromDao);
    }

}
