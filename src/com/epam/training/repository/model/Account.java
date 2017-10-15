package com.epam.training.repository.model;

public class Account {

    private Integer accountMoney;

    private Integer pinCode;

    public Account(Integer pinCodeDB, Integer accountMoney) {
        this.pinCode = pinCodeDB;
        this.accountMoney = accountMoney;
    }

    @Override
    public String toString() {
        return pinCode + "," + accountMoney;
    }

    public Integer getAccountMoney() {
        return accountMoney;
    }

    public void setAccountMoney(Integer accountMoney) {
        this.accountMoney = accountMoney;
    }

    public Integer getPinCode() {
        return pinCode;
    }

    public void setPinCode(Integer pinCode) {
        this.pinCode = pinCode;
    }


}
