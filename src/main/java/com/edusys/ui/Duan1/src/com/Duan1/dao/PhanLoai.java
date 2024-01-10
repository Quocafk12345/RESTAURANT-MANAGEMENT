
package com.Duan1.dao;

public class PhanLoai {
       String MaNV;
       int CaLam;
       String MaSoBan;

    public PhanLoai() {
    }

    public PhanLoai(String MaNV, int CaLam, String MaSoBan) {
        this.MaNV = MaNV;
        this.CaLam = CaLam;
        this.MaSoBan = MaSoBan;
    }

    public String getMaNV() {
        return MaNV;
    }

    public void setMaNV(String MaNV) {
        this.MaNV = MaNV;
    }

    public int getCaLam() {
        return CaLam;
    }

    public void setCaLam(int CaLam) {
        this.CaLam = CaLam;
    }

    public String getMaSoBan() {
        return MaSoBan;
    }

    public void setMaSoBan(String MaSoBan) {
        this.MaSoBan = MaSoBan;
    }
       
}
