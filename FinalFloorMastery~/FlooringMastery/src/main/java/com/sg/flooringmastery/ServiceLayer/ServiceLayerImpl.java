/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.ServiceLayer;

import com.sg.flooringmastery.dao.OrderDaoPersistenceException;
import com.sg.flooringmastery.dao.OrdersDao;
import com.sg.flooringmastery.dao.ProductDaoException;
import com.sg.flooringmastery.dao.ProductsDao;
import com.sg.flooringmastery.dao.TaxDao;
import com.sg.flooringmastery.dao.TaxDaoException;
import com.sg.flooringmastery.dto.Order;
import com.sg.flooringmastery.dto.Product;
import com.sg.flooringmastery.dto.Tax;
import java.math.BigDecimal;
import java.util.List;
import com.sg.flooringmastery.dao.ProdPersistenceException;

/**
 *
 * @author nick9
 */
public class ServiceLayerImpl implements ServiceLayer{

    private OrdersDao orderDao;
    private ProductsDao productDao;
    private TaxDao taxDao;
    
    public ServiceLayerImpl(OrdersDao orderDao,ProductsDao productsDao,TaxDao taxDao){
        this.orderDao = orderDao;
        this.productDao = productsDao;
        this.taxDao = taxDao;
    }

    ServiceLayerImpl(OrdersDao dao) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    //ALL ORDER METHODS*******************
    @Override
    public Order addOrder(String orderNumber, Order order) throws OrderDaoPersistenceException{
        return orderDao.addOrder(orderNumber, order);
    }

    @Override
    public List<Order> getAllOrders() throws OrderDaoPersistenceException{
        return orderDao.getAllOrders();
    }

    @Override
    public Order getOrder(String orderNumber) throws OrderDaoPersistenceException{
        return orderDao.getOrder(orderNumber);
    }

    @Override
    public Order removeOrder(String orderNumber) throws OrderDaoPersistenceException{
        return orderDao.removeOrder(orderNumber);
    }

    @Override
    public void editOrder(String oldOrderNumber, Order OrderToEdit) throws OrderDaoPersistenceException{
        orderDao.editOrder(oldOrderNumber, OrderToEdit);
    }

    @Override
    public void loadOrder() throws OrderDaoPersistenceException {
        orderDao.loadOrder();
    }

    @Override
    public void writeOrder() throws OrderDaoPersistenceException {
        orderDao.writeOrder();
    }
    
    @Override
    public List<Order> getOrdersByDate(String date) throws OrderDaoPersistenceException {
        return orderDao.getOrdersByDate(date);
    }
    
    @Override
    public String getHighestOrderNumber() throws OrderDaoPersistenceException {
        return orderDao.getHighestOrderNumber();
    }
    
    @Override
    public void writeAllOrdersToCorrectFile() throws OrderDaoPersistenceException,ProdPersistenceException{
            orderDao.writeAllOrdersToCorrectFile();
    }
//ALL PRODUCT METHODS************************
    @Override
    public Product addProduct(String productType, Product product) throws ProductDaoException{
        return productDao.addProduct(productType, product);
    }

    @Override
    public List<Product> getAllProducts() throws ProductDaoException{
        return productDao.getAllProducts();
    }

    @Override
    public Product getProduct(String productType) throws ProductDaoException{
        return productDao.getProduct(productType);
    }

    @Override
    public Product removeProduct(String productType) throws ProductDaoException{
        return productDao.removeProduct(productType);
    }

    @Override
    public void editProduct(String productType, Product ProductToEdit) throws ProductDaoException{
        productDao.editProduct(productType, ProductToEdit);
    }

    @Override
    public void loadProduct() throws ProductDaoException {
        productDao.loadProduct();
    }

    @Override
    public void writeProduct() throws ProductDaoException {
        productDao.writeProduct();
    }
//ALL TAX METHODS**************************

    @Override
    public Tax addTax(String state, Tax tax){
        return taxDao.addTax(state, tax);
    }

    @Override
    public List<Tax> getAllTaxes() {
        return taxDao.getAllTaxes();
    }

    @Override
    public Tax getTax(String state) {
        return taxDao.getTax(state);
    }

    @Override
    public Tax removeTax(String state) {
        return taxDao.removeTax(state);
    }

    @Override
    public void editTax(String state, Tax taxToEdit) {
        taxDao.editTax(state, taxToEdit);
    }

    @Override
    public void loadTax() throws TaxDaoException {
        taxDao.loadTax();
    }

    @Override
    public void writeTax() throws TaxDaoException {
        taxDao.writeTax();
    }
    
    public Order setTaxRateByState(Order order){
            String state = order.getState();
            Tax tax = taxDao.getTax(state);
            BigDecimal taxRate = tax.getTaxRate();
            order.setTaxRate(taxRate);
        return order;
    }
    
    public Order setCostsPerSquareFoot(Order order) throws ProductDaoException{
        String productType = order.getProductType();
        Product product = productDao.getProduct(productType);
        BigDecimal cpsf = product.getCostPerSquareFoot();
        BigDecimal lcpsf = product.getLaborCostPerSquareFoot();
        order.setCostPerSquareFoot(cpsf);
        order.setLaborCostPerSquareFoot(lcpsf);
        return order;
    }
    public Order setMaterialCost(Order order){
        BigDecimal area = order.getArea();
        BigDecimal cpsf = order.getCostPerSquareFoot();
        BigDecimal materialCost = area.multiply(cpsf);
        order.setMaterialCost(materialCost);
        return order;
    }
    public Order setLaborCost(Order order){
        BigDecimal lcpsf = order.getLaborCostPerSquareFoot();
        BigDecimal area = order.getArea();
        BigDecimal laborCost = lcpsf.multiply(area);
        order.setLaborCost(laborCost);
        return order;
    }
    public Order setTaxAndTotal(Order order){
        BigDecimal materialCost = order.getMaterialCost();
        BigDecimal laborCost = order.getLaborCost();
        BigDecimal subtotal = materialCost.add(laborCost);
        BigDecimal taxRate = order.getTaxRate().movePointLeft(2);
        BigDecimal Tax = subtotal.multiply(taxRate);
        order.setTax(Tax);
        BigDecimal total = subtotal.add(Tax);
        order.setTotal(total);
        return order;
    }
    public Order setAllOrderProperties(Order order) throws ProductDaoException{
        Order newOrder = setTaxAndTotal(setLaborCost(setMaterialCost(setCostsPerSquareFoot(setTaxRateByState(order)))));
        return newOrder;
    }
    
}
