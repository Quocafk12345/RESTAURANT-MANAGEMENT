/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.edusys.ui;

import com.edusys.dao.BanDAO;
import com.edusys.dao.ChiTietHoaDonDAO;
import com.edusys.dao.MuaNguyenLieuDAO;
import com.edusys.dao.HoaDonDAO;
import com.edusys.dao.LoaiTDDAO;
import com.edusys.dao.MonDAO;
import com.edusys.dao.MuaNguyenLieuDAO;
import com.edusys.dao.MuaNguyenLieuDAO;
import com.edusys.entity.Ban;
import com.edusys.entity.ChiTietHoaDon;
import com.edusys.entity.MuaNguyenLieu;
import com.edusys.entity.LoaiTD;
import com.edusys.entity.MuaNguyenLieu;
import com.edusys.entity.MuaNguyenLieu;
import com.edusys.utils.ImageRenderer;
import com.edusys.utils.MgsBox;
import com.edusys.utils.XImage;
import java.awt.Frame;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.Timer;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author MS QUOC
 */
public class CapNhatMuaNguyenLieu extends javax.swing.JFrame {

    /**
     * Creates new form OrderJFrame
     */
    MuaNguyenLieuDAO mnldao = new MuaNguyenLieuDAO();
    MonDAO mondao = new MonDAO();
    HoaDonDAO hoaDondao = new HoaDonDAO();
    ChiTietHoaDonDAO chiTietHoaDonDAO = new ChiTietHoaDonDAO();
    int row = 0;

