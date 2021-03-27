
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class HieuThuoc {
    public static List<Thuoc> Media = new ArrayList<Thuoc>();
    public static List<SanPhamBan> sanPhamBans = new ArrayList<SanPhamBan>();
    public static List<SanPhamHuy> sanPhamHuys = new ArrayList<SanPhamHuy>();

    public HieuThuoc() throws IOException {
        Nhap nhap = new Nhap();
        nhap.Nhap(this);
    }
}
