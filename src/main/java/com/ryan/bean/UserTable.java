package com.ryan.bean;

/**
 * @description:
 * @author: Bubble
 * @create: 2022-04-19 10:43 上午
 */
public class UserTable {
    String user;
    int balance;

    public UserTable(String user, int balance) {
        this.user = user;
        this.balance = balance;
    }

    public UserTable() {
    }

    @Override
    public String toString() {
        return "UserTable{" +
                "user='" + user + '\'' +
                ", balance=" + balance +
                '}';
    }

    public String getuser() {
        return user;
    }

    public void setuser(String user) {
        this.user = user;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }
}
