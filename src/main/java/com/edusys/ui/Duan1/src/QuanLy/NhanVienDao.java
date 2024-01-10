
package QuanLy;

import java.util.ArrayList;
import java.util.List;
import java.sql.ResultSet;
public class NhanVienDao extends QLNhanVienDao<NhanVien, String>{
    final String INSERT_SQL = "INSERT INTO NhanVien(tenNV, SoDT, CaLam, Email, VaiTro, MatKhau, XacNhanMK) values(?,?,?,?,?,?,?)";
    final String UPDATE_SQL = "UPDATE NhanVien set SoDT = ?, CaLam = ?, Email = ?, VaiTro = ?, MatKhau = ?, XacNhanMK = ? WHERE tenNV = ? ";
    final String DELETE_SQL = "DELETE FROM NhanVien WHERE tenNV = ?";
    final String SELECT_ALL_SQL = "SELECT * FROM NhanVien";
    final String SELECT_BY_ID_SQL = "SELECT * FROM NhanVien where tenNV = ?";

    @Override
    public void insert(NhanVien entity) {
        jdbcHelper.update(INSERT_SQL, entity.getTenNV(), entity.getSoDT(), entity.getGioLam(), entity.getEmail(), entity.isVaiTro(), entity.getMatKhau(), entity.getXacNhanMK());
    }

    @Override
    public void update(NhanVien entity) {
        jdbcHelper.update(UPDATE_SQL, entity.getSoDT(), entity.getGioLam(), entity.getEmail(), entity.isVaiTro(), entity.getMatKhau(), entity.getXacNhanMK(), entity.getTenNV());
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
                entity.setTenNV(rs.getString("tenNV"));
                entity.setSoDT(rs.getString("SoDT"));
                entity.setGioLam(rs.getString("CaLam"));
                entity.setEmail(rs.getString("Email"));
                entity.setVaiTro(rs.getBoolean("VaiTro"));
                entity.setMatKhau(rs.getString("MatKhau"));
                entity.setXacNhanMK(rs.getString("XacNhanMK"));
                list.add(entity);
            } 
            rs.getStatement().getConnection().close();
            return list;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
      
    }
    }
    
    
   
    
    

