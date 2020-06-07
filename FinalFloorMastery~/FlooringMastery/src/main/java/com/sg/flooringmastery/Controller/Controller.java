/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.Controller;

import com.sg.flooringmastery.ServiceLayer.ServiceLayer;
import com.sg.flooringmastery.dao.OrderDaoPersistenceException;
import com.sg.flooringmastery.dao.ProdPersistenceException;
import com.sg.flooringmastery.dao.ProductDaoException;
import com.sg.flooringmastery.dao.TaxDaoException;
import com.sg.flooringmastery.dto.Order;
import com.sg.flooringmastery.dto.Product;
import com.sg.flooringmastery.ui.FlooringView;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 *
 * @author nick9
 */
public class Controller {

    private FlooringView view;
    private ServiceLayer service;

    public Controller(ServiceLayer service, FlooringView view) {
        this.service = service;
        this.view = view;
    }

    public void run()  {
      try{
        service.loadOrder();
        service.loadTax();
        service.loadProduct();
            
        boolean keepGoing = true;
        int menuSelection = 0;
      
     
          

        
        while (keepGoing) {

            menuSelection = view.printMenuAndGetSelection();

            switch (menuSelection) {
                case 1:
                    displayOrdersByDate();
                    break;
                case 2:
                    addOrder();
                    break;
                case 3:
                    editOrder();
                    break;
                case 4:
                    removeOrder();
                    break;
                case 5:
                    saveEverything();
                    break;
                case 6:
                    keepGoing = false;
                    break;
                default:
                    view.defualtBanner();
            }
            
        }
        view.goodByeMessage();
       
           } catch (OrderDaoPersistenceException | NumberFormatException |TaxDaoException | ProductDaoException | ProdPersistenceException e) {
            view.displayErrorMessage(e.getMessage());
         
             
           }}

    private void displayOrdersByDate() throws OrderDaoPersistenceException {
        String date = view.showPromptAndGetDate();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        LocalDate ld = LocalDate.parse(date, formatter);
        String formatted = ld.format(formatter);
        List<Order> orders = service.getOrdersByDate(formatted);
        view.listMenu(orders);
    }

    private void addOrder() throws TaxDaoException,OrderDaoPersistenceException, ProductDaoException, ProdPersistenceException {
        String newOrderNum = service.getHighestOrderNumber();
        List<Product> products = service.getAllProducts();
        view.listProducts(products);
        Order newOrder = view.getNewOrderInfo(newOrderNum);
        Boolean save = view.saveOption();
        if(save) {
            service.setAllOrderProperties(newOrder);
            service.addOrder(newOrder.getOrderNumber(), newOrder);
        } else {
        }
    }

    private void removeOrder() throws OrderDaoPersistenceException {
        view.removeOrderBanner();
        String orderNum = view.getOrderToBeRemoved();
        Boolean save = view.saveOption();
        if (save != false) {
            service.removeOrder(orderNum );
            view.removeSuccessBanner();
        }
    }

    private void editOrder() throws TaxDaoException, OrderDaoPersistenceException, ProductDaoException, ProdPersistenceException {
        String date = view.showPromptAndGetDate();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        LocalDate ld = LocalDate.parse(date, formatter);
        String formatted = ld.format(formatter);
        String orderNum = view.getOrderNumber();
        Order orderToEdit = service.getOrder(orderNum);
        view.showOrder(orderToEdit);
        Order editedOrder = view.editOrder(orderToEdit);
        editedOrder.setDate(formatted);
        service.setAllOrderProperties(editedOrder);
        service.addOrder(editedOrder.getOrderNumber(), editedOrder);
    }

    private void saveEverything() throws OrderDaoPersistenceException, ProdPersistenceException {
        service.writeAllOrdersToCorrectFile();
    }
}
