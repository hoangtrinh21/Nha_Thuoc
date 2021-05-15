package Entities;

import java.sql.Date;

public class ProductSell {
    private int IDSell;
    private int amountSell;
    private Date dateSell;

    public ProductSell(int IDSell, int amountSell, Date dateSell) {
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

    public void setDateSell(Date dateSell) {
        this.dateSell = dateSell;
    }

    public Date getDateSell() {
        return dateSell;
    }

    public void setAmountSell(int amountSell) {
        this.amountSell = amountSell;
    }

    public int getAmountSell() {
        return amountSell;
    }
}