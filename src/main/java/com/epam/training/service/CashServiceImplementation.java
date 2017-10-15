package com.epam.training.service;


import com.epam.training.repository.AccountRepository;
import com.epam.training.repository.model.Account;


import javax.xml.ws.ServiceMode;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

@ServiceMode
public class CashServiceImplementation implements CashService {
    AccountRepository accountRepository;

    final HashMap<String, Integer> banknotes = new HashMap<>();

    public CashServiceImplementation(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
        banknotes.put("5", 5);
        banknotes.put("10", 10);
        banknotes.put("20", 20);
    }

    public AccountRepository getAccountRepository() {
        return accountRepository;
    }

    public void setAccountRepository(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

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
            account.setAccountMoney(account.getAccountMoney() - outputcash);
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
        System.out.println("Please, enter  your banknotes (value 5, 10 or 20): ");
        System.out.println("Warning!!! Another banknotes have been ignored!!!");
        Scanner inputcash = new Scanner(System.in);
        String getmoney = inputcash.nextLine();
        String[] inputMomey = getmoney.split("[\\p{P} \\t\\n\\r&&[^.]]+|\\.(?!\\S)");
        HashMap<String, Integer> inputMoneyMap = new HashMap<>();
        Integer summ = 0;
        for (String money : inputMomey) {
            String inmoney = money.trim();
            if (banknotes.keySet().contains(inmoney)) {
                summ += banknotes.get(inmoney);
            }
        }
        System.out.println("You put on your credit card: " + summ + " dollars!");
        Account account = accountRepository.get(pinCode);
        account.setAccountMoney(summ + account.getAccountMoney());
        accountRepository.save(account);
        System.out.println("You have on your credit card: " + account.getAccountMoney() + " dollars!");
    }

    @Override
    public Integer chooseOperation() throws IOException {
        Integer operation;
        System.out.println("Please, choose the operation: enter 1, if You want to put money" +
                "enter 2, if You want to get money, 3 - You want to see balance; Another number - if You want exit");
        Scanner in = new Scanner(System.in);
        operation = in.nextInt();
        return operation;
    }

    @Override
    public void getBalance(Integer pinCode) {
        System.out.println("Your balance " + accountRepository.get(pinCode).getAccountMoney() + " dollars");
    }

    @Override
    public boolean accountExists(Integer pin) {
        return accountRepository.get(pin) != null;
    }
}
