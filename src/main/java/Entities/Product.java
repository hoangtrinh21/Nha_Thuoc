package Entities;

import java.time.LocalDate;

public class Product {
    private int ID;
    private String name;
    private String group;
    private String function;
    private int amount;
    private int priceImport;
    private int priceSell;
    private String note;
    private LocalDate expiryDate;

    public Product(int ID, String name, String group, String function, int amount,
                   int priceImport, int priceSell, String note, LocalDate expiryDate) {
        this.ID = ID;
        this.name = name;
        this.group = group;
        this.function = function;
        this.amount = amount;
        this.priceImport = priceImport;
        this.priceSell = priceSell;
        this.note = note;
        this.expiryDate = expiryDate;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getID() {
        return ID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setFunction(String function) {
        this.function = function;
    }

    public String getFunction() {
        return function;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getGroup() {
        return group;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getAmount() {
        return amount;
    }

    public void setPriceSell(int priceSell) {
        this.priceSell = priceSell;
    }

    public int getPriceSell() {
        return priceSell;
    }

    public void setPriceImport(int priceImport) {
        this.priceImport = priceImport;
    }

    public int getPriceImport() {
        return priceImport;
    }

    public void setExpiryDate(LocalDate expiryDate) {
        this.expiryDate = expiryDate;
    }

    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getNote() {
        return note;
    }
}
