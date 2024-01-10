/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.edusys.dao;

import com.edusys.entity.Ban;
import com.edusys.utils.JdbcHelper;
import com.edusys.entity.DatMon;
import com.edusys.entity.HoaDon;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author MS QUOC
 */
public class DatMonDAO extends EdusysDAO<DatMon, Integer> {

    final String INSERT_SQL = "INSERT INTO DatMon( MaSoBan, TenLoai,TenMon, DonGia, SoLuong, ThoiGianLap,SoHD) VALUES (?,?,?,?,?,?,?)";
    final String UPDATE_SQL = "UPDATE DatMon SET MaSoBan = ?, TenLoai = ?, TenMon = ?, DonGia = ?, SoLuong = ?, ThoiGianLap = ?, SoHD=? WHERE MaOrder = ?";
    final String DELETE_SQL = "DELETE FROM DatMon WHERE MaOrder = ?";
    final String SELECT_BY_ID_SQL = "SELECT * FROM DatMon WHERE MaOrder = ?";
    final String SELECT_ALL_SQL = "SELECT * FROM DatMon";
    final String SELECT_BY_MABAN_SQL = "SELECT * FROM DatMon WHERE MaSoBan like ?";
    final String FIND_BY_DATE_SQL = "SELECT * FROM DatMon WHERE ThoiGianLap = ?";
    @Override
    public void insert(DatMon entity) {
        JdbcHelper.excuteUpdate(INSERT_SQL,  entity.getMaSoBan(), entity.getTenLoai(), entity.getTenMon(), entity.getDonGia(), entity.getSoLuong(), entity.getNgayDatban(),entity.getSoHD());
    }

    @Override
    public void update(DatMon entity) {
        JdbcHelper.excuteUpdate(UPDATE_SQL, entity.getMaSoBan(), entity.getTenLoai(), entity.getTenMon(), entity.getDonGia(), entity.getSoLuong(), entity.getNgayDatban(),entity.getSoHD(), entity.getMaOrder());
    }

    @Override
    public void delete(Integer id) {
        JdbcHelper.excuteUpdate(DELETE_SQL, id);
    }

    @Override
    public List<DatMon> selectAll() {
        return selectBySql(SELECT_ALL_SQL);
    }

    @Override
    public DatMon selectById(Integer id) {
        List<DatMon> list = this.selectBySql(SELECT_BY_ID_SQL, id);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);//Trả về danh sách chứa phần tử đầu tiên
    }

    @Override
    public List<DatMon> selectBySql(String sql, Object... args) {
        List<DatMon> list = new ArrayList<>();
        try {
            ResultSet rs = JdbcHelper.excuteQuery(sql, args);
            while (rs.next()) {
                DatMon entity = new DatMon();
                entity.setMaOrder(rs.getInt("MaOrder"));
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

    public List<DatMon> selectByMaBan(String maSoBan){
        List<DatMon> list = new ArrayList<>();
        
        try {
            ResultSet rs = JdbcHelper.excuteQuery(SELECT_BY_MABAN_SQL, maSoBan);
            
            while (rs.next()) {
            
                DatMon entity =new DatMon();
                entity.setMaOrder(rs.getInt("MaOrder"));
                entity.setMaSoBan(rs.getString("MaSoBan"));
                entity.setTenLoai(rs.getString("TenLoai"));
                entity.setTenMon(rs.getString("TenMon"));
                entity.setDonGia(rs.getDouble("DonGia"));
                entity.setSoLuong(rs.getInt("SoLuong"));
                entity.setSoHD(rs.getInt("SoHD"));
                list.add(entity);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    
    public List<HoaDon> FindByDate(Date NgayDatBan){
        List<HoaDon> list = new ArrayList<>();
        
        try {
            ResultSet rs = JdbcHelper.excuteQuery(FIND_BY_DATE_SQL, NgayDatBan);
            
            HoaDon entity = new HoaDon();
            entity.setMaHoaDon(rs.getInt("SoHD"));
            entity.setTenKhachHang(rs.getString("TenKhachHang"));
            entity.setSDTKH(rs.getString("SDTKhachHang"));
            entity.setNgayDatban(rs.getDate("ThoiGianLap"));
            entity.setMaSoBan(rs.getString("MaSoBan"));
            entity.setMaNV(rs.getString("MaNV"));
            entity.setTongTien(rs.getDouble("TongTien"));
            entity.setTrangThai(rs.getString("TrangThaiThanhToan"));
            
            list.add(entity);
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
