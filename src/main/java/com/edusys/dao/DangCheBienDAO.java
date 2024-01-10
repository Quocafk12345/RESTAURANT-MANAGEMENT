/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.edusys.dao;

import com.edusys.entity.DaHoanThanh;
import com.edusys.entity.DangCheBien;
import com.edusys.utils.JdbcHelper;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author MS QUOC
 */
public class DangCheBienDAO extends EdusysDAO<DangCheBien, Integer> {

    final String INSERT_SQL = "INSERT INTO DangCheBien(MaSoBan, ThoiGianLam,TenMon, SoLuong) VALUES (?,?,?,?)";
    final String UPDATE_SQL = "UPDATE DangCheBien SET MaSoBan = ?, ThoiGianLam= ?,TenMon=?, SoLuong=? WHERE DangCheBienID = ?";
    final String DELETE_SQL = "DELETE DangCheBien WHERE DangCheBienID = ?";
    final String SELECT_ALL_SQL = "SELECT*FROM DangCheBien";
    final String SELECT_BY_ID_SQL = "SELECT*FROM DangCheBien WHERE DangCheBienID=?";
    final String SELECT_BY_MABAN_SQL = "SELECT * FROM DangCheBien WHERE MaSoBan like ?";

    @Override
    public void insert(DangCheBien entity) {
        JdbcHelper.excuteUpdate(INSERT_SQL, entity.getMaSoBan(), entity.getThoiGianLam(), entity.getTenMon(), entity.getSoLuong());
    }

    @Override
    public void update(DangCheBien entity) {
        JdbcHelper.excuteUpdate(UPDATE_SQL, entity.getMaSoBan(), entity.getThoiGianLam(), entity.getTenMon(), entity.getSoLuong(),entity.getDangCheBienID());
    }

    @Override
    public void delete(Integer id) {
        JdbcHelper.excuteUpdate(DELETE_SQL, id);
    }

    @Override
    public List<DangCheBien> selectAll() {
        return selectBySql(SELECT_ALL_SQL);
    }

    @Override
    public DangCheBien selectById(Integer id) {
        List<DangCheBien> list = this.selectBySql(SELECT_BY_ID_SQL, id);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    @Override
    public List<DangCheBien> selectBySql(String sql, Object... args) {
        List<DangCheBien> list = new ArrayList<>();
        try {
            ResultSet rs = JdbcHelper.excuteQuery(sql, args);
            while (rs.next()) {
                DangCheBien entity = new DangCheBien();
                entity.setDangCheBienID(rs.getInt("DangCheBienID"));
                entity.setThoiGianLam(rs.getString("ThoiGianLam"));
                entity.setMaSoBan(rs.getString("MaSoBan"));
                entity.setTenMon(rs.getString("TenMon"));
                entity.setSoLuong(rs.getInt("SoLuong"));
                list.add(entity);
            }
            rs.getStatement().getConnection().close();
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
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
