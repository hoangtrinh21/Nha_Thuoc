package Entities;

import java.sql.Date;

public class Product {
    private int ID;
    private String name;
    private String group;
    private String function;
    private int amount;
    private int priceImport;
    private int priceSell;
    private String note;
    private Date expiryDate;
    private String img;

    public Product() {

    }

    public Product(int ID, String name, String group, String function, int amount, int priceImport, int priceSell, String note, Date expiryDate, String img) {
        this.ID = ID;
        this.name = name;
        this.group = group;
        this.function = function;
        this.amount = amount;
        this.priceImport = priceImport;
        this.priceSell = priceSell;
        this.note = note;
        this.expiryDate = expiryDate;
        this.img = img;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getFunction() {
        return function;
    }

    public void setFunction(String function) {
        this.function = function;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getPriceImport() {
        return priceImport;
    }

    public void setPriceImport(int priceImport) {
        this.priceImport = priceImport;
    }

    public int getPriceSell() {
        return priceSell;
    }

    public void setPriceSell(int priceSell) {
        this.priceSell = priceSell;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}