/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.edusys.ui;

import com.edusys.utils.XImage;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.Timer;
import com.edusys.utils.Auth;
import com.edusys.utils.MgsBox;
import java.awt.Desktop;
import java.net.URI;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.edusys.ui.ThongKe;

/**
 *
 * @author MS QUOC
 */
public class QH_RESTAURANT extends javax.swing.JFrame {

    /**
     * Creates new form QH_RESTAURANT
     */
    public QH_RESTAURANT() {
        initComponents();
        init();
        this.updateNhanVienLabel();
    }

    void openWelcome() {
        new ChaoJDialog(this, true).setVisible(true);
    }

    void openTaiKhoan() {
        new TaiKhoanJDialog(this, rootPaneCheckingEnabled).setVisible(true);
    }

    private void updateNhanVienLabel() {
        if (Auth.isAuthenticated()) {
            String userID = Auth.user.getMaNV();
            boolean role = Auth.user.isVaiTro();
            lblTenNV.setText(Auth.user.getTenNV());
            lblCaLam.setText(String.valueOf(Auth.user.getCaLam()));
            lblVaiTro.setText(Auth.user.isVaiTro() ? "Quản lý" : "Nhân viên");
            lblNhanVien.setText(userID + "(" + role + ")");
            // Tạo đối tượng SimpleDateFormat để định dạng ngày
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

// Lấy ngày hiện tại
            Date currentDate = new Date();

// Định dạng ngày hiện tại theo định dạng bạn mong muốn
            String formattedDate = dateFormat.format(currentDate);

// Cập nhật nội dung của JLabel với ngày hiện tại
            lblNgayLamViec.setText("Ngày làm: " + formattedDate);
        }
    }

    void openLogin() {
        new DangNhapJDialog(this, true).setVisible(true);
        dispose();
    }

    void logOut() {
        Auth.clear();
        this.dispose();
        new DangNhapJDialog(this, rootPaneCheckingEnabled).setVisible(true);
    }

    void exit() {
        if (MgsBox.confirm(this, "Bạn có muốn thoát không ?")) {
            System.exit(0);
        }
    }

    void openQuanLyNV() {
        if (Auth.isAuthenticated()) {
            if (!Auth.isManager()) {
                MgsBox.alert(this, "Bạn không có quyền xem quản lý hóa đơn!");
            } else {
                QuanLyNhanVienJDialog qlnv = new QuanLyNhanVienJDialog(this, rootPaneCheckingEnabled);
                qlnv.setVisible(true);
            }
        }
    }

