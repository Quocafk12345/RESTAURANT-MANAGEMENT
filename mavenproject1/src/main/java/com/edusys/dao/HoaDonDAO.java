/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.edusys.dao;

import com.edusys.utils.JdbcHelper;
import com.edusys.entity.DatMon;
import com.edusys.entity.HoaDon;
import com.edusys.entity.NhanVien;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author MS QUOC
 */
public class HoaDonDAO extends EdusysDAO<HoaDon, Integer> {

    final String INSERT_SQL = "INSERT INTO HoaDonBanAn( SoHD,MaSoBan,  TenKhachHang,SDTKhachHang, ThoiGianLap,MaNV,TongTien,TienMat,TienDu,TrangThaiThanhToan) VALUES (?,?,?,?,?,?,?,?,?,?)";
    final String UPDATE_SQL = "UPDATE HoaDon SET MaSoBan = ?, TenKhachHang = ?, SDTKhachHang = ?, ThoiGianLap=?,MaNV = ?,TongTien = ?,TienMat = ?,TienDu = ?,TrangThaiThanhToan = ? WHERE SoHD=?";
    final String DELETE_SQL = "DELETE FROM HoaDonBanAn WHERE SoHD = ?";
    final String SELECT_BY_ID_SQL = "SELECT * FROM DatMon WHERE SoHD = ?";
    final String SELECT_ALL_SQL = "SELECT * FROM HoaDonBanAn";
    final String SELECT_BY_MABAN_SQL = "SELECT*FROM HoaDonBanAn WHERE MaSoBan = ?";
    final String FIND_BY_MONTH_SQL = "SELECT * FROM HoaDonBanAn WHERE MONTH(ThoiGianLap) = ? AND YEAR(ThoiGianLap)=?";
    final String FIND_BY_YEAR_SQL = "SELECT*FROM HoaDonBanAn WHERE YEAR(ThoiGianLap) = ?";
    final String FIND_BY_DATE_SQL = "SELECT*FROM HoaDonBanAn WHERE ThoiGianLap BETWEEN ? AND ?";
    @Override
    public void insert(HoaDon entity) {
        JdbcHelper.excuteUpdate(INSERT_SQL, entity.getMaHoaDon(), entity.getMaSoBan(), entity.getTenKhachHang(), entity.getSDTKH(), entity.getMaNV(), entity.getNgayDatban(), entity.getTongTien(), entity.getTienMat(), entity.getTienDu(), entity.getTrangThai());
    }

    @Override
    public void update(HoaDon entity) {
        JdbcHelper.excuteUpdate(UPDATE_SQL, entity.getMaSoBan(), entity.getTenKhachHang(), entity.getSDTKH(), entity.getNgayDatban(), entity.getMaNV(), entity.getTongTien(), entity.getTienMat(), entity.getTienDu(), entity.getTrangThai(), entity.getMaHoaDon());
    }

    @Override
    public void delete(Integer id) {
        JdbcHelper.excuteUpdate(DELETE_SQL, id);
    }

    @Override
    public List<HoaDon> selectAll() {
        return selectBySql(SELECT_ALL_SQL);
    }

    @Override
    public HoaDon selectById(Integer id) {
        List<HoaDon> list = this.selectBySql(SELECT_BY_ID_SQL, id);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);//Trả về danh sách chứa phần tử đầu tiên
    }

    @Override
    public List<HoaDon> selectBySql(String sql, Object... args) {
        List<HoaDon> list = new ArrayList<>();
        try {
            ResultSet rs = JdbcHelper.excuteQuery(sql, args);
            while (rs.next()) {
                HoaDon entity = new HoaDon();
                entity.setMaHoaDon(rs.getInt("SoHD"));
                entity.setMaSoBan(rs.getString("MaSoBan"));
                entity.setTenKhachHang(rs.getString("TenKhachHang"));
                entity.setSDTKH(rs.getString("SDTKhachHang"));
                entity.setMaNV(rs.getString("MaNV"));
                entity.setNgayDatban(rs.getDate("ThoiGianLap"));
                entity.setTongTien(rs.getDouble("TongTien"));
                entity.setTienMat(rs.getDouble("TienMat"));
                entity.setTienDu(rs.getDouble("TienDu"));
                entity.setTrangThai(rs.getString("TrangThaiThanhToan"));
                list.add(entity);
            }
            rs.getStatement().getConnection().close();
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }

    }

    public List<HoaDon> selectByMaBan(String maSoBan) {
        List<HoaDon> list = new ArrayList<>();

        try {
            ResultSet rs = JdbcHelper.excuteQuery(SELECT_BY_MABAN_SQL, maSoBan);

            while (rs.next()) {

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
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<HoaDon> FindByDate(Date NgayDatBan) {
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

     public List<HoaDon> FindByMonth(Date NgayDatBan) {
        List<HoaDon> list = new ArrayList<>();

        try {
            ResultSet rs = JdbcHelper.excuteQuery(FIND_BY_MONTH_SQL, NgayDatBan);

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
     
      public List<HoaDon> FindByYear(Date NgayDatBan) {
        List<HoaDon> list = new ArrayList<>();

        try {
            ResultSet rs = JdbcHelper.excuteQuery(FIND_BY_YEAR_SQL, NgayDatBan);

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
