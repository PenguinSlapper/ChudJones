/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.ui;

import java.util.Scanner;

/**
 *
 * @author nick9
 */
public class UserIOImpl implements UserIO {

    final private Scanner console = new Scanner(System.in);

    @Override
    public void print(String msg
    ) {
        System.out.println(msg);
    }

    @Override
    public String readString(String msgPrompt
    ) {
        System.out.println(msgPrompt);
        return console.nextLine();
    }

    @Override
    public int readInt(String msgPrompt
    ) {
        boolean invalidInput = true;
        int num = 0;
        while (invalidInput) {
            try {

                String stringValue = this.readString(msgPrompt);

                num = Integer.parseInt(stringValue);
                invalidInput = false;
            } catch (NumberFormatException e) {

                System.out.println("You can't do that Dave...!");
            }
        }
        return num;
    }

    @Override
    public int readInt(String msgPrompt, int min, int max
    ) {
        int result;
        do {
            result = readInt(msgPrompt);
        } while (result < min || result > max);

        return result;
    }

    @Override
    public long readLong(String msgPrompt
    ) {
        while (true) {
            try {
                return Long.parseLong(this.readString(msgPrompt));
            } catch (NumberFormatException e) {
                System.out.println("You can't do that Dave...!");
            }
        }
    }

    @Override
    public long readLong(String msgPrompt, long min, long max
    ) {
        long result;
        do {
            result = readLong(msgPrompt);
        } while (result < min || result > max);

        return result;
    }

    @Override
    public float readFloat(String msgPrompt
    ) {
        while (true) {
            try {
                return Float.parseFloat(this.readString(msgPrompt));
            } catch (NumberFormatException e) {
                System.out.println("You can't do that Dave...!");
            }
        }
    }

    @Override
    public float readFloat(String msgPrompt, float min, float max
    ) {
        float result;
        do {
            result = readFloat(msgPrompt);
        } while (result < min || result > max);

        return result;
    }

    @Override
    public double readDouble(String msgPrompt
    ) {
        while (true) {
            try {
                return Double.parseDouble(this.readString(msgPrompt));
            } catch (NumberFormatException e) {
                System.out.println("You can't do that Dave...!");
            }
        }
    }

    @Override
    public double readDouble(String msgPrompt, double min, double max
    ) {
        double result;
        do {
            result = readDouble(msgPrompt);
        } while (result < min || result > max);
        return result;
    }
}
