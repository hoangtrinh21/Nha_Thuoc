public class ThucHienXuatThongTin {
    public ThucHienXuatThongTin(){

    }

    // xuat hang
    public Thuoc XuatThongTinSanPhamKhiDaDuocTimKiem(HieuThuoc hieuThuoc, String maThuocOrTenThuoc){
        Thuoc thuoc = new Thuoc();
        for(Thuoc i : hieuThuoc.Media){
            if(i.getMaSanPham().equals(maThuocOrTenThuoc) || i.getTenSanPham().equals(maThuocOrTenThuoc)){
                thuoc = i;
                break;
            }
        }
        return thuoc;
    }

    public String[] DanhSachDanhMuc(HieuThuoc hieuThuoc){
        String[] danhMuc = new String[50];
        int dem = 0;
        for(Thuoc i : hieuThuoc.Media){
            if(dem == 0){
                danhMuc[0] = i.getNhomSanPham();
                dem++;
            }
            else {
                boolean f = false;
                for(String j : danhMuc){
                    if(i.getNhomSanPham().equals(j)){
                        f = true;
                    }
                }
                if(f == false){
                    danhMuc[dem] = i.getNhomSanPham();
                    dem++;
                }
            }

        }
        return danhMuc;
    }

    public Thuoc[] XuatNhomThuoc(HieuThuoc hieuThuoc, String TenNhomThuoc){
        Thuoc[] nhom = null;
        int dem = 0;
        for(Thuoc i : hieuThuoc.Media){
            if(i.getNhomSanPham().equals(TenNhomThuoc)){
                nhom[dem] = i;
                dem++;
            }
        }
        return nhom;
    }

    //doanh so
    public int DoanhSo(HieuThuoc hieuThuoc){
        int TongTien = 0;
        for(SanPhamBan i : hieuThuoc.sanPhamBans){
            for(Thuoc j : hieuThuoc.Media){
                TongTien += i.getSoLuong() * XuatThongTinSanPhamKhiDaDuocTimKiem(hieuThuoc, i.getMaSanPham()).getLoiNhuan();
            }
        }

        for(SanPhamHuy i : hieuThuoc.sanPhamHuys){
            for(Thuoc j : hieuThuoc.Media){
                TongTien -= i.getSoLuong() * XuatThongTinSanPhamKhiDaDuocTimKiem(hieuThuoc, i.getMaSanPham()).getGiaMua();
            }
        }
        return TongTien;
    }
}
