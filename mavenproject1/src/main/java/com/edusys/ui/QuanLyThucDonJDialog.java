/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package com.edusys.ui;

import com.edusys.utils.ImageRenderer;
import com.Duan1.utils.MsgBox;
import com.edusys.dao.LoaiTDDAO;
import com.edusys.dao.MonDAO;
import com.edusys.entity.LoaiTD;
import com.edusys.entity.NhanVien;
import com.edusys.entity.Mon;
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
public class QuanLyThucDonJDialog extends javax.swing.JDialog {

    /**
     * Creates new form QuanLyThucDonJDialog
     */
    public QuanLyThucDonJDialog(java.awt.Frame parent, boolean modal) {
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
                setLoaiTDFromSelectedRow();
            }
        });
        cboTraCuuLoaiThucDon.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedLoaiTD = cboTraCuuLoaiThucDon.getSelectedItem().toString();

                //Gọi hàm truy vấn và hiển thị bản ghi của loại thực đơn được chọn
                List<Mon> records = dao.selectByLoaiTD(selectedLoaiTD);

                //Hiển thị danh sách bản ghi lên bảng tblDanhSach
                showRecordsByLoaiTD();
            }
        });
    }

    public boolean isQuanLySelected = true;

    MonDAO dao = new MonDAO();
    int row = 0;

    void fillToTable() {
        DefaultTableModel model = (DefaultTableModel) tblDanhSach.getModel();
        model.setRowCount(0);
        try {
            List<Mon> list = dao.selectAll();

            int columnCount = tblDanhSach.getColumnCount();
            int columnWidth = 100;
            for (Mon td : list) {
                Image im = Toolkit.getDefaultToolkit().createImage(td.getHinh());
                ImageIcon ic = new ImageIcon(im);
                Object[] rows = {td.getMaMon(), ic, td.getTenMon(), td.getDonGia(), td.getNgayADGia(), td.getDonViTinh(), td.getTenLoai()};
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
        Mon entity = getForm();
        if (lblHinhAnh.getIcon() != null) {
            dao.insert(entity);
            fillToTable();
            clearForm();
        } else {
            MgsBox.alert(this, "Chưa chọn hình ảnh!");
        }
    }

    private void update() {
        Mon model = getForm();
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
            String MaMon = txtTenMon1.getText();
                dao.delete(MaMon);
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
                Mon td = dao.selectById(maTD);
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
        txtTenMon.setText("");
        txtDonViTinh.setText("");
        txtDonGia.setText("");
        cboLoaiTD.setSelectedIndex(0); // Chọn mục đầu tiên hoặc một giá trị mặc định khác
        dateNAD.setDate(new Date()); // Đặt ngày hiện tại hoặc ngày mặc định khác
    }

    void setForm(Mon td) {
        txtTenMon1.setText(Integer.valueOf(td.getMaMon()).toString());
        String imagePath = td.getHinh();
        ImageIcon imageIcon = new ImageIcon(imagePath);
        lblHinhAnh.setIcon(imageIcon);
        txtTenMon.setText(td.getTenMon());

        txtDonGia.setText(String.valueOf(td.getDonGia()));
        txtDonViTinh.setText(td.getDonViTinh());
        dateNAD.setDate(td.getNgayADGia());
        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
        cboLoaiTD.setSelectedItem(td.getTenLoai());
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

    private Mon getForm() {
        Mon td = new Mon();
        td.setMaMon(Integer.parseInt(txtTenMon1.getText()));
        ImageIcon imageIcon = (ImageIcon) lblHinhAnh.getIcon();
        if (imageIcon != null) {
            String imagePath = imageIcon.getDescription();
            td.setHinh(imagePath);
        }
        td.setTenMon(txtTenMon.getText());
        td.setDonGia(Double.parseDouble(txtDonGia.getText()));
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        td.setNgayADGia(dateNAD.getDate());
        td.setDonViTinh(txtDonViTinh.getText());
        td.setTenLoai((String) cboLoaiTD.getSelectedItem());

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
        Mon td = new Mon();

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

    void loadLoaiThucDonToCbo() {
        DefaultComboBoxModel model = (DefaultComboBoxModel) cboLoaiTD.getModel();
        model.removeAllElements();

        //Lấy danh sách loại thực đơn từ database
        LoaiTDDAO dao = new LoaiTDDAO();
        List<LoaiTD> list = dao.selectAll();

        //Thêm loại thực đơn vào comboBox
        for (LoaiTD loaiTD : list) {
            model.addElement(loaiTD.getTenLoai());

        }
    }

    void loadLoaiThucDonToCboTraCuuLoaiTD() {
        DefaultComboBoxModel model = (DefaultComboBoxModel) cboTraCuuLoaiThucDon.getModel();
        model.removeAllElements();

        LoaiTDDAO dao = new LoaiTDDAO();
        List<LoaiTD> list = dao.selectAll();

        for (LoaiTD loaiTD : list) {

            model.addElement(loaiTD.getTenLoai());
        }

    }

    private void setLoaiTDFromSelectedRow() {
        //Chỉ mục của cột loại thực đơn
        final int LOAI_TD_COLUMN_INDEX = 6;

        //Lấy chỉ mục của hàng được chọn
        int selectedRow = tblDanhSach.getSelectedRow();
        if (selectedRow >= 0) {
            //Lấy giá trị của cột loại thực đơn
            Object loaiThucDonValue = tblDanhSach.getValueAt(selectedRow, LOAI_TD_COLUMN_INDEX);

            //Đặt giá trị cho cboLoaiTD
            cboLoaiTD.setSelectedItem(loaiThucDonValue.toString());
            System.out.println("Selected Loai Thuc Don: " + loaiThucDonValue.toString());

        }
    }

    private void showRecordsByLoaiTD() {
        //Lấy loại thực đon được chọn từ cboLoaiTD
        String selectedLoaiTD = cboTraCuuLoaiThucDon.getSelectedItem().toString();

        //Gọi hàm truy vấn và hiển thị bản ghi của loại thực đơn được chọn
        List<Mon> records = dao.selectByLoaiTD(selectedLoaiTD);

        loadLoaiThucDonToCboTraCuuLoaiTD();
        //Hiển thị danh sách bản ghi lên bảng tblDanhSach
        HienThiBanGhiLoaiThucDon(records);
    }

    private void HienThiBanGhiLoaiThucDon(List<Mon> records) {
        DefaultTableModel model = (DefaultTableModel) tblDanhSach.getModel();
        model.setRowCount(0);

        for (Mon td : records) {
            Object[] rows = {td.getMaMon(), getImageIcon(td.getHinh()), td.getTenMon(), td.getDonGia(), td.getNgayADGia(), td.getDonViTinh(), td.getTenLoai()};
            model.addRow(rows);
        }
        tblDanhSach.setModel(model);
    }

    public List<Mon> searchByName(String tenMon) {
    List<Mon> resultList = new ArrayList<>();

    try {
        // Sử dụng PreparedStatement để tránh vấn đề về SQL injection
        String sql = "SELECT * FROM Mon WHERE TenMon LIKE ?";
        // "%" + tenMon + "%" để tìm kiếm tất cả các món có tên chứa tenMon
        ResultSet rs = JdbcHelper.excuteQuery(sql, "%" + tenMon + "%");

        while (rs.next()) {
            Mon entity = new Mon();
            entity.setMaMon(rs.getInt("MaMon"));
            entity.setHinh(rs.getString("Hinh"));
            entity.setTenMon(rs.getString("TenMon"));
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

    private void TimKiem(List<Mon> records) {
        DefaultTableModel model = (DefaultTableModel) tblDanhSach.getModel();
        model.setRowCount(0);

        for (Mon record : records) {
            Object[] rows = {record.getMaMon(), getImageIcon(record.getHinh()), record.getTenMon(), record.getDonGia(), record.getNgayADGia(), record.getDonViTinh(), record.getTenLoai()};
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

        jLabel8 = new javax.swing.JLabel();
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
        txtTenMon = new javax.swing.JTextField();
        cboLoaiTD = new javax.swing.JComboBox<>();
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
        txtTenMon1 = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        txtTraCuuTen = new javax.swing.JTextField();
        cboTraCuuLoaiThucDon = new javax.swing.JComboBox<>();
        btnTim = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();

        jLabel8.setText("jLabel8");

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
                "Mã món", "Hình ảnh", "Tên món", "Đơn Giá", "Ngày áp dụng giá", "Đơn Vị Tính", "Loại thực đơn"
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
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 252, Short.MAX_VALUE)))
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
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 569, Short.MAX_VALUE)
                .addContainerGap())
        );

        cc.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 140, 1000, 620));

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
        cc.add(btnXoa, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 550, 80, 30));

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
        cc.add(btnThem, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 550, 80, 30));

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

        jLabel1.setFont(new java.awt.Font("Segoe UI Black", 1, 12)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Loại thực đơn:");
        cc.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 270, -1, -1));

        jLabel3.setFont(new java.awt.Font("Segoe UI Black", 1, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Tên món:");
        cc.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 210, -1, -1));

        txtTenMon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTenMonActionPerformed(evt);
            }
        });
        cc.add(txtTenMon, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 230, 200, 30));

        cboLoaiTD.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Chọn loại thực đơn", "-Món ăn chính", "-Món khai vị", "-Đồ uống", "-Món tráng miệng" }));
        cboLoaiTD.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cboLoaiTDMouseClicked(evt);
            }
        });
        cc.add(cboLoaiTD, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 290, 200, 30));

        jLabel2.setFont(new java.awt.Font("Segoe UI Black", 1, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Đơn giá:");
        cc.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 340, -1, -1));
        cc.add(txtDonGia, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 360, 200, 30));

        jLabel4.setFont(new java.awt.Font("Segoe UI Black", 1, 12)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Ngày áp dụng:");
        cc.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 410, -1, -1));
        cc.add(dateNAD, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 430, 200, 30));

        jLabel5.setFont(new java.awt.Font("Segoe UI Black", 1, 12)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Đơn vị tính:");
        cc.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 480, -1, -1));

        txtDonViTinh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDonViTinhActionPerformed(evt);
            }
        });
        cc.add(txtDonViTinh, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 500, 200, 30));

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
        cc.add(btnSua1, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 550, 80, 30));

        jLabel13.setFont(new java.awt.Font("Segoe UI Black", 1, 12)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setText("Hình ảnh:");
        cc.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 140, -1, -1));

        jLabel12.setFont(new java.awt.Font("Segoe UI Black", 1, 12)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setText("ID:");
        cc.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 150, -1, -1));

        txtTenMon1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTenMon1ActionPerformed(evt);
            }
        });
        cc.add(txtTenMon1, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 170, 200, 30));

        jPanel1.setBackground(new java.awt.Color(218, 212, 181));

        jLabel17.setFont(new java.awt.Font("Segoe UI Black", 1, 18)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(167, 49, 33));
        jLabel17.setText("Tra cứu thực đơn");

        cboTraCuuLoaiThucDon.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        cboTraCuuLoaiThucDon.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Chọn Loại Thực Đơn", " ", " " }));
        cboTraCuuLoaiThucDon.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cboTraCuuLoaiThucDonMouseClicked(evt);
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

        jLabel6.setText("Loại thực đơn:");

        jLabel7.setText("Tên thực đơn:");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(141, 141, 141)
                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(157, 157, 157)
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel17)))
                .addContainerGap(394, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addGap(142, 142, 142)
                    .addComponent(txtTraCuuTen, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(110, 110, 110)
                    .addComponent(cboTraCuuLoaiThucDon, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(95, 95, 95)
                    .addComponent(btnTim, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(163, Short.MAX_VALUE)))
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
                            .addComponent(cboTraCuuLoaiThucDon, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnTim, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addContainerGap(10, Short.MAX_VALUE)))
        );

        cc.add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 20, 1000, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(cc, javax.swing.GroupLayout.DEFAULT_SIZE, 1503, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(cc, javax.swing.GroupLayout.DEFAULT_SIZE, 776, Short.MAX_VALUE)
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

    private void cboLoaiTDMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cboLoaiTDMouseClicked
        // TODO add your handling code here:
        loadLoaiThucDonToCbo();
    }//GEN-LAST:event_cboLoaiTDMouseClicked

    private void btnLamMoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLamMoiActionPerformed
        // TODO add your handling code here:
        clearForm();
    }//GEN-LAST:event_btnLamMoiActionPerformed

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed
        // TODO add your handling code here:
        insert();
    }//GEN-LAST:event_btnThemActionPerformed

    private void txtTenMonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTenMonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTenMonActionPerformed

    private void txtTenMon1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTenMon1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTenMon1ActionPerformed

    private void cboTraCuuLoaiThucDonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cboTraCuuLoaiThucDonMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_cboTraCuuLoaiThucDonMouseClicked

    private void btnTimActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTimActionPerformed
        // TODO add your handling code here:
        String tenMonCanTim = txtTraCuuTen.getText();
        List<Mon> searchResult = dao.searchByName(tenMonCanTim);
        
        TimKiem(searchResult);
    }//GEN-LAST:event_btnTimActionPerformed

    private void btnSua1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSua1ActionPerformed
        // TODO add your handling code here:
        update();
    }//GEN-LAST:event_btnSua1ActionPerformed

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
            java.util.logging.Logger.getLogger(QuanLyThucDonJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(QuanLyThucDonJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(QuanLyThucDonJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(QuanLyThucDonJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                QuanLyThucDonJDialog dialog = new QuanLyThucDonJDialog(new javax.swing.JFrame(), true);
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
    private javax.swing.JComboBox<String> cboLoaiTD;
    private javax.swing.JComboBox<String> cboTraCuuLoaiThucDon;
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
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblHinhAnh;
    private javax.swing.JTable tblDanhSach;
    private javax.swing.JTextField txtDonGia;
    private javax.swing.JTextField txtDonViTinh;
    private javax.swing.JTextField txtTenMon;
    private javax.swing.JTextField txtTenMon1;
    private javax.swing.JTextField txtTraCuuTen;
    // End of variables declaration//GEN-END:variables
}
