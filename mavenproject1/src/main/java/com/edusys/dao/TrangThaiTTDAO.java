/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.edusys.dao;
import com.edusys.utils.JdbcHelper;
import com.edusys.entity.TrangThai;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author MS QUOC
 */
public class TrangThaiTTDAO extends EdusysDAO<TrangThai, String>{
    final String SELECT_ALL_SQL = "select*from TrangThai";

    @Override
    public void insert(TrangThai entity) {
    }

    @Override
    public void update(TrangThai entity) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void delete(String id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<TrangThai> selectAll() {
        return selectBySql(SELECT_ALL_SQL);
    }

    @Override
    public TrangThai selectById(String id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<TrangThai> selectBySql(String sql, Object... args) {
        List<TrangThai> list = new ArrayList<>();
        try {
            ResultSet rs = JdbcHelper.excuteQuery(sql, args);
            while (rs.next()) {                
                TrangThai entity = new TrangThai();
                entity.setTrangThaiTT(rs.getString("TrangThai"));
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
    public void updatePassword(String id, String newPassword) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean checkPassword(String id, String password) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
        
}
