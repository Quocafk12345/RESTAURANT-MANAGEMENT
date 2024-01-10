/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.edusys.entity;

/**
 *
 * @author MS QUOC
 */
public class Ban {

    private String MaSoBan;
    private double TongTien;
    private double TienMat;
    private double TienDu;

    public Ban() {

    }

    public Ban(String MaSoBan) {
        this.MaSoBan = MaSoBan;

    }

    public String getMaSoBan() {
        return MaSoBan;
    }

    public void setMaSoBan(String MaSoBan) {
        this.MaSoBan = MaSoBan;
    }

}
