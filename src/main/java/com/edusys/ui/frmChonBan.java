/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.edusys.ui;

import com.edusys.dao.DatMonDAO;
import com.edusys.utils.XImage;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Quan DB
 */
public class frmChonBan extends javax.swing.JFrame {

    /**
     * Creates new form frmChonBan
     */
   private Map<String, String> trangThaiBan = new HashMap<>();
    private String selectedTableCode; // Biến để lưu trữ mã bàn được chọn
    DatMonDAO datMonDAO = new DatMonDAO();
    private javax.swing.JButton[] btnBanArr;
    ThongTinban thongTinBanForm = new ThongTinban(this, rootPaneCheckingEnabled);

    public frmChonBan() {
        initComponents();
        setIconImage(XImage.getAppIcon());
        setLocationRelativeTo(null);
        setTitle("QH-RESTAURANT SYSTEM");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        initializeBanStatus();
        setLocationRelativeTo(null);
        btnBanArr = new javax.swing.JButton[]{btnBan101, btnBan102, btnBan103, btnBan108, btnBan105, btnBan106, btnBan107, btnBan108, btnBan201, btnBan202, btnBan203, btnBan204, btnBan205, btnBan206, btnBan207, btnBan208};

    }
      public String getSelectedTableCode() {
        return selectedTableCode;
    }

    private void handleButtonClick(String tableCode, javax.swing.JButton button) {
        selectedTableCode = tableCode; // Lưu trữ mã bàn được chọn
        thongTinBanForm.setSelectedTableCode(selectedTableCode);
        button.setText("Đã đặt");
        button.setBackground(Color.DARK_GRAY);
        button.setForeground(Color.WHITE);
    }

