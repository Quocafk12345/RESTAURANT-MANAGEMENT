/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.edusys.dao;

import com.edusys.entity.Mon;
import com.edusys.entity.MuaNguyenLieu;
import com.edusys.utils.JdbcHelper;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author MS QUOC
 */
public class MuaNguyenLieuDAO extends EdusysDAO<MuaNguyenLieu,Integer> {

    final String INSERT_SQL = "INSERT INTO MuaNguyenLieu (TenMon, DonGia,ThoiGianLap,DonViTinh,TenLoai,HinhAnh,SoHD,SoLuong)\n" +
"VALUES(?,?,?,?,?,?,?,?)";
    final String UPDATE_SQL = "UPDATE MuaNguyenLieu SET TenMon = ?, DonGia = ?, ThoiGianLap = ?, DonViTinh = ?, TenLoai = ? ,HinhAnh = ?, SoHD = ?, SoLuong = ? WHERE MaMuaNL = ?" ;
    final String DELETE_SQL = "DELETE MuaNguyenLieu WHERE MaMuaNL = ?";
    final String SELECT_ALL_SQL = "SELECT * FROM MuaNguyenLieu";
    final String SELECT_BY_ID_SQL = "SELECT * FROM MuaNguyenLieu WHERE MaMuaNL = ?";
    final String SELECT_ALL_LOAINGUYENLIEU_SQL = "select * from Mon where TenLoai = ?";

    @Override
    public void insert(MuaNguyenLieu entity) {
        JdbcHelper.excuteUpdate(INSERT_SQL,entity.getTenMon(),entity.getDonGia(),entity.getThoiGianLap(),entity.getDonViTinh(),entity.getTenLoai(),entity.getHinhAnh(),entity.getSoHD(),entity.getSoLuong());
    }

    @Override
    public void update( MuaNguyenLieu entity) {
        JdbcHelper.excuteUpdate(UPDATE_SQL,entity.getTenMon(),entity.getDonGia(),entity.getThoiGianLap(),entity.getDonViTinh(),entity.getTenLoai(),entity.getHinhAnh(),entity.getSoHD(),entity.getSoLuong(),entity.getMaMuaNL());
    }

    @Override
    public void delete(Integer id) {
        JdbcHelper.excuteUpdate(DELETE_SQL, id);
    }

    @Override
    public List<MuaNguyenLieu> selectAll() {
        return selectBySql(SELECT_ALL_SQL);
    }

    @Override
    public MuaNguyenLieu selectById(Integer id) {
        List<MuaNguyenLieu> list = this.selectBySql(SELECT_BY_ID_SQL, id);
        if(list.isEmpty()){
            return null;
        }
        return list.get(0);
    }

    @Override
    public List<MuaNguyenLieu> selectBySql(String sql, Object... args) {
        List<MuaNguyenLieu> list = new ArrayList<>();
        try {
            ResultSet rs = JdbcHelper.excuteQuery(sql, args);
            while(rs.next()){
                MuaNguyenLieu entity = new MuaNguyenLieu();
                entity.setMaMuaNL(rs.getInt("MaMuaNL"));
                entity.setTenLoai(rs.getString("TenLoai"));
                entity.setTenMon(rs.getString("TenMon"));
                entity.setDonGia(rs.getDouble("DonGia"));
                entity.setSoLuong(rs.getInt("SoLuong"));
                entity.setThoiGianLap(rs.getDate("ThoiGianLap"));
                entity.setSoHD(rs.getInt("SoHD"));
                entity.setHinhAnh(rs.getString("HinhAnh"));
                entity.setDonViTinh(rs.getString("DonViTinh"));
                entity.setMaNguyenLieu(rs.getInt("MaNguyenLieu"));
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
     public List<MuaNguyenLieu> selectByLoaiTD(String TenLoai) {
        List<MuaNguyenLieu> list = new ArrayList<>();

        try {
            ResultSet rs = JdbcHelper.excuteQuery(SELECT_ALL_LOAINGUYENLIEU_SQL, TenLoai);

            while (rs.next()) {
                MuaNguyenLieu entity = new MuaNguyenLieu();
                entity.setMaMuaNL(rs.getInt("MaMuaNL"));
                entity.setHinhAnh(rs.getString("HinhAnh"));
                entity.setTenMon(rs.getString("TenMon"));
                entity.setDonGia(rs.getDouble("DonGia"));
                entity.setThoiGianLap(rs.getDate("ThoiGianLap"));
                entity.setDonViTinh(rs.getString("DonViTinh"));
                entity.setTenLoai(rs.getString("TenLoai"));
                entity.setMaLoai(rs.getInt("MaLoai"));
                entity.setSoLuong(rs.getInt("SoLuong"));
                entity.setMaNguyenLieu(rs.getInt("MaNguyenLieu"));
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
