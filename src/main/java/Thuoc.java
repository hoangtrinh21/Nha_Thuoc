public class Thuoc {
    private String MaSanPham;
    private String TenSanPham;
    private String ChucNang;
    private String HanSuDung;
    private int GiaBan;
    private int GiaMua;
    private int SoLuong;
    private String LuuY;
    private String NhomSanPham;

    public Thuoc(){
        MaSanPham = TenSanPham = ChucNang = HanSuDung = LuuY = NhomSanPham = "";
        GiaBan = GiaMua = SoLuong = 0;
    }

    public Thuoc(String maSanPham, String tenSanPham, String chucNang, String hanSuDung, int giaBan, int giaMua, int soLuong, String luuY, String nhomSanPham) {
        MaSanPham = maSanPham;
        TenSanPham = tenSanPham;
        ChucNang = chucNang;
        HanSuDung = hanSuDung;
        GiaBan = giaBan;
        GiaMua = giaMua;
        SoLuong = soLuong;
        LuuY = luuY;
        NhomSanPham = nhomSanPham;
    }

    public int getLoiNhuan(){
        return this.GiaBan - this.GiaMua;
    }


    public String getMaSanPham() {
        return MaSanPham;
    }

    public void setMaSanPham(String maSanPham) {
        MaSanPham = maSanPham;
    }

    public String getTenSanPham() {
        return TenSanPham;
    }

    public void setTenSanPham(String tenSanPham) {
        TenSanPham = tenSanPham;
    }

    public String getChucNang() {
        return ChucNang;
    }

    public void setChucNang(String chucNang) {
        ChucNang = chucNang;
    }

    public String getHanSuDung() {
        return HanSuDung;
    }

    public void setHanSuDung(String hanSuDung) {
        HanSuDung = hanSuDung;
    }

    public int getGiaBan() {
        return GiaBan;
    }

    public void setGiaBan(int giaBan) {
        GiaBan = giaBan;
    }

    public int getGiaMua() {
        return GiaMua;
    }

    public void setGiaMua(int giaMua) {
        GiaMua = giaMua;
    }

    public int getSoLuong() {
        return SoLuong;
    }

    public void setSoLuong(int soLuong) {
        SoLuong = soLuong;
    }

    public String getLuuY() {
        return LuuY;
    }

    public void setLuuY(String luuY) {
        LuuY = luuY;
    }

    public String getNhomSanPham() {
        return NhomSanPham;
    }

    public void setNhomSanPham(String nhomSanPham) {
        NhomSanPham = nhomSanPham;
    }

    @Override
    public String toString() {
        return "Thuoc{" +
                "MaSanPham='" + MaSanPham + '\'' +
                ", TenSanPham='" + TenSanPham + '\'' +
                ", ChucNang='" + ChucNang + '\'' +
                ", HanSuDung='" + HanSuDung + '\'' +
                ", GiaBan=" + GiaBan +
                ", GiaMua=" + GiaMua +
                ", SoLuong=" + SoLuong +
                ", LuuY='" + LuuY + '\'' +
                ", NhomSanPham='" + NhomSanPham + '\'' +
                '}';
    }
}
