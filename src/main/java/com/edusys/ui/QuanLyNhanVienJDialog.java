/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package com.edusys.ui;

import com.edusys.utils.Auth;
import com.edusys.dao.NhanVienDAO;
import com.edusys.entity.Mon;
import com.edusys.entity.NhanVien;
import com.edusys.utils.ExcelExporter;
import com.edusys.utils.ImageRenderer;
import com.edusys.utils.XImage;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.swing.table.DefaultTableModel;
import com.edusys.utils.MgsBox;
import com.toedter.calendar.JDateChooser;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.File;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import org.apache.poi.ss.usermodel.*;

/**
 *
 * @author MS QUOC
 */
public class QuanLyNhanVienJDialog extends javax.swing.JDialog {

    /**
     * Creates new form QuanLyNhanVienJDialog
     */
    public QuanLyNhanVienJDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        init();
        initComponents();
        setLocationRelativeTo(null);
        fillTable();
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        tblDanhSach.setDefaultEditor(Object.class, null);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        dateNgaySinh.setDateFormatString("yyyy/MM/dd"); // Định dạng ngày tháng cho ngày sinh

    }

    boolean validateForm() {
        if (txtMaNV.getText().isEmpty()) {
            MgsBox.alert(this, "Vui lòng nhập đầy đủ thông tin!");
            return false;
        } else if (txtEmail.getText().equals("")) {
            MgsBox.alert(this, "Vui lòng nhập đầy đủ thông tin!");
            return false;
        } else if (txtTenDN.getText().equals("")) {
            MgsBox.alert(this, "Vui lòng nhập đầy đủ thông tin!");
            return false;
        } else if (txtTenNV.getText().equals("")) {
            MgsBox.alert(this, "Vui lòng nhập đầy đủ thông tin!");
            return false;
        } else if (txtTenNV.getText().equals("")) {
            MgsBox.alert(this, "Vui lòng nhập đầy đủ thông tin!");
            return false;
        }
        if (!txtEmail.getText().matches("^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$")) {
            MgsBox.alert(this, "Email sai định dạng");
            txtEmail.requestFocus();
            return false;
        } else if (!(txtSDT.getText()).matches("[0-9]{10}$")) {
            MgsBox.alert(this, "");
            MgsBox.alert(this, "Sai định dạng số điện thoại."); // Đặt thông báo lỗi trong JLabel
            txtSDT.requestFocus();
            return false;
        } 
        return true;
    }
    int row = 0;

    void init() {
        setIconImage(XImage.getAppIcon());
        setLocationRelativeTo(null);
        setTitle("QH-RESTAURANT SYSTEM");

    }

    NhanVienDAO nvDao = new NhanVienDAO();
    ExcelExporter excelExporter = new ExcelExporter();

