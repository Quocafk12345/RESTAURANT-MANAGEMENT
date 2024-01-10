/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.edusys.ui;

import com.edusys.dao.BanDAO;
import com.edusys.dao.DaHoanThanhDAO;
import com.edusys.dao.DangCheBienDAO;
import com.edusys.dao.DatMonDAO;
import com.edusys.dao.ThoiGianLamDAO;
import com.edusys.entity.Ban;
import com.edusys.entity.DaHoanThanh;
import com.edusys.entity.DangCheBien;
import com.edusys.entity.DatMon;
import com.edusys.entity.ThoiGianLam;
import com.edusys.utils.MgsBox;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author MS QUOC
 */
public class Bep extends javax.swing.JFrame {

    /**
     * Creates new form LapHoaDon
     */
    DangCheBienDAO dangCheBienDao = new DangCheBienDAO();
    DaHoanThanhDAO daHoanThanhDAO = new DaHoanThanhDAO();
    int row = 0;

    public Bep() {
        initComponents();
        fillToTableThucDon();
        fillToTableDangCheBien();
        fillToTableDaHoanThanh();
        loadBanAnToCbo();
        loadTGLamToCbo();
        tblDatMon.setDefaultEditor(Object.class, null);
    }

    DatMonDAO datMonDAO = new DatMonDAO();

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

    private DangCheBien getForm() {
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
        DangCheBien entity = getForm();
        if (entity != null) {
            dangCheBienDao.insert(entity);
            fillToTableDangCheBien();
            clearForm();
        }
    }
    
