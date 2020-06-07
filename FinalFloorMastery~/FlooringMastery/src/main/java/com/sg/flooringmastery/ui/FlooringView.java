/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.ui;

import com.sg.flooringmastery.dto.Order;
import com.sg.flooringmastery.dto.Product;
import com.sg.flooringmastery.dto.Tax;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 *
 * @author nick9
 */
public class FlooringView {

    private UserIO io;
    private Order order;
    private Product product;
    private Tax tax;

    public FlooringView(UserIO io) {
        this.io = io;
    }

    public int printMenuAndGetSelection() {
        io.print("* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *");
        io.print("<<<Flooring Program>>>");
        io.print("1. Display Orders");
        io.print("* 2. Add an Order");
        io.print("* 3. Edit an Order");
        io.print("* 4. Remove an Order");
        io.print("* 5. Save Current Work");
        io.print("* 6. Quit");

        return io.readInt("Please select from "
                + " the above choices", 1, 6);
    }

    public void defualtBanner() {
        io.print("Unknown Command, don't try to break what isn't yours");
    }

    public void goodByeMessage() {
        io.print("*********GoodBye*********");
    }

    public String showPromptAndGetDate() throws NumberFormatException {
        try {
            io.print("Orders are searchable by date");
            return io.readString("Please enter the date of the order you wish to lookup in mm/dd/yyyy");
        } catch (NumberFormatException e) {
            displayErrorMessage(e.getMessage());
        }
        return null;
    }

    public void listMenu(List<Order> orders) throws NumberFormatException {
        try {
            io.print(" Date|OrderNumber|CustomerName|StateTaxRate|"
                    + "ProductType|Area|CostPerSquareFoot|LaborCost|"
                    + "PerSquareFoot|MaterialCost|LaborCost|Tax|Total");
            for (Order currentOrder : orders) {
                io.print(currentOrder.getDate()
                        + " :  " + currentOrder.getOrderNumber()
                        + " :  " + currentOrder.getCustomerName()
                        + " :  " + currentOrder.getState()
                        + " :  " + currentOrder.getTaxRate()
                        + " :  " + currentOrder.getProductType()
                        + " :  " + currentOrder.getArea()
                        + " :  " + currentOrder.getCostPerSquareFoot()
                        + " :  " + currentOrder.getLaborCostPerSquareFoot()
                        + " :  " + currentOrder.getMaterialCost()
                        + " :  " + currentOrder.getLaborCost()
                        + " :  " + currentOrder.getTax()
                        + " :  " + currentOrder.getTotal());

            }
        } catch (NumberFormatException e) {
            displayErrorMessage(e.getMessage());
        }
    }

    public Order getNewOrderInfo(String orderNum) throws NumberFormatException {
        try {
            io.print("* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *");
            io.print("******** Add Order ********");
            String finalOrderNumber = io.readString("Please Enter Order Number");
            String customerName = io.readString("Please enter the name you wish to appear on the order:");
            String state = io.readString("Please enter the name of the state the order is being purchased in:");
            String productType = io.readString("Please enter the type of flooring you wish to purchase:");
            BigDecimal area = new BigDecimal(io.readString("Please enter the size of the area you wish to cover in sq ft:"));
            String date = io.readString("Please enter the current date in MM/dd/yyyy");
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
            LocalDate ld = LocalDate.parse(date, formatter);
            String formatted = ld.format(formatter);
            Order currentOrder = new Order(finalOrderNumber);
            currentOrder.getOrderNumber();
            currentOrder.setDate(formatted);
            currentOrder.setCustomerName(customerName);
            currentOrder.setState(state);
            currentOrder.setProductType(productType);
            currentOrder.setArea(area);
            currentOrder.getDate();
            io.print("* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *");
            return currentOrder;

        } catch (NumberFormatException e) {
            displayErrorMessage(e.getMessage());
        }
        return null;
    }

    public Order editOrder(Order order) throws NumberFormatException {
        try {
            io.print("* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *");
            io.print("Edit Order");
            io.print("Order Number: " + order.getOrderNumber());
            io.print("Name: " + order.getCustomerName());
            String newName = io.readString("If you wish to change this type what you want, otherwise hit enter");
            io.print("State: " + order.getState());
            String newState = io.readString("If you wish to change this type what you want, otherwise hit enter");
            io.print("Product Type: " + order.getProductType());
            String newProduct = io.readString("If you wish to change this type what you want, otherwise hit enter");
            io.print("Area: " + order.getArea().toString());
            String newArea = io.readString("If you wish to change this type what you want, otherwise hit enter");
            int userChoice = io.readInt("Would you like to save your changes to this order? 1=Yes 2=No", 1, 2);
            boolean keepGoing = true;
            while (keepGoing) {
                switch (userChoice) {
                    case 1:
                        if (newName != null && !newName.isEmpty()) {
                            order.setCustomerName(newName);
                        }
                        if (newState != null && !newState.isEmpty()) {
                            order.setState(newState);
                        }
                        if (newProduct != null && !newProduct.isEmpty()) {
                            order.setProductType(newProduct);
                        }
                        if (newArea != null && !newArea.isEmpty()) {
                            if (!newArea.isEmpty()) {
                                order.setArea(new BigDecimal(newArea));
                            }
                        }
                        keepGoing = false;
                        break;
                    case 2:
                        io.print("well that was a waste of time");
                        keepGoing = false;
                        break;
                    default:
                        io.print("Unrecognized Response please try again");
                        io.print("just type y or n and hit enter, I know you can do it");
                        keepGoing = true;
                }
            }
            return order;
        } catch (NumberFormatException e) {
            displayErrorMessage(e.getMessage());
        }
        return null;
    }

    public void listProducts(List<Product> products) {
        io.print("ProductType|CostPerSquareFoot|LaborCostPerSquareFoot");
        for (Product currentProduct : products) {
            io.print(currentProduct.getProductType()
                    + " :  " + currentProduct.getCostPerSquareFoot().toString()
                    + " :  " + currentProduct.getLaborCostPerSquareFoot().toString());
        }
    }

    public void removeOrderBanner() {
        io.print("******* Remove Order *******");
    }

    public void getOrderDatetoberemoved() throws NumberFormatException {
        try {
            io.readString("Please Enter the Date of the Otder you wish to remove.");
        } catch (NumberFormatException e) {
            displayErrorMessage(e.getMessage());
        }
    }

    public String getOrderToBeRemoved() throws NumberFormatException {
        try {
            io.print("If you do not have your order number, "
                    + "you can find it using the 'Display Orders' menu option");
            return io.readString("please enter the order "
                    + "number of the order you wish to remove");
        } catch (NumberFormatException e) {
            displayErrorMessage(e.getMessage());
        }
        return null;
    }

    public void removeSuccessBanner() {
        io.print("******* Order Removed *******");
    }

    public String getOrderNumber() throws NumberFormatException {
      try{
        return io.readString("Please enter your order number");
  } catch (NumberFormatException e) {
            displayErrorMessage(e.getMessage());
        }
        return null;
    }

    public void showOrder(Order order) {
        io.print(order.getOrderNumber());
    }

    public Boolean saveOption() {
        final String saveMe = "y";
        Boolean answer = false;
        io.print("would you like to save current progress? y/n");
        String userChoice = io.readString("y/n hit enter when done");
        if (userChoice.equalsIgnoreCase(saveMe)) {
            answer = true;
        }
        return answer;
    }

    public void displayErrorMessage(String message) {
        io.print("Sorry Something Went Wrong Goodbye");
    }
}