//    void fillTable() {
//        DefaultTableModel model = (DefaultTableModel) tblDanhSach.getModel();
//        model.setRowCount(0);
//        try {
//            String keyword = txtTraCuuMaNV.getText();
//            List<NhanVien> list = nvDao.selectAll();
//
//            for (NhanVien nh : list) {
//                Object[] row = {
//                    nh.getMaNV(),
//                    nh.getTenNV(),
//                    nh.getNgaySinh(),
//                    nh.getSDT(),
//                    nh.getTenDN(),
//                    nh.getCaLam(),
//                    nh.getEmail(),
//                    nh.isGioiTinh() ? "Nam" : "Nữ",
//                    nh.getMatKhau(),
//                    nh.isVaiTro() ? "Quản lý" : "Nhân viên"
//                };
//                model.addRow(row);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            MgsBox.alert(this, "Lỗi truy vấn dữ liệu!");
//        }
//    }
    void fillTable() {
        DefaultTableModel model = (DefaultTableModel) tblDanhSach.getModel();
        model.setRowCount(0);
        try {
            List<NhanVien> list = nvDao.selectAll();

            int columnCount = tblDanhSach.getColumnCount();
            int columnWidth = 100;
            for (NhanVien nh : list) {
                Image im = Toolkit.getDefaultToolkit().createImage(nh.getHinhAnh());
                ImageIcon ic = new ImageIcon(im);
                Object[] rows = {nh.getMaNV(), ic, nh.getTenNV(), nh.getNgaySinh(), nh.getSDT(), nh.getTenDN(), nh.getCaLam(), nh.getEmail(), nh.isGioiTinh() ? "Nam" : "Nữ", nh.getMatKhau(), nh.isVaiTro() ? "Quản lý" : "Nhân viên"};
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

    private void insert() {
        NhanVien entity = getForm();
        try {

            if (lblHinhAnh.getIcon() != null) {
                nvDao.insert(entity);
                fillTable();
                clearForm();
            } else {
                MgsBox.alert(this, "Chưa chọn hình ảnh!");
            }
        } catch (Exception e) {
            e.printStackTrace(); // In ra lỗi cụ thể
            MgsBox.alert(this, "Thêm mới thành công!");
        }
    }

//    void insert() {
//        NhanVien model = getForm();
//        try {
//            nvDao.insert(model);
//            this.fillTable();
//            this.clearForm();
//            MgsBox.alert(this, "Thêm mới thành công!");
//        } catch (Exception e) {
//            e.printStackTrace(); // In ra lỗi cụ thể
//            MgsBox.alert(this, "Thêm mới thành công!");
//        }
//    }
    private void update() {
        NhanVien entity = getForm();
        String confirm = new String(txtMaNV.getText());
        if (!confirm.equals(entity.getMaNV())) {
            MgsBox.alert(this, "Xác nhận mã nhân viên không đúng!");
        } else {
            try {
                if (lblHinhAnh.getIcon() != null) {
                    nvDao.update(entity);
                    fillTable();
                    clearForm();
                    MgsBox.alert(this, "Cập nhật thành công!");
                } else {
                    MgsBox.alert(this, "Cập nhật thất bại!");
                }
            } catch (Exception e) {
                MgsBox.alert(this, "Cập nhật thất bại!" + e.getMessage());
            }
        }
    }
//        void update() {
//        NhanVien model = getForm();
//        String confirm = new String(txtMaNV.getText());
//        if (!confirm.equals(model.getMaNV())) {
//            MgsBox.alert(this, "Xác nhận mã nhân viên không đúng!");
//        } else {
//            try {
//                nvDao.update(model);
//                this.fillTable();
//                MgsBox.alert(this, "Cập nhật thành công!");
//            } catch (Exception e) {
//                MgsBox.alert(this, "Cập nhật thất bại!" + e.getMessage());
//            }
//        }
//    }

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

    //-------------------------------------------------------------
    void fillTable2() {
        DefaultTableModel model = (DefaultTableModel) tblDanhSach.getModel();
        model.setRowCount(0); // Xóa tất cả dòng trong bảng

        try {
            String keyword = txtTraCuuMaNV.getText();
            List<NhanVien> list = nvDao.selectAll();

            for (NhanVien nh : list) {
                Object[] row = {
                    nh.getMaNV(),
                    nh.getTenNV(),
                    nh.getNgaySinh(),
                    nh.getSDT(),
                    nh.getTenDN(),
                    nh.getCaLam(),
                    nh.getEmail(),
                    nh.isGioiTinh() ? "Nam" : "Nữ",
                    nh.getMatKhau(),
                    nh.isVaiTro(),};
                model.addRow(row);
            }

            // Xóa nội dung của trường tìm kiếm sau khi fill table
            txtTraCuuMaNV.setText("");
        } catch (Exception e) {
            e.printStackTrace();
            MgsBox.alert(this, "Lỗi truy vấn dữ liệu!");
        }
    }

    private void timTheoMaHoacTen() {
        DefaultTableModel model = (DefaultTableModel) tblDanhSach.getModel();
        model.setRowCount(0);
        try {
            String tuKhoa = txtTraCuuMaNV.getText().trim(); // Lấy từ khoá tìm kiếm từ TextField
            List<NhanVien> danhSachNhanVien = nvDao.selectByKeyword(tuKhoa); // Thực hiện truy vấn theo MaNV hoặc TenNV

            if (!danhSachNhanVien.isEmpty()) {
                for (NhanVien nv : danhSachNhanVien) {
                    Object[] row = {
                        nv.getMaNV(),
                        nv.getTenNV(),
                        nv.getNgaySinh(),
                        nv.getSDT(),
                        nv.getTenDN(),
                        nv.getCaLam(),
                        nv.getEmail(),
                        nv.isGioiTinh() ? "Nam" : "Nữ",
                        nv.getMatKhau(),
                        nv.isVaiTro() ? "Quản lý" : "Nhân viên"};
                    model.addRow(row);
                }
            } else {
                MgsBox.alert(this, "Không tìm thấy nhân viên có Mã hoặc Tên: " + tuKhoa);
            }
        } catch (Exception e) {
            MgsBox.alert(this, "Lỗi truy vấn dữ liệu!");
        }
    }

    void edit() {
        try {
            String MaNV = (String) tblDanhSach.getValueAt(this.row, 0);
            NhanVien model = nvDao.selectById(MaNV);

            if (model != null) {
                setForm(model);
                updateStatus();
            }

        } catch (Exception e) {
            MgsBox.alert(this, "Lỗi truy vấn dữ liệu!");
        }
    }

    void setForm(NhanVien model) {
        txtMaNV.setText(model.getMaNV());
        String imagePath = model.getHinhAnh();
        ImageIcon imageIcon = new ImageIcon(imagePath);
        lblHinhAnh.setIcon(imageIcon);
        txtTenNV.setText(model.getTenNV());
        dateNgaySinh.setDate(model.getNgaySinh());
        txtSDT.setText(model.getSDT());
        txtTenDN.setText(model.getTenDN());
        txtCaLam.setText(Integer.valueOf(model.getCaLam()).toString());
        txtEmail.setText(model.getEmail());
        rdoNam.setSelected(model.isGioiTinh());
        rdoNu.setSelected(!model.isGioiTinh());
        txtMK.setText(model.getMatKhau());
        rdoQuanLy.setSelected(model.isVaiTro());
        rdoNhanVien.setSelected(!model.isVaiTro());

    }

    NhanVien getForm() {
        NhanVien model = new NhanVien();
        model.setMaNV(txtMaNV.getText());
        ImageIcon imageIcon = (ImageIcon) lblHinhAnh.getIcon();
        if (imageIcon != null) {
            String imagePath = imageIcon.getDescription();
            model.setHinhAnh(imagePath);
        }
        model.setTenNV(txtTenNV.getText());
        dateNgaySinh.setDateFormatString("yyyy/MM/dd"); // Định dạng ngày tháng cho ngày sinh
        model.setNgaySinh(dateNgaySinh.getDate());
        model.setSDT(txtSDT.getText());
        model.setTenDN(txtTenDN.getText());
        model.setCaLam((String) (txtCaLam.getText()));
        model.setEmail(txtEmail.getText());
        model.setGioiTinh(rdoNam.isSelected());
        model.setMatKhau(new String(txtMK.getPassword()));
        model.setVaiTro(rdoNhanVien.isSelected());

        return model;
    }

    void updateStatus() {
        boolean edit = this.row >= 0;

        txtTenNV.setEditable(true);
        btnThem.setEnabled(true);
        btnSua.setEnabled(true);
        btnXoa.setEnabled(true);
    }

    void clearForm() {
        txtCaLam.setText("");
        txtEmail.setText("");
        txtMK.setText("");
        txtMaNV.setText("");
        txtSDT.setText("");
        txtTenDN.setText("");
        txtTenNV.setText("");
        txtTraCuuMaNV.setText("");
    }

//
    //-----------------------------------------------------------------------
    void delete() {
        if (MgsBox.confirm(this, "Bạn thực sự muốn xóa nhân viên này?")) {
            String MaNV = txtMaNV.getText();
            try {
                nvDao.delete(MaNV);
                this.fillTable();
                this.clearForm();
                MgsBox.alert(this, "Xóa thành công!");
            } catch (Exception e) {
                e.printStackTrace();
                MgsBox.alert(this, "Xóa thất bại");
            }
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        buttonGroup2 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jpanel = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtMaNV = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtTenDN = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtMK = new javax.swing.JPasswordField();
        jLabel13 = new javax.swing.JLabel();
        txtTenNV = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        txtSDT = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        txtEmail = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        txtCaLam = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        rdoNam = new javax.swing.JRadioButton();
        rdoNu = new javax.swing.JRadioButton();
        rdoQuanLy = new javax.swing.JRadioButton();
        rdoNhanVien = new javax.swing.JRadioButton();
        dateNgaySinh = new com.toedter.calendar.JDateChooser();
        jPanel3 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        txtTraCuuMaNV = new javax.swing.JTextField();
        btnTim = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblDanhSach = new javax.swing.JTable();
        btnToi = new javax.swing.JButton();
        btnLui = new javax.swing.JButton();
        btnToiDau = new javax.swing.JButton();
        btnToiCuoi = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        btnXoa = new javax.swing.JButton();
        btnThem = new javax.swing.JButton();
        btnSua = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        btnXuatFileExcel = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        lblHinhAnh = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(149, 35, 35));

        jpanel.setBackground(new java.awt.Color(218, 212, 181));
        jpanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Thông tin nhân viên", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.ABOVE_TOP, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N
        jpanel.setForeground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Segoe UI Black", 1, 12)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(149, 35, 35));
        jLabel1.setText("Mã nhân viên:");

        jLabel2.setFont(new java.awt.Font("Segoe UI Black", 1, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(149, 35, 35));
        jLabel2.setText("Tên đăng nhập:");

        jLabel4.setFont(new java.awt.Font("Segoe UI Black", 1, 12)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(149, 35, 35));
        jLabel4.setText("Ngày sinh:");

        jLabel5.setFont(new java.awt.Font("Segoe UI Black", 1, 12)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(149, 35, 35));
        jLabel5.setText("Mật khẩu:");

        jLabel6.setFont(new java.awt.Font("Segoe UI Black", 1, 12)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(149, 35, 35));
        jLabel6.setText("Vai trò:");

        jLabel13.setFont(new java.awt.Font("Segoe UI Black", 1, 12)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(149, 35, 35));
        jLabel13.setText("Tên nhân viên:");

        jLabel15.setFont(new java.awt.Font("Segoe UI Black", 1, 12)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(149, 35, 35));
        jLabel15.setText("Email:");

        jLabel16.setFont(new java.awt.Font("Segoe UI Black", 1, 12)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(149, 35, 35));
        jLabel16.setText("Số điện thoại:");

        jLabel17.setFont(new java.awt.Font("Segoe UI Black", 1, 12)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(149, 35, 35));
        jLabel17.setText("Ca làm:");

        jLabel18.setFont(new java.awt.Font("Segoe UI Black", 1, 12)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(149, 35, 35));
        jLabel18.setText("Giới tính:");

        buttonGroup2.add(rdoNam);
        rdoNam.setFont(new java.awt.Font("Segoe UI Black", 1, 12)); // NOI18N
        rdoNam.setForeground(new java.awt.Color(149, 35, 35));
        rdoNam.setText("Nam");

        buttonGroup2.add(rdoNu);
        rdoNu.setFont(new java.awt.Font("Segoe UI Black", 1, 12)); // NOI18N
        rdoNu.setForeground(new java.awt.Color(149, 35, 35));
        rdoNu.setText("Nữ");

        buttonGroup1.add(rdoQuanLy);
        rdoQuanLy.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        rdoQuanLy.setForeground(new java.awt.Color(153, 0, 51));
        rdoQuanLy.setText("Quản lý");
        rdoQuanLy.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdoQuanLyActionPerformed(evt);
            }
        });

        buttonGroup1.add(rdoNhanVien);
        rdoNhanVien.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        rdoNhanVien.setForeground(new java.awt.Color(153, 0, 51));
        rdoNhanVien.setText("Nhân viên");

        javax.swing.GroupLayout jpanelLayout = new javax.swing.GroupLayout(jpanel);
        jpanel.setLayout(jpanelLayout);
        jpanelLayout.setHorizontalGroup(
            jpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpanelLayout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addGroup(jpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jpanelLayout.createSequentialGroup()
                        .addGroup(jpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel2)
                                .addComponent(jLabel13, javax.swing.GroupLayout.Alignment.TRAILING))
                            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addGroup(jpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtMaNV)
                            .addComponent(txtMK)
                            .addComponent(txtTenNV)
                            .addComponent(txtTenDN, javax.swing.GroupLayout.DEFAULT_SIZE, 182, Short.MAX_VALUE)))
                    .addGroup(jpanelLayout.createSequentialGroup()
                        .addComponent(jLabel18)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(rdoNam, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(rdoNu, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(53, 53, 53)
                .addGroup(jpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel17)
                    .addComponent(jLabel6)
                    .addComponent(jLabel16)
                    .addComponent(jLabel15)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jpanelLayout.createSequentialGroup()
                        .addComponent(rdoQuanLy)
                        .addGap(18, 18, 18)
                        .addComponent(rdoNhanVien, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(dateNgaySinh, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtSDT, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(txtEmail, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(txtCaLam, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(140, 140, 140))
        );
        jpanelLayout.setVerticalGroup(
            jpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpanelLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jpanelLayout.createSequentialGroup()
                        .addGroup(jpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(dateNgaySinh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpanelLayout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 5, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel4)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel15))
                        .addGroup(jpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jpanelLayout.createSequentialGroup()
                                .addGap(56, 56, 56)
                                .addGroup(jpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txtCaLam, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel17)))
                            .addGroup(jpanelLayout.createSequentialGroup()
                                .addGroup(jpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txtSDT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel16))
                                .addGap(7, 7, 7)
                                .addGroup(jpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel6)
                                    .addComponent(rdoQuanLy)
                                    .addComponent(rdoNhanVien))))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jpanelLayout.createSequentialGroup()
                        .addGroup(jpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtMaNV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtTenNV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel13))
                        .addGap(12, 12, 12)
                        .addGroup(jpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtTenDN, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(txtMK, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel18)
                            .addComponent(rdoNam)
                            .addComponent(rdoNu))
                        .addGap(43, 43, 43))))
        );

        jPanel3.setBackground(new java.awt.Color(218, 212, 181));
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Tra cứu", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.ABOVE_TOP, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N

        jLabel7.setFont(new java.awt.Font("Segoe UI Black", 1, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(149, 35, 35));
        jLabel7.setText("Nhập mã nhân viên");

        txtTraCuuMaNV.setFont(new java.awt.Font("Segoe UI Black", 1, 18)); // NOI18N
        txtTraCuuMaNV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTraCuuMaNVActionPerformed(evt);
            }
        });

        btnTim.setBackground(new java.awt.Color(0, 102, 102));
        btnTim.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnTim.setForeground(new java.awt.Color(255, 255, 255));
        btnTim.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/edusys/icon/images/Search.png"))); // NOI18N
        btnTim.setText("Tìm");
        btnTim.setBorder(null);
        btnTim.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTimActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(txtTraCuuMaNV, javax.swing.GroupLayout.PREFERRED_SIZE, 299, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnTim, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 9, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTraCuuMaNV, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnTim, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(63, 63, 63))
        );

        jPanel4.setBackground(new java.awt.Color(218, 212, 181));
        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Danh sách nhân viên", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.ABOVE_TOP, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N

        jScrollPane1.setBackground(new java.awt.Color(0, 102, 255));

        tblDanhSach.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Mã NV", "", "Họ tên", "Ngày sinh", "Số điện thoại", "Tên ĐN", "Ca làm", "Email", "Giới tính", "Mật khẩu", "Vai trò"
            }
        ));
        tblDanhSach.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblDanhSachMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblDanhSach);

        btnToi.setBackground(new java.awt.Color(0, 102, 102));
        btnToi.setFont(new java.awt.Font("Segoe UI Black", 1, 18)); // NOI18N
        btnToi.setForeground(new java.awt.Color(255, 255, 255));
        btnToi.setText("-->");
        btnToi.setBorder(null);

        btnLui.setBackground(new java.awt.Color(0, 102, 102));
        btnLui.setFont(new java.awt.Font("Segoe UI Black", 1, 18)); // NOI18N
        btnLui.setForeground(new java.awt.Color(255, 255, 255));
        btnLui.setText("<--");
        btnLui.setBorder(null);

        btnToiDau.setBackground(new java.awt.Color(0, 102, 102));
        btnToiDau.setFont(new java.awt.Font("Segoe UI Black", 1, 18)); // NOI18N
        btnToiDau.setForeground(new java.awt.Color(255, 255, 255));
        btnToiDau.setText("<<");
        btnToiDau.setBorder(null);
        btnToiDau.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnToiDauActionPerformed(evt);
            }
        });

        btnToiCuoi.setBackground(new java.awt.Color(0, 102, 102));
        btnToiCuoi.setFont(new java.awt.Font("Segoe UI Black", 1, 18)); // NOI18N
        btnToiCuoi.setForeground(new java.awt.Color(255, 255, 255));
        btnToiCuoi.setText(">>");
        btnToiCuoi.setBorder(null);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1135, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnToiDau, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnLui, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnToi, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnToiCuoi, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnToiDau)
                        .addComponent(btnLui))
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnToi)
                        .addComponent(btnToiCuoi)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 321, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(285, 285, 285))
        );

        jPanel5.setBackground(new java.awt.Color(218, 212, 181));
        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Xử lý", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.ABOVE_TOP, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N
        jPanel5.setForeground(new java.awt.Color(0, 153, 153));

        btnXoa.setBackground(new java.awt.Color(0, 102, 102));
        btnXoa.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnXoa.setForeground(new java.awt.Color(255, 255, 255));
        btnXoa.setText("Xóa ND");
        btnXoa.setBorder(null);
        btnXoa.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnXoa.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnXoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaActionPerformed(evt);
            }
        });

        btnThem.setBackground(new java.awt.Color(0, 102, 102));
        btnThem.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnThem.setForeground(new java.awt.Color(255, 255, 255));
        btnThem.setText("Thêm ND");
        btnThem.setBorder(null);
        btnThem.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnThem.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnThem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemActionPerformed(evt);
            }
        });

        btnSua.setBackground(new java.awt.Color(0, 102, 102));
        btnSua.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnSua.setForeground(new java.awt.Color(255, 255, 255));
        btnSua.setText("Sửa ND");
        btnSua.setBorder(null);
        btnSua.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnSua.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnSua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnThem, javax.swing.GroupLayout.DEFAULT_SIZE, 136, Short.MAX_VALUE)
            .addComponent(btnSua, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnXoa, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnThem, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnSua, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnXoa, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(15, Short.MAX_VALUE))
        );

        jButton1.setBackground(new java.awt.Color(153, 0, 51));
        jButton1.setFont(new java.awt.Font("Segoe UI Black", 1, 18)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("PHÂN CÔNG");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        btnXuatFileExcel.setBackground(new java.awt.Color(153, 0, 51));
        btnXuatFileExcel.setFont(new java.awt.Font("Segoe UI Black", 1, 18)); // NOI18N
        btnXuatFileExcel.setForeground(new java.awt.Color(255, 255, 255));
        btnXuatFileExcel.setText("XUẤT FILE EXCEL");
        btnXuatFileExcel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXuatFileExcelActionPerformed(evt);
            }
        });

        jPanel2.setBackground(new java.awt.Color(0, 153, 153));

        lblHinhAnh.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "HÌNH ẢNH", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI Black", 1, 12), new java.awt.Color(0, 51, 51))); // NOI18N
        lblHinhAnh.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblHinhAnhMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblHinhAnh, javax.swing.GroupLayout.DEFAULT_SIZE, 174, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(lblHinhAnh, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(4, 4, 4)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jpanel, javax.swing.GroupLayout.PREFERRED_SIZE, 702, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addComponent(btnXuatFileExcel, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)))
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jpanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(12, 12, 12)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 384, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnXuatFileExcel, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 32, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtTraCuuMaNVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTraCuuMaNVActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTraCuuMaNVActionPerformed

    private void btnTimActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTimActionPerformed
        // TODO add your handling code here:
        timTheoMaHoacTen();
    }//GEN-LAST:event_btnTimActionPerformed

    private void tblDanhSachMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblDanhSachMouseClicked
        // TODO add your handling code here:
        if (evt.getClickCount() == 2) {
            this.row = tblDanhSach.getSelectedRow();
            edit();
        }
    }//GEN-LAST:event_tblDanhSachMouseClicked

    private void btnToiDauActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnToiDauActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnToiDauActionPerformed

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed
        // TODO add your handling code here:
        insert();

    }//GEN-LAST:event_btnThemActionPerformed

    private void rdoQuanLyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdoQuanLyActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rdoQuanLyActionPerformed

    private void btnSuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaActionPerformed
        // TODO add your handling code here:
        update();
    }//GEN-LAST:event_btnSuaActionPerformed

    private void btnXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaActionPerformed
        // TODO add your handling code here:
        delete();
    }//GEN-LAST:event_btnXoaActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        dispose();
        new frmQuanLyPhanCong().setVisible(true);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void btnXuatFileExcelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXuatFileExcelActionPerformed
        // TODO add your handling code here:
        excelExporter.exportToExcel(tblDanhSach, "C:\\Users\\MS QUOC\\Documents\\NetBeansProjects\\mavenproject\\src\\main\\java\\com\\edusys\\excel\\QuanLyNhanVien.xlsx");
        MgsBox.alert(this, "Xuất Excel thành công!");
    }//GEN-LAST:event_btnXuatFileExcelActionPerformed

    private void lblHinhAnhMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblHinhAnhMouseClicked
        // TODO add your handling code here:
        String imagePath = chooseImage();
        if (imagePath != null) {
            ImageIcon imageIcon = new ImageIcon(imagePath);
            lblHinhAnh.setIcon(imageIcon);
        }
    }//GEN-LAST:event_lblHinhAnhMouseClicked

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
            java.util.logging.Logger.getLogger(QuanLyNhanVienJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(QuanLyNhanVienJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(QuanLyNhanVienJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(QuanLyNhanVienJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                QuanLyNhanVienJDialog dialog = new QuanLyNhanVienJDialog(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnLui;
    private javax.swing.JButton btnSua;
    private javax.swing.JButton btnThem;
    private javax.swing.JButton btnTim;
    private javax.swing.JButton btnToi;
    private javax.swing.JButton btnToiCuoi;
    private javax.swing.JButton btnToiDau;
    private javax.swing.JButton btnXoa;
    private javax.swing.JButton btnXuatFileExcel;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private com.toedter.calendar.JDateChooser dateNgaySinh;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel jpanel;
    private javax.swing.JLabel lblHinhAnh;
    private javax.swing.JRadioButton rdoNam;
    private javax.swing.JRadioButton rdoNhanVien;
    private javax.swing.JRadioButton rdoNu;
    private javax.swing.JRadioButton rdoQuanLy;
    private javax.swing.JTable tblDanhSach;
    private javax.swing.JTextField txtCaLam;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JPasswordField txtMK;
    private javax.swing.JTextField txtMaNV;
    private javax.swing.JTextField txtSDT;
    private javax.swing.JTextField txtTenDN;
    private javax.swing.JTextField txtTenNV;
    private javax.swing.JTextField txtTraCuuMaNV;
    // End of variables declaration//GEN-END:variables
}
