package com.Duan1.dao;

import com.Duan1.utils.jdbcHelper;
import java.util.ArrayList;
import java.util.List;
import java.sql.ResultSet;

public class PhanLoaiDAO extends QLNhanVienDao<PhanLoai, String> {

    final String INSERT_SQL = "INSERT INTO PhanCong(MaNV,CaLam, MaSoBan) VALUES (?,?,?)";
    final String UPDATE_SQL = "UPDATE PhanCong SET CaLam = ?, MaSoBan = ?  WHERE MaNV = ?";
    final String DELETE_SQL = "DELETE FROM PhanCong WHERE MaNV = ?";
    final String SELECT_ALL_SQL = "SELECT * FROM PhanCong";
    final String SELECT_BY_ID_SQL = "SELECT * FROM PhanCong WHERE MaNV = ?";

    @Override
    public void insert(PhanLoai entity) {
        jdbcHelper.update(INSERT_SQL, entity.getMaNV(), entity.getCaLam(), entity.getMaSoBan());
    }

    @Override
    public void update(PhanLoai entity) {
        jdbcHelper.update(UPDATE_SQL, entity.getCaLam(), entity.getMaSoBan(), entity.getMaNV());
    }

    @Override
    public void delete(String id) {
        jdbcHelper.update(DELETE_SQL, id);
    }

    @Override
    public List<PhanLoai> selectAll() {
        return this.selectBySql(SELECT_ALL_SQL);
    }

    @Override
    public PhanLoai selectById(String id) {
         List<PhanLoai> list = this.selectBySql(SELECT_BY_ID_SQL, id);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    @Override
    protected List<PhanLoai> selectBySql(String sql, Object... args) {
         List<PhanLoai> list = new ArrayList<PhanLoai>();
        try {
            ResultSet rs = jdbcHelper.query(sql, args);
            while (rs.next()) {
                PhanLoai entity = new PhanLoai();
                entity.setMaNV(rs.getString("MaNV"));
                entity.setCaLam(rs.getInt("CaLam"));
                entity.setMaSoBan(rs.getString("MaSoBan"));
             
                list.add(entity);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return list;
    }

}
