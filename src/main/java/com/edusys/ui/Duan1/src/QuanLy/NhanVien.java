/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package QuanLy;

public class NhanVien {
    String tenNV;
    String soDT;
    String GioLam;
    String Email;
    boolean VaiTro;
    String MatKhau;
    String XacNhanMK;

    public NhanVien() {
    }

    public NhanVien(String tenNV, String soDT, String GioLam, String Email, boolean VaiTro, String MatKhau, String XacNhanMK) {
        this.tenNV = tenNV;
        this.soDT = soDT;
        this.GioLam = GioLam;
        this.Email = Email;
        this.VaiTro = VaiTro;
        this.MatKhau = MatKhau;
        this.XacNhanMK = XacNhanMK;
    }

    public String getTenNV() {
        return tenNV;
    }

    public void setTenNV(String tenNV) {
        this.tenNV = tenNV;
    }

    public String getSoDT() {
        return soDT;
    }

    public void setSoDT(String soDT) {
        this.soDT = soDT;
    }

    public String getGioLam() {
        return GioLam;
    }

    public void setGioLam(String GioLam) {
        this.GioLam = GioLam;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }

    public boolean isVaiTro() {
        return VaiTro;
    }

    public void setVaiTro(boolean VaiTro) {
        this.VaiTro = VaiTro;
    }

    public String getMatKhau() {
        return MatKhau;
    }

    public void setMatKhau(String MatKhau) {
        this.MatKhau = MatKhau;
    }

    public String getXacNhanMK() {
        return XacNhanMK;
    }

    public void setXacNhanMK(String XacNhanMK) {
        this.XacNhanMK = XacNhanMK;
    }
    
}
