package com.example.pasargad.moneycontrol;

public class CategoryModel {
    private String categoryId;
    private String categoryTitle;
    private String categoryDetiles;
    private String categoryPrice;
    private long categoryDate;
    private boolean categoryType;

    public CategoryModel(String categoryId, String categoryTitle, String categoryDetiles, String categoryPrice, long categoryDate, boolean categoryType) {
        this.categoryId = categoryId;
        this.categoryTitle = categoryTitle;
        this.categoryDetiles = categoryDetiles;
        this.categoryPrice = categoryPrice;
        this.categoryDate = categoryDate;
        this.categoryType = categoryType;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryTitle() {
        return categoryTitle;
    }

    public void setCategoryTitle(String categoryTitle) {
        this.categoryTitle = categoryTitle;
    }

    public String getCategoryDetiles() {
        return categoryDetiles;
    }

    public void setCategoryDetiles(String categoryDetiles) {
        this.categoryDetiles = categoryDetiles;
    }

    public String getCategoryPrice() {
        return categoryPrice;
    }

    public void setCategoryPrice(String categoryPrice) {
        this.categoryPrice = categoryPrice;
    }

    public long getCategoryDate() {
        return categoryDate;
    }

    public void setCategoryDate(long categoryDate) {
        this.categoryDate = categoryDate;
    }

    public boolean isCategoryType() {
        return categoryType;
    }

    public void setCategoryType(boolean categoryType) {
        this.categoryType = categoryType;
    }
}
