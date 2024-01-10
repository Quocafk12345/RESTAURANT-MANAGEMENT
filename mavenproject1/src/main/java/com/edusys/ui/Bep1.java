/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.edusys.ui;

import com.edusys.dao.BanDAO;
import com.edusys.dao.DaHoanThanhDAO;
import com.edusys.dao.DangCheBienDAO;
import com.edusys.dao.DatMonDAO;
import com.edusys.dao.HoaDonDAO;
import com.edusys.dao.LoaiNLDAO;
import com.edusys.dao.LoaiTDDAO;
import com.edusys.dao.MuaNguyenLieuDAO;
import com.edusys.dao.NguyenLieuDAO;
import com.edusys.dao.ThoiGianLamDAO;
import com.edusys.entity.Ban;
import com.edusys.entity.ChiTietHoaDon;
import com.edusys.entity.DaHoanThanh;
import com.edusys.entity.DangCheBien;
import com.edusys.entity.DatMon;
import com.edusys.entity.LoaiNL;
import com.edusys.entity.LoaiTD;
import com.edusys.entity.MuaNguyenLieu;
import com.edusys.entity.NguyenLieu;
import com.edusys.entity.ThoiGianLam;
import com.edusys.utils.ImageRenderer;
import com.edusys.utils.JdbcHelper;
import com.edusys.utils.MgsBox;
import com.edusys.utils.XImage;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.event.ListSelectionEvent;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author MS QUOC
 */
public class Bep1 extends javax.swing.JFrame {

    /**
     * Creates new form Bep1
     */
    DangCheBienDAO dangCheBienDao = new DangCheBienDAO();
    DaHoanThanhDAO daHoanThanhDAO = new DaHoanThanhDAO();
    DatMonDAO datMonDAO = new DatMonDAO();
    NguyenLieuDAO dao = new NguyenLieuDAO();
    HoaDonDAO hoadondao = new HoaDonDAO();
    MuaNguyenLieuDAO mnldao = new MuaNguyenLieuDAO();
    
    public boolean isQuanLySelected = true;
    int row = 0;

