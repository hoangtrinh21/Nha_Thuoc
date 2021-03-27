import java.io.IOException;

public class KiemThu {
    public static void main(String[] args) throws IOException {
        HieuThuoc hieuThuoc = new HieuThuoc();
        ThucHienXuatThongTin Thxtt = new ThucHienXuatThongTin();
        int doangSo = Thxtt.DoanhSo(hieuThuoc);
        System.out.println(doangSo);
    }
}
