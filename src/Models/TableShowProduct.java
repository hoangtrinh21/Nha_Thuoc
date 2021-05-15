package Models;

import java.sql.Date;

public class TableShowProduct {
    private String name;
    private int id;
    private int price;
    private Date hsd;
    private int number;
    private String function;
    private String note;

    public TableShowProduct() {}

    public TableShowProduct(String name, int id, int price, Date hsd, int number, String function, String note) {
        this.name = name;
        this.id = id;
        this.price = price;
        this.hsd = hsd;
        this.number = number;
        this.function = function;
        this.note = note;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public int getPrice() {
        return price;
    }

    public int getNumber() {
        return number;
    }

    public Date getHsd() {
        return hsd;
    }

    public String getFunction() {
        return function;
    }

    public String getNote() {
        return note;
    }

    public void setFunction(String function) {
        this.function = function;
    }

    public void setHsd(Date hsd) {
        this.hsd = hsd;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
