package Entities;

import java.time.LocalDate;

public class ProductCancel {
    private int IDCancel;
    private int amountCancel;
    private LocalDate dateCancel;

    public ProductCancel(int IDCancel, int amountCancel, LocalDate dateCancel) {
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

    public void setDateCancel(LocalDate dateCancel) {
        this.dateCancel = dateCancel;
    }

    public LocalDate getDateCancel() {
        return dateCancel;
    }
}
