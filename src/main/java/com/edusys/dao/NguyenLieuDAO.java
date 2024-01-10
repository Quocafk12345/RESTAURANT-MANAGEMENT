/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.edusys.dao;

import com.edusys.entity.NguyenLieu;
import com.edusys.utils.JdbcHelper;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;
import javax.swing.ImageIcon;

/**
 *
 * @author MS QUOC
 */
public class NguyenLieuDAO extends EdusysDAO<NguyenLieu, String> {

    final String INSERT_SQL = "INSERT INTO NguyenLieu (MaNguyenLieu,Hinh,TenNguyenLieu,DonGia,NgayADGia,DonViTinh,TenLoai) \n"
            + "VALUES  (?,?,?,?,?,?,?)";
    final String UPDATE_SQL = "update NguyenLieu set Hinh=?,TenNguyenLieu=?,DonGia=?,NgayADGia=?,DonViTinh=?,TenLoai=? where MaNguyenLieu = ?";
    final String DELETE_SQL = "delete from NguyenLieu where MaNguyenLieu = ?";
    final String SELECT_ALL_SQL = "select * from NguyenLieu";
    final String SELECT_BY_ID_SQL = "select * from NguyenLieu where MaNguyenLieu = ?";
    final String SELECT_ALL_LOAITD_SQL = "select * from NguyenLieu where TenLoai = ?";
    final String SELECT_BY_NAME_SQL = "select * from NguyenLieu where TenNguyenLieu like ?";
    final String SELECT_ALL_MASOBAN_SQL = "select * from NguyenLieu where MaSoBan = ?";
    @Override
    public void insert(NguyenLieu entity) {
        JdbcHelper.excuteUpdate(INSERT_SQL,entity.getMaNguyenLieu(), entity.getHinh(), entity.getTenNguyenLieu(), entity.getDonGia(), entity.getNgayADGia(), entity.getDonViTinh(), entity.getTenLoai());
    }

    @Override
    public void update(NguyenLieu entity) {
        JdbcHelper.excuteUpdate(UPDATE_SQL, entity.getHinh(), entity.getTenNguyenLieu(), entity.getDonGia(), entity.getNgayADGia(), entity.getDonViTinh(), entity.getTenLoai(), entity.getMaNguyenLieu());
    }

    @Override
    public void delete(String id) {
        JdbcHelper.excuteUpdate(DELETE_SQL, id);
    }

    @Override
    public List<NguyenLieu> selectAll() {
        return selectBySql(SELECT_ALL_SQL);
    }

    @Override
    public NguyenLieu selectById(String id) {
        List<NguyenLieu> list = this.selectBySql(SELECT_BY_ID_SQL, id);
        if (list.isEmpty()) {
            return null;// Trả về danh sách rỗng nếu ko co du lieu
        }
        return list.get(0);// Tra ve danh sach chua phan tu dau tien
    }

    @Override
    public List<NguyenLieu> selectBySql(String sql, Object... args) {
        List<NguyenLieu> list = new ArrayList<>();
        try {
            ResultSet rs = JdbcHelper.excuteQuery(sql, args);
            while (rs.next()) {
                NguyenLieu entity = new NguyenLieu();
                entity.setMaNguyenLieu(rs.getInt("MaNguyenLieu"));
                entity.setHinh(rs.getString("Hinh"));
                entity.setTenNguyenLieu(rs.getString("TenNguyenLieu"));
                entity.setDonGia(rs.getDouble("DonGia"));
                entity.setNgayADGia(rs.getDate("NgayADGia"));
                entity.setDonViTinh(rs.getString("DonViTinh"));
                entity.setTenLoai(rs.getString("TenLoai"));
                entity.setMaLoai(rs.getInt("MaLoai"));

                list.add(entity);
            }
            rs.getStatement().getConnection().close();
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updatePassword(String id, String newPassword) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean checkPassword(String id, String password) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public List<NguyenLieu> selectByLoaiNL(String TenLoai) {
        List<NguyenLieu> list = new ArrayList<>();

        try {
            ResultSet rs = JdbcHelper.excuteQuery(SELECT_ALL_LOAITD_SQL, TenLoai);

            while (rs.next()) {
                NguyenLieu entity = new NguyenLieu();
                entity.setMaNguyenLieu(rs.getInt("MaNguyenLieu"));
                entity.setHinh(rs.getString("Hinh"));
                entity.setTenNguyenLieu(rs.getString("TenNguyenLieu"));
                entity.setDonGia(rs.getDouble("DonGia"));
                entity.setNgayADGia(rs.getDate("NgayADGia"));
                entity.setDonViTinh(rs.getString("DonViTinh"));
                entity.setTenLoai(rs.getString("TenLoai"));
                entity.setMaLoai(rs.getInt("MaLoai"));
                list.add(entity);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

      public List<NguyenLieu> selectByMaSoBan(String MaSoBan) {
        List<NguyenLieu> list = new ArrayList<>();

        try {
            ResultSet rs = JdbcHelper.excuteQuery(SELECT_ALL_MASOBAN_SQL, MaSoBan);

            while (rs.next()) {
                NguyenLieu entity = new NguyenLieu();
                entity.setMaNguyenLieu(rs.getInt("MaNguyenLieu"));
                entity.setHinh(rs.getString("Hinh"));
                entity.setTenNguyenLieu(rs.getString("TenNguyenLieu"));
                entity.setDonGia(rs.getDouble("DonGia"));
                entity.setNgayADGia(rs.getDate("NgayADGia"));
                entity.setDonViTinh(rs.getString("DonViTinh"));
                entity.setTenLoai(rs.getString("TenLoai"));
                entity.setMaLoai(rs.getInt("MaLoai"));
                list.add(entity);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    public List<NguyenLieu> searchByName(String tenNguyenLieu) {
        List<NguyenLieu> resultList = new ArrayList<>();

        try {
            ResultSet rs = JdbcHelper.excuteQuery(SELECT_BY_NAME_SQL, "%" + tenNguyenLieu + "%");
            while (rs.next()) {
                NguyenLieu entity = new NguyenLieu();
                entity.setHinh(rs.getString("Hinh"));
                entity.setMaNguyenLieu(rs.getInt("MaNguyenLieu"));
                entity.setTenNguyenLieu(rs.getString("TenNguyenLieu"));
                entity.setDonGia(rs.getDouble("DonGia"));
                entity.setNgayADGia(rs.getDate("NgayADGia"));
                entity.setDonViTinh(rs.getString("DonViTinh"));
                entity.setTenLoai(rs.getString("TenLoai"));
                entity.setMaLoai(rs.getInt("MaLoai"));
                resultList.add(entity);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultList;
    }
}
