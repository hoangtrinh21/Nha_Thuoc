package Models;

public class TableShowProductSells {
     private String ten;
        private int MaSP;
        private String chucNang;
        private int giaThanh;


        public TableShowProductSells() {}

        public TableShowProductSells(String ten, int MaSP, String chucNang, int giaThanh) {
            this.ten = ten;
            this.MaSP = MaSP;
            this.chucNang = chucNang;
            this.giaThanh = giaThanh;
        }

        public String getTen() {
            return ten;
        }
        public int getMaSP() {
            return MaSP;
        }
        public String getChucNang() {
            return chucNang;
        }
        public int getGiaThanh() {
            return giaThanh;
        }

}
