package Entities;

import java.sql.Date;

public class ProductImport {
    private int IDImport;
    private int amountImport;
    private Date dateImport;

    public ProductImport(int IDImport, int amountImport, Date dateImport) {
        this.IDImport = IDImport;
        this.amountImport = amountImport;
        this.dateImport = dateImport;
    }

    public void setDateImport(Date dateImport) {
        this.dateImport = dateImport;
    }

    public Date getDateImport() {
        return dateImport;
    }

    public void setIDImport(int IDImport) {
        this.IDImport = IDImport;
    }

    public int getIDImport() {
        return IDImport;
    }

    public void setAmountImport(int amountImport) {
        this.amountImport = amountImport;
    }

    public int getAmountImport() {
        return amountImport;
    }
}
