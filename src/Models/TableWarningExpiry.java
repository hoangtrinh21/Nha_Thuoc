package Models;

import java.sql.Date;

public class TableWarningExpiry {
    private int id;
    private int amount;
    private String name;
    private int price;
    private Date expiry;

    public TableWarningExpiry() {}

    public TableWarningExpiry(int id, String name, int amount, int price, Date expiry) {
        this.id = id;
        this.name = name;
        this.amount = amount;
        this.price = price;
        this.expiry = expiry;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public int getAmount() {
        return amount;
    }

    public int getId() {
        return id;
    }

    public Date getExpiry() {
        return expiry;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setExpiry(Date expiry) {
        this.expiry = expiry;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
