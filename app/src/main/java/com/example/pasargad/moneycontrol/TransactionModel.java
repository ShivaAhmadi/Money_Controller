package com.example.pasargad.moneycontrol;

public class TransactionModel {

    public String title, details;

    public TransactionModel(){

    }

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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public boolean isIncome() {
        return isIncome;
    }

    public void setIncome(boolean income) {
        isIncome = income;
    }

    public long getSubmittedAt() {
        return submittedAt;
    }

    public void setSubmittedAt(long submittedAt) {
        this.submittedAt = submittedAt;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
