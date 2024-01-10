/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.edusys.dao;

import com.edusys.entity.Mon;
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
public class MonDAO extends EdusysDAO<Mon, String> {

    final String INSERT_SQL = "INSERT INTO Mon (MaMon,Hinh,TenMon,DonGia,NgayADGia,DonViTinh,TenLoai) \n"
            + "VALUES  (?,?,?,?,?,?,?)";
    final String UPDATE_SQL = "update Mon set Hinh=?,TenMon=?,DonGia=?,NgayADGia=?,DonViTinh=?,TenLoai=? where MaMon = ?";
    final String DELETE_SQL = "delete from Mon where MaMon = ?";
    final String SELECT_ALL_SQL = "select * from Mon";
    final String SELECT_BY_ID_SQL = "select * from Mon where MaMon = ?";
    final String SELECT_ALL_LOAITD_SQL = "select * from Mon where TenLoai = ?";
    final String SELECT_BY_NAME_SQL = "select * from Mon where TenMon like ?";
    final String SELECT_ALL_MASOBAN_SQL = "select * from Mon where MaSoBan = ?";
    @Override
    public void insert(Mon entity) {
        JdbcHelper.excuteUpdate(INSERT_SQL,entity.getMaMon(), entity.getHinh(), entity.getTenMon(), entity.getDonGia(), entity.getNgayADGia(), entity.getDonViTinh(), entity.getTenLoai());
    }

    @Override
    public void update(Mon entity) {
        JdbcHelper.excuteUpdate(UPDATE_SQL, entity.getHinh(), entity.getTenMon(), entity.getDonGia(), entity.getNgayADGia(), entity.getDonViTinh(), entity.getTenLoai(), entity.getMaMon());
    }

    @Override
    public void delete(String id) {
        JdbcHelper.excuteUpdate(DELETE_SQL, id);
    }

    @Override
    public List<Mon> selectAll() {
        return selectBySql(SELECT_ALL_SQL);
    }

    @Override
    public Mon selectById(String id) {
        List<Mon> list = this.selectBySql(SELECT_BY_ID_SQL, id);
        if (list.isEmpty()) {
            return null;// Trả về danh sách rỗng nếu ko co du lieu
        }
        return list.get(0);// Tra ve danh sach chua phan tu dau tien
    }

    @Override
    public List<Mon> selectBySql(String sql, Object... args) {
        List<Mon> list = new ArrayList<>();
        try {
            ResultSet rs = JdbcHelper.excuteQuery(sql, args);
            while (rs.next()) {
                Mon entity = new Mon();
                entity.setMaMon(rs.getInt("MaMon"));
                entity.setHinh(rs.getString("Hinh"));
                entity.setTenMon(rs.getString("TenMon"));
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

    public List<Mon> selectByLoaiTD(String TenLoai) {
        List<Mon> list = new ArrayList<>();

        try {
            ResultSet rs = JdbcHelper.excuteQuery(SELECT_ALL_LOAITD_SQL, TenLoai);

            while (rs.next()) {
                Mon entity = new Mon();
                entity.setMaMon(rs.getInt("MaMon"));
                entity.setHinh(rs.getString("Hinh"));
                entity.setTenMon(rs.getString("TenMon"));
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

      public List<Mon> selectByMaSoBan(String MaSoBan) {
        List<Mon> list = new ArrayList<>();

        try {
            ResultSet rs = JdbcHelper.excuteQuery(SELECT_ALL_MASOBAN_SQL, MaSoBan);

            while (rs.next()) {
                Mon entity = new Mon();
                entity.setMaMon(rs.getInt("MaMon"));
                entity.setHinh(rs.getString("Hinh"));
                entity.setTenMon(rs.getString("TenMon"));
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
    public List<Mon> searchByName(String tenMon) {
        List<Mon> resultList = new ArrayList<>();

        try {
            ResultSet rs = JdbcHelper.excuteQuery(SELECT_BY_NAME_SQL, "%" + tenMon + "%");
            while (rs.next()) {
                Mon entity = new Mon();
                entity.setHinh(rs.getString("Hinh"));
                entity.setMaMon(rs.getInt("MaMon"));
                entity.setTenMon(rs.getString("TenMon"));
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
