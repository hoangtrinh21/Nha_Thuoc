public class SanPhamHuy {
    private String MaSanPham;
    private String Han_Su_Dung;
    private int SoLuong;

    public SanPhamHuy(String maSanPham, String han_SuDung, int soLuong) {
        MaSanPham = maSanPham;
        Han_Su_Dung = han_SuDung;
        SoLuong = soLuong;
    }

    public SanPhamHuy() {

    }

    public String getMaSanPham() {
        return MaSanPham;
    }

    public void setMaSanPham(String maSanPham) {
        MaSanPham = maSanPham;
    }

    public String getHan_SuDung() {
        return Han_Su_Dung;
    }

    public void setHan_SuDung(String han_SuDung) {
        Han_Su_Dung = han_SuDung;
    }

    public int getSoLuong() {
        return SoLuong;
    }

    public void setSoLuong(int soLuong) {
        SoLuong = soLuong;
    }
}
