/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.edusys.ui;

import com.edusys.dao.BanDAO;
import com.edusys.dao.ChiTietHoaDonDAO;
import com.edusys.dao.DatMonDAO;
import com.edusys.dao.HoaDonDAO;
import com.edusys.dao.LoaiTDDAO;
import com.edusys.dao.MonDAO;
import com.edusys.entity.Ban;
import com.edusys.entity.ChiTietHoaDon;
import com.edusys.entity.DatMon;
import com.edusys.entity.HoaDon;
import com.edusys.entity.LoaiTD;
import com.edusys.entity.Mon;
import com.edusys.utils.ImageRenderer;
import com.edusys.utils.MgsBox;
import com.edusys.utils.XImage;
import java.awt.Frame;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.SpinnerNumberModel;
import javax.swing.Timer;
import javax.swing.table.DefaultTableModel;
import javax.swing.JSpinner;

/**
 *
 * @author MS QUOC
 */
public class OrderJFrame extends javax.swing.JFrame {

    /**
     * Creates new form OrderJFrame
     */
    MonDAO mondao = new MonDAO();
    DatMonDAO datmondao = new DatMonDAO();
    HoaDonDAO hoaDondao = new HoaDonDAO();
    ChiTietHoaDonDAO chiTietHoaDonDAO = new ChiTietHoaDonDAO();
    int row = 0;

    boolean validateFormThucDon() {
        if (cboChonBan.getSelectedItem().toString().equals("Chọn bàn")) {
            MgsBox.alert(this, "Vui lòng chọn bàn!");
            return false;
        } else {
//            for (int i = 0; i < tblTDDuocChon.getRowCount(); i++) {
//                if (tblThucDon.getValueAt(row, 0).equals(String.valueOf(tblTDDuocChon.getValueAt(i, 2)))){
//                    int sum = Integer.valueOf(String.valueOf(spnSoLuong.getValue())) + Integer.valueOf(String.valueOf(tblTDDuocChon.getValueAt(i, 5)));
//                    tblTDDuocChon.setValueAt(sum, i, 5);
//                }
//            }

        }
        return true;
    }

    boolean validateFormCapNhat() {
        if (txtTenMon.getText().isEmpty()) {
            MgsBox.alert(this, "Vui lòng nhập đầy đủ thông tin!");
            return false;
        } else if (txtDonGia.getText().isEmpty()) {
            MgsBox.alert(this, "Vui lòng nhập đầy đủ thông tin!");
            return false;
        } else if (txtMaOrder.getText().isEmpty()) {
            MgsBox.alert(this, "Vui lòng nhập đầy đủ thông tin!");
            return false;
        }
        return true;
    }

