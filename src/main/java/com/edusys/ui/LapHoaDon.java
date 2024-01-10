/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.edusys.ui;

import com.edusys.dao.BanDAO;
import com.edusys.dao.DatMonDAO;
import com.edusys.dao.HoaDonDAO;
import com.edusys.dao.LoaiTDDAO;
import com.edusys.dao.NhanVienDAO;
import com.edusys.entity.Ban;
import com.edusys.entity.HoaDon;
import com.edusys.entity.ChiTietHoaDon;
import com.edusys.entity.DatMon;
import com.edusys.entity.LoaiTD;
import com.edusys.entity.NhanVien;
import com.edusys.utils.MgsBox;
import com.edusys.utils.XImage;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.PrinterException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

/**
 *
 * @author MS QUOC
 */
public class LapHoaDon extends javax.swing.JFrame {

    /**
     * Creates new form LapHoaDon
     */
    DatMonDAO datMonDao = new DatMonDAO();
    HoaDonDAO hoaDonDao = new HoaDonDAO();
    BanDAO banDao = new BanDAO();

    public LapHoaDon() {
        initComponents();
        setLocationRelativeTo(null);
        setIconImage(XImage.getAppIcon());
        setLocationRelativeTo(null);
        setTitle("QH-RESTAURANT SYSTEM");
        tblHoaDon.setDefaultEditor(Object.class, null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        fillToTableHoaDon();
        loadSoHDToCBO();
        cboChonBan.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedMaSoBan = cboChonBan.getSelectedItem().toString();
                List<DatMon> records = datMonDao.selectByMaBan(selectedMaSoBan);
                showRecordByBanAn();
                cal(records);
            }
        });



    }

    boolean validateForm() {
        if (cboSoHD.getSelectedItem().equals("Chọn Bàn")) {
            MgsBox.alert(this, "Vui lòng chọn bàn!");
            return false;
        } else if (txtTenKhachHang.getText().equals("")) {
            MgsBox.alert(this, "Vui lòng nhập tên khách hàng");
            return false;
        } else if (!txtTenKhachHang.getText().matches("^[^0-9]+$")) {
            MgsBox.alert(this, "Tên khách hàng không hợp lệ!");
            return false;
        } else if (txtSDTKH.getText().equals("")) {
            MgsBox.alert(this, "Vui lòng nhập đầy đủ thông tin!");
            return false;
        } else if (!isPhoneNumberValid(txtSDTKH.getText())) {
            MgsBox.alert(this, "Số điện thoại không hợp lệ!");
            return false;
        }
        return true;
    }

    private boolean isPhoneNumberValid(String phoneNumber) {
        // Kiểm tra chuỗi có chứa chỉ chữ số và có độ dài từ 10 đến 15 ký tự
        return phoneNumber.matches("\\d{10,15}");
    }

    void fillToTableHoaDon() {
        DefaultTableModel model = (DefaultTableModel) tblHoaDon.getModel();
        model.setRowCount(0);
        try {
            List<DatMon> list = datMonDao.selectAll();
            for (DatMon datMon : list) {
                Object[] row = {
                    datMon.getSoHD(),
                    datMon.getTenMon(),
                    datMon.getDonGia(),
                    datMon.getSoLuong()
                };
                model.addRow(row);
            }
        } catch (Exception e) {
            MgsBox.alert(this, "Lỗi truy vấn dữ liệu!");
        }
    }

    void loadDataToCboNhanVien() {
        DefaultComboBoxModel model = (DefaultComboBoxModel) cboChonNV.getModel();
        model.removeAllElements();

        NhanVienDAO dao = new NhanVienDAO();
        List<NhanVien> list = dao.selectAll();

        for (NhanVien nhanVien : list) {
            model.addElement(nhanVien.getMaNV());
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

    void loadSoHDToCBO() {
        DefaultComboBoxModel model = (DefaultComboBoxModel) cboSoHD.getModel();
        model.removeAllElements();

        List<HoaDon> list = hoaDonDao.selectAll();

        for (HoaDon hoadon : list) {
            model.addElement(hoadon.getMaHoaDon());
        }

    }

    void showRecordByBanAn() {
        String selectedBanAn = cboChonBan.getSelectedItem().toString();

        List<DatMon> records = datMonDao.selectByMaBan(selectedBanAn);

        hienThiBanGhiTheoMaBan(records);

    }

    void hienThiBanGhiTheoMaBan(List<DatMon> records) {
        DefaultTableModel model = (DefaultTableModel) tblHoaDon.getModel();
        model.setRowCount(0);

        for (DatMon hoaDon : records) {
            Object[] rows = {hoaDon.getMaOrder(), hoaDon.getTenMon(), hoaDon.getDonGia(), hoaDon.getSoLuong()};
            model.addRow(rows);
        }

        tblHoaDon.setModel(model);
    }

    void cal(List<DatMon> records) {
        double sum = 0;
        for (DatMon datMon : records) {
            double price = datMon.getDonGia();
            int quantity = datMon.getSoLuong();
            sum += price * quantity;
        }
        DecimalFormat df = new DecimalFormat("00.0");
        TongTien.setText(df.format(sum));
    }

    private HoaDon getDataForHoaDon() {
        HoaDon hoaDon = new HoaDon();
        int selectedRow = tblHoaDon.getSelectedRow();
        hoaDon.setMaSoBan(cboChonBan.getSelectedItem().toString());
        hoaDon.setTenKhachHang(txtTenKhachHang.getText());
        try {
            hoaDon.setSDTKH((txtSDTKH.getText()));
        } catch (NumberFormatException e) {
            MgsBox.alert(this, "Lỗi: Số điện thoại không hợp lệ.");
            return null; // Kết thúc phương thức nếu có lỗi
        }
        hoaDon.setNgayDatban(dateNgayLapHD.getDate());
        hoaDon.setMaNV(cboChonNV.getSelectedItem().toString());
        try {
            hoaDon.setTongTien(Double.parseDouble(TongTien.getText().replace(",", "")));
        } catch (NumberFormatException e) {
            MgsBox.alert(this, "Lỗi: Giá trị tổng tiền không hợp lệ.");
            return null;
        }
        hoaDon.setTienMat(Double.parseDouble(txtTienMat.getText()));
        hoaDon.setTienDu(Double.parseDouble(TienDu.getText()));
        hoaDon.setTrangThai(cboTrangThai.getSelectedItem().toString());
        hoaDon.setMaHoaDon(Integer.parseInt(cboSoHD.getSelectedItem().toString()));
        return hoaDon;
    }

    void updateHoaDon() {
        HoaDon model = getDataForHoaDon();
        if (model != null) {
            hoaDonDao.update(model);
            MgsBox.alert(this, "Thêm hóa đơn thành công");
        } else {
            MgsBox.alert(this, "Thêm hóa đơn thất bại!");
        }

    }

    void insertIntoHoaDon() {
        // Lấy đối tượng HoaDon từ bảng
        HoaDon entity = getDataForHoaDon();

        if (entity != null) {
            try {
                // Chèn đối tượng HoaDon vào cơ sở dữ liệu
                hoaDonDao.insert(entity);
                // Hoặc bạn có thể thông báo thành công hoặc làm bất kỳ xử lý nào khác ở đây
                System.out.println("Chèn thành công vào cơ sở dữ liệu!");
            } catch (Exception e) {
                // Xử lý ngoại lệ nếu có lỗi khi chèn vào cơ sở dữ liệu
                e.printStackTrace();
                // Hoặc bạn có thể thông báo lỗi hoặc làm bất kỳ xử lý nào khác ở đây
                System.err.println("Lỗi khi chèn vào cơ sở dữ liệu: " + e.getMessage());
            }
        } else {
            // Hoặc bạn có thể thông báo lỗi hoặc làm bất kỳ xử lý nào khác nếu dữ liệu không hợp lệ
            System.err.println("Dữ liệu không hợp lệ. Không thể chèn vào cơ sở dữ liệu!");
        }
    }

//    void isertIntoChiTietHoaDon() {
//        ChiTietHoaDon entity = getDataForHoaDon();
//     
//    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel4 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblHoaDon = new javax.swing.JTable();
        jLabel13 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        cboChonBan = new javax.swing.JComboBox<>();
        jPanel2 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        dateNgayLapHD = new com.toedter.calendar.JDateChooser();
        jLabel5 = new javax.swing.JLabel();
        cboChonNV = new javax.swing.JComboBox<>();
        btnChuyenKhoan = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        TongTien = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        txtTienMat = new javax.swing.JTextField();
        jPanel7 = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        TienDu = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        btnTinhTien = new javax.swing.JButton();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        cboTrangThai = new javax.swing.JComboBox<>();
        jLabel21 = new javax.swing.JLabel();
        cboSoHD = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        lblogo = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        txtTenKhachHang = new javax.swing.JTextField();
        jLabel26 = new javax.swing.JLabel();
        txtSDTKH = new javax.swing.JTextField();
        btnLapHD = new javax.swing.JButton();
        jPanel8 = new javax.swing.JPanel();
        jScrollPane6 = new javax.swing.JScrollPane();
        txtHoaDon = new javax.swing.JTextArea();
        jButton11 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel4.setBackground(new java.awt.Color(149, 35, 35));

        jPanel1.setBackground(new java.awt.Color(218, 212, 181));

        tblHoaDon.setFont(new java.awt.Font("Segoe UI Black", 1, 12)); // NOI18N
        tblHoaDon.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Mã hóa đơn", "Thực đơn", "Đơn giá", "Số lượng"
            }
        ));
        jScrollPane1.setViewportView(tblHoaDon);

        jLabel13.setText("Chọn bàn:");

        jLabel17.setBackground(new java.awt.Color(218, 212, 181));
        jLabel17.setFont(new java.awt.Font("Segoe UI Black", 1, 24)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(167, 49, 33));
        jLabel17.setText("Món đã chọn");

        cboChonBan.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Chọn bàn", "B101", "B102", "B103", "B104", "B105", "B106", "B107", "B108", "B109", "B110", "B201", "B202", "B203", "B204", "B205", "B206", "B207", "B208", "B209", "B210", " " }));
        cboChonBan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboChonBanActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(60, 60, 60)
                        .addComponent(cboChonBan, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 670, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(7, 7, 7)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cboChonBan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(8, 8, 8)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 410, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel2.setBackground(new java.awt.Color(218, 212, 181));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "THÔNG TIN HÓA ĐƠN", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI Black", 1, 14), new java.awt.Color(167, 49, 33))); // NOI18N
        jPanel2.setForeground(new java.awt.Color(218, 212, 181));

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(167, 49, 33));
        jLabel4.setText("Ngày lập hóa đơn");

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(167, 49, 33));
        jLabel5.setText("Thanh toán bằng chuyển khoản:");

        cboChonNV.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Chọn nhân viên", " " }));
        cboChonNV.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cboChonNVMouseClicked(evt);
            }
        });
        cboChonNV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboChonNVActionPerformed(evt);
            }
        });

        btnChuyenKhoan.setBackground(new java.awt.Color(149, 35, 35));
        btnChuyenKhoan.setFont(new java.awt.Font("Segoe UI Black", 1, 12)); // NOI18N
        btnChuyenKhoan.setForeground(new java.awt.Color(255, 255, 255));
        btnChuyenKhoan.setText("Chuyển khoản");
        btnChuyenKhoan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnChuyenKhoanActionPerformed(evt);
            }
        });

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));

        jLabel7.setText("VNĐ");

        jLabel6.setBackground(new java.awt.Color(0, 0, 0));
        jLabel6.setText("Tổng tiền:");

        TongTien.setBackground(new java.awt.Color(0, 0, 0));
        TongTien.setText("....");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(45, 45, 45)
                .addComponent(TongTien, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(jLabel6)
                    .addComponent(TongTien))
                .addContainerGap(8, Short.MAX_VALUE))
        );

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));

        jLabel11.setText("VNĐ");

        jLabel12.setBackground(new java.awt.Color(0, 0, 0));
        jLabel12.setText("Tiền mặt:");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(txtTienMat, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(jLabel12)
                    .addComponent(txtTienMat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));

        jLabel14.setText("VNĐ");

        jLabel15.setBackground(new java.awt.Color(0, 0, 0));
        jLabel15.setText("Tiền dư:");

        TienDu.setBackground(new java.awt.Color(0, 0, 0));
        TienDu.setText("....");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(45, 45, 45)
                .addComponent(TienDu, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(jLabel15)
                    .addComponent(TienDu))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(167, 49, 33));
        jLabel8.setText("Nhân viên");

        jLabel16.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(167, 49, 33));
        jLabel16.setText("Thanh toán bằng tiền mặt:");

        btnTinhTien.setBackground(new java.awt.Color(149, 35, 35));
        btnTinhTien.setFont(new java.awt.Font("Segoe UI Black", 1, 12)); // NOI18N
        btnTinhTien.setForeground(new java.awt.Color(255, 255, 255));
        btnTinhTien.setText("Tính tiền");
        btnTinhTien.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTinhTienActionPerformed(evt);
            }
        });

        jLabel19.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(167, 49, 33));
        jLabel19.setText("Mã hóa đơn:");

        jLabel20.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(167, 49, 33));
        jLabel20.setText("Trạng thái thanh toán:");

        cboTrangThai.setFont(new java.awt.Font("Segoe UI Black", 1, 12)); // NOI18N
        cboTrangThai.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Chọn trạng thái", "Đã thanh toán", "Chưa thanh toán" }));

        jLabel21.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(167, 49, 33));
        jLabel21.setText("Mã hóa đơn");

        cboSoHD.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Số hóa đơn", " ", " " }));
        cboSoHD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboSoHDActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(95, 95, 95)
                        .addComponent(dateNgayLapHD, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addComponent(jLabel8))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(95, 95, 95)
                        .addComponent(cboChonNV, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(5, 5, 5)
                        .addComponent(jLabel16))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(5, 5, 5)
                        .addComponent(btnTinhTien)
                        .addGap(5, 5, 5)
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(95, 95, 95)
                        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(95, 95, 95)
                        .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addComponent(jLabel5))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel20)
                                .addGap(18, 18, 18)
                                .addComponent(cboTrangThai, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(btnChuyenKhoan)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addGap(77, 77, 77)
                                        .addComponent(cboSoHD, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(297, 297, 297)
                                .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap(29, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel19)
                        .addGap(44, 44, 44))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel21)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cboSoHD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(24, 24, 24)))
                .addComponent(jLabel4)
                .addGap(4, 4, 4)
                .addComponent(dateNgayLapHD, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jLabel8)
                .addGap(4, 4, 4)
                .addComponent(cboChonNV, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addComponent(jLabel16)
                .addGap(4, 4, 4)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnTinhTien, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(16, 16, 16)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addComponent(jLabel5)
                .addGap(4, 4, 4)
                .addComponent(btnChuyenKhoan, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel20)
                    .addComponent(cboTrangThai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        jLabel1.setFont(new java.awt.Font("Segoe UI Black", 1, 70)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Hóa đơn");

        jLabel2.setFont(new java.awt.Font("Segoe UI Black", 1, 70)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Lập");

        lblogo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/edusys/icon/images/logoMini.png"))); // NOI18N

        jPanel3.setBackground(new java.awt.Color(218, 212, 181));

        jLabel9.setBackground(new java.awt.Color(218, 212, 181));
        jLabel9.setFont(new java.awt.Font("Segoe UI Black", 1, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(167, 49, 33));
        jLabel9.setText("THÔNG TIN KHÁCH HÀNG");

        jLabel18.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(167, 49, 33));
        jLabel18.setText("Tên khách hàng:");

        jLabel26.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel26.setForeground(new java.awt.Color(167, 49, 33));
        jLabel26.setText("Số điện thoại khách hàng:");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                            .addGap(96, 96, 96)
                            .addComponent(txtSDTKH, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel3Layout.createSequentialGroup()
                            .addGap(28, 28, 28)
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel26)
                                .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(96, 96, 96)
                        .addComponent(txtTenKhachHang, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(108, 108, 108)
                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(64, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 49, Short.MAX_VALUE)
                .addComponent(jLabel18)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtTenKhachHang, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(jLabel26)
                .addGap(4, 4, 4)
                .addComponent(txtSDTKH, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        btnLapHD.setBackground(new java.awt.Color(242, 232, 198));
        btnLapHD.setFont(new java.awt.Font("Segoe UI Black", 0, 12)); // NOI18N
        btnLapHD.setForeground(new java.awt.Color(167, 49, 33));
        btnLapHD.setText("LẬP HÓA ĐƠN");
        btnLapHD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLapHDActionPerformed(evt);
            }
        });

        jPanel8.setBackground(new java.awt.Color(204, 0, 51));
        jPanel8.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "HÓA ĐƠN", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 36), new java.awt.Color(255, 255, 255))); // NOI18N

        txtHoaDon.setColumns(20);
        txtHoaDon.setRows(5);
        jScrollPane6.setViewportView(txtHoaDon);

        jButton11.setBackground(new java.awt.Color(242, 232, 198));
        jButton11.setFont(new java.awt.Font("Segoe UI Black", 1, 14)); // NOI18N
        jButton11.setForeground(new java.awt.Color(167, 49, 33));
        jButton11.setText("In hóa đơn");
        jButton11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton11ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 359, Short.MAX_VALUE)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton11, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 609, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(jButton11, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(lblogo, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(32, 32, 32)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 326, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(193, 193, 193))
                    .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 410, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnLapHD, javax.swing.GroupLayout.PREFERRED_SIZE, 410, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(422, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addComponent(jLabel2)
                                .addGap(5, 5, 5)
                                .addComponent(jLabel1))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(lblogo, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel4Layout.createSequentialGroup()
                            .addGap(13, 13, 13)
                            .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(btnLapHD, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(jPanel8, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(102, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cboChonNVMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cboChonNVMouseClicked
        // TODO add your handling code here:
        loadDataToCboNhanVien();
    }//GEN-LAST:event_cboChonNVMouseClicked

    private void btnChuyenKhoanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnChuyenKhoanActionPerformed
        // TODO add your handling code here:

    }//GEN-LAST:event_btnChuyenKhoanActionPerformed

    private boolean validateTienMat() {
        if (txtTienMat.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập tiền mặt!");
            return false;
        }

        try {
            Double.parseDouble(txtTienMat.getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Tiền mặt phải là số!");
            return false;
        }

        return true;
    }

    private void btnTinhTienActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTinhTienActionPerformed
        // TODO add your handling code here:
        if (validateTienMat()) {
            double tongTien = Double.valueOf(TongTien.getText());
            double tienMat = Double.valueOf(txtTienMat.getText());
            double tienDu = tienMat - tongTien;
            TienDu.setText(String.valueOf(tienDu));
        }
    }//GEN-LAST:event_btnTinhTienActionPerformed

    private void btnLapHDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLapHDActionPerformed
        // TODO add your handling code here:

        if (validateForm()) {
            updateHoaDon();
            try {
                txtHoaDon.setText("QH_RESTAURANT\n");
                txtHoaDon.append("                    \t\tQuang Trung Street\n");
                txtHoaDon.append("                    \t\tGò Vấp, TP Hồ Chí Minh\n");
                txtHoaDon.append("                    \t\t+12435539\n\n");
                txtHoaDon.append(" -------------------------------------------------------------------------------------\n\n");
                txtHoaDon.append("Tên khách hàng:\n" + txtTenKhachHang.getText() + "\n");
                txtHoaDon.append("Số điện thoại:\n" + txtSDTKH.getText() + "\n");
                txtHoaDon.append("Tên Món \t\tGiá \tSố lượng\n");
                txtHoaDon.append(" -------------------------------------------------------------------------------------\n");

                DefaultTableModel model = (DefaultTableModel) tblHoaDon.getModel();

                int columnWidth = 30; // Đặt chiều rộng mong muốn cho cột "Tên Món"

                for (int i = 0; i < tblHoaDon.getRowCount(); i++) {
                    String tenMon = model.getValueAt(i, 1).toString();
                    String SoLuong = model.getValueAt(i, 2).toString();
                    String Gia = model.getValueAt(i, 3).toString();

                    // Điều chỉnh độ dài của tên món để đạt được chiều rộng mong muốn
                    if (tenMon.length() < columnWidth) {
                        int padding = columnWidth - tenMon.length();
                        String paddedTenMon = tenMon + " ".repeat(padding);
                        txtHoaDon.append(paddedTenMon + "\t" + SoLuong + "\t" + Gia + "\n");
                    } else {
                        // Nếu tên món đã đủ chiều rộng, không cần điều chỉnh
                        txtHoaDon.append(tenMon + "\t" + SoLuong + "\t" + Gia + "\n");
                    }
                }

                txtHoaDon.append(" -------------------------------------------------------------------------------------\n");
                txtHoaDon.append("Tổng tiền: " + TongTien.getText() + "\n");
                txtHoaDon.append("Tiền mặt: " + txtTienMat.getText() + "\n");
                txtHoaDon.append("Tiền dư: " + TienDu.getText() + "\n");
                txtHoaDon.append(" -------------------------------------------------------------------------------------\n");
                txtHoaDon.append("Cảm ơn vì đã sử dụng dịch vụ...!" + "\n");
                txtHoaDon.append(" -------------------------------------------------------------------------------------\n");

            } catch (Exception e) {
                // Xử lý ngoại lệ
            }
        }
    }//GEN-LAST:event_btnLapHDActionPerformed

    private void cboChonNVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboChonNVActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cboChonNVActionPerformed

    private void jButton11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton11ActionPerformed
        try {
            txtHoaDon.print();//IN HOA DON
        } catch (PrinterException ex) {
            Logger.getLogger(LapHoaDon.class.getName()).log(Level.SEVERE, null, ex);
        }

    }//GEN-LAST:event_jButton11ActionPerformed

    private void cboSoHDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboSoHDActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cboSoHDActionPerformed

    private void cboChonBanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboChonBanActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cboChonBanActionPerformed

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
            java.util.logging.Logger.getLogger(LapHoaDon.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(LapHoaDon.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(LapHoaDon.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(LapHoaDon.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new LapHoaDon().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel TienDu;
    private javax.swing.JLabel TongTien;
    private javax.swing.JButton btnChuyenKhoan;
    private javax.swing.JButton btnLapHD;
    private javax.swing.JButton btnTinhTien;
    private javax.swing.JComboBox<String> cboChonBan;
    private javax.swing.JComboBox<String> cboChonNV;
    private javax.swing.JComboBox<String> cboSoHD;
    private javax.swing.JComboBox<String> cboTrangThai;
    private com.toedter.calendar.JDateChooser dateNgayLapHD;
    private javax.swing.JButton jButton11;
    private javax.swing.JLabel jLabel1;
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
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JLabel lblogo;
    private javax.swing.JTable tblHoaDon;
    private javax.swing.JTextArea txtHoaDon;
    private javax.swing.JTextField txtSDTKH;
    private javax.swing.JTextField txtTenKhachHang;
    private javax.swing.JTextField txtTienMat;
    // End of variables declaration//GEN-END:variables
}
