/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.dao;

import com.sg.flooringmastery.dto.Order;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 *
 * @author nick9
 */
public class OrdersDaoImpl implements OrdersDao  {

    private Map<String, Order> orders = new HashMap<>();
    public static String LIBRARY_FILE = "Orders_06012013.txt";
    public static final String DELIMITER = ",";
    private final File folder = new File("Flooring/Orders");

    @Override
    public Order addOrder(String orderNumber, Order order) throws OrderDaoPersistenceException {
        Order newOrder = orders.put(orderNumber, order);
        return newOrder;
    }

    @Override
    public List<Order> getAllOrders() throws OrderDaoPersistenceException {
        return new ArrayList<>(orders.values());
    }

    @Override
    public List<Order> getOrdersByDate(String date) throws OrderDaoPersistenceException {
        return orders.values()
                .stream()
                .filter(s -> s.getDate().equals(date))
                .collect(Collectors.toList());
    }

    @Override
    public String getHighestOrderNumber() throws OrderDaoPersistenceException {
        loadOrder();
        Comparator<Order> comparator = Comparator.comparing((integer) -> integer.getOrderNumber());
        String OrderNum = orders.values()
                .stream().max(comparator).get().toString();
        return OrderNum;
    }

    @Override
    public Order getOrder(String orderNumber) throws OrderDaoPersistenceException {
        Order order = orders.get(orderNumber);
        return order;
    }

    @Override
    public Order removeOrder(String orderNumber) throws OrderDaoPersistenceException {
        Order removedOrder = orders.remove(orderNumber);
        writeOrder();
        return removedOrder;
    }

    @Override
    public void editOrder(String oldOrderNumber, Order orderToEdit) throws OrderDaoPersistenceException {
        if (oldOrderNumber == orderToEdit.getOrderNumber()) {
            this.orders.replace(oldOrderNumber, orderToEdit);
        } else {
            Order oldOrder = this.orders.remove(oldOrderNumber);
            this.orders.put(orderToEdit.getOrderNumber(), orderToEdit);
        }
        this.writeOrder();
    }

    @Override
    public void loadOrder() throws OrderDaoPersistenceException {
        Scanner scanner;

        FilenameFilter orderFileFilter = new FilenameFilter() {
            public boolean accept(File dir, String filename) {
                if (filename.startsWith("Orders_")) {
                    return true;
                } else {
                    return false;
                }
            }
        };
        File[] listOfFiles = folder.listFiles(orderFileFilter);
        for (int i = 0; i < listOfFiles.length; i++) {

            if (listOfFiles[i].isFile()) {
                File newFile = listOfFiles[i];
                try {

                    scanner = new Scanner(
                            new BufferedReader(
                                    new FileReader(newFile)));
                } catch (FileNotFoundException e) {
                    throw new OrderDaoPersistenceException(
                            "-_- Could not load data into memory.", e);
                }

                String currentLine;

                String[] currentTokens;

                while (scanner.hasNextLine()) {

                    currentLine = scanner.nextLine();

                    currentTokens = currentLine.split(DELIMITER);

                    Order currentOrder = new Order(currentTokens[0]);

                    currentOrder.setCustomerName(currentTokens[1]);
                    currentOrder.setState(currentTokens[2]);
                    currentOrder.setTaxRate(new BigDecimal(currentTokens[3]));
                    currentOrder.setProductType(currentTokens[4]);
                    currentOrder.setArea(new BigDecimal(currentTokens[5]));
                    currentOrder.setCostPerSquareFoot(new BigDecimal(currentTokens[6]));
                    currentOrder.setLaborCostPerSquareFoot(new BigDecimal(currentTokens[7]));
                    currentOrder.setMaterialCost(new BigDecimal(currentTokens[8]));
                    currentOrder.setLaborCost(new BigDecimal(currentTokens[9]));
                    currentOrder.setTax(new BigDecimal(currentTokens[10]));
                    currentOrder.setTotal(new BigDecimal(currentTokens[11]));
                    currentOrder.setDate(getDateFromFile(newFile));

                    orders.put(currentOrder.getOrderNumber(), currentOrder);
                }

                scanner.close();
            }
        }
    }

