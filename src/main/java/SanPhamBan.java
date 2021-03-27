public class SanPhamBan {
    private String MaSanPham;
    private String NgayBan;
    private int SoLuong;

    public SanPhamBan(String maSanPham, String ngayBan, int soLuong) {
        MaSanPham = maSanPham;
        NgayBan = ngayBan;
        SoLuong = soLuong;
    }

    public SanPhamBan(){

    }

    public String getMaSanPham() {
        return MaSanPham;
    }

    public void setMaSanPham(String maSanPham) {
        MaSanPham = maSanPham;
    }

    public String getNgayBan() {
        return NgayBan;
    }

    public void setNgayBan(String ngayBan) {
        NgayBan = ngayBan;
    }

    public int getSoLuong() {
        return SoLuong;
    }

    public void setSoLuong(int soLuong) {
        SoLuong = soLuong;
    }

}
