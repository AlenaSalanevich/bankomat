package com.epam.training;

import com.epam.training.repository.AccountFileRepository;
import com.epam.training.service.CashServiceImplementation;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Bankomat {
    public static void main(String[] arg) throws IOException, InputMismatchException, NullPointerException {
        CashServiceImplementation cashServiceImplementation = new CashServiceImplementation(new AccountFileRepository());
        System.out.println("Please, enter your pinCode!");
        Scanner scanner = new Scanner(System.in);
        Integer pinCode = scanner.nextInt();
        Integer operation;
        boolean accountExists = cashServiceImplementation.accountExists(pinCode);
        if (accountExists) {
            operation = cashServiceImplementation.chooseOperation();
        } else {
            operation = 4;
        }
        while (operation >= 1 && operation <= 4) {
            switch (operation) {
                case 1: {
                    cashServiceImplementation.putCash(pinCode);
                    operation = cashServiceImplementation.chooseOperation();
                    break;
                }
                case 2: {
                    cashServiceImplementation.getCash(pinCode);
                    operation = cashServiceImplementation.chooseOperation();
                    break;
                }
                case 3:
                    cashServiceImplementation.getBalance(pinCode);
                    operation = cashServiceImplementation.chooseOperation();
                    break;
                case 4:
                    System.exit(0);
            }
        }
    }
}






