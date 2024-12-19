package com.example.repository;

import com.example.model.Account;

import java.util.HashMap;
import java.util.Map;

public class AccountRepository {

    private static final Map<Integer, Account> DB = new HashMap<>();

    static {
        DB.put(1, new Account(1, 1500));
        DB.put(2, new Account(2, 2000));
        DB.put(3, new Account(3, 3000));
        DB.put(4, new Account(4, 5000));
        DB.put(5, new Account(5, 3200));
    }

    public Account getAccount(int id) {
        if (DB.containsKey(id)) {
            return DB.get(id);
        }
        throw new IllegalArgumentException("No such account");
    }

    public void saveAccount(Account account) {
        DB.put(account.getId(), account);
    }

    public void transferAccount(Account accountFrom, Account accountTo, int amount) {
        accountFrom.setAmount(accountFrom.getAmount() - amount);
        accountTo.setAmount(accountTo.getAmount() + amount);
        saveAccount(accountFrom);
        saveAccount(accountTo);
    }

}