    public Bep1() {
        initComponents();
        fillToTableThucDon();
        fillToTableDangCheBien();
        fillToTableDaHoanThanh();
        loadBanAnToCbo();
        loadTGLamToCbo();
        tblDatMon.setDefaultEditor(Object.class, null);
        fillToTable();
        fillToTableMuaNguyenLieu();
        fillToTableNguyenLieu();
        editNguyenLieu();
        updateStatus();
        setTitle("QH-RESTAURANT");
        setLocationRelativeTo(null);
        setIconImage(XImage.getAppIcon());
        tblDanhSach.setDefaultEditor(Object.class, null);
        // Sử dụng hàm này trong sự kiện khi người dùng chọn một hàng trong bảng
        tblDanhSach.getSelectionModel().addListSelectionListener((ListSelectionEvent event) -> {
            if (!event.getValueIsAdjusting()) {
                setLoaiNLFromSelectedRow();
            }
        });
        cboTraCuuLoaiNguyenLieu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedLoaiNL = cboTraCuuLoaiNguyenLieu.getSelectedItem().toString();

                //Gọi hàm truy vấn và hiển thị bản ghi của loại thực đơn được chọn
                List<NguyenLieu> records = dao.selectByLoaiNL(selectedLoaiNL);

                //Hiển thị danh sách bản ghi lên bảng tblDanhSach
                showRecordsByLoaiNL();
            }
        });
    }

    //FORM THỰC ĐƠN
    void fillToTableThucDon() {
        DefaultTableModel model = (DefaultTableModel) tblDatMon.getModel();
        model.setRowCount(0);
        try {
            List<DatMon> list = datMonDAO.selectAll();
            for (DatMon datMon : list) {
                Object[] row = {
                    datMon.getMaOrder(), datMon.getMaSoBan(), datMon.getTenMon(), datMon.getDonGia(), datMon.getSoLuong()
                };
                model.addRow(row);
            }
        } catch (Exception e) {
            MgsBox.alert(this, "Lỗi truy vấn dữ liệu!");
        }
    }

    void fillToTableDangCheBien() {
        DefaultTableModel model = (DefaultTableModel) tblDangCheBien.getModel();
        model.setRowCount(0);
        try {
            List<DangCheBien> list = dangCheBienDao.selectAll();
            for (DangCheBien dangCheBien : list) {
                Object[] row = {
                    dangCheBien.getDangCheBienID(), dangCheBien.getMaSoBan(), dangCheBien.getThoiGianLam(), dangCheBien.getTenMon(), dangCheBien.getSoLuong()
                };
                model.addRow(row);
            }
        } catch (Exception e) {
            MgsBox.alert(this, "Lỗi truy vấn dữ liệu!");
        }
    }

    void fillToTableDaHoanThanh() {
        DefaultTableModel model = (DefaultTableModel) tblDaHoanThanh.getModel();
        model.setRowCount(0);
        try {
            List<DaHoanThanh> list = daHoanThanhDAO.selectAll();
            for (DaHoanThanh daHoanThanh : list) {
                Object[] row = {
                    daHoanThanh.getDaHoanThanhID(), daHoanThanh.getMaSoBan(), daHoanThanh.getTenMon(), daHoanThanh.getSoLuong()
                };
                model.addRow(row);
            }
        } catch (Exception e) {
        }
    }

    void setForm(DatMon datMon) {

        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
        cboChonBan.setSelectedItem(datMon.getMaSoBan());
        txtTenMon.setText(datMon.getTenMon());
        spnSoLuong.setValue(datMon.getSoLuong());
    }

    void clearForm() {

        txtTenMon.setText("");
    }

    private DangCheBien getFormDangCheBien() {
        DangCheBien dangCheBien = new DangCheBien();
        dangCheBien.setMaSoBan(cboChonBan.getSelectedItem().toString());
        dangCheBien.setTenMon(txtTenMon.getText());
        dangCheBien.setThoiGianLam(cboThoiGianLam.getSelectedItem().toString());
        dangCheBien.setSoLuong((Integer) (spnSoLuong.getValue()));
        return dangCheBien;
    }

    private DaHoanThanh getFormDaHoanThanh() {
        DaHoanThanh daHoanThanh = new DaHoanThanh();
        int selectedRow = tblDangCheBien.getSelectedRow();
        daHoanThanh.setMaSoBan((String) tblDangCheBien.getValueAt(selectedRow, 1));
        daHoanThanh.setTenMon((String) tblDangCheBien.getValueAt(selectedRow, 3));
        daHoanThanh.setSoLuong((Integer) tblDangCheBien.getValueAt(selectedRow, 4));

        return daHoanThanh;
    }

    void InsertDangCheBien() {
        DangCheBien entity = getFormDangCheBien();
        if (entity != null) {
            dangCheBienDao.insert(entity);
            fillToTableDangCheBien();
            clearForm();
        }
    }
    
     void UpdateDangCheBien() {
        DangCheBien entity = getFormDangCheBien();
        if (entity != null) {
            dangCheBienDao.update(entity);
            fillToTableDangCheBien();
            clearForm();
        }
    }

    void InsertDaHoanThanh() {
        DaHoanThanh entity = getFormDaHoanThanh();
        if (entity != null) {
            daHoanThanhDAO.insert(entity);
            fillToTableDaHoanThanh();
            clearForm();
        }
    }

    void loadBanAnToCbo() {
        DefaultComboBoxModel model = (DefaultComboBoxModel) cboChonBan.getModel();
        model.removeAllElements();

        BanDAO dao = new BanDAO();
        List<Ban> list = dao.selectAll();
        for (Ban ban : list) {
            model.addElement(ban.getMaSoBan());
        }

    }

    void loadTGLamToCbo() {
        DefaultComboBoxModel model = (DefaultComboBoxModel) cboThoiGianLam.getModel();
        model.removeAllElements();

        ThoiGianLamDAO dao = new ThoiGianLamDAO();
        List<ThoiGianLam> list = dao.selectAll();
        for (ThoiGianLam thoiGianLam : list) {
            model.addElement(thoiGianLam.getThoiGianLam());
        }

    }

    void editTblThucDon() {
        int selectedRow = tblDatMon.getSelectedRow();
        if (selectedRow != -1) {
            Object maDangCheBienObject = tblDatMon.getValueAt(selectedRow, 0);
            if (maDangCheBienObject != null) {
                int maDangCheBien = (Integer) maDangCheBienObject;
                // Gọi hàm xóa với mã món
                DatMon datmon = datMonDAO.selectById(maDangCheBien);
                setForm(datmon);
                fillToTableDangCheBien();
            }
        }
    }

    void delete() {
        int selectedRow = tblDangCheBien.getSelectedRow();
        if (selectedRow != -1) {
            Object maDangCheBienObject = tblDangCheBien.getValueAt(selectedRow, 0);
            if (maDangCheBienObject != null) {
                int maDangCheBien = (Integer) maDangCheBienObject;
                dangCheBienDao.delete(maDangCheBien);
                fillToTableDangCheBien();
            }
        }
    }

    void deleteDaHoanThanh() {
        int selectedRow = tblDaHoanThanh.getSelectedRow();
        if (selectedRow != -1) {
            Object maDaHoanThanhObject = tblDaHoanThanh.getValueAt(selectedRow, 0);
            if (maDaHoanThanhObject != null) {
                if (MgsBox.confirm(this, "Bạn thực sự muốn xóa món này?")) {
                    int maDaHoanThanh = (Integer) maDaHoanThanhObject;
                    daHoanThanhDAO.delete(maDaHoanThanh);
                    fillToTableDaHoanThanh();
                } else {
                    MgsBox.alert(this, "Xóa thất bại!");
                }
            }
        }
    }

    //FORM NGUYÊN LIỆU
     void fillToTable() {
        DefaultTableModel model = (DefaultTableModel) tblDanhSach.getModel();
        model.setRowCount(0);
        try {
            List<NguyenLieu> list = dao.selectAll();

            int columnCount = tblDanhSach.getColumnCount();
            int columnWidth = 100;
            for (NguyenLieu td : list) {
                Image im = Toolkit.getDefaultToolkit().createImage(td.getHinh());
                ImageIcon ic = new ImageIcon(im);
                Object[] rows = {td.getMaNguyenLieu(),ic,  td.getTenNguyenLieu(), td.getDonGia(), td.getNgayADGia(), td.getDonViTinh(), td.getTenLoai()};
                model.addRow(rows);
            }
        } catch (Exception e) {
            e.printStackTrace();
            MgsBox.alert(this, "Lỗi truy vấn dữ liệu!");
        }
        tblDanhSach.getColumnModel().getColumn(1).setCellRenderer(new ImageRenderer());
    }

    private ImageIcon getImageIcon(String imagePath) {
        if (imagePath != null && !imagePath.isEmpty()) {
            return new ImageIcon(imagePath);
        }
        return null;
    }

    private void insertNguyenLieu() {
        NguyenLieu entity = getFormNguyenLieu();
        if (lblHinhAnh.getIcon() != null) {
            dao.insert(entity);
            fillToTable();
            clearForm();
        } else {
            MgsBox.alert(this, "Chưa chọn hình ảnh!");
        }
    }

    private void updateNguyenLieu() {
        NguyenLieu model = getFormNguyenLieu();
        if (lblHinhAnh.getIcon() != null) {
            dao.update(model);
            fillToTable();
            clearForm();
            MgsBox.alert(this, "Sửa món thành công!");
        } else {
            MgsBox.alert(this, "Cập nhật thất bại!");
        }
    }

    public void deleteNguyenLieu() {

        if (MgsBox.confirm(this, "Bạn thực sự muốn xóa món này?")) {
            String MaNguyenLieu = txtTenNguyenLieu.getText();
                dao.delete(MaNguyenLieu);
                this.fillToTable();
                this.clearForm();
                MgsBox.alert(this, "Xóa món thành công");
            
        }else{
            MgsBox.alert(this, "Xóa món thất bại!");
        }

    }

    void editNguyenLieu() {
        try {
            int selectedRow = tblDanhSach.getSelectedRow();
            if (selectedRow >= 0) {
                String maTD = tblDanhSach.getValueAt(this.row, 0).toString();
                NguyenLieu td = dao.selectById(maTD);
                if (td != null) {
                    setForm(td);
                    updateStatus();
                }
            } else {
                clearForm();
            };
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    void clearFormNguyenLieu() {
        txtTenNguyenLieu.setText("");
        txtDonViTinh.setText("");
        txtDonGia.setText("");
        cboLoaiNL.setSelectedIndex(0); // Chọn mục đầu tiên hoặc một giá trị mặc định khác
        dateNAD.setDate(new Date()); // Đặt ngày hiện tại hoặc ngày mặc định khác
    }

    void setForm(NguyenLieu td) {
        txtMaNguyenLieu.setText(Integer.valueOf(td.getMaNguyenLieu()).toString());
        String imagePath = td.getHinh();
        ImageIcon imageIcon = new ImageIcon(imagePath);
        lblHinhAnh.setIcon(imageIcon);
        txtTenNguyenLieu.setText(td.getTenNguyenLieu());

        txtDonGia.setText(String.valueOf(td.getDonGia()));
        txtDonViTinh.setText(td.getDonViTinh());
        dateNAD.setDate(td.getNgayADGia());
        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
        cboLoaiNL.setSelectedItem(td.getTenLoai());
    }

    private String chooseImage() {
        JFileChooser fileChooser = new JFileChooser("C:\\Users\\MS QUOC\\Documents\\NetBeansProjects\\mavenproject1\\src\\main\\resources\\com\\edusys\\icon\\images");
        fileChooser.setDialogTitle("Chọn hình ảnh");

        // Chỉ chấp nhận các loại file hình ảnh
        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Hình ảnh", "jpg", "png", "gif"));

        int result = fileChooser.showOpenDialog(this);

        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            return selectedFile.getAbsolutePath();
        }

        return null;
    }

    private NguyenLieu getFormNguyenLieu() {
        NguyenLieu td = new NguyenLieu();
        td.setMaNguyenLieu(Integer.parseInt(txtMaNguyenLieu.getText()));
        ImageIcon imageIcon = (ImageIcon) lblHinhAnh.getIcon();
        if (imageIcon != null) {
            String imagePath = imageIcon.getDescription();
            td.setHinh(imagePath);
        }
        td.setTenNguyenLieu(txtTenNguyenLieu.getText());
        td.setDonGia(Double.parseDouble(txtDonGia.getText()));
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        td.setNgayADGia(dateNAD.getDate());
        td.setDonViTinh(txtDonViTinh.getText());
        td.setTenLoai((String) cboLoaiNL.getSelectedItem());

        return td;
    }

    void updateStatus() {
        boolean edit = (this.row >= 0);
        boolean first = (this.row == 0);
        boolean last = (this.row == tblDanhSach.getRowCount() - 1);

        btnThem.setEnabled(edit);
        btnLamMoi.setEnabled(edit);
        btnXoa.setEnabled(edit);

        btnFirst.setEnabled(edit && !first);
        btnPre.setEnabled(edit && !first);
        btnNext.setEnabled(edit && !last);
        btnLast.setEnabled(edit && !last);
        NguyenLieu td = new NguyenLieu();

    }

    void first() {
        this.row = 0;
        this.editNguyenLieu();
    }

    void pre() {
        if (this.row > 0) {
            this.row--;
            this.editNguyenLieu();
        }
    }

    void next() {
        if (this.row < tblDanhSach.getRowCount() - 1) {
            this.row++;
            this.editNguyenLieu();
        }
    }

    void last() {
        this.row = tblDanhSach.getRowCount() - 1;
        this.editNguyenLieu();
    }

    void loadLoaiNguyenLieuToCbo() {
        DefaultComboBoxModel model = (DefaultComboBoxModel) cboLoaiNL.getModel();
        model.removeAllElements();

        //Lấy danh sách loại thực đơn từ database
        LoaiNLDAO dao = new LoaiNLDAO();
        List<LoaiNL> list = dao.selectAll();

        //Thêm loại thực đơn vào comboBox
        for (LoaiNL loaiTD : list) {
            model.addElement(loaiTD.getTenLoai());

        }
    }

    void loadLoaiNguyenLieuToCboTraCuuLoaiNL() {
        DefaultComboBoxModel model = (DefaultComboBoxModel) cboTraCuuLoaiNguyenLieu.getModel();
        model.removeAllElements();

        LoaiNLDAO dao = new LoaiNLDAO();
        List<LoaiNL> list = dao.selectAll();

        for (LoaiNL loaiTD : list) {

            model.addElement(loaiTD.getTenLoai());
        }

    }

    private void setLoaiNLFromSelectedRow() {
        //Chỉ mục của cột loại thực đơn
        final int LOAI_TD_COLUMN_INDEX = 6;

        //Lấy chỉ mục của hàng được chọn
        int selectedRow = tblDanhSach.getSelectedRow();
        if (selectedRow >= 0) {
            //Lấy giá trị của cột loại thực đơn
            Object loaiNguyenLieuValue = tblDanhSach.getValueAt(selectedRow, LOAI_TD_COLUMN_INDEX);

            //Đặt giá trị cho cboLoaiNL
            cboLoaiNL.setSelectedItem(loaiNguyenLieuValue.toString());
            System.out.println("Selected Loai Thuc Don: " + loaiNguyenLieuValue.toString());

        }
    }

    private void showRecordsByLoaiNL() {
        //Lấy loại thực đon được chọn từ cboLoaiNL
        String selectedLoaiNL = cboTraCuuLoaiNguyenLieu.getSelectedItem().toString();

        //Gọi hàm truy vấn và hiển thị bản ghi của loại thực đơn được chọn
        List<NguyenLieu> records = dao.selectByLoaiNL(selectedLoaiNL);

        loadLoaiNguyenLieuToCboTraCuuLoaiNL();
        //Hiển thị danh sách bản ghi lên bảng tblDanhSach
        HienThiBanGhiLoaiNguyenLieu(records);
    }

    private void HienThiBanGhiLoaiNguyenLieu(List<NguyenLieu> records) {
        DefaultTableModel model = (DefaultTableModel) tblDanhSach.getModel();
        model.setRowCount(0);

        for (NguyenLieu td : records) {
            Object[] rows = {td.getMaNguyenLieu(), getImageIcon(td.getHinh()), td.getTenNguyenLieu(), td.getDonGia(), td.getNgayADGia(), td.getDonViTinh(), td.getTenLoai()};
            model.addRow(rows);
        }
        tblDanhSach.setModel(model);
    }

    public List<NguyenLieu> searchByName(String tenNguyenLieu) {
    List<NguyenLieu> resultList = new ArrayList<>();

    try {
        // Sử dụng PreparedStatement để tránh vấn đề về SQL injection
        String sql = "SELECT * FROM NguyenLieu WHERE TenNguyenLieu LIKE ?";
        // "%" + tenNguyenLieu + "%" để tìm kiếm tất cả các món có tên chứa tenNguyenLieu
        ResultSet rs = JdbcHelper.excuteQuery(sql, "%" + tenNguyenLieu + "%");

        while (rs.next()) {
            NguyenLieu entity = new NguyenLieu();
            entity.setMaNguyenLieu(rs.getInt("MaNguyenLieu"));
            entity.setHinh(rs.getString("Hinh"));
            entity.setTenNguyenLieu(rs.getString("TenNguyenLieu"));
            entity.setDonGia(rs.getDouble("DonGia"));
            entity.setNgayADGia(rs.getDate("NgayADGia"));
            entity.setDonViTinh(rs.getString("DonViTinh"));
            entity.setTenLoai(rs.getString("TenLoai"));
            entity.setMaLoai(rs.getInt("MaLoai"));
            resultList.add(entity);
        }
    } catch (Exception e) {
        e.printStackTrace();
    }

    return resultList;
}

    private void TimKiemNguyenLieu(List<NguyenLieu> records) {
        DefaultTableModel model = (DefaultTableModel) tblDanhSach.getModel();
        model.setRowCount(0);

        for (NguyenLieu record : records) {
            Object[] rows = {record.getMaNguyenLieu(), getImageIcon(record.getHinh()), record.getTenNguyenLieu(), record.getDonGia(), record.getNgayADGia(), record.getDonViTinh(), record.getTenLoai()};
            model.addRow(rows);
        }
        tblDanhSach.setModel(model);
    }
    
    
    //form cập nhật nguyên liệu đã mua
      private int getSoLuongFromSpinner() {
        int value = (Integer) spnSoLuong.getValue();
        System.out.println("Spinner value: " + value);
        return (int) value;
    }

    void fillToTableNguyenLieu() {
        DefaultTableModel model = (DefaultTableModel) tblThucDon.getModel();
        model.setRowCount(0);
        try {
            List<MuaNguyenLieu> list = mnldao.selectAll();

            int columnCount = tblThucDon.getColumnCount();
            int columnWidth = 100;
            for (MuaNguyenLieu mnl : list) {
                Image im = Toolkit.getDefaultToolkit().createImage(mnl.getHinhAnh());
                ImageIcon ic = new ImageIcon(im);
                Object[] row
                        = {mnl.getMaNguyenLieu(), ic, mnl.getTenMon(), mnl.getDonGia()};
                model.addRow(row);
            }
        } catch (Exception e) {
            e.printStackTrace();
            MgsBox.alert(this, "Lỗi truy vấn dữ liệu!");
        }
        tblThucDon.getColumnModel().getColumn(1).setCellRenderer(new ImageRenderer());
    }

    void fillToTableMuaNguyenLieu() {
        DefaultTableModel model = (DefaultTableModel) tblTDDuocChon.getModel();
        model.setRowCount(0);
        try {
            List<MuaNguyenLieu> list = mnldao.selectAll();
            for (MuaNguyenLieu muaNguyenLieu : list) {
                Object[] row = {
                    muaNguyenLieu.getSoHD(), muaNguyenLieu.getMaMuaNL(), muaNguyenLieu.getTenLoai(), muaNguyenLieu.getTenMon(),muaNguyenLieu.getSoLuong(), muaNguyenLieu.getDonGia()
                };
                model.addRow(row);

            }
        } catch (Exception e) {
            MgsBox.alert(this, "Lỗi truy vấn dữ liệu");
        }
    }

    private MuaNguyenLieu getFormMuaNguyenLieu() {
        MuaNguyenLieu muaNguyenLieu = new MuaNguyenLieu();
        int selectedRow = tblThucDon.getSelectedRow();
        muaNguyenLieu.setSoHD((Integer) tblThucDon.getValueAt(selectedRow, 0));
        muaNguyenLieu.setTenLoai((String) cboThucDon.getSelectedItem());
        muaNguyenLieu.setTenMon((String) tblThucDon.getValueAt(selectedRow, 2));
        muaNguyenLieu.setDonGia((Double) tblThucDon.getValueAt(selectedRow, 3));
        muaNguyenLieu.setSoLuong((Integer) spnSoLuong.getValue());
        Date date = null;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd ");
        try {
            date = dateFormat.parse(lblTime.getText());
        } catch (Exception e) {
        }
        muaNguyenLieu.setThoiGianLap(date);
        return muaNguyenLieu;
    }

    void setFormMuaNguyenLieu(MuaNguyenLieu muaNguyenLieu) {
        txtMaHoaDon.setText(Integer.valueOf(muaNguyenLieu.getSoHD()).toString());
        txtMaMua.setText(String.valueOf(muaNguyenLieu.getMaMuaNL()));
        txtTenLoai.setText(muaNguyenLieu.getTenLoai().toString());
        txtTenNguyenLieu.setText(muaNguyenLieu.getTenMon());
        txtDonGia.setText(String.valueOf(muaNguyenLieu.getDonGia()));
        spnSoLuong1.setValue(muaNguyenLieu.getSoLuong());
    }

    void edit() {

        int selectedRow = tblTDDuocChon.getSelectedRow();
        if (selectedRow != -1) {
            // Có dòng được chọn
            Object maMuaNguyenLieuObject = tblTDDuocChon.getValueAt(selectedRow, 1);

            if (maMuaNguyenLieuObject != null) {
                int maMuaNguyenLieu = (Integer) maMuaNguyenLieuObject;

                // Gọi hàm xóa với mã món
                MuaNguyenLieu muaNguyenLieu = mnldao.selectById(maMuaNguyenLieu);
                setFormMuaNguyenLieu(muaNguyenLieu);
                fillToTableMuaNguyenLieu();
            }
        }

    }

    void clearFormMuaNguyenLieu() {
        txtMaHoaDon.setText("");
        txtMaMua.setText("");
        txtTenLoai.setText("");
        txtTenNguyenLieu.setText("");
        txtDonGia.setText("");
        txtSoHD.setText("");
        spnSoLuong1.setValue(null);
    }

    private int getSoLuongFromSpinner1() {
        int value = (Integer) spnSoLuong1.getValue();
        System.out.println("Spinner value: " + value);
        return (int) value;
    }

    private ChiTietHoaDon getFormChiTietHoaDon() {
        ChiTietHoaDon chiTietHoaDon = new ChiTietHoaDon();
        int selectedRow = tblThucDon.getSelectedRow();
        chiTietHoaDon.setTenLoai((String) cboThucDon.getSelectedItem());
        chiTietHoaDon.setTenMon((String) tblThucDon.getValueAt(selectedRow, 0));
        chiTietHoaDon.setDonGia((Double) tblThucDon.getValueAt(selectedRow, 1));
        chiTietHoaDon.setSoLuong((Integer) spnSoLuong.getValue());
        chiTietHoaDon.setSoHD(Integer.parseInt(txtSoHD.getText()));
        return chiTietHoaDon;
    }

    void insertMuaNguyenLieu() {
        MuaNguyenLieu entity = getFormMuaNguyenLieu();
        mnldao.insert(entity);
        fillToTableMuaNguyenLieu();

    }

 

    private void updateMuaNguyenLieu() {
    int selectedRow = tblTDDuocChon.getSelectedRow();

    if (selectedRow != -1) {
        Object maMuaNguyenLieuObject = tblTDDuocChon.getValueAt(selectedRow, 1);

        if (maMuaNguyenLieuObject != null) {
            int maMuaNguyenLieu = (Integer) maMuaNguyenLieuObject;
            MuaNguyenLieu model = mnldao.selectById(maMuaNguyenLieu);

            if (model != null) {
                int newSoLuong = getSoLuongFromSpinner();
                
                // Check if newSoLuong is less than 1, set it to 1
                if (newSoLuong < 1) {
                    newSoLuong = 1;
                }
                
                model.setSoLuong(newSoLuong);
                mnldao.update(model);
                fillToTableMuaNguyenLieu();
                clearFormMuaNguyenLieu();
                MgsBox.alert(this, "Cập nhật thành công");
            } else {
                MgsBox.alert(this, "Dữ liệu không hợp lệ cho mã món.");
            }
        } else {
            MgsBox.alert(this, "Dữ liệu không hợp lệ cho mã món.");
        }
    } else {
        MgsBox.alert(this, "Vui lòng chọn một món để cập nhật.");
    }
}


    private void updateHoaDon() {
        int selectedRow = tblTDDuocChon.getSelectedRow();

        if (selectedRow != -1) {
            Object maHoaDonObject = tblTDDuocChon.getValueAt(selectedRow, 0);

            if (maHoaDonObject != null) {
                int maHoaDon = (Integer) maHoaDonObject;
                com.edusys.entity.HoaDon model = hoadondao.selectById(maHoaDon);

                if (model != null) {
                    hoadondao.update(model);
                    fillToTableMuaNguyenLieu();
                    clearFormMuaNguyenLieu();
                    MgsBox.alert(this, "Cập nhật thành công");
                } else {
                    MgsBox.alert(this, "Dữ liệu không hợp lệ cho mã món.");
                }
            } else {
                MgsBox.alert(this, "Dữ liệu không hợp lệ cho mã món.");
            }
        } else {
            MgsBox.alert(this, "Vui lòng chọn một món để cập nhật.");
        }
    }

    private void deleteMuaNguyenLieu() {
        int selectedRow = tblTDDuocChon.getSelectedRow();

        if (selectedRow != -1) {
            // Có dòng được chọn
            Object maMuaNguyenLieuObject = tblTDDuocChon.getValueAt(selectedRow, 2);

            if (maMuaNguyenLieuObject != null) {
                int maMuaNguyenLieu = (Integer) maMuaNguyenLieuObject;

                if (MgsBox.confirm(this, "Bạn thực sự muốn xóa món này?")) {
                    // Gọi hàm xóa với mã món
                    mnldao.delete(maMuaNguyenLieu);
                    fillToTableMuaNguyenLieu();
                    MgsBox.alert(this, "Xóa món thành công");
                } else {
                    MgsBox.alert(this, "Xóa món thất bại!");
                }
            }
        } else {
        }
    }

    private void deleteChiTietHoaDon() {
        int selectedRow = tblTDDuocChon.getSelectedRow();

        if (selectedRow != -1) {
            // Có dòng được chọn
            Object maHoaDonObject = tblTDDuocChon.getValueAt(selectedRow, 0);

            if (maHoaDonObject != null) {
                int maHoaDon = (Integer) maHoaDonObject;

                if (MgsBox.confirm(this, "Bạn thực sự muốn xóa món này?")) {
                    // Gọi hàm xóa với mã món
                    hoadondao.delete(maHoaDon);
                    fillToTableMuaNguyenLieu();
                    MgsBox.alert(this, "Xóa món thành công");
                } else {
                    MgsBox.alert(this, "Xóa món thất bại!");
                }
            }
        } else {
            MgsBox.alert(this, "Vui lòng chọn một món để xóa.");
        }
    }

    void loadLoaiThucDonToCBO() {
        DefaultComboBoxModel model = (DefaultComboBoxModel) cboThucDon.getModel();
        model.removeAllElements();

        LoaiTDDAO dao = new LoaiTDDAO();
        List<LoaiTD> list = dao.selectAll();

        for (LoaiTD loaiTD : list) {
            model.addElement(loaiTD.getTenLoai());
        }
    }

 

    

    //Hiển thị bản ghi loại thực đơn
    void ShowRecordsByLoaiTD() {
        String selectedLoaiTD = cboThucDon.getSelectedItem().toString();

        List<MuaNguyenLieu> records = mnldao.selectByLoaiTD(selectedLoaiTD);

        loadLoaiThucDonToCBO();

        HienThiBanGhiLoaiTD(records);
    }

    void HienThiBanGhiLoaiTD(List<MuaNguyenLieu> records) {
        DefaultTableModel model = (DefaultTableModel) tblThucDon.getModel();
        model.setRowCount(0);

        for (MuaNguyenLieu record : records) {
            Image im = Toolkit.getDefaultToolkit().createImage(record.getHinhAnh());
            ImageIcon ic = new ImageIcon(im);

            Object[] rows = {record.getMaMuaNL(), ic, record.getTenMon(), record.getDonGia()};
            model.addRow(rows);
        }
        tblThucDon.setModel(model);

    }

    public String getSelectedTableCode() {
        int selectedRow = tblTDDuocChon.getSelectedRow();
        if (selectedRow != -1) {
            return (String) tblTDDuocChon.getValueAt(selectedRow, 0);
        }
        return null;
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel4 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblDatMon = new javax.swing.JTable();
        jLabel9 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblDangCheBien = new javax.swing.JTable();
        jLabel10 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        cboChonBan = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        txtTenMon = new javax.swing.JTextField();
        spnSoLuong = new javax.swing.JSpinner();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        btnThem = new javax.swing.JButton();
        cboThoiGianLam = new javax.swing.JComboBox<>();
        jPanel6 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblDaHoanThanh = new javax.swing.JTable();
        jLabel11 = new javax.swing.JLabel();
        btnXoaDHT = new javax.swing.JButton();
        jPanel7 = new javax.swing.JPanel();
        btnHoanThanh = new javax.swing.JButton();
        btnXoa = new javax.swing.JButton();
        btnSua = new javax.swing.JButton();
        cc = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblDanhSach = new javax.swing.JTable();
        btnFirst = new javax.swing.JButton();
        btnPre = new javax.swing.JButton();
        btnNext = new javax.swing.JButton();
        btnLast = new javax.swing.JButton();
        btnXoa1 = new javax.swing.JButton();
        btnThem1 = new javax.swing.JButton();
        btnLamMoi = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txtTenNguyenLieu = new javax.swing.JTextField();
        cboLoaiNL = new javax.swing.JComboBox<>();
        jLabel8 = new javax.swing.JLabel();
        txtDonGia = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        dateNAD = new com.toedter.calendar.JDateChooser();
        jLabel14 = new javax.swing.JLabel();
        txtDonViTinh = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        lblHinhAnh = new javax.swing.JLabel();
        btnSua1 = new javax.swing.JButton();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        txtMaNguyenLieu = new javax.swing.JTextField();
        jPanel9 = new javax.swing.JPanel();
        jLabel19 = new javax.swing.JLabel();
        txtTraCuuTen = new javax.swing.JTextField();
        cboTraCuuLoaiNguyenLieu = new javax.swing.JComboBox<>();
        btnTim = new javax.swing.JButton();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jPanel10 = new javax.swing.JPanel();
        jPanel11 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        tblTDDuocChon = new javax.swing.JTable();
        jButton2 = new javax.swing.JButton();
        jPanel12 = new javax.swing.JPanel();
        jLabel22 = new javax.swing.JLabel();
        cboThucDon = new javax.swing.JComboBox<>();
        jScrollPane6 = new javax.swing.JScrollPane();
        tblThucDon = new javax.swing.JTable();
        spnSoLuong1 = new javax.swing.JSpinner();
        jLabel24 = new javax.swing.JLabel();
        jButton4 = new javax.swing.JButton();
        txtSoHD = new javax.swing.JTextField();
        jLabel25 = new javax.swing.JLabel();
        jPanel13 = new javax.swing.JPanel();
        jLabel26 = new javax.swing.JLabel();
        txtTenLoai = new javax.swing.JTextField();
        txtTenNguyenLieu1 = new javax.swing.JTextField();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        txtDonGia1 = new javax.swing.JTextField();
        spnSoLuong2 = new javax.swing.JSpinner();
        jLabel29 = new javax.swing.JLabel();
        btnCapNhat = new javax.swing.JButton();
        jLabel30 = new javax.swing.JLabel();
        txtMaHoaDon = new javax.swing.JTextField();
        jLabel31 = new javax.swing.JLabel();
        txtMaMua = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        lblTime = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(149, 35, 35));

        jPanel4.setBackground(new java.awt.Color(149, 35, 35));
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBackground(new java.awt.Color(218, 212, 181));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tblDatMon.setFont(new java.awt.Font("Segoe UI Black", 0, 12)); // NOI18N
        tblDatMon.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Hình ảnh", "Mã bàn", "Tên thực đơn", "Số lượng"
            }
        ));
        tblDatMon.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblDatMonMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblDatMon);

        jPanel2.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 80, 530, 550));

        jLabel9.setBackground(new java.awt.Color(218, 212, 181));
        jLabel9.setFont(new java.awt.Font("Segoe UI Black", 1, 24)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(167, 49, 33));
        jLabel9.setText("Danh sách thực đơn");
        jPanel2.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 0, 260, 70));

        jPanel4.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 554, 700));

        jPanel5.setBackground(new java.awt.Color(218, 212, 181));
        jPanel5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tblDangCheBien.setFont(new java.awt.Font("Segoe UI Black", 1, 12)); // NOI18N
        tblDangCheBien.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "ID", "Mã bàn", "Thời gian làm", "Tên món", "Số lượng"
            }
        ));
        jScrollPane2.setViewportView(tblDangCheBien);

        jPanel5.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, 740, 210));

        jLabel10.setBackground(new java.awt.Color(218, 212, 181));
        jLabel10.setFont(new java.awt.Font("Segoe UI Black", 1, 18)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(167, 49, 33));
        jLabel10.setText("Danh sách thực đơn đang chế biến");
        jPanel5.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 10, 330, 30));

        jPanel4.add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(820, 0, 766, 283));

        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setText("Bàn:");
        jPanel3.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(29, 21, 56, -1));

        cboChonBan.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Chọn bàn", " " }));
        jPanel3.add(cboChonBan, new org.netbeans.lib.awtextra.AbsoluteConstraints(29, 43, 180, -1));

        jLabel3.setText("Tên món:");
        jPanel3.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(29, 87, 66, -1));
        jPanel3.add(txtTenMon, new org.netbeans.lib.awtextra.AbsoluteConstraints(29, 109, 180, -1));
        jPanel3.add(spnSoLuong, new org.netbeans.lib.awtextra.AbsoluteConstraints(29, 165, -1, -1));

        jLabel4.setText("Số lượng");
        jPanel3.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(29, 143, 66, -1));

        jLabel5.setText("Thời gian làm:");
        jPanel3.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(29, 199, -1, -1));

        btnThem.setBackground(new java.awt.Color(0, 102, 102));
        btnThem.setFont(new java.awt.Font("Segoe UI Black", 1, 18)); // NOI18N
        btnThem.setForeground(new java.awt.Color(255, 255, 255));
        btnThem.setText("CHẾ BIẾN");
        btnThem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemActionPerformed(evt);
            }
        });
        jPanel3.add(btnThem, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 260, 180, 59));

        cboThoiGianLam.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "30 phút", "1 giờ", "1 giờ 30 phút", "2 giờ", "2 giờ 30 phút", "3 giờ", " " }));
        jPanel3.add(cboThoiGianLam, new org.netbeans.lib.awtextra.AbsoluteConstraints(29, 221, 180, -1));

        jPanel4.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 0, 253, 370));

        jPanel6.setBackground(new java.awt.Color(218, 212, 181));
        jPanel6.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tblDaHoanThanh.setFont(new java.awt.Font("Segoe UI Black", 1, 12)); // NOI18N
        tblDaHoanThanh.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "ID", "Mã bàn", "Tên món", "Số lượng"
            }
        ));
        jScrollPane3.setViewportView(tblDaHoanThanh);

        jPanel6.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, 740, 210));

        jLabel11.setBackground(new java.awt.Color(218, 212, 181));
        jLabel11.setFont(new java.awt.Font("Segoe UI Black", 1, 18)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(167, 49, 33));
        jLabel11.setText("Danh sách thực đơn đã hoàn thành");
        jPanel6.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 10, 330, 30));

        btnXoaDHT.setBackground(new java.awt.Color(0, 102, 102));
        btnXoaDHT.setFont(new java.awt.Font("Segoe UI Black", 0, 12)); // NOI18N
        btnXoaDHT.setForeground(new java.awt.Color(255, 255, 255));
        btnXoaDHT.setText("XÓA");
        btnXoaDHT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaDHTActionPerformed(evt);
            }
        });
        jPanel6.add(btnXoaDHT, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 260, 129, 51));

        jPanel4.add(jPanel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(820, 370, 766, 320));

        btnHoanThanh.setBackground(new java.awt.Color(0, 102, 102));
        btnHoanThanh.setFont(new java.awt.Font("Segoe UI Black", 0, 12)); // NOI18N
        btnHoanThanh.setForeground(new java.awt.Color(255, 255, 255));
        btnHoanThanh.setText("HOÀN THÀNH");
        btnHoanThanh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHoanThanhActionPerformed(evt);
            }
        });

        btnXoa.setBackground(new java.awt.Color(0, 102, 102));
        btnXoa.setFont(new java.awt.Font("Segoe UI Black", 0, 12)); // NOI18N
        btnXoa.setForeground(new java.awt.Color(255, 255, 255));
        btnXoa.setText("XÓA");
        btnXoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaActionPerformed(evt);
            }
        });

        btnSua.setBackground(new java.awt.Color(0, 102, 102));
        btnSua.setFont(new java.awt.Font("Segoe UI Black", 0, 12)); // NOI18N
        btnSua.setForeground(new java.awt.Color(255, 255, 255));
        btnSua.setText("SỬA");
        btnSua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnSua, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnXoa, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnHoanThanh, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnXoa, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(btnHoanThanh, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(btnSua, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        jPanel4.add(jPanel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(820, 280, -1, 90));

        jTabbedPane1.addTab("THỰC ĐƠN", jPanel4);

        cc.setBackground(new java.awt.Color(149, 35, 35));
        cc.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel8.setBackground(new java.awt.Color(218, 212, 181));

        jLabel12.setFont(new java.awt.Font("Segoe UI Black", 1, 18)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(167, 49, 33));
        jLabel12.setText("Danh Sách Nguyên Liệu");

        tblDanhSach.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "ID", "Hình ảnh", "Tên nguyên liệu", "Đơn Giá", "Ngày áp dụng giá", "Đơn Vị Tính", "Loại nguyên liệu"
            }
        ));
        tblDanhSach.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblDanhSachMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(tblDanhSach);

        btnFirst.setText("<<");
        btnFirst.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFirstActionPerformed(evt);
            }
        });

        btnPre.setText("<--");
        btnPre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPreActionPerformed(evt);
            }
        });

        btnNext.setText("-->");
        btnNext.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNextActionPerformed(evt);
            }
        });

        btnLast.setText(">>");
        btnLast.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLastActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane4))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGap(280, 280, 280)
                        .addComponent(jLabel12)
                        .addGap(27, 27, 27)
                        .addComponent(btnFirst, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnPre, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnNext, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnLast, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 235, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(btnFirst)
                    .addComponent(btnPre)
                    .addComponent(btnNext)
                    .addComponent(btnLast))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 456, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(29, Short.MAX_VALUE))
        );

        cc.add(jPanel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 140, 1010, 530));

        btnXoa1.setBackground(new java.awt.Color(218, 212, 181));
        btnXoa1.setFont(new java.awt.Font("Segoe UI Black", 1, 14)); // NOI18N
        btnXoa1.setForeground(new java.awt.Color(167, 49, 33));
        btnXoa1.setText("Xóa");
        btnXoa1.setBorder(null);
        btnXoa1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoa1ActionPerformed(evt);
            }
        });
        cc.add(btnXoa1, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 550, 80, 30));

        btnThem1.setBackground(new java.awt.Color(218, 212, 181));
        btnThem1.setFont(new java.awt.Font("Segoe UI Black", 1, 14)); // NOI18N
        btnThem1.setForeground(new java.awt.Color(167, 49, 33));
        btnThem1.setText("Thêm");
        btnThem1.setBorder(null);
        btnThem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThem1ActionPerformed(evt);
            }
        });
        cc.add(btnThem1, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 550, 80, 30));

        btnLamMoi.setBackground(new java.awt.Color(218, 212, 181));
        btnLamMoi.setFont(new java.awt.Font("Segoe UI Black", 1, 14)); // NOI18N
        btnLamMoi.setForeground(new java.awt.Color(167, 49, 33));
        btnLamMoi.setText("Làm mới");
        btnLamMoi.setBorder(null);
        btnLamMoi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLamMoiActionPerformed(evt);
            }
        });
        cc.add(btnLamMoi, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 550, 80, 30));

        jLabel6.setFont(new java.awt.Font("Segoe UI Black", 1, 12)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Loại :");
        cc.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 270, -1, -1));

        jLabel7.setFont(new java.awt.Font("Segoe UI Black", 1, 12)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Tên  nguyên liệu:");
        cc.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 210, -1, -1));

        txtTenNguyenLieu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTenNguyenLieuActionPerformed(evt);
            }
        });
        cc.add(txtTenNguyenLieu, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 230, 200, 30));

        cboLoaiNL.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Chọn loại nguyên liệu", " " }));
        cboLoaiNL.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cboLoaiNLMouseClicked(evt);
            }
        });
        cc.add(cboLoaiNL, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 290, 200, 30));

        jLabel8.setFont(new java.awt.Font("Segoe UI Black", 1, 12)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Đơn giá:");
        cc.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 340, -1, -1));

        txtDonGia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDonGiaActionPerformed(evt);
            }
        });
        cc.add(txtDonGia, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 360, 200, 30));

        jLabel13.setFont(new java.awt.Font("Segoe UI Black", 1, 12)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setText("Ngày áp dụng:");
        cc.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 410, -1, -1));
        cc.add(dateNAD, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 430, 200, 30));

        jLabel14.setFont(new java.awt.Font("Segoe UI Black", 1, 12)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setText("Đơn vị tính:");
        cc.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 480, -1, -1));

        txtDonViTinh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDonViTinhActionPerformed(evt);
            }
        });
        cc.add(txtDonViTinh, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 500, 200, 30));

        jLabel15.setFont(new java.awt.Font("Segoe UI Black", 1, 36)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setText("QUẢN LÝ");
        cc.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 30, 180, 50));

        jLabel16.setFont(new java.awt.Font("Segoe UI Black", 1, 36)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(255, 255, 255));
        jLabel16.setText("NGUYÊN LIỆU");
        cc.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 80, -1, -1));

        lblHinhAnh.setFont(new java.awt.Font("Segoe UI Black", 1, 12)); // NOI18N
        lblHinhAnh.setForeground(new java.awt.Color(255, 255, 255));
        lblHinhAnh.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        lblHinhAnh.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblHinhAnhMouseClicked(evt);
            }
        });
        cc.add(lblHinhAnh, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 170, 150, 170));

        btnSua1.setBackground(new java.awt.Color(218, 212, 181));
        btnSua1.setFont(new java.awt.Font("Segoe UI Black", 1, 14)); // NOI18N
        btnSua1.setForeground(new java.awt.Color(167, 49, 33));
        btnSua1.setText("Sửa");
        btnSua1.setBorder(null);
        btnSua1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSua1ActionPerformed(evt);
            }
        });
        cc.add(btnSua1, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 550, 80, 30));

        jLabel17.setFont(new java.awt.Font("Segoe UI Black", 1, 12)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(255, 255, 255));
        jLabel17.setText("Hình ảnh:");
        cc.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 140, -1, -1));

        jLabel18.setFont(new java.awt.Font("Segoe UI Black", 1, 12)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(255, 255, 255));
        jLabel18.setText("ID:");
        cc.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 150, -1, -1));

        txtMaNguyenLieu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMaNguyenLieuActionPerformed(evt);
            }
        });
        cc.add(txtMaNguyenLieu, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 170, 200, 30));

        jPanel9.setBackground(new java.awt.Color(218, 212, 181));

        jLabel19.setFont(new java.awt.Font("Segoe UI Black", 1, 18)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(167, 49, 33));
        jLabel19.setText("Tra cứu nguyên liệu");

        cboTraCuuLoaiNguyenLieu.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        cboTraCuuLoaiNguyenLieu.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Chọn Loại Nguyên Liệu", " ", " " }));
        cboTraCuuLoaiNguyenLieu.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cboTraCuuLoaiNguyenLieuMouseClicked(evt);
            }
        });

        btnTim.setBackground(new java.awt.Color(149, 35, 35));
        btnTim.setFont(new java.awt.Font("Segoe UI Black", 1, 12)); // NOI18N
        btnTim.setText("Tìm");
        btnTim.setBorder(null);
        btnTim.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTimActionPerformed(evt);
            }
        });

        jLabel20.setFont(new java.awt.Font("Segoe UI Black", 1, 12)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(153, 0, 0));
        jLabel20.setText("Loại nguyên liệu :");

        jLabel21.setFont(new java.awt.Font("Segoe UI Black", 1, 12)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(153, 0, 0));
        jLabel21.setText("Tên nguyên liệu:");

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGap(141, 141, 141)
                        .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(156, 156, 156)
                        .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel19)))
                .addContainerGap(405, Short.MAX_VALUE))
            .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel9Layout.createSequentialGroup()
                    .addGap(142, 142, 142)
                    .addComponent(txtTraCuuTen, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(110, 110, 110)
                    .addComponent(cboTraCuuLoaiNguyenLieu, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(95, 95, 95)
                    .addComponent(btnTim, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(173, Short.MAX_VALUE)))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(3, 3, 3)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel20)
                    .addComponent(jLabel21))
                .addContainerGap(45, Short.MAX_VALUE))
            .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel9Layout.createSequentialGroup()
                    .addGap(60, 60, 60)
                    .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(txtTraCuuTen, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cboTraCuuLoaiNguyenLieu, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnTim, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addContainerGap(11, Short.MAX_VALUE)))
        );

        cc.add(jPanel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 20, 1010, -1));

        jTabbedPane1.addTab("NGUYÊN LIỆU", cc);

        jPanel10.setBackground(new java.awt.Color(149, 35, 35));
        jPanel10.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Cập nhật nguyên liệu đã mua", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 48), new java.awt.Color(255, 255, 255))); // NOI18N

        jPanel11.setBackground(new java.awt.Color(218, 212, 181));
        jPanel11.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Nguyên liệu đã được thêm", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 36), new java.awt.Color(153, 0, 51))); // NOI18N

        tblTDDuocChon.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Mã hóa đơn", "Mã mua ", "Tên loại", "Tên nguyên liệu", "Số lượng", "Đơn giá"
            }
        ));
        tblTDDuocChon.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblTDDuocChonMouseClicked(evt);
            }
        });
        jScrollPane5.setViewportView(tblTDDuocChon);

        jButton2.setBackground(new java.awt.Color(149, 35, 35));
        jButton2.setFont(new java.awt.Font("Segoe UI Black", 1, 12)); // NOI18N
        jButton2.setForeground(new java.awt.Color(255, 255, 255));
        jButton2.setText("xóa");
        jButton2.setBorder(null);
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 553, Short.MAX_VALUE)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 408, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(48, 48, 48))
        );

        jPanel12.setBackground(new java.awt.Color(218, 212, 181));
        jPanel12.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Nguyên liệu", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 36), new java.awt.Color(153, 0, 51))); // NOI18N

        jLabel22.setFont(new java.awt.Font("Segoe UI Black", 1, 14)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(149, 35, 35));
        jLabel22.setText("Chọn loại nguyên liệu:");

        cboThucDon.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Chọn loại nguyên liệu", " ", " " }));
        cboThucDon.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cboThucDonMouseClicked(evt);
            }
        });

        tblThucDon.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Mã nguyên liệu", "", "Tên nguyên liệu", "Đơn giá"
            }
        ));
        jScrollPane6.setViewportView(tblThucDon);

        jLabel24.setFont(new java.awt.Font("Segoe UI Black", 1, 14)); // NOI18N
        jLabel24.setForeground(new java.awt.Color(153, 0, 51));
        jLabel24.setText("Số lượng:");

        jButton4.setBackground(new java.awt.Color(149, 35, 35));
        jButton4.setFont(new java.awt.Font("Segoe UI Black", 1, 12)); // NOI18N
        jButton4.setForeground(new java.awt.Color(255, 255, 255));
        jButton4.setText("Thêm");
        jButton4.setBorder(null);
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jLabel25.setFont(new java.awt.Font("Segoe UI Black", 1, 14)); // NOI18N
        jLabel25.setForeground(new java.awt.Color(149, 35, 35));
        jLabel25.setText("Số hóa đơn:");

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 587, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(jPanel12Layout.createSequentialGroup()
                            .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(495, 495, 495))
                        .addGroup(jPanel12Layout.createSequentialGroup()
                            .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jLabel25, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtSoHD, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGap(164, 164, 164)
                            .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(cboThucDon, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel22))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(spnSoLuong1, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel24))
                            .addGap(74, 74, 74)))))
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addComponent(jLabel22)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cboThucDon))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel12Layout.createSequentialGroup()
                        .addComponent(jLabel25)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtSoHD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel12Layout.createSequentialGroup()
                        .addComponent(jLabel24)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(spnSoLuong1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 335, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(63, 63, 63))
        );

        jPanel13.setBackground(new java.awt.Color(218, 212, 181));
        jPanel13.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Cập nhật", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 24), new java.awt.Color(0, 0, 255))); // NOI18N

        jLabel26.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel26.setText("Tên loại:");

        jLabel27.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel27.setText("Tên nguyên liệu:");

        jLabel28.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel28.setText("Đơn giá:");

        jLabel29.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel29.setForeground(new java.awt.Color(167, 49, 33));
        jLabel29.setText("Số lượng:");

        btnCapNhat.setBackground(new java.awt.Color(149, 35, 35));
        btnCapNhat.setFont(new java.awt.Font("Segoe UI Black", 1, 12)); // NOI18N
        btnCapNhat.setForeground(new java.awt.Color(255, 255, 255));
        btnCapNhat.setText("Cập nhật");
        btnCapNhat.setBorder(null);
        btnCapNhat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCapNhatActionPerformed(evt);
            }
        });

        jLabel30.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel30.setText("Mã hóa đơn:");

        jLabel31.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel31.setText("Mã mua:");

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnCapNhat, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel13Layout.createSequentialGroup()
                            .addComponent(jLabel27, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(txtTenNguyenLieu1, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel13Layout.createSequentialGroup()
                            .addComponent(jLabel26, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtTenLoai, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel13Layout.createSequentialGroup()
                            .addComponent(jLabel30, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addComponent(txtMaHoaDon, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel13Layout.createSequentialGroup()
                            .addComponent(jLabel31, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addComponent(txtMaMua))
                        .addGroup(jPanel13Layout.createSequentialGroup()
                            .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel28, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel29, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel13Layout.createSequentialGroup()
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(spnSoLuong2, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(88, 88, 88))
                                .addGroup(jPanel13Layout.createSequentialGroup()
                                    .addGap(30, 30, 30)
                                    .addComponent(txtDonGia1))))))
                .addContainerGap(19, Short.MAX_VALUE))
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addContainerGap(29, Short.MAX_VALUE)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtMaHoaDon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel30))
                .addGap(28, 28, 28)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtMaMua, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel31))
                .addGap(26, 26, 26)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTenLoai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel26))
                .addGap(26, 26, 26)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTenNguyenLieu1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel27))
                .addGap(28, 28, 28)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel28)
                    .addComponent(txtDonGia1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 33, Short.MAX_VALUE)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(spnSoLuong2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel29))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnCapNhat, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel10Layout.createSequentialGroup()
                .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, 611, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(104, Short.MAX_VALUE))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                .addGap(53, 53, 53)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, 538, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(68, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("MUA NGUYÊN LIỆU", jPanel10);

        jLabel2.setFont(new java.awt.Font("Segoe UI Black", 1, 100)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Bếp");

        lblTime.setFont(new java.awt.Font("Segoe UI Black", 1, 14)); // NOI18N
        lblTime.setForeground(new java.awt.Color(255, 255, 255));
        lblTime.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/edusys/icon/images/Date.png"))); // NOI18N
        lblTime.setText("yyyy/MM/dd HH:mm:ss");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblTime)
                .addGap(83, 83, 83))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblTime))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 761, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tblDatMonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblDatMonMouseClicked
        // TODO add your handling code here:
        if (evt.getClickCount() == 1) {
            this.row = tblDatMon.getSelectedRow();
            this.editTblThucDon();
        }
    }//GEN-LAST:event_tblDatMonMouseClicked

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed
        // TODO add your handling code here:
        InsertDangCheBien();
    }//GEN-LAST:event_btnThemActionPerformed

    private void btnHoanThanhActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHoanThanhActionPerformed
        // TODO add your handling code here:
        InsertDaHoanThanh();
        delete();
    }//GEN-LAST:event_btnHoanThanhActionPerformed

    private void btnXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaActionPerformed
        // TODO add your handling code here:
        delete();
    }//GEN-LAST:event_btnXoaActionPerformed

    private void btnSuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaActionPerformed
        // TODO add your handling code here:
        UpdateDangCheBien();
    }//GEN-LAST:event_btnSuaActionPerformed

    private void btnXoaDHTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaDHTActionPerformed
        // TODO add your handling code here:
        deleteDaHoanThanh();
    }//GEN-LAST:event_btnXoaDHTActionPerformed

    private void tblDanhSachMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblDanhSachMouseClicked
        // TODO add your handling code here:
        if (evt.getClickCount() == 2) {
            this.row = tblDanhSach.getSelectedRow();
            this.editNguyenLieu();

        }
    }//GEN-LAST:event_tblDanhSachMouseClicked

    private void btnFirstActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFirstActionPerformed
        // TODO add your handling code here:
        first();
    }//GEN-LAST:event_btnFirstActionPerformed

    private void btnPreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPreActionPerformed
        // TODO add your handling code here:
        pre();
    }//GEN-LAST:event_btnPreActionPerformed

    private void btnNextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNextActionPerformed
        // TODO add your handling code here:
        next();
    }//GEN-LAST:event_btnNextActionPerformed

    private void btnLastActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLastActionPerformed
        // TODO add your handling code here:
        last();
    }//GEN-LAST:event_btnLastActionPerformed

    private void btnXoa1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoa1ActionPerformed
        // TODO add your handling code here:
        delete();
    }//GEN-LAST:event_btnXoa1ActionPerformed

    private void btnThem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThem1ActionPerformed
        // TODO add your handling code here:
        insertNguyenLieu();
    }//GEN-LAST:event_btnThem1ActionPerformed

    private void btnLamMoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLamMoiActionPerformed
        // TODO add your handling code here:
        clearForm();
    }//GEN-LAST:event_btnLamMoiActionPerformed

    private void txtTenNguyenLieuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTenNguyenLieuActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTenNguyenLieuActionPerformed

    private void cboLoaiNLMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cboLoaiNLMouseClicked
        // TODO add your handling code here:
        loadLoaiNguyenLieuToCbo();
    }//GEN-LAST:event_cboLoaiNLMouseClicked

    private void txtDonGiaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDonGiaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDonGiaActionPerformed

    private void txtDonViTinhActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDonViTinhActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDonViTinhActionPerformed

    private void lblHinhAnhMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblHinhAnhMouseClicked
        // TODO add your handling code here:
        String imagePath = chooseImage();
        if (imagePath != null) {
            ImageIcon imageIcon = new ImageIcon(imagePath);
            lblHinhAnh.setIcon(imageIcon);
        }
    }//GEN-LAST:event_lblHinhAnhMouseClicked

    private void btnSua1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSua1ActionPerformed
        // TODO add your handling code here:
        updateNguyenLieu();
    }//GEN-LAST:event_btnSua1ActionPerformed

    private void txtMaNguyenLieuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMaNguyenLieuActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtMaNguyenLieuActionPerformed

    private void cboTraCuuLoaiNguyenLieuMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cboTraCuuLoaiNguyenLieuMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_cboTraCuuLoaiNguyenLieuMouseClicked

    private void btnTimActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTimActionPerformed
        // TODO add your handling code here:
        String tenNguyenLieuCanTim = txtTraCuuTen.getText();
        List<NguyenLieu> searchResult = dao.searchByName(tenNguyenLieuCanTim);

        TimKiemNguyenLieu(searchResult);
    }//GEN-LAST:event_btnTimActionPerformed

    private void tblTDDuocChonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblTDDuocChonMouseClicked
        // TODO add your handling code here:
        if (evt.getClickCount() == 2) {
            this.row = tblThucDon.getSelectedRow();
            this.edit();
        }
    }//GEN-LAST:event_tblTDDuocChonMouseClicked

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        deleteMuaNguyenLieu();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void cboThucDonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cboThucDonMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_cboThucDonMouseClicked

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        insertMuaNguyenLieu();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void btnCapNhatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCapNhatActionPerformed
        // TODO add your handling code here:
        updateMuaNguyenLieu();
    }//GEN-LAST:event_btnCapNhatActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Bep1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Bep1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Bep1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Bep1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Bep1().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCapNhat;
    private javax.swing.JButton btnFirst;
    private javax.swing.JButton btnHoanThanh;
    private javax.swing.JButton btnLamMoi;
    private javax.swing.JButton btnLast;
    private javax.swing.JButton btnNext;
    private javax.swing.JButton btnPre;
    private javax.swing.JButton btnSua;
    private javax.swing.JButton btnSua1;
    private javax.swing.JButton btnThem;
    private javax.swing.JButton btnThem1;
    private javax.swing.JButton btnTim;
    private javax.swing.JButton btnXoa;
    private javax.swing.JButton btnXoa1;
    private javax.swing.JButton btnXoaDHT;
    private javax.swing.JComboBox<String> cboChonBan;
    private javax.swing.JComboBox<String> cboLoaiNL;
    private javax.swing.JComboBox<String> cboThoiGianLam;
    private javax.swing.JComboBox<String> cboThucDon;
    private javax.swing.JComboBox<String> cboTraCuuLoaiNguyenLieu;
    private javax.swing.JPanel cc;
    private com.toedter.calendar.JDateChooser dateNAD;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JLabel lblHinhAnh;
    private javax.swing.JLabel lblTime;
    private javax.swing.JSpinner spnSoLuong;
    private javax.swing.JSpinner spnSoLuong1;
    private javax.swing.JSpinner spnSoLuong2;
    private javax.swing.JTable tblDaHoanThanh;
    private javax.swing.JTable tblDangCheBien;
    private javax.swing.JTable tblDanhSach;
    private javax.swing.JTable tblDatMon;
    private javax.swing.JTable tblTDDuocChon;
    private javax.swing.JTable tblThucDon;
    private javax.swing.JTextField txtDonGia;
    private javax.swing.JTextField txtDonGia1;
    private javax.swing.JTextField txtDonViTinh;
    private javax.swing.JTextField txtMaHoaDon;
    private javax.swing.JTextField txtMaMua;
    private javax.swing.JTextField txtMaNguyenLieu;
    private javax.swing.JTextField txtSoHD;
    private javax.swing.JTextField txtTenLoai;
    private javax.swing.JTextField txtTenMon;
    private javax.swing.JTextField txtTenNguyenLieu;
    private javax.swing.JTextField txtTenNguyenLieu1;
    private javax.swing.JTextField txtTraCuuTen;
    // End of variables declaration//GEN-END:variables
}
