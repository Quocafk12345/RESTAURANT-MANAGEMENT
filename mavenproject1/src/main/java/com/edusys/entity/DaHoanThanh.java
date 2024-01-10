/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.edusys.entity;

/**
 *
 * @author MS QUOC
 */
public class DaHoanThanh {

    private String MaSoBan;
    private String TenMon;
    private int SoLuong;
    private int DaHoanThanhID;

    public DaHoanThanh() {
    }

    public DaHoanThanh(String MaSoBan, String TenMon, int SoLuong,int DaHoanThanhID) {
        this.MaSoBan = MaSoBan;
        this.TenMon = TenMon;
        this.SoLuong = SoLuong;
        this.DaHoanThanhID = DaHoanThanhID;
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

    public int getDaHoanThanhID() {
        return DaHoanThanhID;
    }

    public void setDaHoanThanhID(int DaHoanThanhID) {
        this.DaHoanThanhID = DaHoanThanhID;
    }

}
