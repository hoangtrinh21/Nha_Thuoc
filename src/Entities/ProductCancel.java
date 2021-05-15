package Entities;

import java.sql.Date;

public class ProductCancel {
    private int IDCancel;
    private int amountCancel;
    private Date dateCancel;

    public ProductCancel(int IDCancel, int amountCancel, Date dateCancel) {
        this.IDCancel = IDCancel;
        this.amountCancel = amountCancel;
        this.dateCancel = dateCancel;
    }

    public void setIDCancel(int IDCancel) {
        this.IDCancel = IDCancel;
    }

    public int getIDCancel() {
        return IDCancel;
    }

    public void setAmountCancel(int amountCancel) {
        this.amountCancel = amountCancel;
    }

    public int getAmountCancel() {
        return amountCancel;
    }

    public void setDateCancel(Date dateCancel) {
        this.dateCancel = dateCancel;
    }

    public Date getDateCancel() {
        return dateCancel;
    }
}
