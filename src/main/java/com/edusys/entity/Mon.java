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
public class Mon {

    private int MaMon;
    private String TenMon;
    private double DonGia;
    private Date NgayADGia;
    private String DonViTinh;
    private int MaLoai;
    private String TenLoai;
    private String Hinh;
    private int SoLuongDaBan;
    public Mon() {

    }

    public Mon(int MaMon, String TenMon, double DonGia, Date NgayADGia, String DonViTinh, int MaLoai, String TenLoai, String Hinh,int SoLuongDaBan){
        this.MaMon = MaMon;
        this.TenMon = TenMon;
        this.DonGia = DonGia;
        this.NgayADGia = NgayADGia;
        this.DonViTinh = DonViTinh;
        this.MaLoai = MaLoai;
        this.TenLoai = TenLoai;
        this.Hinh = Hinh;
        this.SoLuongDaBan = SoLuongDaBan;
    }

    public int getSoLuongDaBan() {
        return SoLuongDaBan;
    }

    public void setSoLuongDaBan(int SoLuongDaBan) {
        this.SoLuongDaBan = SoLuongDaBan;
    }
    
  

    public double getDonGia() {
        return DonGia;
    }

    public void setDonGia(double DonGia) {
        this.DonGia = DonGia;
    }

    public Date getNgayADGia() {
        return NgayADGia;
    }

    public void setNgayADGia(Date NgayADGia) {
        this.NgayADGia = NgayADGia;
    }

    public String getDonViTinh() {
        return DonViTinh;
    }

    public void setDonViTinh(String DonViTinh) {
        this.DonViTinh = DonViTinh;
    }

    public String getTenLoai() {
        return TenLoai;
    }

    public void setTenLoai(String TenLoai) {
        this.TenLoai = TenLoai;
    }


    public String getHinh() {
        return Hinh;
    }

    public void setHinh(String Hinh) {
        this.Hinh = Hinh;
    }

  

    public int getMaMon() {
        return MaMon;
    }

    public void setMaMon(int MaMon) {
        this.MaMon = MaMon;
    }

    public int getMaLoai() {
        return MaLoai;
    }

    public void setMaLoai(int MaLoai) {
        this.MaLoai = MaLoai;
    }

    public String getTenMon() {
        return TenMon;
    }

    public void setTenMon(String TenMon) {
        this.TenMon = TenMon;
    }
}
