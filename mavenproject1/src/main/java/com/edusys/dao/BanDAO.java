/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.edusys.dao;

import com.edusys.utils.JdbcHelper;
import com.edusys.entity.Ban;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author MS QUOC
 */
public class BanDAO extends EdusysDAO<Ban, String> {

    final String SELECT_ALL_SQL = "select*from BanAn";
    final String SELEC_MA_SO_BAN_SQL = "SELECT MaSoBan FROM BanAn";

    @Override
    public void insert(Ban entity) {
    }

    @Override
    public void update(Ban entity) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void delete(String id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<Ban> selectAll() {
        return selectBySql(SELECT_ALL_SQL);
    }

    @Override
    public Ban selectById(String id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<Ban> selectBySql(String sql, Object... args) {
        List<Ban> list = new ArrayList<>();
        try {
            ResultSet rs = JdbcHelper.excuteQuery(sql, args);
            while (rs.next()) {
                Ban entity = new Ban();
                entity.setMaSoBan(rs.getString("MaSoBan"));
                list.add(entity);
            }
            rs.getStatement().getConnection().close();
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    public List<Ban> selectMaSoBan() {
        List<Ban> list = new ArrayList<>();
        
        try {
            ResultSet rs = JdbcHelper.excuteQuery(SELEC_MA_SO_BAN_SQL);
            while (rs.next()) {                
                Ban entity = new Ban();
                entity.setMaSoBan(rs.getString("MaSoBan"));
                list.add(entity);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public void updatePassword(String id, String newPassword) {

    }

    @Override
    public boolean checkPassword(String id, String password) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
