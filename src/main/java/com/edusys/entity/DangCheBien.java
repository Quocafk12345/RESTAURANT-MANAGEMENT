/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.edusys.entity;

/**
 *
 * @author MS QUOC
 */
public class DangCheBien {
    private int DangCheBienID;
    private String MaSoBan;
    private String TenMon;
    private int SoLuong;
    private String ThoiGianLam;

    public DangCheBien() {
    }

    
    public DangCheBien(String MaSoBan, String TenMon, int SoLuong, String ThoiGianLam,int DangCheBienID) {
        this.MaSoBan = MaSoBan;
        this.TenMon = TenMon;
        this.SoLuong = SoLuong;
        this.ThoiGianLam = ThoiGianLam;
        this.DangCheBienID = DangCheBienID;
    }

    public String getMaSoBan() {
        return MaSoBan;
    }

    public void setMaSoBan(String MaSoBan) {
        this.MaSoBan = MaSoBan;
    }

    public String getTenMon() {
        return TenMon;
    }

    public void setTenMon(String TenMon) {
        this.TenMon = TenMon;
    }

    public int getSoLuong() {
        return SoLuong;
    }

    public void setSoLuong(int SoLuong) {
        this.SoLuong = SoLuong;
    }

    public String getThoiGianLam() {
        return ThoiGianLam;
    }

    public void setThoiGianLam(String ThoiGianLam) {
        this.ThoiGianLam = ThoiGianLam;
    }

    public int getDangCheBienID() {
        return DangCheBienID;
    }

    public void setDangCheBienID(int DangCheBienID) {
        this.DangCheBienID = DangCheBienID;
    }
    
    
}
