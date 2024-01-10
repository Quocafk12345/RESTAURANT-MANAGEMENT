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
public class MuaNguyenLieu{
    private int MaMuaNL;
    private String TenLoai;
    private String TenMon;
    private double DonGia;
    private int SoLuong;
    private Date ThoiGianLap;
    private int SoHD;
    private String HinhAnh;
    private int MaNguyenLieu;
    private int MaLoai;
    private String DonViTinh;
    public MuaNguyenLieu() {
    }

    public MuaNguyenLieu(int MaMuaNL, String TenLoai, String TenMon, double DonGia, int SoLuong, Date ThoiGianLap, int SoHD, String HinhAnh, int MaNguyenLieu, int MaLoai,String DonViTinh) {
        this.MaMuaNL = MaMuaNL;
        this.TenLoai = TenLoai;
        this.TenMon = TenMon;
        this.DonGia = DonGia;
        this.SoLuong = SoLuong;
        this.ThoiGianLap = ThoiGianLap;
        this.SoHD = SoHD;
        this.HinhAnh = HinhAnh;
        this.MaNguyenLieu = MaNguyenLieu;
        this.MaLoai = MaLoai;
        this.DonViTinh = DonViTinh;
    }

    public int getMaMuaNL() {
        return MaMuaNL;
    }

    public void setMaMuaNL(int MaMuaNL) {
        this.MaMuaNL = MaMuaNL;
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

    public Date getThoiGianLap() {
        return ThoiGianLap;
    }

    public void setThoiGianLap(Date ThoiGianLap) {
        this.ThoiGianLap = ThoiGianLap;
    }

    public int getSoHD() {
        return SoHD;
    }

    public void setSoHD(int SoHD) {
        this.SoHD = SoHD;
    }

    public String getHinhAnh() {
        return HinhAnh;
    }

    public void setHinhAnh(String HinhAnh) {
        this.HinhAnh = HinhAnh;
    }

    public int getMaNguyenLieu() {
        return MaNguyenLieu;
    }

    public void setMaNguyenLieu(int MaNguyenLieu) {
        this.MaNguyenLieu = MaNguyenLieu;
    }

    public int getMaLoai() {
        return MaLoai;
    }

    public void setMaLoai(int MaLoai) {
        this.MaLoai = MaLoai;
    }

    public String getDonViTinh() {
        return DonViTinh;
    }

    public void setDonViTinh(String DonViTinh) {
        this.DonViTinh = DonViTinh;
    }
    
    
}
