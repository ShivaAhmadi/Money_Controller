package com.example.pasargad.moneycontrol;

public class TransactionModel {

    public String title, details;

    public boolean isIncome = false;

    public long submittedAt, date;

    public int price;

    public TransactionModel(String title, String details, boolean isIncome, long submittedAt, long date, int price) {
        this.title = title;
        this.details = details;
        this.isIncome = isIncome;
        this.submittedAt = submittedAt;
        this.date = date;
        this.price = price;
    }
}
