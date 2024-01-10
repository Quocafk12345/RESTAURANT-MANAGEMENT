/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.edusys.entity;

import java.util.Date;

/**
 *
 * @author MS QUOC
 */
public class NhanVien {

    private String MaNV;
    private String TenNV;
    private Date NgaySinh;
    private String SDT;
    private String TenDN;
    private String CaLam;
    private String Email;
    private boolean GioiTinh;
    private String MatKhau;
    private boolean VaiTro;
    private String MaSoBan;

    public NhanVien() {
    }

    public NhanVien(String maNV, String TenNV, Date NgaySinh, String SDT, String TenDN, String CaLam, String Email, String matKhau, boolean VaiTro,String MaSoBan) {
        this.MaNV = MaNV;
        this.TenNV = TenNV;
        this.NgaySinh = NgaySinh;
        this.CaLam = CaLam;
        this.TenDN = TenDN;
        this.Email = Email;
        this.MatKhau = MatKhau;
        this.VaiTro = VaiTro;
        this.SDT = SDT;
        this.GioiTinh = GioiTinh;
        this.MaSoBan = MaSoBan;
    }

    @Override
    public String toString() {
        return this.MaNV + " (" + this.NgaySinh + ")"; // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
    }

    public Date getNgaySinh() {
        return NgaySinh;
    }

    public void setNgaySinh(Date NgaySinh) {
        this.NgaySinh = NgaySinh;
    }

    public String getTenDN() {
        return TenDN;
    }

    public void setTenDN(String TenDN) {
        this.TenDN = TenDN;
    }

    public String getCaLam() {
        return CaLam;
    }

    public void setCaLam(String CaLam) {
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

    public String getSDT() {
        return SDT;
    }

    public void setSDT(String SDT) {
        this.SDT = SDT;
    }

    public boolean getGioiTinh() {
        return GioiTinh;
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

    public String getMatKhau() {
        return MatKhau;
    }

    public void setMatKhau(String MatKhau) {
        this.MatKhau = MatKhau;
    }

    public boolean isVaiTro() {
        return VaiTro;
    }

    public void setVaiTro(boolean VaiTro) {
        this.VaiTro = VaiTro;
    }

    public String getMaSoBan() {
        return MaSoBan;
    }

    public void setMaSoBan(String MaSoBan) {
        this.MaSoBan = MaSoBan;
    }



}
