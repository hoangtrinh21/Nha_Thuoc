import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Scanner;

public class Nhap {

    public void Nhap(HieuThuoc hieuThuoc) throws IOException {
        NhapTuFileThuoc(hieuThuoc);
        NhapTuFileBan(hieuThuoc);
        NhapTuFileHuy(hieuThuoc);
    }

    public void NhapTuFileThuoc(HieuThuoc hieuThuoc) throws IOException {
        Scanner scanner = new Scanner(Paths.get("src/main/java/Thuoc.txt"));

        while (scanner.hasNextLine()) {
            String MaSanPham = scanner.nextLine();
            String TenSanPham = scanner.nextLine();
            String ChucNang = scanner.nextLine();
            String HanSuDung = scanner.nextLine();
            int GiaBan = scanner.nextInt();
            int GiaMua = scanner.nextInt();
            int SoLuong= scanner.nextInt();
            scanner.nextLine();
            String LuuY = scanner.nextLine();
            String NhomSanPham = scanner.nextLine();
            Thuoc thuoc = new Thuoc(MaSanPham, TenSanPham, ChucNang, HanSuDung, GiaBan, GiaMua, SoLuong, LuuY, NhomSanPham);
            hieuThuoc.Media.add(thuoc);
        }
    }

    public void NhapTuFileBan(HieuThuoc danhSachBan) throws IOException {
        Scanner scanner = new Scanner(Paths.get("src/main/java/ThuocBan.txt"));

        while (scanner.hasNextLine()) {
            String MaSanPham =  scanner.nextLine();
            String NgayBan = scanner.nextLine();
            int SoLuong = scanner.nextInt();
            SanPhamBan sanPhamBan = new SanPhamBan(MaSanPham, NgayBan, SoLuong);
            danhSachBan.sanPhamBans.add(sanPhamBan);
        }
    }

    public void NhapTuFileHuy(HieuThuoc danhSachBan) throws IOException {
        Scanner scanner = new Scanner(Paths.get("src/main/java/ThuocHuy.txt"));

        while (scanner.hasNextLine()) {
            String MaSanPham =  scanner.nextLine();
            String HanSuDung = scanner.nextLine();
            int SoLuong = scanner.nextInt();
            SanPhamHuy sanPhamHuy = new SanPhamHuy(MaSanPham, HanSuDung, SoLuong);
            danhSachBan.sanPhamHuys.add(sanPhamHuy);
        }
    }

}
