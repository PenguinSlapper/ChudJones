/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.dao;

import com.sg.flooringmastery.dto.Tax;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 *
 * @author nick9
 */
public class TaxDaoImpl implements TaxDao {

    private Map<String, Tax> taxes = new HashMap<>();
    public static final String LIBRARY_FILE = "Flooring/Data/Taxes.txt";
    public static final String DELIMITER = ",";

    @Override
    public Tax addTax(String state, Tax tax) {
        Tax newTax = taxes.put(state, tax);
        return newTax;
    }

    @Override
    public List<Tax> getAllTaxes() {
        return new ArrayList<>(taxes.values());
    }

    @Override
    public Tax getTax(String state) {
        Tax tax = taxes.get(state);
        return tax;
    }

    @Override
    public Tax removeTax(String state) {
        Tax removedTax = taxes.remove(state);
        return removedTax;
    }

    @Override
    public void editTax(String state, Tax taxToEdit) {
        if (state == taxToEdit.getState()) {
            this.taxes.replace(state, taxToEdit);
        } else {
            Tax oldTax = this.taxes.remove(state);
            this.taxes.put(taxToEdit.getState(), taxToEdit);
        }
    }

    @Override
    public void loadTax() throws TaxDaoException {
        Scanner scanner;

        try {

            scanner = new Scanner(
                    new BufferedReader(
                            new FileReader(LIBRARY_FILE)));
        } catch (FileNotFoundException e) {
            throw new TaxDaoException(
                    "-_- Could not load roster data into memory.", e);
        }

        String currentLine;

        String[] currentTokens;

        while (scanner.hasNextLine()) {

            currentLine = scanner.nextLine();

            currentTokens = currentLine.split(DELIMITER);

            Tax currentTax = new Tax((currentTokens[0]));

            currentTax.setTaxRate(new BigDecimal(currentTokens[1]));

            taxes.put(currentTax.getState(), currentTax);
        }

        scanner.close();
    }

    @Override
    public void writeTax() throws TaxDaoException {

        PrintWriter out;

        try {
            out = new PrintWriter(new FileWriter(LIBRARY_FILE));
        } catch (IOException e) {
            throw new TaxDaoException(
                    "Could not save student data.", e);
        }

        List<Tax> TaxList = this.getAllTaxes();
        for (Tax currentTax : TaxList) {

            out.println(currentTax.getState() + DELIMITER
                    + currentTax.getTaxRate());

            out.flush();
        }

        out.close();
    }

}
