package Entities;

import java.time.LocalDate;

public class ProductImport {
    private int IDImport;
    private int amountImport;
    private LocalDate dateImport;

    public ProductImport(int IDImport, int amountImport, LocalDate dateImport) {
        this.IDImport = IDImport;
        this.amountImport = amountImport;
        this.dateImport = dateImport;
    }

    public void setDateImport(LocalDate dateImport) {
        this.dateImport = dateImport;
    }

    public LocalDate getDateImport() {
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
