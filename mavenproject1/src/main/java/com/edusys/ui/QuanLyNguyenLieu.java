/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package com.edusys.ui;

import com.edusys.utils.ImageRenderer;
import com.Duan1.utils.MsgBox;
import com.edusys.dao.LoaiNLDAO;
import com.edusys.dao.NguyenLieuDAO;
import com.edusys.entity.LoaiNL;
import com.edusys.entity.NhanVien;
import com.edusys.entity.NguyenLieu;
import com.edusys.utils.Auth;
import com.edusys.utils.JdbcHelper;
import com.edusys.utils.MgsBox;
import com.edusys.utils.XImage;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.event.ListSelectionEvent;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.util.ArrayList;
import javax.swing.JTable;

/**
 *
 * @author MS QUOC
 */
public class QuanLyNguyenLieu extends javax.swing.JDialog {

    /**
     * Creates new form QuanLyNguyenLieuJDialog
     */
    public QuanLyNguyenLieu(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        fillToTable();
        edit();
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

    public boolean isQuanLySelected = true;

    NguyenLieuDAO dao = new NguyenLieuDAO();
    int row = 0;

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

    private void insert() {
        NguyenLieu entity = getForm();
        if (lblHinhAnh.getIcon() != null) {
            dao.insert(entity);
            fillToTable();
            clearForm();
        } else {
            MgsBox.alert(this, "Chưa chọn hình ảnh!");
        }
    }

    private void update() {
        NguyenLieu model = getForm();
        if (lblHinhAnh.getIcon() != null) {
            dao.update(model);
            fillToTable();
            clearForm();
            MgsBox.alert(this, "Sửa món thành công!");
        } else {
            MgsBox.alert(this, "Cập nhật thất bại!");
        }
    }

    public void delete() {

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

    void edit() {
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

    void clearForm() {
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

    private NguyenLieu getForm() {
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
        this.edit();
    }

    void pre() {
        if (this.row > 0) {
            this.row--;
            this.edit();
        }
    }

    void next() {
        if (this.row < tblDanhSach.getRowCount() - 1) {
            this.row++;
            this.edit();
        }
    }

    void last() {
        this.row = tblDanhSach.getRowCount() - 1;
        this.edit();
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

    private void TimKiem(List<NguyenLieu> records) {
        DefaultTableModel model = (DefaultTableModel) tblDanhSach.getModel();
        model.setRowCount(0);

        for (NguyenLieu record : records) {
            Object[] rows = {record.getMaNguyenLieu(), getImageIcon(record.getHinh()), record.getTenNguyenLieu(), record.getDonGia(), record.getNgayADGia(), record.getDonViTinh(), record.getTenLoai()};
            model.addRow(rows);
        }
        tblDanhSach.setModel(model);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        cc = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblDanhSach = new javax.swing.JTable();
        btnFirst = new javax.swing.JButton();
        btnPre = new javax.swing.JButton();
        btnNext = new javax.swing.JButton();
        btnLast = new javax.swing.JButton();
        btnXoa = new javax.swing.JButton();
        btnThem = new javax.swing.JButton();
        btnLamMoi = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtTenNguyenLieu = new javax.swing.JTextField();
        cboLoaiNL = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        txtDonGia = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        dateNAD = new com.toedter.calendar.JDateChooser();
        jLabel5 = new javax.swing.JLabel();
        txtDonViTinh = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        lblHinhAnh = new javax.swing.JLabel();
        btnSua1 = new javax.swing.JButton();
        jLabel13 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        txtMaNguyenLieu = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        txtTraCuuTen = new javax.swing.JTextField();
        cboTraCuuLoaiNguyenLieu = new javax.swing.JComboBox<>();
        btnTim = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        cc.setBackground(new java.awt.Color(149, 35, 35));
        cc.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBackground(new java.awt.Color(218, 212, 181));

        jLabel9.setFont(new java.awt.Font("Segoe UI Black", 1, 18)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(167, 49, 33));
        jLabel9.setText("Danh Sách Thực Đơn");

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
        jScrollPane1.setViewportView(tblDanhSach);

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

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(280, 280, 280)
                        .addComponent(jLabel9)
                        .addGap(27, 27, 27)
                        .addComponent(btnFirst, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnPre, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnNext, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnLast, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 262, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(btnFirst)
                    .addComponent(btnPre)
                    .addComponent(btnNext)
                    .addComponent(btnLast))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 549, Short.MAX_VALUE)
                .addContainerGap())
        );

        cc.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 140, 1010, 600));

        btnXoa.setBackground(new java.awt.Color(218, 212, 181));
        btnXoa.setFont(new java.awt.Font("Segoe UI Black", 1, 14)); // NOI18N
        btnXoa.setForeground(new java.awt.Color(167, 49, 33));
        btnXoa.setText("Xóa");
        btnXoa.setBorder(null);
        btnXoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaActionPerformed(evt);
            }
        });
        cc.add(btnXoa, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 700, 80, 30));

        btnThem.setBackground(new java.awt.Color(218, 212, 181));
        btnThem.setFont(new java.awt.Font("Segoe UI Black", 1, 14)); // NOI18N
        btnThem.setForeground(new java.awt.Color(167, 49, 33));
        btnThem.setText("Thêm");
        btnThem.setBorder(null);
        btnThem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemActionPerformed(evt);
            }
        });
        cc.add(btnThem, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 700, 80, 30));

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
        cc.add(btnLamMoi, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 700, 80, 30));

        jLabel1.setFont(new java.awt.Font("Segoe UI Black", 1, 12)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Loại :");
        cc.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 330, -1, -1));

        jLabel3.setFont(new java.awt.Font("Segoe UI Black", 1, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Tên  nguyên liệu:");
        cc.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 240, -1, -1));

        txtTenNguyenLieu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTenNguyenLieuActionPerformed(evt);
            }
        });
        cc.add(txtTenNguyenLieu, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 260, 200, 30));

        cboLoaiNL.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Chọn loại nguyên liệu", " " }));
        cboLoaiNL.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cboLoaiNLMouseClicked(evt);
            }
        });
        cc.add(cboLoaiNL, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 350, 200, 30));

        jLabel2.setFont(new java.awt.Font("Segoe UI Black", 1, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Đơn giá:");
        cc.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 430, -1, -1));

        txtDonGia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDonGiaActionPerformed(evt);
            }
        });
        cc.add(txtDonGia, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 450, 200, 30));

        jLabel4.setFont(new java.awt.Font("Segoe UI Black", 1, 12)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Ngày áp dụng:");
        cc.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 530, -1, -1));
        cc.add(dateNAD, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 550, 200, 30));

        jLabel5.setFont(new java.awt.Font("Segoe UI Black", 1, 12)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Đơn vị tính:");
        cc.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 630, -1, -1));

        txtDonViTinh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDonViTinhActionPerformed(evt);
            }
        });
        cc.add(txtDonViTinh, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 650, 200, 30));

        jLabel10.setFont(new java.awt.Font("Segoe UI Black", 1, 36)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("QUẢN LÝ");
        cc.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 30, 180, 50));

        jLabel11.setFont(new java.awt.Font("Segoe UI Black", 1, 36)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("THỰC ĐƠN");
        cc.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 80, -1, -1));

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
        cc.add(btnSua1, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 700, 80, 30));

        jLabel13.setFont(new java.awt.Font("Segoe UI Black", 1, 12)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setText("Hình ảnh:");
        cc.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 140, -1, -1));

        jLabel12.setFont(new java.awt.Font("Segoe UI Black", 1, 12)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setText("ID:");
        cc.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 150, -1, -1));

        txtMaNguyenLieu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMaNguyenLieuActionPerformed(evt);
            }
        });
        cc.add(txtMaNguyenLieu, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 170, 200, 30));

        jPanel1.setBackground(new java.awt.Color(218, 212, 181));

        jLabel17.setFont(new java.awt.Font("Segoe UI Black", 1, 18)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(167, 49, 33));
        jLabel17.setText("Tra cứu thực đơn");

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

        jLabel6.setFont(new java.awt.Font("Segoe UI Black", 1, 12)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(153, 0, 0));
        jLabel6.setText("Loại nguyên liệu :");

        jLabel7.setFont(new java.awt.Font("Segoe UI Black", 1, 12)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(153, 0, 0));
        jLabel7.setText("Tên nguyên liệu:");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(141, 141, 141)
                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(156, 156, 156)
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel17)))
                .addContainerGap(405, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addGap(142, 142, 142)
                    .addComponent(txtTraCuuTen, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(110, 110, 110)
                    .addComponent(cboTraCuuLoaiNguyenLieu, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(95, 95, 95)
                    .addComponent(btnTim, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(173, Short.MAX_VALUE)))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(3, 3, 3)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jLabel7))
                .addContainerGap(45, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addGap(60, 60, 60)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(txtTraCuuTen, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cboTraCuuLoaiNguyenLieu, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnTim, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addContainerGap(11, Short.MAX_VALUE)))
        );

        cc.add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 20, 1010, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(cc, javax.swing.GroupLayout.PREFERRED_SIZE, 1424, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(cc, javax.swing.GroupLayout.PREFERRED_SIZE, 603, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaActionPerformed
        // TODO add your handling code here:
        delete();
    }//GEN-LAST:event_btnXoaActionPerformed

    private void txtDonViTinhActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDonViTinhActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDonViTinhActionPerformed

    private void tblDanhSachMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblDanhSachMouseClicked
        // TODO add your handling code here:
        if (evt.getClickCount() == 2) {
            this.row = tblDanhSach.getSelectedRow();
            this.edit();

        }
    }//GEN-LAST:event_tblDanhSachMouseClicked

    private void lblHinhAnhMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblHinhAnhMouseClicked
        // TODO add your handling code here:
        String imagePath = chooseImage();
        if (imagePath != null) {
            ImageIcon imageIcon = new ImageIcon(imagePath);
            lblHinhAnh.setIcon(imageIcon);
        }

    }//GEN-LAST:event_lblHinhAnhMouseClicked

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

    private void cboLoaiNLMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cboLoaiNLMouseClicked
        // TODO add your handling code here:
        loadLoaiNguyenLieuToCbo();
    }//GEN-LAST:event_cboLoaiNLMouseClicked

    private void btnLamMoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLamMoiActionPerformed
        // TODO add your handling code here:
        clearForm();
    }//GEN-LAST:event_btnLamMoiActionPerformed

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed
        // TODO add your handling code here:
        insert();
    }//GEN-LAST:event_btnThemActionPerformed

    private void txtTenNguyenLieuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTenNguyenLieuActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTenNguyenLieuActionPerformed

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
        
        TimKiem(searchResult);
    }//GEN-LAST:event_btnTimActionPerformed

    private void btnSua1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSua1ActionPerformed
        // TODO add your handling code here:
        update();
    }//GEN-LAST:event_btnSua1ActionPerformed

    private void txtDonGiaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDonGiaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDonGiaActionPerformed

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
            java.util.logging.Logger.getLogger(QuanLyNguyenLieu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(QuanLyNguyenLieu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(QuanLyNguyenLieu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(QuanLyNguyenLieu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                QuanLyNguyenLieu dialog = new QuanLyNguyenLieu(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton btnFirst;
    private javax.swing.JButton btnLamMoi;
    private javax.swing.JButton btnLast;
    private javax.swing.JButton btnNext;
    private javax.swing.JButton btnPre;
    private javax.swing.JButton btnSua1;
    private javax.swing.JButton btnThem;
    private javax.swing.JButton btnTim;
    private javax.swing.JButton btnXoa;
    private javax.swing.JComboBox<String> cboLoaiNL;
    private javax.swing.JComboBox<String> cboTraCuuLoaiNguyenLieu;
    private javax.swing.JPanel cc;
    private com.toedter.calendar.JDateChooser dateNAD;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblHinhAnh;
    private javax.swing.JTable tblDanhSach;
    private javax.swing.JTextField txtDonGia;
    private javax.swing.JTextField txtDonViTinh;
    private javax.swing.JTextField txtMaNguyenLieu;
    private javax.swing.JTextField txtTenNguyenLieu;
    private javax.swing.JTextField txtTraCuuTen;
    // End of variables declaration//GEN-END:variables
}
