/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.edusys.dao;

import com.edusys.entity.ChiTietHoaDon;
import com.edusys.entity.DatMon;
import com.edusys.utils.JdbcHelper;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author MS QUOC
 */
public class ChiTietHoaDonDAO extends EdusysDAO<ChiTietHoaDon, Integer> {

    final String INSERT_SQL = "INSERT INTO DatMon( MaSoBan, TenLoai,TenMon, DonGia, SoLuong,SoHD) VALUES (?,?,?,?,?,?)";
    final String UPDATE_SQL = "UPDATE DatMon SET MaSoBan = ?, TenLoai = ?, TenMon = ?, DonGia = ?, SoLuong = ?, SoHD=? WHERE MaOrder = ?";
    final String DELETE_SQL = "DELETE FROM DatMon WHERE MaOrder = ?";
    final String SELECT_BY_ID_SQL = "SELECT * FROM DatMon WHERE MaOrder = ?";
    final String SELECT_ALL_SQL = "SELECT * FROM DatMon";
    final String SELECT_BY_MABAN_SQL = "SELECT * FROM DatMon WHERE MaSoBan like ?";

    @Override
    public void insert(ChiTietHoaDon entity) {
        JdbcHelper.excuteUpdate(INSERT_SQL, entity.getMaSoBan(), entity.getTenLoai(), entity.getTenMon(), entity.getDonGia(), entity.getSoLuong(), entity.getSoHD());
    }

    @Override
    public void update(ChiTietHoaDon entity) {
        JdbcHelper.excuteUpdate(UPDATE_SQL, entity.getMaSoBan(), entity.getTenLoai(), entity.getTenMon(), entity.getDonGia(), entity.getSoLuong(), entity.getNgayDatban(), entity.getSoHD(), entity.getMaCTHD());
    }

    @Override
    public void delete(Integer id) {
        JdbcHelper.excuteUpdate(DELETE_SQL, id);
    }

    @Override
    public List<ChiTietHoaDon> selectAll() {
        return selectBySql(SELECT_ALL_SQL);
    }

    @Override
    public ChiTietHoaDon selectById(Integer id) {
        List<ChiTietHoaDon> list = this.selectBySql(SELECT_BY_ID_SQL, id);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);//Trả về danh sách chứa phần tử đầu tiên
    }

    @Override
    public List<ChiTietHoaDon> selectBySql(String sql, Object... args) {
        List<ChiTietHoaDon> list = new ArrayList<>();
        try {
            ResultSet rs = JdbcHelper.excuteQuery(sql, args);
            while (rs.next()) {
                ChiTietHoaDon entity = new ChiTietHoaDon();
                entity.setMaCTHD(rs.getInt("MaCTHD"));
                entity.setMaSoBan(rs.getString("MaSoBan"));
                entity.setTenLoai(rs.getString("TenLoai"));
                entity.setTenMon(rs.getString("TenMon"));
                entity.setDonGia(rs.getDouble("DonGia"));
                entity.setSoLuong(rs.getInt("SoLuong"));
                entity.setNgayDatban(rs.getDate("ThoiGianLap"));
                entity.setSoHD(rs.getInt("SoHD"));
                list.add(entity);
            }
            rs.getStatement().getConnection().close();
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }

    }

    public List<ChiTietHoaDon> selectByMaBan(String maSoBan) {
        List<ChiTietHoaDon> list = new ArrayList<>();

        try {
            ResultSet rs = JdbcHelper.excuteQuery(SELECT_BY_MABAN_SQL, maSoBan);

            while (rs.next()) {

                ChiTietHoaDon entity = new ChiTietHoaDon();
                entity.setMaCTHD(rs.getInt("MaCTHD"));
                entity.setMaSoBan(rs.getString("MaSoBan"));
                entity.setTenLoai(rs.getString("TenLoai"));
                entity.setTenMon(rs.getString("TenMon"));
                entity.setDonGia(rs.getDouble("DonGia"));
                entity.setSoHD(rs.getInt("SoHD"));
                list.add(entity);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public void updatePassword(Integer id, String newPassword) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean checkPassword(Integer id, String password) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
