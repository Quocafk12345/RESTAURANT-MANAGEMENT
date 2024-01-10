/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.edusys.dao;

import com.edusys.entity.NhanVien;
import com.edusys.utils.JdbcHelper;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author MS QUOC
 */
public class NhanVienDAO extends EdusysDAO<NhanVien, String> {

    final String INSERT_SQL = "INSERT INTO nhanvien(MaNV,HinhAnh,TenNV,NgaySinh,SDT,TenDN,CaLam,Email,GioiTinh,MatKhau,VaiTro,MaSoBan) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";
    final String UPDATE_SQL = "update nhanvien set HinhAnh=?,TenNV = ?,SDT=?,TenDN=?,NgaySinh = ?,CaLam = ?,Email = ?, GioiTinh = ?,MatKhau = ? ,VaiTro = ? where MaNV = ?";
    final String DELETE_SQL = "delete from nhanvien where MaNV = ?";
    final String SELECT_ALL_SQL = "select * from nhanvien";
    final String SELECT_BY_ID_SQL = "select * from nhanvien where MaNV = ?";
    final String UPDATE_PASSWORD_SQL = "UPDATE nhanvien SET MatKhau = ? WHERE MaNV = ?";
    final String CHECK_PASSWORD_SQL = "SELECT MatKhau FROM nhanvien WHERE MaNV = ?";
    final String GET_VAITRO_SQL = "SELECT DISTINCT VaiTro FROM NhanVien WHERE MaNV = ?";
    final String INSERT_CA_LAM_SQL = "INSERT INTO nhanvien(MaNV,CaLam,MaSoBan) VALUES (?,?,?)";
    final String UPDATE_CA_LAM_SQL = "UPDATE nhanvien SET CaLam=?,MaSoBan=? WHERE MaNV = ?";
    final String DELETE_CA_LAM_SQL = "delete from nhanvien where MaNV=?";
    @Override
    public void insert(NhanVien entity) {
        JdbcHelper.excuteUpdate(INSERT_SQL, entity.getMaNV(), entity.getHinhAnh(),entity.getTenNV(), entity.getNgaySinh(), entity.getSDT(), entity.getTenDN(), entity.getCaLam(), entity.getEmail(), entity.isGioiTinh(), entity.getMatKhau(), entity.isVaiTro(), entity.getMaSoBan());
    }

    public void insertCaLam(NhanVien entity) {
        JdbcHelper.excuteUpdate(INSERT_CA_LAM_SQL, entity.getMaNV(), entity.getCaLam(), entity.getMaSoBan());
    }

    public void updateCaLam(NhanVien entity) {
        JdbcHelper.excuteUpdate(UPDATE_CA_LAM_SQL,  entity.getCaLam(), entity.getMaSoBan(),entity.getMaNV());
    }

    @Override
    public void update(NhanVien entity) {
        JdbcHelper.excuteUpdate(UPDATE_SQL, entity.getHinhAnh(),entity.getTenNV(), entity.getSDT(), entity.getTenDN(), entity.getNgaySinh(), entity.getCaLam(), entity.getEmail(), entity.isGioiTinh(), entity.getMatKhau(), entity.isVaiTro(), entity.getMaNV());
    }

    @Override
    public void delete(String id) {
        JdbcHelper.excuteUpdate(DELETE_SQL, id);
    }
     public void deleteCaLam(String id) {
        JdbcHelper.excuteUpdate(DELETE_CA_LAM_SQL, id);
    }

    @Override
    public List<NhanVien> selectAll() {
        return selectBySql(SELECT_ALL_SQL);
    }

    @Override
    public void updatePassword(String maNV, String newPassword) {
        String sql = "UPDATE nhanvien SET MatKhau = ? WHERE MaNV = ?";
        JdbcHelper.excuteUpdate(sql, newPassword, maNV);
    }

    @Override
    public boolean checkPassword(String maNV, String password) {
        String sql = "SELECT * FROM nhanvien WHERE MaNV = ? AND MatKhau = ?";
        List<NhanVien> result = selectBySql(sql, maNV, password);
        return !result.isEmpty();
    }

    @Override
    public List<NhanVien> selectBySql(String sql, Object... args) {
        List<NhanVien> list = new ArrayList<>();
        try {
            ResultSet rs = JdbcHelper.excuteQuery(sql, args);
            while (rs.next()) {
                NhanVien entity = new NhanVien();
                entity.setMaNV(rs.getString("MaNV"));
                entity.setHinhAnh(rs.getString("HinhAnh"));
                entity.setTenNV(rs.getString("TenNV"));
                entity.setNgaySinh(rs.getDate("NgaySinh"));
                entity.setSDT(rs.getString("SDT"));
                entity.setTenDN(rs.getString("TenDN"));
                entity.setCaLam(rs.getString("CaLam"));
                entity.setEmail(rs.getString("Email"));
                entity.setVaiTro(rs.getBoolean("VaiTro"));
                entity.setMatKhau(rs.getString("MatKhau"));
                entity.setGioiTinh(rs.getBoolean("GioiTinh"));
                entity.setMaSoBan(rs.getString("MaSoBan"));

                list.add(entity);
            }
            rs.getStatement().getConnection().close();
            return list;
        } catch (Exception e) {
            throw new RuntimeException(e);

        }
    }

    public boolean isMaNVTonTai(String MaNV) {
        try {
            String sql = "SELECT COUNT(*) FROM NhanVien WHERE MaNV=?";
            ResultSet rs = JdbcHelper.excuteQuery(sql, MaNV);
            if (rs.next()) {
                int count = rs.getInt(1);//Lấy giá trị ở cột thứ nhất của dòng hiện tại trong resulset
                return count > 0;//hàm trả về true nếu số lượng bản ghi lớn hơn 0, ngược lại trả về false
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public NhanVien selectById(String id) {
        List<NhanVien> list = this.selectBySql(SELECT_BY_ID_SQL, id);
        if (list.isEmpty()) {
            return null; // Trả về danh sách rỗng nếu không có dữ liệu
        }
        return list.get(0); // Trả về danh sách chứa phần tử đầu tiên
    }

    public List<NhanVien> selectByKeyword(String keyword) {
        String sql = "SELECT * FROM NhanVien WHERE TenNV LIKE ? OR MaNV LIKE ?";
        return this.selectBySql(sql, "%" + keyword + "%", "%" + keyword + "%");
    }

}
