package com.epam.training.repository;

import com.epam.training.repository.model.Account;

import java.io.FileNotFoundException;

public interface AccountRepository {

    Account save (Account account);
    Account get (Integer pinCode);
    }
