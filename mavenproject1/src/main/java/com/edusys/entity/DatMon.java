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
public class DatMon {
    private int MaOrder;
    private String MaSoBan;
    private String TenLoai;
    private String TenMon;
    private double DonGia;
    private int SoLuong;
    private Date NgayDatban;
    private int SoHD;
    public DatMon() {
    }

    public DatMon(int MaOrder, String MaSoBan, String TenLoai, String TenMon, double DonGia, int SoLuong, Date NgayDatban,int SoHD) {
        this.MaOrder = MaOrder;
        this.MaSoBan = MaSoBan;
        this.TenLoai = TenLoai;
        this.TenMon = TenMon;
        this.DonGia = DonGia;
        this.SoLuong = SoLuong;
        this.NgayDatban = NgayDatban;
        this.SoHD = SoHD;
    }

    public int getMaOrder() {
        return MaOrder;
    }

    public void setMaOrder(int MaOrder) {
        this.MaOrder = MaOrder;
    }

    public String getMaSoBan() {
        return MaSoBan;
    }

    public void setMaSoBan(String MaSoBan) {
        this.MaSoBan = MaSoBan;
    }

    public String getTenLoai() {
        return TenLoai;
    }

    public void setTenLoai(String TenLoai) {
        this.TenLoai = TenLoai;
    }

    public String getTenMon() {
        return TenMon;
    }

    public void setTenMon(String TenMon) {
        this.TenMon = TenMon;
    }

    public double getDonGia() {
        return DonGia;
    }

    public void setDonGia(double DonGia) {
        this.DonGia = DonGia;
    }

    public int getSoLuong() {
        return SoLuong;
    }

    public void setSoLuong(int SoLuong) {
        this.SoLuong = SoLuong;
    }

    public Date getNgayDatban() {
        return NgayDatban;
    }

    public void setNgayDatban(Date NgayDatban) {
        this.NgayDatban = NgayDatban;
    }

    public int getSoHD() {
        return SoHD;
    }

    public void setSoHD(int SoHD) {
        this.SoHD = SoHD;
    }
    
    
    
}