    void UpdateDangCheBien() {
        DangCheBien entity = getForm();
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

    void edit() {
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

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel3 = new javax.swing.JPanel();
        jLocaleChooserBeanInfo1 = new com.toedter.components.JLocaleChooserBeanInfo();
        jCalendarTheme1 = new com.toedter.plaf.JCalendarTheme();
        jPanel4 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblDatMon = new javax.swing.JTable();
        jLabel9 = new javax.swing.JLabel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jLabel2 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblDangCheBien = new javax.swing.JTable();
        jLabel10 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
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
        jPanel7 = new javax.swing.JPanel();
        btnHoanThanh = new javax.swing.JButton();
        btnXoa = new javax.swing.JButton();
        btnSua = new javax.swing.JButton();
        btnXoaDHT = new javax.swing.JButton();

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel4.setBackground(new java.awt.Color(149, 35, 35));
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(218, 212, 181));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

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
        if (tblDatMon.getColumnModel().getColumnCount() > 0) {
            tblDatMon.getColumnModel().getColumn(0).setResizable(false);
        }

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 80, 530, 580));

        jLabel9.setBackground(new java.awt.Color(218, 212, 181));
        jLabel9.setFont(new java.awt.Font("Segoe UI Black", 1, 24)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(167, 49, 33));
        jLabel9.setText("Danh sách thực đơn");
        jPanel1.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 0, 260, 70));
        jPanel1.add(jTabbedPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, -70, 1570, 740));

        jPanel4.add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 160, 554, 660));

        jLabel2.setFont(new java.awt.Font("Segoe UI Black", 1, 100)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Bếp");
        jPanel4.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 6, 260, 72));

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
        if (tblDangCheBien.getColumnModel().getColumnCount() > 0) {
            tblDangCheBien.getColumnModel().getColumn(0).setResizable(false);
        }

        jPanel5.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, 740, 210));

        jLabel10.setBackground(new java.awt.Color(218, 212, 181));
        jLabel10.setFont(new java.awt.Font("Segoe UI Black", 1, 18)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(167, 49, 33));
        jLabel10.setText("Danh sách thực đơn đang chế biến");
        jPanel5.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 10, 330, 30));

        jPanel4.add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(831, 119, 766, 283));

        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setText("Bàn:");
        jPanel2.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(29, 21, 56, -1));

        cboChonBan.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Chọn bàn", " " }));
        jPanel2.add(cboChonBan, new org.netbeans.lib.awtextra.AbsoluteConstraints(29, 43, 180, -1));

        jLabel3.setText("Tên món:");
        jPanel2.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(29, 87, 66, -1));
        jPanel2.add(txtTenMon, new org.netbeans.lib.awtextra.AbsoluteConstraints(29, 109, 180, -1));
        jPanel2.add(spnSoLuong, new org.netbeans.lib.awtextra.AbsoluteConstraints(29, 165, -1, -1));

        jLabel4.setText("Số lượng");
        jPanel2.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(29, 143, 66, -1));

        jLabel5.setText("Thời gian làm:");
        jPanel2.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(29, 199, -1, -1));

        btnThem.setBackground(new java.awt.Color(0, 102, 102));
        btnThem.setFont(new java.awt.Font("Segoe UI Black", 1, 18)); // NOI18N
        btnThem.setForeground(new java.awt.Color(255, 255, 255));
        btnThem.setText("CHẾ BIẾN");
        btnThem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemActionPerformed(evt);
            }
        });
        jPanel2.add(btnThem, new org.netbeans.lib.awtextra.AbsoluteConstraints(29, 270, 180, 59));

        cboThoiGianLam.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "30 phút", "1 giờ", "1 giờ 30 phút", "2 giờ", "2 giờ 30 phút", "3 giờ", " " }));
        jPanel2.add(cboThoiGianLam, new org.netbeans.lib.awtextra.AbsoluteConstraints(29, 221, 180, -1));

        jPanel4.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(572, 119, 253, -1));

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
        if (tblDaHoanThanh.getColumnModel().getColumnCount() > 0) {
            tblDaHoanThanh.getColumnModel().getColumn(0).setResizable(false);
        }

        jPanel6.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, 740, 240));

        jLabel11.setBackground(new java.awt.Color(218, 212, 181));
        jLabel11.setFont(new java.awt.Font("Segoe UI Black", 1, 18)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(167, 49, 33));
        jLabel11.setText("Danh sách thực đơn đã hoàn thành");
        jPanel6.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 10, 330, 30));

        jPanel4.add(jPanel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(831, 502, 766, 282));

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

        jPanel4.add(jPanel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(831, 408, -1, -1));

        btnXoaDHT.setBackground(new java.awt.Color(0, 102, 102));
        btnXoaDHT.setFont(new java.awt.Font("Segoe UI Black", 0, 12)); // NOI18N
        btnXoaDHT.setForeground(new java.awt.Color(255, 255, 255));
        btnXoaDHT.setText("XÓA");
        btnXoaDHT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaDHTActionPerformed(evt);
            }
        });
        jPanel4.add(btnXoaDHT, new org.netbeans.lib.awtextra.AbsoluteConstraints(652, 545, 129, 51));

        getContentPane().add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1580, 790));

        pack();
    }// </editor-fold>//GEN-END:initComponents

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
    }//GEN-LAST:event_btnSuaActionPerformed

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed
        // TODO add your handling code here:
        InsertDangCheBien();
    }//GEN-LAST:event_btnThemActionPerformed

    private void tblDatMonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblDatMonMouseClicked
        // TODO add your handling code here:
        if (evt.getClickCount() == 1) {
            this.row = tblDatMon.getSelectedRow();
            this.edit();
        }
    }//GEN-LAST:event_tblDatMonMouseClicked

    private void btnXoaDHTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaDHTActionPerformed
        // TODO add your handling code here:
        deleteDaHoanThanh();
    }//GEN-LAST:event_btnXoaDHTActionPerformed

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
            java.util.logging.Logger.getLogger(Bep.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Bep.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Bep.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Bep.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Bep().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnHoanThanh;
    private javax.swing.JButton btnSua;
    private javax.swing.JButton btnThem;
    private javax.swing.JButton btnXoa;
    private javax.swing.JButton btnXoaDHT;
    private javax.swing.JComboBox<String> cboChonBan;
    private javax.swing.JComboBox<String> cboThoiGianLam;
    private com.toedter.plaf.JCalendarTheme jCalendarTheme1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel9;
    private com.toedter.components.JLocaleChooserBeanInfo jLocaleChooserBeanInfo1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JSpinner spnSoLuong;
    private javax.swing.JTable tblDaHoanThanh;
    private javax.swing.JTable tblDangCheBien;
    private javax.swing.JTable tblDatMon;
    private javax.swing.JTextField txtTenMon;
    // End of variables declaration//GEN-END:variables
}
