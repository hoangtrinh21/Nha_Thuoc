package Cortroller;

import Entities.Lists;
import Entities.Product;
import Entities.ProductImport;

import java.time.LocalDate;
import java.util.Scanner;

public class Imports {
    private void printf(String a) {
        System.out.print(a);
    }

    public void importProduct(Lists lists) {  //Nhap san pham moi
        Scanner scanner = new Scanner(System.in);
        printf("Ma san pham: ");
        int ID = scanner.nextInt();
        scanner.nextLine();
        printf("Ten san pham: ");
        String name = scanner.nextLine();
        printf("Nhom san pham: ");
        String group = scanner.nextLine();
        printf("chuc nagn: ");
        String function = scanner.nextLine();
        printf("gia nhap: ");
        int priceImport = scanner.nextInt();
        printf("gia ban: ");
        int priceSell = scanner.nextInt();
        scanner.nextLine();
        printf("Luu y: ");
        String note = scanner.nextLine();
        printf("so luong nhap: ");
        int amountImport = scanner.nextInt();
        printf("Han su dung(ngay, thang, nam: ");
        int ngay = scanner.nextInt();
        int thang = scanner.nextInt();
        int nam = scanner.nextInt();
        LocalDate expiry = LocalDate.of(nam, thang, ngay);
        ProductImport productImport = new ProductImport(ID, amountImport, LocalDate.now());
        lists.productImportList.add(productImport);
        Product product = new Product(ID, name, group, function, amountImport, priceImport, priceSell, note, expiry);
        lists.productList.add(product);
    }

    public void updateProduct(Lists lists, Product product) {  //cap nhat thong tin san pham

    }

    public void show(Lists lists) {
        System.out.println("------------------------------------------------");
        System.out.println("No | ID| Name | nhom | Chuc nang | So luong | gia nhap | gia ban | luu y | han su dung");
        for (int i = 0; i < lists.productList.size(); i++) {
            System.out.printf("%-3s| %-10s| %-10s| %-10s| %-10s| %-10s| %-10s| %-10s| %-10s1| %s%n",
                    i + 1,
                    lists.productList.get(i).getID(),
                    lists.productList.get(i).getName(),
                    lists.productList.get(i).getGroup(),
                    lists.productList.get(i).getFunction(),
                    lists.productList.get(i).getAmount(),
                    lists.productList.get(i).getPriceImport(),
                    lists.productList.get(i).getPriceSell(),
                    lists.productList.get(i).getNote(),
                    lists.productList.get(i).getExpiryDate());
        }
        System.out.println("------------------------------------------------");
        System.out.println("No | ID | So luong nhap | han su dung");
        for (int i = 0; i < lists.productList.size(); i++) {
            System.out.printf("%-3s| %-10s| %-10s| %s%n",
                    i + 1,
                    lists.productImportList.get(i).getIDImport(),
                    lists.productImportList.get(i).getAmountImport(),
                    lists.productImportList.get(i).getDateImport());
        }
    }
}
