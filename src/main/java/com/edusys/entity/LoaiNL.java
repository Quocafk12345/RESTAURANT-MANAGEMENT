/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.edusys.entity;

/**
 *
 * @author MS QUOC
 */
public class LoaiNL {
    private int MaLoai;
    private String Nhom;
    private String TenLoai;
    
    public LoaiNL(){
    }
    public LoaiNL(int MaLoai, String Nhom, String TenLoai){
        this.MaLoai = MaLoai;
        this.Nhom = Nhom;
        this.TenLoai = TenLoai;
    }

    public int getMaLoai() {
        return MaLoai;
    }

    public void setMaLoai(int MaLoai) {
        this.MaLoai = MaLoai;
    }

    public String getNhom() {
        return Nhom;
    }

    public void setNhom(String Nhom) {
        this.Nhom = Nhom;
    }

    public String getTenLoai() {
        return TenLoai;
    }

    public void setTenLoai(String TenLoai) {
        this.TenLoai = TenLoai;
    }
    
    
}
