/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.edusys.dao;

import com.edusys.entity.ThoiGianLam;
import com.edusys.entity.ThoiGianLam;
import com.edusys.utils.JdbcHelper;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author MS QUOC
 */
public class ThoiGianLamDAO extends EdusysDAO<ThoiGianLam, String>{


/**
 *
 * @author MS QUOC
 */

    final String SELECT_ALL_SQL = "select*from ThoiGianLam";
    final String SELEC_MA_SO_BAN_SQL = "SELECT ThoiGianLamID FROM ThoiGianLam";

    @Override
    public void insert(ThoiGianLam entity) {
    }

    @Override
    public void update(ThoiGianLam entity) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void delete(String id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<ThoiGianLam> selectAll() {
        return selectBySql(SELECT_ALL_SQL);
    }

    @Override
    public ThoiGianLam selectById(String id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<ThoiGianLam> selectBySql(String sql, Object... args) {
        List<ThoiGianLam> list = new ArrayList<>();
        try {
            ResultSet rs = JdbcHelper.excuteQuery(sql, args);
            while (rs.next()) {
                ThoiGianLam entity = new ThoiGianLam();
                entity.setThoiGianLam(rs.getString("ThoiGianLamID"));
                list.add(entity);
            }
            rs.getStatement().getConnection().close();
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    public List<ThoiGianLam> selectMaSoThoiGianLam() {
        List<ThoiGianLam> list = new ArrayList<>();
        
        try {
            ResultSet rs = JdbcHelper.excuteQuery(SELEC_MA_SO_BAN_SQL);
            while (rs.next()) {                
                ThoiGianLam entity = new ThoiGianLam();
                entity.setThoiGianLam(rs.getString("ThoiGianLamID"));
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
