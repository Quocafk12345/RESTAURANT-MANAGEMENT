/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.edusys.dao;

import com.edusys.entity.LoaiNL;
import com.edusys.utils.JdbcHelper;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;
/**
 *
 * @author MS QUOC
 */
public class LoaiNLDAO extends EdusysDAO<LoaiNL, Integer>{
    final String SELECT_ALL_SQL = "select * from LoaiNguyenLieu";
    @Override
    public void insert(LoaiNL entity) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void update(LoaiNL entity) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void delete(Integer id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<LoaiNL> selectAll() {
        return selectBySql(SELECT_ALL_SQL);
    }

    @Override
    public LoaiNL selectById(Integer id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<LoaiNL> selectBySql(String sql, Object... args) {
        List<LoaiNL> list = new ArrayList<>();
        try {
            ResultSet rs = JdbcHelper.excuteQuery(sql, args);
            while (rs.next()) {                
                LoaiNL entity =  new LoaiNL();
                entity.setMaLoai(rs.getInt("MaLoai"));
                entity.setNhom(rs.getString("Nhom"));
                entity.setTenLoai(rs.getString("TenLoai"));
                
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