    public String getDateFromFile(File file) throws OrderDaoPersistenceException {
        String fileName = file.getName();
        String[] currentTokens = fileName.split("_");
        String date = currentTokens[1];
        String months = date.substring(0, 2);
        String days = date.substring(2, 4);
        String years = date.substring(4, 8);
        String finalDate = (months + "/" + days + "/" + years);

        return finalDate;
    }

    @Override
    public void writeOrder() throws OrderDaoPersistenceException {

        PrintWriter out;

        try {
            out = new PrintWriter(new FileWriter(LIBRARY_FILE));
        } catch (IOException e) {
            throw new OrderDaoPersistenceException(
                    "Could not save data.", e);
        }

        List<Order> OrderList = this.getAllOrders();
        for (Order currentOrder : OrderList) {

            out.println(currentOrder.getOrderNumber() + DELIMITER
                    + currentOrder.getCustomerName() + DELIMITER
                    + currentOrder.getState() + DELIMITER
                    + currentOrder.getTaxRate() + DELIMITER
                    + currentOrder.getProductType() + DELIMITER
                    + currentOrder.getArea() + DELIMITER
                    + currentOrder.getCostPerSquareFoot() + DELIMITER
                    + currentOrder.getLaborCostPerSquareFoot() + DELIMITER
                    + currentOrder.getMaterialCost() + DELIMITER
                    + currentOrder.getLaborCost() + DELIMITER
                    + currentOrder.getTax() + DELIMITER
                    + currentOrder.getTotal());

            out.flush();
        }

        out.close();
    }

    private String getFileNameFromDate(String order) {
        String[] dateArray = order.split("/");
        String date = (dateArray[0] + dateArray[1] + dateArray[2]);
        String fileName = (this.folder + "/Orders_" + date + ".txt");
        return fileName;
    }

    private List<String> setListOfAllDates() throws OrderDaoPersistenceException {
        List<String> dates = new ArrayList<>();
        List<Order> sortedOrders = getAllOrders();
        for (Order currentOrder : sortedOrders) {
            String date = currentOrder.getDate();
            dates.add(date);
        }

        dates = dates.stream().distinct().collect(Collectors.toList());
        return dates;
    }

    private void writeListOfOrders(List<Order> OrderList, String currentDate) throws OrderDaoPersistenceException {

        PrintWriter out;
        try {
            out = new PrintWriter(new FileWriter(currentDate));
        } catch (IOException e) {
            throw new OrderDaoPersistenceException(
                    "Could not save data.", e);
        }
        for (Order currentOrder : OrderList) {

            out.println(currentOrder.getOrderNumber() + DELIMITER
                    + currentOrder.getCustomerName() + DELIMITER
                    + currentOrder.getState() + DELIMITER
                    + currentOrder.getTaxRate() + DELIMITER
                    + currentOrder.getProductType() + DELIMITER
                    + currentOrder.getArea() + DELIMITER
                    + currentOrder.getCostPerSquareFoot() + DELIMITER
                    + currentOrder.getLaborCostPerSquareFoot() + DELIMITER
                    + currentOrder.getMaterialCost() + DELIMITER
                    + currentOrder.getLaborCost() + DELIMITER
                    + currentOrder.getTax() + DELIMITER
                    + currentOrder.getTotal());

            out.flush();
        }

        out.close();
    }

    @Override
    public void writeAllOrdersToCorrectFile() throws OrderDaoPersistenceException, ProdPersistenceException {
        if (isProductionMode()) {
            List<String> dates = setListOfAllDates();
            for (String currentDate : dates) {
                writeListOfOrders(getOrdersByDate(currentDate), getFileNameFromDate(currentDate));
            }
        } else {
            throw new ProdPersistenceException("Cant save put me in prod pls");
        }
    }

    public boolean isProductionMode() throws OrderDaoPersistenceException {
        final String prod = ("prod");
        final String train = ("train");
        Scanner scanner;
        try {

            scanner = new Scanner(
                    new BufferedReader(
                            new FileReader("Flooring/Mode/modeSwitch.txt")));
        } catch (FileNotFoundException e) {
            throw new OrderDaoPersistenceException(
                    "-_- Could not load roster data into memory.", e);
        }
        String currentLine;
        currentLine = scanner.nextLine();
        if (currentLine.equalsIgnoreCase(prod)) {
            return true;
        } else {
            return false;
        }
    }

}