    private void initializeBanStatus() {

        btnBan101.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleButtonClick("B101", btnBan101);
            }
        });
        btnBan102.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleButtonClick("Đã đặt", btnBan102);
            }
        });
        btnBan103.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleButtonClick("Đã đặt", btnBan103);
            }
        });
        btnBan108.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleButtonClick("Đã đặt", btnBan105);
            }
        });
        btnBan106.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleButtonClick("Đã đặt", btnBan106);
            }
        });
        btnBan107.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleButtonClick("Đã đặt", btnBan107);
            }
        });
        btnBan108.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleButtonClick("Đã đặt", btnBan108);
            }
        });
        btnBan201.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleButtonClick("Đã đặt", btnBan201);
            }
        });
        btnBan202.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleButtonClick("Đã đặt", btnBan202);
            }
        });
        btnBan203.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleButtonClick("Đã đặt", btnBan203);
            }
        });
        btnBan204.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleButtonClick("Đã đặt", btnBan204);
            }
        });
        btnBan205.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleButtonClick("Đã đặt", btnBan205);
            }
        });
        btnBan206.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleButtonClick("Đã đặt", btnBan206);
            }
        });
        btnBan207.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleButtonClick("Đã đặt", btnBan207);
            }
        });
        btnBan208.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleButtonClick("Đã đặt", btnBan208);
            }
        });
    }

    private void updateTrangThaiBan(String maBan) {
        if (trangThaiBan.containsKey(maBan)) {
            trangThaiBan.put(maBan, "Đã đặt");
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

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel3 = new javax.swing.JPanel();
        btnBan102 = new javax.swing.JButton();
        btnBan103 = new javax.swing.JButton();
        btnBan104 = new javax.swing.JButton();
        btnBan105 = new javax.swing.JButton();
        btnBan106 = new javax.swing.JButton();
        btnBan107 = new javax.swing.JButton();
        btnBan108 = new javax.swing.JButton();
        btnBan101 = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        btnBan202 = new javax.swing.JButton();
        btnBan203 = new javax.swing.JButton();
        btnBan204 = new javax.swing.JButton();
        btnBan205 = new javax.swing.JButton();
        btnBan206 = new javax.swing.JButton();
        btnBan207 = new javax.swing.JButton();
        btnBan208 = new javax.swing.JButton();
        btnBan201 = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(0, 102, 102));

        jPanel2.setBackground(new java.awt.Color(242, 232, 198));

        jPanel3.setBackground(new java.awt.Color(149, 35, 35));

        btnBan102.setBackground(new java.awt.Color(218, 212, 181));
        btnBan102.setFont(new java.awt.Font("Segoe UI Black", 1, 24)); // NOI18N
        btnBan102.setForeground(new java.awt.Color(0, 102, 102));
        btnBan102.setText("T1 - 2");
        btnBan102.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBan102ActionPerformed(evt);
            }
        });

        btnBan103.setBackground(new java.awt.Color(218, 212, 181));
        btnBan103.setFont(new java.awt.Font("Segoe UI Black", 1, 24)); // NOI18N
        btnBan103.setForeground(new java.awt.Color(0, 102, 102));
        btnBan103.setText("T1 - 3");
        btnBan103.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBan103ActionPerformed(evt);
            }
        });

        btnBan104.setBackground(new java.awt.Color(218, 212, 181));
        btnBan104.setFont(new java.awt.Font("Segoe UI Black", 1, 24)); // NOI18N
        btnBan104.setForeground(new java.awt.Color(0, 102, 102));
        btnBan104.setText("T1 - 4");
        btnBan104.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBan104ActionPerformed(evt);
            }
        });

        btnBan105.setBackground(new java.awt.Color(218, 212, 181));
        btnBan105.setFont(new java.awt.Font("Segoe UI Black", 1, 24)); // NOI18N
        btnBan105.setForeground(new java.awt.Color(0, 102, 102));
        btnBan105.setText("T1 - 5");
        btnBan105.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBan105ActionPerformed(evt);
            }
        });

        btnBan106.setBackground(new java.awt.Color(218, 212, 181));
        btnBan106.setFont(new java.awt.Font("Segoe UI Black", 1, 24)); // NOI18N
        btnBan106.setForeground(new java.awt.Color(0, 102, 102));
        btnBan106.setText("T1 - 6");
        btnBan106.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBan106ActionPerformed(evt);
            }
        });

        btnBan107.setBackground(new java.awt.Color(218, 212, 181));
        btnBan107.setFont(new java.awt.Font("Segoe UI Black", 1, 24)); // NOI18N
        btnBan107.setForeground(new java.awt.Color(0, 102, 102));
        btnBan107.setText("T1 - 7");
        btnBan107.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBan107ActionPerformed(evt);
            }
        });

        btnBan108.setBackground(new java.awt.Color(218, 212, 181));
        btnBan108.setFont(new java.awt.Font("Segoe UI Black", 1, 24)); // NOI18N
        btnBan108.setForeground(new java.awt.Color(0, 102, 102));
        btnBan108.setText("T1 - 8");
        btnBan108.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBan108ActionPerformed(evt);
            }
        });

        btnBan101.setBackground(new java.awt.Color(218, 212, 181));
        btnBan101.setFont(new java.awt.Font("Segoe UI Black", 1, 24)); // NOI18N
        btnBan101.setForeground(new java.awt.Color(0, 102, 102));
        btnBan101.setText("T1 - 1");
        btnBan101.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBan101ActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Segoe UI Black", 1, 36)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Chọn bàn");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(89, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnBan101, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnBan107, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnBan108, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(59, 59, 59)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnBan104, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(btnBan106, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnBan105, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(btnBan102, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(56, 56, 56)
                        .addComponent(btnBan103, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(83, 83, 83))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnBan101, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnBan102, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnBan103, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(40, 40, 40)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnBan104, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnBan108, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(33, 33, 33)))
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnBan107, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnBan105, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnBan106, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(22, 22, 22))
        );

        jTabbedPane1.addTab("          Tầng 1          ", jPanel3);

        jPanel5.setBackground(new java.awt.Color(149, 35, 35));

        btnBan202.setBackground(new java.awt.Color(218, 212, 181));
        btnBan202.setFont(new java.awt.Font("Segoe UI Black", 1, 24)); // NOI18N
        btnBan202.setForeground(new java.awt.Color(0, 102, 102));
        btnBan202.setText("T2 - 2");
        btnBan202.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBan202ActionPerformed(evt);
            }
        });

        btnBan203.setBackground(new java.awt.Color(218, 212, 181));
        btnBan203.setFont(new java.awt.Font("Segoe UI Black", 1, 24)); // NOI18N
        btnBan203.setForeground(new java.awt.Color(0, 102, 102));
        btnBan203.setText("T2 - 3");
        btnBan203.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBan203ActionPerformed(evt);
            }
        });

        btnBan204.setBackground(new java.awt.Color(218, 212, 181));
        btnBan204.setFont(new java.awt.Font("Segoe UI Black", 1, 24)); // NOI18N
        btnBan204.setForeground(new java.awt.Color(0, 102, 102));
        btnBan204.setText("T2 - 4");
        btnBan204.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBan204ActionPerformed(evt);
            }
        });

        btnBan205.setBackground(new java.awt.Color(218, 212, 181));
        btnBan205.setFont(new java.awt.Font("Segoe UI Black", 1, 24)); // NOI18N
        btnBan205.setForeground(new java.awt.Color(0, 102, 102));
        btnBan205.setText("T2 - 5");
        btnBan205.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBan205ActionPerformed(evt);
            }
        });

        btnBan206.setBackground(new java.awt.Color(218, 212, 181));
        btnBan206.setFont(new java.awt.Font("Segoe UI Black", 1, 24)); // NOI18N
        btnBan206.setForeground(new java.awt.Color(0, 102, 102));
        btnBan206.setText("T2 - 6");
        btnBan206.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBan206ActionPerformed(evt);
            }
        });

        btnBan207.setBackground(new java.awt.Color(218, 212, 181));
        btnBan207.setFont(new java.awt.Font("Segoe UI Black", 1, 24)); // NOI18N
        btnBan207.setForeground(new java.awt.Color(0, 102, 102));
        btnBan207.setText("T2 - 7");
        btnBan207.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBan207ActionPerformed(evt);
            }
        });

        btnBan208.setBackground(new java.awt.Color(218, 212, 181));
        btnBan208.setFont(new java.awt.Font("Segoe UI Black", 1, 24)); // NOI18N
        btnBan208.setForeground(new java.awt.Color(0, 102, 102));
        btnBan208.setText("T2 - 8");
        btnBan208.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBan208ActionPerformed(evt);
            }
        });

        btnBan201.setBackground(new java.awt.Color(218, 212, 181));
        btnBan201.setFont(new java.awt.Font("Segoe UI Black", 1, 24)); // NOI18N
        btnBan201.setForeground(new java.awt.Color(0, 102, 102));
        btnBan201.setText("T2 - 1");
        btnBan201.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBan201ActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Segoe UI Black", 1, 36)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Chọn bàn");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap(89, Short.MAX_VALUE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnBan201, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnBan207, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnBan208, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(59, 59, 59)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnBan204, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(btnBan206, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnBan205, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(btnBan202, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(56, 56, 56)
                        .addComponent(btnBan203, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(83, 83, 83))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnBan201, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnBan202, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnBan203, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(40, 40, 40)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnBan204, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnBan208, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(33, 33, 33)))
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnBan207, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnBan205, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnBan206, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(22, 22, 22))
        );

        jTabbedPane1.addTab("          Tầng 2          ", jPanel5);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 453, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(149, 149, 149)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(247, 247, 247)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(59, 59, 59)
                        .addComponent(jLabel1))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(51, 51, 51)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(54, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 1177, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnBan101ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBan101ActionPerformed
        // TODO add your handling code here:
        new ThongTinban(this, rootPaneCheckingEnabled).setVisible(true);
        dispose();
    }//GEN-LAST:event_btnBan101ActionPerformed

    private void btnBan107ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBan107ActionPerformed
        // TODO add your handling code here:
        new ThongTinban(this, rootPaneCheckingEnabled).setVisible(true);
        dispose();
    }//GEN-LAST:event_btnBan107ActionPerformed

    private void btnBan102ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBan102ActionPerformed
        // TODO add your handling code here:
        new ThongTinban(this, rootPaneCheckingEnabled).setVisible(true);
        dispose();
    }//GEN-LAST:event_btnBan102ActionPerformed

    private void btnBan202ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBan202ActionPerformed
        // TODO add your handling code here:
        new ThongTinban(this, rootPaneCheckingEnabled).setVisible(true);
        dispose();
    }//GEN-LAST:event_btnBan202ActionPerformed

    private void btnBan207ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBan207ActionPerformed
        // TODO add your handling code here:
        new ThongTinban(this, rootPaneCheckingEnabled).setVisible(true);
        dispose();
    }//GEN-LAST:event_btnBan207ActionPerformed

    private void btnBan201ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBan201ActionPerformed
        // TODO add your handling code here:
        new ThongTinban(this, rootPaneCheckingEnabled).setVisible(true);
        dispose();
    }//GEN-LAST:event_btnBan201ActionPerformed

    private void btnBan103ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBan103ActionPerformed
        // TODO add your handling code here:new ThongTinban(this, rootPaneCheckingEnabled).setVisible(true);
        dispose();
    }//GEN-LAST:event_btnBan103ActionPerformed

    private void btnBan108ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBan108ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnBan108ActionPerformed

    private void btnBan203ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBan203ActionPerformed
        // TODO add your handling code here:
        new ThongTinban(this, rootPaneCheckingEnabled).setVisible(true);
        dispose();
    }//GEN-LAST:event_btnBan203ActionPerformed

    private void btnBan104ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBan104ActionPerformed
        // TODO add your handling code here:
        new ThongTinban(this, rootPaneCheckingEnabled).setVisible(true);
        dispose();
    }//GEN-LAST:event_btnBan104ActionPerformed

    private void btnBan105ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBan105ActionPerformed
        // TODO add your handling code here:
        new ThongTinban(this, rootPaneCheckingEnabled).setVisible(true);
        dispose();
    }//GEN-LAST:event_btnBan105ActionPerformed

    private void btnBan106ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBan106ActionPerformed
        // TODO add your handling code here:
        new ThongTinban(this, rootPaneCheckingEnabled).setVisible(true);
        dispose();
    }//GEN-LAST:event_btnBan106ActionPerformed

    private void btnBan204ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBan204ActionPerformed
        // TODO add your handling code here:
        new ThongTinban(this, rootPaneCheckingEnabled).setVisible(true);
        dispose();
    }//GEN-LAST:event_btnBan204ActionPerformed

    private void btnBan205ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBan205ActionPerformed
        // TODO add your handling code here:
        new ThongTinban(this, rootPaneCheckingEnabled).setVisible(true);
        dispose();
    }//GEN-LAST:event_btnBan205ActionPerformed

    private void btnBan206ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBan206ActionPerformed
        // TODO add your handling code here:
        new ThongTinban(this, rootPaneCheckingEnabled).setVisible(true);
        dispose();
    }//GEN-LAST:event_btnBan206ActionPerformed

    private void btnBan208ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBan208ActionPerformed
        // TODO add your handling code here:
        new ThongTinban(this, rootPaneCheckingEnabled).setVisible(true);
        dispose();
    }//GEN-LAST:event_btnBan208ActionPerformed

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
            java.util.logging.Logger.getLogger(frmChonBan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(frmChonBan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(frmChonBan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(frmChonBan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new frmChonBan().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBan101;
    private javax.swing.JButton btnBan102;
    private javax.swing.JButton btnBan103;
    private javax.swing.JButton btnBan104;
    private javax.swing.JButton btnBan105;
    private javax.swing.JButton btnBan106;
    private javax.swing.JButton btnBan107;
    private javax.swing.JButton btnBan108;
    private javax.swing.JButton btnBan201;
    private javax.swing.JButton btnBan202;
    private javax.swing.JButton btnBan203;
    private javax.swing.JButton btnBan204;
    private javax.swing.JButton btnBan205;
    private javax.swing.JButton btnBan206;
    private javax.swing.JButton btnBan207;
    private javax.swing.JButton btnBan208;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JTabbedPane jTabbedPane1;
    // End of variables declaration//GEN-END:variables
}
