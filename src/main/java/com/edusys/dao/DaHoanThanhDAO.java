/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.edusys.dao;

import com.edusys.utils.JdbcHelper;
import com.edusys.entity.DaHoanThanh;
import com.edusys.entity.DatMon;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author MS QUOC
 */
public class DaHoanThanhDAO extends EdusysDAO<DaHoanThanh, Integer> {

    final String INSERT_SQL = "INSERT INTO DaHoanThanh(MaSoBan, TenMon, SoLuong) VALUES (?,?,?)";
    final String DELETE_SQL = "DELETE DaHoanThanh WHERE DaHoanThanh = ?";
    final String SELECT_ALL_SQL = "SELECT*FROM DaHoanThanh";
    final String SELECT_BY_ID_SQL = "SELECT*FROM DaHoanThanh WHERE DaHoanThanh=?";
    final String SELECT_BY_MABAN_SQL = "SELECT * FROM DangCheBien WHERE MaSoBan like ?";

    @Override
    public void insert(DaHoanThanh entity) {
        JdbcHelper.excuteUpdate(INSERT_SQL, entity.getMaSoBan(), entity.getTenMon(), entity.getSoLuong());
    }

    @Override
    public void update(DaHoanThanh entity) {
    }

    @Override
    public void delete(Integer id) {
        JdbcHelper.excuteUpdate(DELETE_SQL, id);
    }

    @Override
    public List<DaHoanThanh> selectAll() {
        return selectBySql(SELECT_ALL_SQL);
    }

    @Override
    public DaHoanThanh selectById(Integer id) {
        List<DaHoanThanh> list = this.selectBySql(SELECT_BY_ID_SQL, id);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    @Override
    public List<DaHoanThanh> selectBySql(String sql, Object... args) {
        List<DaHoanThanh> list = new ArrayList<>();
        try {
            ResultSet rs = JdbcHelper.excuteQuery(sql, args);
            while (rs.next()) {
                DaHoanThanh entity = new DaHoanThanh();
                entity.setDaHoanThanhID(rs.getInt("DaHoanThanh"));
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

    public List<DaHoanThanh> selectByMaSoBan(String sql, Object... args) {
        List<DaHoanThanh> list = new ArrayList<>();
        try {
            ResultSet rs = JdbcHelper.excuteQuery(SELECT_BY_MABAN_SQL, args);
            while (rs.next()) {
                DaHoanThanh entity = new DaHoanThanh();
                entity.setDaHoanThanhID(rs.getInt("DaHoanThanh"));
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