    void openHuongDan() {
        try {
            Desktop.getDesktop().browse(new URI("http://www.kansastag.gov/advhtml_doc_upload/caplio_500se_software_user_guide.pdf"));
        } catch (Exception e) {
            Logger.getLogger(QH_RESTAURANT.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    void openThongKe() {
        if (Auth.isAuthenticated()) {
            if (!Auth.isManager()) {
                MgsBox.alert(this, "Bạn không có quyền xem thống kê!");
            } else {
                ThongKe thongKe = new ThongKe();
                thongKe.setVisible(true);
            }
        }
    }

    void openBanAn() {
        BanAn banAn = new BanAn(this, rootPaneCheckingEnabled);
        banAn.setVisible(true);
    }

    void openQuanLyHoaDon() {
        if (Auth.isAuthenticated()) {
            if (!Auth.isManager()) {
                MgsBox.alert(this, "Bạn không có quyền xem quản lý hóa đơn!");
            } else {
                QuanLyHoaDonJDialog qlhd = new QuanLyHoaDonJDialog(this, rootPaneCheckingEnabled);
                qlhd.setVisible(true);
            }
        }
    }

 
 void openQuanLyThucDon() {
        if (Auth.isAuthenticated()) {
            if (!Auth.isManager()) {
                MgsBox.alert(this, "Bạn không có quyền xem doanh thu!");
            } else {
                QuanLyThucDonJDialog qltd = new QuanLyThucDonJDialog(this, rootPaneCheckingEnabled);
                qltd.setVisible(true);
            }
        }
    }
  void openBep() {
        if (Auth.isAuthenticated()) {
            if (!Auth.isManager()) {
                MgsBox.alert(this, "Bạn không có quyền xem bếp!");
            } else {
                Bep1 bep = new Bep1();
                bep.setVisible(true);
            }
        }
    }
  
   void openQuanLyPhanCong(){
        if (Auth.isAuthenticated()) {
            if ( !Auth.isManager()) {
                MgsBox.alert(this, "Bạn không có quyền xem doanh thu!");
            } else {
                QuanLyHoaDonJDialog qlhd = new QuanLyHoaDonJDialog(this, rootPaneCheckingEnabled);
                qlhd.setVisible(true);
            }
        }
    }
 void openOrder() {
        new OrderJFrame().setVisible(true);
    }

    void openLapHoaDon() {
        new LapHoaDon().setVisible(true);
    }

    void openDangKy() {
        new DangKyJDialog(this, rootPaneCheckingEnabled).setVisible(true);
    }

    void openMenu() {
        new Menu().setVisible(true);
    }
 void openQMK() {
        new QuenMatKhauJDialog(this, rootPaneCheckingEnabled).setVisible(true);
    }
    void openQLHD() {
        new QuanLyHoaDonJDialog(this, rootPaneCheckingEnabled).setVisible(true);
    }

     void openLHD() {
        new LapHoaDon();
    }
//    void openGioiThieu(){
//        new GioiThieuJDialog(this, rootPaneCheckingEnabled).setVisible(true);
//    }
    void init() {
        setIconImage(XImage.getAppIcon());
        setLocationRelativeTo(null);
        setTitle("QH-RESTAURANT SYSTEM");
        new Timer(1000, new ActionListener() {
            SimpleDateFormat format = new SimpleDateFormat("hh:mm:ss a");

            @Override
            public void actionPerformed(ActionEvent e) {
                lblDongHo.setText(format.format(new Date()));
            }
        }).start();
        this.openWelcome();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jMenu1 = new javax.swing.JMenu();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        btnOrder = new javax.swing.JButton();
        btnMenu = new javax.swing.JButton();
        btnTaiKhoan = new javax.swing.JButton();
        btnBanAn = new javax.swing.JButton();
        btnThanhToan = new javax.swing.JButton();
        btnHoaDon = new javax.swing.JButton();
        lblNhanVien = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        btnQLNV = new javax.swing.JButton();
        btnDX = new javax.swing.JButton();
        btnThongKe = new javax.swing.JButton();
        btnQuanLyHoaDon = new javax.swing.JButton();
        btnBep = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        lblDongHo = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        lblTenNV = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        lblNgayLamViec = new javax.swing.JLabel();
        lblCaLam = new javax.swing.JLabel();
        lblSoHD = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        lblVaiTro = new javax.swing.JLabel();
        lblHinhAnh = new javax.swing.JLabel();
        btnBep1 = new javax.swing.JButton();

        jMenu1.setText("jMenu1");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(51, 51, 255));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel3.setBackground(new java.awt.Color(149, 35, 35));

        jLabel6.setFont(new java.awt.Font("Segoe UI Black", 1, 48)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("FAST - FRESH - FRIENDLY");

        btnOrder.setBackground(new java.awt.Color(218, 212, 181));
        btnOrder.setFont(new java.awt.Font("Segoe UI Black", 1, 36)); // NOI18N
        btnOrder.setForeground(new java.awt.Color(167, 49, 33));
        btnOrder.setText("ORDER");
        btnOrder.setBorder(null);
        btnOrder.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOrderActionPerformed(evt);
            }
        });

        btnMenu.setBackground(new java.awt.Color(218, 212, 181));
        btnMenu.setFont(new java.awt.Font("Segoe UI Black", 1, 36)); // NOI18N
        btnMenu.setForeground(new java.awt.Color(167, 49, 33));
        btnMenu.setText("MENU");
        btnMenu.setBorder(null);
        btnMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMenuActionPerformed(evt);
            }
        });

        btnTaiKhoan.setBackground(new java.awt.Color(218, 212, 181));
        btnTaiKhoan.setFont(new java.awt.Font("Segoe UI Black", 1, 36)); // NOI18N
        btnTaiKhoan.setForeground(new java.awt.Color(167, 49, 33));
        btnTaiKhoan.setText("TÀI KHOẢN");
        btnTaiKhoan.setBorder(null);
        btnTaiKhoan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTaiKhoanActionPerformed(evt);
            }
        });

        btnBanAn.setBackground(new java.awt.Color(218, 212, 181));
        btnBanAn.setFont(new java.awt.Font("Segoe UI Black", 1, 36)); // NOI18N
        btnBanAn.setForeground(new java.awt.Color(167, 49, 33));
        btnBanAn.setText("BÀN ĂN");
        btnBanAn.setBorder(null);
        btnBanAn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBanAnActionPerformed(evt);
            }
        });

        btnThanhToan.setBackground(new java.awt.Color(218, 212, 181));
        btnThanhToan.setFont(new java.awt.Font("Segoe UI Black", 1, 36)); // NOI18N
        btnThanhToan.setForeground(new java.awt.Color(167, 49, 33));
        btnThanhToan.setText("THANH TOÁN");
        btnThanhToan.setBorder(null);
        btnThanhToan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThanhToanActionPerformed(evt);
            }
        });

        btnHoaDon.setBackground(new java.awt.Color(218, 212, 181));
        btnHoaDon.setFont(new java.awt.Font("Segoe UI Black", 1, 36)); // NOI18N
        btnHoaDon.setForeground(new java.awt.Color(167, 49, 33));
        btnHoaDon.setText("LẬP HÓA ĐƠN");
        btnHoaDon.setBorder(null);
        btnHoaDon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHoaDonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 647, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(108, 108, 108))
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnHoaDon, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnOrder, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnMenu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnThanhToan, javax.swing.GroupLayout.DEFAULT_SIZE, 270, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnTaiKhoan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnBanAn, javax.swing.GroupLayout.DEFAULT_SIZE, 270, Short.MAX_VALUE))
                .addContainerGap(28, Short.MAX_VALUE))
        );

        jPanel3Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {btnBanAn, btnHoaDon, btnMenu, btnOrder, btnTaiKhoan, btnThanhToan});

        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(43, 43, 43)
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 187, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(btnOrder, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnTaiKhoan, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnMenu, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(btnBanAn, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnHoaDon, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnThanhToan, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(75, 75, 75))
        );

        jPanel3Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {btnBanAn, btnHoaDon, btnMenu, btnOrder, btnTaiKhoan, btnThanhToan});

        getContentPane().add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 0, 870, 630));

        lblNhanVien.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblNhanVien.setForeground(new java.awt.Color(255, 255, 255));
        lblNhanVien.setText("...");
        getContentPane().add(lblNhanVien, new org.netbeans.lib.awtextra.AbsoluteConstraints(900, 30, 90, -1));

        jPanel1.setBackground(new java.awt.Color(0, 102, 102));

        btnQLNV.setBackground(new java.awt.Color(218, 212, 181));
        btnQLNV.setFont(new java.awt.Font("Segoe UI Black", 1, 12)); // NOI18N
        btnQLNV.setForeground(new java.awt.Color(167, 49, 33));
        btnQLNV.setText("QUẢN LÝ NHÂN VIÊN");
        btnQLNV.setBorder(null);
        btnQLNV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnQLNVActionPerformed(evt);
            }
        });

        btnDX.setBackground(new java.awt.Color(0, 102, 102));
        btnDX.setFont(new java.awt.Font("Segoe UI Black", 1, 12)); // NOI18N
        btnDX.setForeground(new java.awt.Color(255, 255, 255));
        btnDX.setText("ĐĂNG XUẤT");
        btnDX.setBorder(null);
        btnDX.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDXActionPerformed(evt);
            }
        });

        btnThongKe.setBackground(new java.awt.Color(218, 212, 181));
        btnThongKe.setFont(new java.awt.Font("Segoe UI Black", 1, 12)); // NOI18N
        btnThongKe.setForeground(new java.awt.Color(167, 49, 33));
        btnThongKe.setText("THỐNG KÊ");
        btnThongKe.setBorder(null);
        btnThongKe.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThongKeActionPerformed(evt);
            }
        });

        btnQuanLyHoaDon.setBackground(new java.awt.Color(218, 212, 181));
        btnQuanLyHoaDon.setFont(new java.awt.Font("Segoe UI Black", 1, 12)); // NOI18N
        btnQuanLyHoaDon.setForeground(new java.awt.Color(167, 49, 33));
        btnQuanLyHoaDon.setText("QUẢN LÝ HÓA ĐƠN");
        btnQuanLyHoaDon.setBorder(null);
        btnQuanLyHoaDon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnQuanLyHoaDonActionPerformed(evt);
            }
        });

        btnBep.setBackground(new java.awt.Color(218, 212, 181));
        btnBep.setFont(new java.awt.Font("Segoe UI Black", 1, 12)); // NOI18N
        btnBep.setForeground(new java.awt.Color(167, 49, 33));
        btnBep.setText("BẾP");
        btnBep.setBorder(null);
        btnBep.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBepActionPerformed(evt);
            }
        });

        jPanel4.setBackground(new java.awt.Color(218, 212, 181));

        lblDongHo.setBackground(new java.awt.Color(0, 0, 0));
        lblDongHo.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblDongHo.setText("12:12:12:AM");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel2.setText("Ca làm việc:");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel3.setText("Số hóa đơn:");

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel5.setText("Ngày làm việc:");

        lblTenNV.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblTenNV.setText("...");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel4.setText("Nhân viên:");

        lblNgayLamViec.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblNgayLamViec.setText("...");

        lblCaLam.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblCaLam.setText("...");

        lblSoHD.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblSoHD.setText("5");

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel8.setText("Vai trò:");

        lblVaiTro.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblVaiTro.setText("...");

        lblHinhAnh.setFont(new java.awt.Font("Segoe UI Black", 1, 12)); // NOI18N
        lblHinhAnh.setForeground(new java.awt.Color(255, 255, 255));
        lblHinhAnh.setIcon(new javax.swing.ImageIcon("C:\\Users\\MS QUOC\\Downloads\\icons8-employee-96.png")); // NOI18N
        lblHinhAnh.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        lblHinhAnh.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblHinhAnhMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 94, Short.MAX_VALUE)
                        .addComponent(lblHinhAnh, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(lblDongHo))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addGap(39, 39, 39)
                                .addComponent(lblTenNV))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel5)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel8))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblVaiTro)
                                    .addComponent(lblSoHD)
                                    .addComponent(lblCaLam)
                                    .addComponent(lblNgayLamViec, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(lblTenNV))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(lblNgayLamViec))
                .addGap(12, 12, 12)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(lblCaLam))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(lblSoHD))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(lblVaiTro))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblDongHo, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblHinhAnh, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        btnBep1.setBackground(new java.awt.Color(218, 212, 181));
        btnBep1.setFont(new java.awt.Font("Segoe UI Black", 1, 12)); // NOI18N
        btnBep1.setForeground(new java.awt.Color(167, 49, 33));
        btnBep1.setText("QUÊN MẬT KHẨU");
        btnBep1.setBorder(null);
        btnBep1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBep1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnDX, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jPanel4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnBep1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnBep, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnThongKe, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnQLNV, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnQuanLyHoaDon, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap())))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28)
                .addComponent(btnQuanLyHoaDon, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnQLNV, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnThongKe, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnBep, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(8, 8, 8)
                .addComponent(btnBep1, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnDX, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 320, 630));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMenuActionPerformed
        // TODO add your handling code here:
        openMenu();
    }//GEN-LAST:event_btnMenuActionPerformed

    private void btnHoaDonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHoaDonActionPerformed
        // TODO add your handling code here:
        openLapHoaDon();
    }//GEN-LAST:event_btnHoaDonActionPerformed

    private void btnBepActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBepActionPerformed
        // TODO add your handling code here:
        openBep();
    }//GEN-LAST:event_btnBepActionPerformed

    private void btnDXActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDXActionPerformed
        // TODO add your handling code here:
        System.exit(0);
    }//GEN-LAST:event_btnDXActionPerformed

    private void btnQLNVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnQLNVActionPerformed
        // TODO add your handling code here:
        openQuanLyNV();
    }//GEN-LAST:event_btnQLNVActionPerformed

    private void btnThongKeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThongKeActionPerformed
        // TODO add your handling code here:
        openThongKe();
    }//GEN-LAST:event_btnThongKeActionPerformed

    private void btnTaiKhoanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTaiKhoanActionPerformed
        // TODO add your handling code here:
        openTaiKhoan();
    }//GEN-LAST:event_btnTaiKhoanActionPerformed

    private void btnBep1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBep1ActionPerformed
        // TODO add your handling code here:
        openQMK();
    }//GEN-LAST:event_btnBep1ActionPerformed

    private void btnOrderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOrderActionPerformed
        // TODO add your handling code here:
        new OrderJFrame().setVisible(true);
    }//GEN-LAST:event_btnOrderActionPerformed

    private void btnThanhToanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThanhToanActionPerformed
        // TODO add your handling code here:
        new LapHoaDon().setVisible(true);
    }//GEN-LAST:event_btnThanhToanActionPerformed

    private void btnBanAnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBanAnActionPerformed
        // TODO add your handling code here:
        new frmChonBan().setVisible(true);
    }//GEN-LAST:event_btnBanAnActionPerformed

    private void btnQuanLyHoaDonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnQuanLyHoaDonActionPerformed
        // TODO add your handling code here:
        openQuanLyHoaDon();
    }//GEN-LAST:event_btnQuanLyHoaDonActionPerformed

    private void lblHinhAnhMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblHinhAnhMouseClicked
     
    }//GEN-LAST:event_lblHinhAnhMouseClicked

    /**sấ
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
            java.util.logging.Logger.getLogger(QH_RESTAURANT.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(QH_RESTAURANT.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(QH_RESTAURANT.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(QH_RESTAURANT.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new QH_RESTAURANT().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBanAn;
    private javax.swing.JButton btnBep;
    private javax.swing.JButton btnBep1;
    private javax.swing.JButton btnDX;
    private javax.swing.JButton btnHoaDon;
    private javax.swing.JButton btnMenu;
    private javax.swing.JButton btnOrder;
    private javax.swing.JButton btnQLNV;
    private javax.swing.JButton btnQuanLyHoaDon;
    private javax.swing.JButton btnTaiKhoan;
    private javax.swing.JButton btnThanhToan;
    private javax.swing.JButton btnThongKe;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JLabel lblCaLam;
    private javax.swing.JLabel lblDongHo;
    private javax.swing.JLabel lblHinhAnh;
    private javax.swing.JLabel lblNgayLamViec;
    private javax.swing.JLabel lblNhanVien;
    private javax.swing.JLabel lblSoHD;
    private javax.swing.JLabel lblTenNV;
    private javax.swing.JLabel lblVaiTro;
    // End of variables declaration//GEN-END:variables
}