    public OrderJFrame() {
        initComponents();
        init();
        setLocationRelativeTo(null);
        tblThucDon.setDefaultEditor(Object.class, null);
        tblTDDuocChon.setDefaultEditor(Object.class, null);
        fillToTableThucDon();
        fillToTableDatMon();
        loadSoHDToCBO();
        loadLoaiThucDonToCBO();
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

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

                List<Mon> records = mondao.selectByLoaiTD(selectedLoaiTD);
                ShowRecordsByLoaiTD();
            }
        });

        cboChonBan.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedMaSoBan = cboChonBan.getSelectedItem().toString();

                List<DatMon> records = datmondao.selectByMaBan(selectedMaSoBan);
                showRecordByBanAn();
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

    void fillToTableThucDon() {
        DefaultTableModel model = (DefaultTableModel) tblThucDon.getModel();
        model.setRowCount(0);
        try {
            List<Mon> list = mondao.selectAll();

            int columnCount = tblThucDon.getColumnCount();
            int columnWidth = 100;
            for (Mon mon : list) {
                Image im = Toolkit.getDefaultToolkit().createImage(mon.getHinh());
                ImageIcon ic = new ImageIcon(im);
                Object[] row
                        = {mon.getMaMon(), ic, mon.getTenMon(), mon.getDonGia()};
                model.addRow(row);
            }
        } catch (Exception e) {
            e.printStackTrace();
            MgsBox.alert(this, "Lỗi truy vấn dữ liệu!");
        }
        tblThucDon.getColumnModel().getColumn(1).setCellRenderer(new ImageRenderer());
    }

    void fillToTableDatMon() {
        DefaultTableModel model = (DefaultTableModel) tblTDDuocChon.getModel();
        model.setRowCount(0);
        String maSoBan = cboChonBan.getSelectedItem().toString();
        try {
            List<DatMon> list = datmondao.selectByMaBan(maSoBan);
            for (DatMon datMon : list) {
                Object[] row = {
                    datMon.getMaSoBan(), datMon.getMaOrder(), datMon.getMaMon(), datMon.getTenMon(), datMon.getDonGia(), datMon.getSoLuong()
                };
                model.addRow(row);

            }
        } catch (Exception e) {
            MgsBox.alert(this, "Lỗi truy vấn dữ liệu");
        }
    }

    private DatMon getFormDatMon() {
        DatMon datmon = new DatMon();
        int selectedRow = tblThucDon.getSelectedRow();
        datmon.setMaSoBan((String) cboChonBan.getSelectedItem());
        datmon.setMaMon((Integer) tblThucDon.getValueAt(selectedRow, 0));
        datmon.setTenMon((String) tblThucDon.getValueAt(selectedRow, 2));
        datmon.setDonGia((Double) tblThucDon.getValueAt(selectedRow, 3));
        datmon.setSoLuong((Integer) spnSoLuong.getValue());
        Date date = null;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd ");
        try {
            date = dateFormat.parse(lblTime.getText());
        } catch (Exception e) {
        }
        datmon.setNgayDatban(date);
        datmon.setSoHD(Integer.parseInt(cboSoHD.getSelectedItem().toString()));
        return datmon;
    }

    void setFormDatMon(DatMon datmon) {
        txtMaOrder.setText(Integer.valueOf(datmon.getMaOrder()).toString());
        txtTenMon.setText(datmon.getTenMon());
        txtDonGia.setText(String.valueOf(datmon.getDonGia()));
        spnSoLuong1.setValue(datmon.getSoLuong());
    }

    void edit() {

        int selectedRow = tblTDDuocChon.getSelectedRow();
        if (selectedRow != -1) {
            // Có dòng được chọn
            Object maOrderObject = tblTDDuocChon.getValueAt(selectedRow, 1);

            if (maOrderObject != null) {
                int maOrder = (Integer) maOrderObject;

                // Gọi hàm xóa với mã món
                DatMon datmon = datmondao.selectById(maOrder);
                setFormDatMon(datmon);
                fillToTableDatMon();
            }
        }

    }

    void clearForm() {
        txtMaOrder.setText("");
        txtTenMon.setText("");
        txtDonGia.setText("");
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
        chiTietHoaDon.setMaSoBan((String) cboChonBan.getSelectedItem());
        chiTietHoaDon.setTenLoai((String) cboThucDon.getSelectedItem());
        chiTietHoaDon.setTenMon((String) tblThucDon.getValueAt(selectedRow, 0));
        chiTietHoaDon.setDonGia((Double) tblThucDon.getValueAt(selectedRow, 1));
        chiTietHoaDon.setSoLuong((Integer) spnSoLuong.getValue());
        chiTietHoaDon.setSoHD(Integer.parseInt(cboSoHD.getSelectedItem().toString()));
        return chiTietHoaDon;
    }

    void insert() {
        DatMon entity = getFormDatMon();
        datmondao.insert(entity);
        fillToTableDatMon();
    }

    void insertChiTietHoaDon() {
        ChiTietHoaDon entity = getFormChiTietHoaDon();
        chiTietHoaDonDAO.insert(entity);

    }

    private void updateDatMon() {
        int selectedRow = tblTDDuocChon.getSelectedRow();

        if (selectedRow != -1) {
            Object maOrderObject = tblTDDuocChon.getValueAt(selectedRow, 1);

            if (maOrderObject != null) {
                int maOrder = (Integer) maOrderObject;
                DatMon model = datmondao.selectById(maOrder);

                if (model != null) {
                    model.setSoLuong(getSoLuongFromSpinner1());
                    datmondao.update(model);
                    fillToTableDatMon();
                    clearForm();
                    MgsBox.alert(this, "Cập nhật thành công");
                } else {
                    MgsBox.alert(this, "Dữ liệu không hợp lệ cho mã món.");
                }
            } else {
                MgsBox.alert(this, "Dữ liệu không hợp lệ cho mã món.");
            }
        } else {
            MgsBox.alert(this, "Vui lòng chọn một sản phẩm để cập nhật");

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
                    fillToTableDatMon();
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

     void loadSoHDToCBO() {
        DefaultComboBoxModel model = (DefaultComboBoxModel) cboSoHD.getModel();
        model.removeAllElements();
        List<HoaDon> list = hoaDondao.selectAll();

        for (HoaDon hoadon : list) {
            model.addElement(hoadon.getMaHoaDon());
        }

    }

    private void deleteDatMon() {
        int selectedRow = tblTDDuocChon.getSelectedRow();

        if (selectedRow != -1) {
            // Có dòng được chọn
            Object maOrderObject = tblTDDuocChon.getValueAt(selectedRow, 1);

            if (maOrderObject != null) {
                int maOrder = (Integer) maOrderObject;

                if (MgsBox.confirm(this, "Bạn thực sự muốn xóa món này?")) {
                    // Gọi hàm xóa với mã món
                    datmondao.delete(maOrder);
                    fillToTableDatMon();
                    MgsBox.alert(this, "Xóa món thành công");
                } else {
                    MgsBox.alert(this, "Xóa món thất bại!");
                }
            }
        } else {
        }
    }

    private void deleteAllDatMon() {

        String selectedMaSoBan = cboChonBan.getSelectedItem().toString();
        if (MgsBox.confirm(this, "Bạn thực sự muốn xóa hết?")) {
            // Gọi hàm xóa với mã món
            datmondao.deleteAllByMaSoBan(selectedMaSoBan);
            fillToTableDatMon();
            MgsBox.alert(this, "Xóa món thành công");
        } else {
            MgsBox.alert(this, "Xóa món thất bại!");
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
                    fillToTableDatMon();
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

    void loadBanAnToCBO() {
        DefaultComboBoxModel model = (DefaultComboBoxModel) cboChonBan.getModel();
        model.removeAllElements();

        BanDAO dao = new BanDAO();
        List<Ban> list = dao.selectAll();

        for (Ban ban : list) {
            model.addElement(ban.getMaSoBan());
        }

    }

    //Hiển thị bản ghi loại thực đơn
    void ShowRecordsByLoaiTD() {
        String selectedLoaiTD = cboThucDon.getSelectedItem().toString();

        List<Mon> records = mondao.selectByLoaiTD(selectedLoaiTD);

        loadLoaiThucDonToCBO();

        HienThiBanGhiLoaiTD(records);
    }

    void HienThiBanGhiLoaiTD(List<Mon> records) {
        DefaultTableModel model = (DefaultTableModel) tblThucDon.getModel();
        model.setRowCount(0);

        for (Mon record : records) {
            Image im = Toolkit.getDefaultToolkit().createImage(record.getHinh());
            ImageIcon ic = new ImageIcon(im);

            Object[] rows = {record.getMaMon(), ic, record.getTenMon(), record.getDonGia()};
            model.addRow(rows);
        }
        tblThucDon.setModel(model);

    }

    void showRecordByBanAn() {
        String selectedBanAn = cboChonBan.getSelectedItem().toString();

        List<DatMon> records = datmondao.selectByMaBan(selectedBanAn);

        hienThiBanGhiTheoMaBan(records);

    }

    void insertOrder() {
        try {
            boolean itemExists = false;
            DatMon entity = getFormDatMon();

            for (int i = 0; i < tblTDDuocChon.getRowCount(); i++) {
                if (tblTDDuocChon.getValueAt(i, 2) != null && (int) tblTDDuocChon.getValueAt(i, 2) == entity.getMaMon()) {
                    // Item with the same ID exists
                    itemExists = true;
                    break;
                }
            }

            if (itemExists) {
                // Item with the same ID exists, perform the update
                int existingRow = getRowIndexByMaMon(entity.getMaMon());
                if (existingRow != -1) {
                    int existingQuantity = (Integer) tblTDDuocChon.getValueAt(existingRow, 5);
                    int newQuantity = (Integer) spnSoLuong.getValue() + existingQuantity;

                    // Calculate the new total price
                    double existingUnitPrice = (Double) tblThucDon.getValueAt(existingRow, 3);
                    double newTotalPrice = existingUnitPrice * newQuantity;

                    // Update the existing item in the table
                    tblTDDuocChon.setValueAt(newQuantity, existingRow, 5);
                    tblTDDuocChon.setValueAt(newTotalPrice, existingRow, 4);

                    // Use the update method of DatMonDAO to update the database
                    DatMon existingEntity = getFormDatMonFromTable(existingRow);
                    existingEntity.setSoLuong(newQuantity);
                    existingEntity.setDonGia(newTotalPrice);
                    datmondao.updateSoLuongDonGia(existingEntity);

                    JOptionPane.showMessageDialog(this, "Cập nhật thành công");
                }
            } else {
                // Item doesn't exist, insert a new record
                datmondao.insert(entity); // Insert the new item in the database
                JOptionPane.showMessageDialog(this, "Thêm thành công");
            }

            fillToTableDatMon();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi trong quá trình xử lý: " + e.getMessage());
        }
    }

    private int getRowIndexByMaMon(int maMon) {
        for (int i = 0; i < tblTDDuocChon.getRowCount(); i++) {
            if (tblTDDuocChon.getValueAt(i, 2) != null && (int) tblTDDuocChon.getValueAt(i, 2) == maMon) {
                return i;
            }
        }
        return -1;
    }

// Phương thức này tạo ra một đối tượng DatMon từ dữ liệu của dòng i trong bảng
    private DatMon getFormDatMonFromTable(int row) {
        DatMon entity = new DatMon();
        entity.setMaSoBan((String) tblTDDuocChon.getValueAt(row, 0)); // Thay đổi từ (Integer) thành (String)
        entity.setMaOrder((Integer) tblTDDuocChon.getValueAt(row, 1));
        entity.setMaMon((Integer) tblTDDuocChon.getValueAt(row, 2));
        entity.setTenMon((String) tblTDDuocChon.getValueAt(row, 3));
        entity.setDonGia((Double) tblTDDuocChon.getValueAt(row, 4));
        entity.setSoLuong((Integer) tblTDDuocChon.getValueAt(row, 5));
        // Ngày đặt bàn không thay đổi nên không cần set
        return entity;
    }

    void hienThiBanGhiTheoMaBan(List<DatMon> records) {
        DefaultTableModel model = (DefaultTableModel) tblTDDuocChon.getModel();
        model.setRowCount(0);

        for (DatMon hoaDon : records) {
            Object[] rows = {hoaDon.getMaSoBan(), hoaDon.getMaOrder(), hoaDon.getMaMon(), hoaDon.getTenMon(), hoaDon.getDonGia(), hoaDon.getSoLuong()};
            model.addRow(rows);
        }

        tblTDDuocChon.setModel(model);
    }
    //Hiển thị bản ghi mã số bàn

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
        jButton5 = new javax.swing.JButton();
        btnXoaHet = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        cboThucDon = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblThucDon = new javax.swing.JTable();
        cboChonBan = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        spnSoLuong = new javax.swing.JSpinner();
        jLabel15 = new javax.swing.JLabel();
        jButton4 = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        cboSoHD = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        lblTime = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        txtMaOrder = new javax.swing.JTextField();
        txtTenMon = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtDonGia = new javax.swing.JTextField();
        spnSoLuong1 = new javax.swing.JSpinner();
        jLabel16 = new javax.swing.JLabel();
        btnCapNhat = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(149, 35, 35));

        jPanel2.setBackground(new java.awt.Color(218, 212, 181));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Thực đơn được chọn", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 36), new java.awt.Color(153, 0, 51))); // NOI18N

        tblTDDuocChon.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Mã số bàn", "Mã Order", "Mã món", "Thực đơn", "Giá", "Số lượng"
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
        jButton2.setText("Xóa");
        jButton2.setBorder(null);
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton5.setBackground(new java.awt.Color(149, 35, 35));
        jButton5.setFont(new java.awt.Font("Segoe UI Black", 1, 14)); // NOI18N
        jButton5.setForeground(new java.awt.Color(255, 255, 255));
        jButton5.setText("Lập hóa đơn");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        btnXoaHet.setBackground(new java.awt.Color(149, 35, 35));
        btnXoaHet.setFont(new java.awt.Font("Segoe UI Black", 1, 12)); // NOI18N
        btnXoaHet.setForeground(new java.awt.Color(255, 255, 255));
        btnXoaHet.setText("Xóa hết");
        btnXoaHet.setBorder(null);
        btnXoaHet.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaHetActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 511, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnXoaHet, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton5)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap(33, Short.MAX_VALUE)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 384, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnXoaHet, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(78, 78, 78))
        );

        jPanel3.setBackground(new java.awt.Color(218, 212, 181));
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Thực đơn", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 36), new java.awt.Color(153, 0, 51))); // NOI18N

        jLabel1.setFont(new java.awt.Font("Segoe UI Black", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(149, 35, 35));
        jLabel1.setText("Chọn loại thực đơn:");

        cboThucDon.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Chọn loại thực đơn", " " }));
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
                "Mã món", "", "Tên món", "Đơn giá"
            }
        ));
        jScrollPane1.setViewportView(tblThucDon);

        cboChonBan.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Chọn bàn", "B101", "B102", "B103", "B104", "B105", "B106", "B107", "B108", "B109", "B110", "B201", "B202", "B203", "B204", "B205", "B206", "B207", "B208", "B209", "B210", " " }));
        cboChonBan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboChonBanActionPerformed(evt);
            }
        });

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

        cboSoHD.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Số hóa đơn", " ", " " }));

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
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cboSoHD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(158, 158, 158)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(cboThucDon, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel1))
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(3, 3, 3)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 519, Short.MAX_VALUE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(spnSoLuong, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cboChonBan, javax.swing.GroupLayout.PREFERRED_SIZE, 207, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jLabel2)
                .addGap(21, 21, 21)
                .addComponent(cboChonBan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(21, 21, 21)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cboThucDon)
                    .addComponent(cboSoHD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
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

        jLabel4.setFont(new java.awt.Font("Segoe UI Black", 1, 60)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Order");

        lblTime.setFont(new java.awt.Font("Segoe UI Black", 1, 14)); // NOI18N
        lblTime.setForeground(new java.awt.Color(255, 255, 255));
        lblTime.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/edusys/icon/images/Date.png"))); // NOI18N
        lblTime.setText("yyyy/MM/dd HH:mm:ss");

        jPanel4.setBackground(new java.awt.Color(218, 212, 181));
        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Cập nhật", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 36), new java.awt.Color(0, 0, 255))); // NOI18N

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel3.setText("Mã Order:");

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel5.setText("Tên món");

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel6.setText("Đơn giá");

        jLabel16.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(167, 49, 33));
        jLabel16.setText("Số lượng");

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

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(spnSoLuong1, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnCapNhat, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(txtTenMon, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtMaOrder, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtDonGia, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(20, 20, 20))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel16, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel4Layout.createSequentialGroup()
                                .addGap(2, 2, 2)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(57, 57, 57)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtMaOrder, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtTenMon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtDonGia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel16)
                .addGap(4, 4, 4)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(spnSoLuong1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCapNhat, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(20, Short.MAX_VALUE))
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
                .addContainerGap()
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 246, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblTime)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel4))
                            .addComponent(lblTime))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 72, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(23, 23, 23))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
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
        deleteDatMon();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        dispose();
        new LapHoaDon().setVisible(true);
    }//GEN-LAST:event_jButton5ActionPerformed

    private void cboThucDonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cboThucDonMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_cboThucDonMouseClicked

    private void cboChonBanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboChonBanActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cboChonBanActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        if (validateFormThucDon()) {
            insertOrder();

        }
    }//GEN-LAST:event_jButton4ActionPerformed

    private void btnCapNhatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCapNhatActionPerformed
        // TODO add your handling code here:
        if (validateFormCapNhat()) {
            updateDatMon();
        }
    }//GEN-LAST:event_btnCapNhatActionPerformed

    private void btnXoaHetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaHetActionPerformed
        // TODO add your handling code here:
        deleteAllDatMon();
    }//GEN-LAST:event_btnXoaHetActionPerformed

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
            java.util.logging.Logger.getLogger(OrderJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(OrderJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(OrderJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(OrderJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new OrderJFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCapNhat;
    private javax.swing.JButton btnXoaHet;
    private javax.swing.JComboBox<String> cboChonBan;
    private javax.swing.JComboBox<String> cboSoHD;
    private javax.swing.JComboBox<String> cboThucDon;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
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
    private javax.swing.JTextField txtMaOrder;
    private javax.swing.JTextField txtTenMon;
    // End of variables declaration//GEN-END:variables
}
