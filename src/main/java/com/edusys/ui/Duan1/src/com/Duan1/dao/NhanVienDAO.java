package com.Duan1.dao;

import com.Duan1.utils.jdbcHelper;
import java.util.ArrayList;
import java.util.List;
import java.sql.ResultSet;

public class NhanVienDAO extends QLNhanVienDao<NhanVien, String> {

    final String INSERT_SQL = "INSERT INTO NhanVien(MaNV, TenNV, NgaySinh, SDT, TenDN, CaLam, Email, GioiTinh, MatKhau, VaiTro) VALUES (?,?,?,?,?,?,?,?,?,?)";
    final String UPDATE_SQL = "UPDATE NhanVien SET TenNV = ?, NgaySinh = ?, SDT = ?, TenDN = ?, CaLam = ?, Email = ?, GioiTinh = ?, MatKhau = ?, VaiTro = ? WHERE MaNV = ?";
    final String DELETE_SQL = "DELETE FROM NhanVien WHERE MaNV = ?";
    final String SELECT_ALL_SQL = "SELECT * FROM NhanVien";
    final String SELECT_BY_ID_SQL = "SELECT * FROM NhanVien WHERE MaNV = ?";
    final String SELECT_BY_KEYWORD_SQL = "SELECT * FROM NhanVien WHERE TenNV LIKE ? OR MaNV LIKE ?";

    @Override
    public void insert(NhanVien entity) {
        jdbcHelper.update(INSERT_SQL, entity.getMaNV(), entity.getTenNV(), entity.getNgaySinh(), entity.getSoDT(), entity.getTenDN(), entity.getCaLam(), entity.getEmail(), entity.isGioiTinh(), entity.getMatKhau(), entity.getVaiTro());
    }

    @Override
    public void update(NhanVien entity) {
        jdbcHelper.update(UPDATE_SQL, entity.getTenNV(), entity.getNgaySinh(), entity.getSoDT(), entity.getTenDN(), entity.getCaLam(), entity.getEmail(), entity.isGioiTinh(), entity.getMatKhau(), entity.getVaiTro(), entity.getMaNV());
    }

    @Override
    public void delete(String id) {
        jdbcHelper.update(DELETE_SQL, id);
    }

    @Override
    public List<NhanVien> selectAll() {
        return this.selectBySql(SELECT_ALL_SQL);
    }

    @Override
    public NhanVien selectById(String id) {
        List<NhanVien> list = this.selectBySql(SELECT_BY_ID_SQL, id);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    

    @Override
    protected List<NhanVien> selectBySql(String sql, Object... args) {
        List<NhanVien> list = new ArrayList<NhanVien>();
        try {
            ResultSet rs = jdbcHelper.query(sql, args);
            while (rs.next()) {
                NhanVien entity = new NhanVien();
                entity.setMaNV(rs.getString("MaNV"));
                entity.setTenNV(rs.getString("TenNV"));
                entity.setNgaySinh(rs.getDate("NgaySinh"));
                entity.setSoDT(rs.getString("SDT"));
                entity.setTenDN(rs.getString("TenDN"));
                entity.setCaLam(rs.getInt("CaLam"));
                entity.setEmail(rs.getString("Email"));
                entity.setGioiTinh(rs.getBoolean("GioiTinh"));
                entity.setMatKhau(rs.getString("MatKhau"));
                entity.setVaiTro(rs.getString("VaiTro"));
                list.add(entity);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return list;
    }
public List<NhanVien> selectByKeyword(String keyword) {
    String sql = "SELECT * FROM NhanVien WHERE TenNV LIKE ? OR MaNV LIKE ?";
    return this.selectBySql(sql, "%" + keyword + "%", "%" + keyword + "%");
}

}
