package Models;

public class TableProductSells {
    private String ten;
    private int soLuong;
    private int thanhTien;
    private int id;
        public TableProductSells(String ten, int soLuong, int thanhTien, int id) {
            this.ten = ten;
            this.soLuong = soLuong;
            this.thanhTien = thanhTien;
            this.id = id;
        }
        public String getTen() {
            return ten;
        }
        public int getSoLuong() {
            return soLuong;
        }
        public int getThanhTien() {
            return thanhTien;
        }
        public int getId() {
            return id;
        }


    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public void setThanhTien(int thanhTien) {
        this.thanhTien = thanhTien;
    }

    public void setId(int id) {
        this.id = id;
    }


}