    public CapNhatMuaNguyenLieu() {
        initComponents();
        init();
        setLocationRelativeTo(null);
        setIconImage(XImage.getAppIcon());
        setLocationRelativeTo(null);
        setTitle("QH-RESTAURANT SYSTEM");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        tblThucDon.setDefaultEditor(Object.class, null);
        tblTDDuocChon.setDefaultEditor(Object.class, null);
        fillToTableMuaNguyenLieu();
        fillToTableNguyenLieu();
        loadLoaiThucDonToCBO();

        Timer timer = new Timer(1000, e -> {
            LocalDateTime currentDateTime = LocalDateTime.now();
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
            String fomatedDateTime = currentDateTime.format(dtf);
            lblTime.setText(fomatedDateTime);
        });
        timer.start();
        cboThucDon.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedLoaiTD = cboThucDon.getSelectedItem().toString();

                List<MuaNguyenLieu> records = mnldao.selectByLoaiTD(selectedLoaiTD);
                ShowRecordsByLoaiTD();
            }
        });

       
    }

    void init() {
        setIconImage(XImage.getAppIcon());
        setLocationRelativeTo(null);
        setTitle("QH-RESTAURANT SYSTEM");

    }

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

    void clearForm() {
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

    void insertChiTietHoaDon() {
        ChiTietHoaDon entity = getFormChiTietHoaDon();
        chiTietHoaDonDAO.insert(entity);

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
                clearForm();
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
                com.edusys.entity.HoaDon model = hoaDondao.selectById(maHoaDon);

                if (model != null) {
                    hoaDondao.update(model);
                    fillToTableMuaNguyenLieu();
                    clearForm();
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
                    hoaDondao.delete(maHoaDon);
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
        jPanel2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblTDDuocChon = new javax.swing.JTable();
        jButton2 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        cboThucDon = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblThucDon = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        spnSoLuong = new javax.swing.JSpinner();
        jLabel15 = new javax.swing.JLabel();
        jButton4 = new javax.swing.JButton();
        txtSoHD = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        lblTime = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        txtTenLoai = new javax.swing.JTextField();
        txtTenNguyenLieu = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtDonGia = new javax.swing.JTextField();
        spnSoLuong1 = new javax.swing.JSpinner();
        jLabel16 = new javax.swing.JLabel();
        btnCapNhat = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        txtMaHoaDon = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        txtMaMua = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(149, 35, 35));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Cập nhật nguyên liệu đã mua", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 48), new java.awt.Color(255, 255, 255))); // NOI18N

        jPanel2.setBackground(new java.awt.Color(218, 212, 181));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Nguyên liệu được trong kho", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 36), new java.awt.Color(153, 0, 51))); // NOI18N

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
        jScrollPane2.setViewportView(tblTDDuocChon);

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

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 540, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap(24, Short.MAX_VALUE)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 384, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(78, 78, 78))
        );

        jPanel3.setBackground(new java.awt.Color(218, 212, 181));
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Nguyên liệu", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 36), new java.awt.Color(153, 0, 51))); // NOI18N

        jLabel1.setFont(new java.awt.Font("Segoe UI Black", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(149, 35, 35));
        jLabel1.setText("Chọn loại nguyên liệu:");

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
        jScrollPane1.setViewportView(tblThucDon);

        jLabel2.setFont(new java.awt.Font("Segoe UI Black", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(149, 35, 35));
        jLabel2.setText("Chọn bàn:");

        jLabel15.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(167, 49, 33));
        jLabel15.setText("Số lượng");

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

        jLabel7.setFont(new java.awt.Font("Segoe UI Black", 1, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(149, 35, 35));
        jLabel7.setText("Số hóa đơn:");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtSoHD, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(164, 164, 164)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(cboThucDon, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel1))
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(3, 3, 3)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(spnSoLuong, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jLabel2)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(64, 64, 64)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cboThucDon))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtSoHD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel15)
                        .addGap(4, 4, 4)
                        .addComponent(spnSoLuong, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 345, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        lblTime.setFont(new java.awt.Font("Segoe UI Black", 1, 14)); // NOI18N
        lblTime.setForeground(new java.awt.Color(255, 255, 255));
        lblTime.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/edusys/icon/images/Date.png"))); // NOI18N
        lblTime.setText("yyyy/MM/dd HH:mm:ss");

        jPanel4.setBackground(new java.awt.Color(218, 212, 181));
        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Cập nhật", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 36), new java.awt.Color(0, 0, 255))); // NOI18N

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel3.setText("Tên loại:");

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel5.setText("Tên nguyên liệu:");

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel6.setText("Đơn giá:");

        jLabel16.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(167, 49, 33));
        jLabel16.setText("Số lượng:");

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

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel8.setText("Mã hóa đơn:");

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel9.setText("Mã mua:");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel4Layout.createSequentialGroup()
                        .addGap(105, 105, 105)
                        .addComponent(btnCapNhat, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(0, 15, Short.MAX_VALUE)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                                .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtTenNguyenLieu, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txtTenLoai, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(18, 18, 18)
                                    .addComponent(txtMaHoaDon, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(18, 18, 18)
                                    .addComponent(txtMaMua, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel4Layout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(spnSoLuong1, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtDonGia, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap(30, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtMaHoaDon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8))
                .addGap(28, 28, 28)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtMaMua, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9))
                .addGap(26, 26, 26)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTenLoai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addGap(26, 26, 26)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTenNguyenLieu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addGap(28, 28, 28)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtDonGia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 32, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(spnSoLuong1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel16))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnCapNhat, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(16, 16, 16))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblTime)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(lblTime)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 69, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(23, 23, 23))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

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
            java.util.logging.Logger.getLogger(CapNhatMuaNguyenLieu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(CapNhatMuaNguyenLieu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(CapNhatMuaNguyenLieu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CapNhatMuaNguyenLieu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new CapNhatMuaNguyenLieu().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCapNhat;
    private javax.swing.JComboBox<String> cboThucDon;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblTime;
    private javax.swing.JSpinner spnSoLuong;
    private javax.swing.JSpinner spnSoLuong1;
    private javax.swing.JTable tblTDDuocChon;
    private javax.swing.JTable tblThucDon;
    private javax.swing.JTextField txtDonGia;
    private javax.swing.JTextField txtMaHoaDon;
    private javax.swing.JTextField txtMaMua;
    private javax.swing.JTextField txtSoHD;
    private javax.swing.JTextField txtTenLoai;
    private javax.swing.JTextField txtTenNguyenLieu;
    // End of variables declaration//GEN-END:variables
}
