/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.edusys.entity;

import java.util.Date;

/**
 *
 * @author MS QUOC
 */
public class HoaDon {

    private int MaHoaDon;
    private String TenKhachHang;
    private String SDTKH;
    private String MaSoBan;
    private String MaNV;
    private Date NgayDatban;
    private double TongTien;
    private double TienMat;
    private double TienDu;
    private String TrangThai;
    public HoaDon() {
    }

    public HoaDon(int MaHoaDon, String TenKhachHang, String SDTKH, String MaSoBan, String MaNV, Date NgayDatban, double TongTien, double TienMat, double TienDu,String TrangThai) {
        this.MaHoaDon = MaHoaDon;
        this.TenKhachHang = TenKhachHang;
        this.SDTKH = SDTKH;
        this.MaSoBan = MaSoBan;
        this.MaNV = MaNV;
        this.NgayDatban = NgayDatban;
        this.TongTien = TongTien;
        this.TienMat = TienMat;
        this.TienDu = TienDu;
        this.TrangThai = TrangThai;
    }

   
    public double getTongTien() {
        return TongTien;
    }

    public void setTongTien(double TongTien) {
        this.TongTien = TongTien;
    }

    public double getTienMat() {
        return TienMat;
    }

    public void setTienMat(double TienMat) {
        this.TienMat = TienMat;
    }

    public double getTienDu() {
        return TienDu;
    }

    public void setTienDu(double TienDu) {
        this.TienDu = TienDu;
    }

    public int getMaHoaDon() {
        return MaHoaDon;
    }

    public void setMaHoaDon(int MaHoaDon) {
        this.MaHoaDon = MaHoaDon;
    }

    public String getMaSoBan() {
        return MaSoBan;
    }

    public void setMaSoBan(String MaSoBan) {
        this.MaSoBan = MaSoBan;
    }

    public String getTenKhachHang() {
        return TenKhachHang;
    }

    public void setTenKhachHang(String TenKhachHang) {
        this.TenKhachHang = TenKhachHang;
    }

    public String getSDTKH() {
        return SDTKH;
    }

    public void setSDTKH(String SDTKH) {
        this.SDTKH = SDTKH;
    }



    public void setNgayDatban(Date NgayDatban) {
        this.NgayDatban = NgayDatban;
    }

    public String getMaNV() {
        return MaNV;
    }

    public void setMaNV(String MaNV) {
        this.MaNV = MaNV;
    }

    public Date getNgayDatban() {
        return NgayDatban;
    }

    public String getTrangThai() {
        return TrangThai;
    }

    public void setTrangThai(String TrangThai) {
        this.TrangThai = TrangThai;
    }

}
