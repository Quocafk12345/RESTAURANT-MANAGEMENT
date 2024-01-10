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
public class NguyenLieu {

    private int MaNguyenLieu;
    private String TenNguyenLieu;
    private double DonGia;
    private Date NgayADGia;
    private String DonViTinh;
    private int MaLoai;
    private String TenLoai;
    private String Hinh;

    public NguyenLieu() {

    }

    public NguyenLieu(int MaNguyenLieu, String TenNguyenLieu, double DonGia, Date NgayADGia, String DonViTinh, int MaLoai, String TenLoai, String Hinh) {
        this.MaNguyenLieu = MaNguyenLieu;
        this.TenNguyenLieu = TenNguyenLieu;
        this.DonGia = DonGia;
        this.NgayADGia = NgayADGia;
        this.DonViTinh = DonViTinh;
        this.MaLoai = MaLoai;
        this.TenLoai = TenLoai;
        this.Hinh = Hinh;
    }

    public int getMaNguyenLieu() {
        return MaNguyenLieu;
    }

    public void setMaNguyenLieu(int MaNguyenLieu) {
        this.MaNguyenLieu = MaNguyenLieu;
    }

    public String getTenNguyenLieu() {
        return TenNguyenLieu;
    }

    public void setTenNguyenLieu(String TenNguyenLieu) {
        this.TenNguyenLieu = TenNguyenLieu;
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

    public int getMaLoai() {
        return MaLoai;
    }

    public void setMaLoai(int MaLoai) {
        this.MaLoai = MaLoai;
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

    
}