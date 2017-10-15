package com.epam.training.service;

import com.epam.training.repository.AccountFileRepository;
import com.epam.training.repository.AccountRepository;
import com.epam.training.repository.model.Account;
import com.epam.training.service.CashService;

import javax.xml.ws.ServiceMode;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

@ServiceMode
public class CashServiceImplementation implements CashService {

    public CashServiceImplementation(AccountRepository accountRepository) {
        this.accountRepository=accountRepository;
    }

    public AccountRepository getAccountRepository() {
        return accountRepository;
    }

    public void setAccountRepository(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    AccountRepository accountRepository;

    final int JACKSON = 20;
    final int TENNER = 10;
    final int FIVER = 5;

    @Override
    public void getCash(Integer pinCode) throws IOException {
        System.out.println("You want to get money!");
        System.out.println("Please, enter amount of money, You want to get ");
        Scanner output = new Scanner(System.in);
        Integer outputcash = output.nextInt();
       Account account = accountRepository.get(pinCode);
        Integer allCash = account.getAccountMoney();

        if (outputcash % 5 != 0) {
            System.out.println("Min banknotes is  5 dollars!");
        } else if (outputcash <= allCash) {
         account.setAccountMoney(account.getAccountMoney()-outputcash);
         accountRepository.save(account);
            System.out.println("Please, give your money!");
            System.out.println("The amount of money on your credit card: " + account.getAccountMoney() + " dollars");
        } else {
            System.out.println("You don't have same money!");
            System.out.println("You have only " + account.getAccountMoney() + " dollars");
        }
    }

    @Override
    public void putCash(Integer pinCode) {
        Integer inCash, inCash10, inCash20, inCash5;
        System.out.println("Please, enter  your banknotes (value 5, 10 or 20): ");
        System.out.println("Warning!!! Another banknotes have been ignored!!!");
        Scanner inputcash = new Scanner(System.in);
        String getmoney = inputcash.next();
        String[] inputMomey = getmoney.split("[\\p{P} \\t\\n\\r&&[^.]]+|\\.(?!\\S)");  //[\p{P} \t\n\r&&[^.]]+|\.(?!\S)
        HashMap<String, Integer> inputMoneyMap = new HashMap<>();
        Integer num;
        for (String money : inputMomey) {
            String inmoney = money.trim();
            num = inputMoneyMap.get(inmoney);
            if (num == null) {
                inputMoneyMap.put(inmoney, 1);
            } else {
                inputMoneyMap.put(inmoney, num + 1);
            }
        }
        if (inputMoneyMap.containsKey("5"))
            inCash5 = inputMoneyMap.get("5") * FIVER;
        else
            inCash5 = 0;
        if (inputMoneyMap.containsKey("20"))
            inCash20 = inputMoneyMap.get("20") * JACKSON;
        else
            inCash20 = 0;
        if (inputMoneyMap.containsKey("10"))
            inCash10 = inputMoneyMap.get("10") * TENNER;
        else
            inCash10 = 0;
        inCash = inCash5 + inCash10 + inCash20;

        System.out.println("You put on your credit card: " + inCash + " dollars!");
       Account account = accountRepository.get(pinCode);
       account.setAccountMoney(inCash+account.getAccountMoney());
       accountRepository.save(account);
        System.out.println("You have on your credit card: "  + account.getAccountMoney()+" dollars!");
           }

    @Override
    public Integer chooseOperation() throws IOException {
        Integer operation;


            System.out.println("Please, choose the operation: enter 1, if You want to put money" +
                    "enter 2, if You want to get money, 3 - You want to see balance; 4 - if You want exit");
            Scanner in = new Scanner(System.in);
            operation = in.nextInt();


        return operation;
    }

    @Override
    public void getBalance(Integer pinCode) {
        System.out.println("Your balance " + accountRepository.get(pinCode).getAccountMoney() + " dollars");
    }

    public boolean accountExists(Integer pin){
        return accountRepository.get(pin)!=null;
    }


}
