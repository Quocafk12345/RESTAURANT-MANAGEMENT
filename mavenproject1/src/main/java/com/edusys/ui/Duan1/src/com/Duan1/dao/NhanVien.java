
package com.Duan1.dao;

import java.util.Date;

public class NhanVien {
     String MaNV;
     String TenNV;
     Date NgaySinh;
     String SoDT;
     String TenDN;
     int CaLam;
     String Email;
     boolean GioiTinh;
     String MatKhau;
     String VaiTro;

    public NhanVien() {
    }

    public NhanVien(String MaNV, String TenNV, Date NgaySinh, String SoDT, String TenDN, int CaLam, String Email, boolean GioiTinh, String MatKhau, String VaiTro) {
        this.MaNV = MaNV;
        this.TenNV = TenNV;
        this.NgaySinh = NgaySinh;
        this.SoDT = SoDT;
        this.TenDN = TenDN;
        this.CaLam = CaLam;
        this.Email = Email;
        this.GioiTinh = GioiTinh;
        this.MatKhau = MatKhau;
        this.VaiTro = VaiTro;
    }

    public String getMaNV() {
        return MaNV;
    }

    public void setMaNV(String MaNV) {
        this.MaNV = MaNV;
    }

    public String getTenNV() {
        return TenNV;
    }

    public void setTenNV(String TenNV) {
        this.TenNV = TenNV;
    }

    public Date getNgaySinh() {
        return NgaySinh;
    }

    public void setNgaySinh(Date NgaySinh) {
        this.NgaySinh = NgaySinh;
    }

    public String getSoDT() {
        return SoDT;
    }

    public void setSoDT(String SoDT) {
        this.SoDT = SoDT;
    }

    public String getTenDN() {
        return TenDN;
    }

    public void setTenDN(String TenDN) {
        this.TenDN = TenDN;
    }

    public int getCaLam() {
        return CaLam;
    }

    public void setCaLam(int CaLam) {
        this.CaLam = CaLam;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }

    public boolean isGioiTinh() {
        return GioiTinh;
    }

    public void setGioiTinh(boolean GioiTinh) {
        this.GioiTinh = GioiTinh;
    }

    public String getMatKhau() {
        return MatKhau;
    }

    public void setMatKhau(String MatKhau) {
        this.MatKhau = MatKhau;
    }

    public String getVaiTro() {
        return VaiTro;
    }

    public void setVaiTro(String VaiTro) {
        this.VaiTro = VaiTro;
    }
    
}
