package Entities;

import java.time.LocalDate;

public class ProductSell {
    private int IDSell;
    private int amountSell;
    private LocalDate dateSell;

    public ProductSell(int IDSell, int amountSell, LocalDate dateSell) {
        this.IDSell = IDSell;
        this.amountSell = amountSell;
        this.dateSell = dateSell;
    }

    public void setIDSell(int IDSell) {
        this.IDSell = IDSell;
    }

    public int getIDSell() {
        return IDSell;
    }

    public void setDateSell(LocalDate dateSell) {
        this.dateSell = dateSell;
    }

    public LocalDate getDateSell() {
        return dateSell;
    }

    public void setAmountSell(int amountSell) {
        this.amountSell = amountSell;
    }

    public int getAmountSell() {
        return amountSell;
    }
}
