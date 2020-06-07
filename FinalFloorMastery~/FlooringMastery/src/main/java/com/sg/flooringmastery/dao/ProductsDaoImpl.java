/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.dao;

import com.sg.flooringmastery.dto.Product;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 *
 * @author nick9
 */
public class ProductsDaoImpl implements ProductsDao {

    private Map<String, Product> products = new HashMap<>();
    public static final String LIBRARY_FILE = "Flooring/Data/Products.txt";
    public static final String DELIMITER = ",";

    @Override
    public Product addProduct(String productType, Product product) throws ProductDaoException {
        Product newProduct = products.put(productType, product);
        writeProduct();
        return newProduct;
    }

    @Override
    public List<Product> getAllProducts() throws ProductDaoException {
        loadProduct();
        return new ArrayList<>(products.values());
    }

    @Override
    public Product getProduct(String productType) throws ProductDaoException {
        Product product = products.get(productType);
        writeProduct();
        return product;
    }

    @Override
    public Product removeProduct(String productType) throws ProductDaoException {
        Product removedProduct = products.remove(productType);
        writeProduct();
        return removedProduct;
    }

    @Override
    public void editProduct(String oldProductType, Product productToEdit) throws ProductDaoException {
        if (oldProductType == productToEdit.getProductType()) {
            this.products.replace(oldProductType, productToEdit);
        } else {
            Product oldProduct = this.products.remove(oldProductType);
            this.products.put(productToEdit.getProductType(), productToEdit);
        }
        this.writeProduct();
    }

    @Override
    public void loadProduct() throws ProductDaoException {
        Scanner scanner;

        try {

            scanner = new Scanner(
                    new BufferedReader(
                            new FileReader(LIBRARY_FILE)));
        } catch (FileNotFoundException e) {
            throw new ProductDaoException(
                    "-_- Could not load product data into memory.", e);
        }

        String currentLine;

        String[] currentTokens;

        while (scanner.hasNextLine()) {

            currentLine = scanner.nextLine();

            currentTokens = currentLine.split(DELIMITER);

            Product currentProduct = new Product((currentTokens[0]));

            BigDecimal costPerSquareFoot = (new BigDecimal(currentTokens[1]));
            currentProduct.setCostPerSquareFoot(costPerSquareFoot);
            BigDecimal setLaborCostPerFoot = new BigDecimal(currentTokens[2]);
            currentProduct.setLaborCostPerSquareFoot(setLaborCostPerFoot);

            products.put(currentProduct.getProductType(), currentProduct);
        }

        scanner.close();
    }

    @Override
    public void writeProduct() throws ProductDaoException {

        PrintWriter out;

        try {
            out = new PrintWriter(new FileWriter(LIBRARY_FILE));
        } catch (IOException e) {
            throw new ProductDaoException(
                    "Could not save data.", e);
        }

        List<Product> ProductList = this.getAllProducts();
        for (Product currentProduct : ProductList) {

            out.println(currentProduct.getProductType() + DELIMITER
                    + currentProduct.getCostPerSquareFoot() + DELIMITER
                    + currentProduct.getLaborCostPerSquareFoot());

            out.flush();
        }

        out.close();
    }
}
