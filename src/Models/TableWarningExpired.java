package Models;

import java.sql.Date;

public class TableWarningExpired {
    private String name;
    private int id;
    private int amount;
    private int price;
    private Date expiry;

    public TableWarningExpired(int id, String name, int amount, int price, Date expiry) {
        this.id = id;
        this.amount = amount;
        this.expiry = expiry;
        this.name = name;
        this.price = price;
    }

    public TableWarningExpired() {}

    public Date getExpiry() {
        return expiry;
    }

    public int getAmount() {
        return amount;
    }

    public int getId() {
        return id;
    }

    public int getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void setExpiry(Date expiry) {
        this.expiry = expiry;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
