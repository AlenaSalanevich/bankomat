package com.epam.training.service;

import java.io.IOException;

public interface CashService {
    void getCash(Integer pinCode) throws IOException;

    void putCash(Integer pinCode);

    boolean accountExists(Integer pinCode);

    Integer chooseOperation() throws IOException;

    void getBalance(Integer pinCode);
}

