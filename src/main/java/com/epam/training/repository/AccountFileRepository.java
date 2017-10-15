package com.epam.training.repository;

import com.epam.training.repository.model.Account;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;


public class AccountFileRepository implements AccountRepository {
    private static final String fileName = "Account.db";
    private static final String fileNameTemp = "AccountTemp";

    public AccountFileRepository() {
    }

    @Override
    public Account save(Account account) {
        File fileDB = new File(fileName);
        File fileDB1 = new File(fileNameTemp);
        try (BufferedReader reader = new BufferedReader(new FileReader(fileDB));
             BufferedWriter writer = new BufferedWriter(new FileWriter(fileDB1))) {
            while (true) {
                String accountLine = reader.readLine();
                if (accountLine == null || accountLine.length() == 0) {
                    break;
                }
                String[] accountInfo = accountLine.split(",");
                Integer pinCodeDB = Integer.valueOf(accountInfo[0]);
                if (account.getPinCode().equals(pinCodeDB)) {
                    writer.write(account.toString());
                } else writer.write(accountLine);
                writer.newLine();
            }
        } catch (IOException e) {
            throw new RuntimeException("DateBase is unavailable", e);
        }
        try {
            Files.copy(fileDB1.toPath(), fileDB.toPath(), StandardCopyOption.REPLACE_EXISTING);
            Files.deleteIfExists(fileDB1.toPath());
        } catch (IOException e) {
            throw new RuntimeException("DateBase is unavailable", e);
        }
        return account;
    }

    @Override
    public Account get(Integer pinCode) {
        File fileDB = new File(fileName);
        try (BufferedReader reader = new BufferedReader(new FileReader(fileDB))) {
            while (true) {
                String accountLine = reader.readLine();
                if (accountLine == null || accountLine.length() == 0) {
                    break;
                }
                String[] accountInfo = accountLine.split(",");
                Integer pinCodeDB = Integer.valueOf(accountInfo[0]);
                if (pinCode.equals(pinCodeDB)) {
                    Account account = new Account(pinCodeDB, Integer.valueOf(accountInfo[1]));
                    return account;
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("DateBase is unavailable", e);
        }
        return null;
    }
}

