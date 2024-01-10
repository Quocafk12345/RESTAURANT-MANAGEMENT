/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package com.edusys.ui;

import com.edusys.utils.MgsBox;
import java.sql.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Properties;
import java.util.Random;
import javax.swing.JOptionPane;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author MS QUOC
 */
public class QuenMatKhauJDialog extends javax.swing.JDialog {

    /**
     * Creates new form QuenMatKhauJDialog
     */
    private String maOTP;
    private String email;

    public QuenMatKhauJDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
    }

   

    private void capNhatMatKhau() {
        String matKhauMoi = JOptionPane.showInputDialog(this, "Nhập mật khẩu mới:");
        String url = "jdbc:sqlserver://DESKTOP-ISEQ9P9\\MSSQLSERVER01;database=RestaurantManagement1;user=sa;password=songlong";
        String query = "UPDATE NhanVien SET MatKhau = ? WHERE Email = ?";

        try (Connection conn = DriverManager.getConnection(url); PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, matKhauMoi);
            pstmt.setString(2, email);
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private boolean kiemTraEmail(String email) {
        // Thực hiện kiểm tra định dạng email (có thể sử dụng biểu thức chính quy)
        // Trả về true nếu đúng, ngược lại trả về false
        return true;
    }

    private String taoMaOTP() {
        // Tạo mã OTP ngẫu nhiên, ví dụ: 6 chữ số
        Random random = new Random();
        int otp = 100000 + random.nextInt(900000);
        return String.valueOf(otp);
    }

    private boolean kiemTraEmailTonTai(String email) {
        String url = "jdbc:sqlserver://DESKTOP-ISEQ9P9\\MSSQLSERVER01;database=RestaurantManagement1;user=sa;password=songlong";
        String query = "SELECT COUNT(*) FROM NhanVien WHERE Email = ?";

        try (Connection conn = DriverManager.getConnection(url); PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, email);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                int count = rs.getInt(1);
                return count > 0;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    private void guiMaOTP(String email, String maOTP) {
        // Thông tin tài khoản email (thay thế bằng thông tin của bạn)
        final String username = "quocntps31889@fpt.edu.vn";
        final String password = "gxllnzjydgnuvloh";

        // Cấu hình thông tin máy chủ email
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        // Tạo đối tượng Session
        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            // Tạo đối tượng MimeMessage
            Message message = new MimeMessage(session);
            // Thiết lập người gửi
            message.setFrom(new InternetAddress(username));
            // Thiết lập người nhận
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
            // Thiết lập tiêu đề
            message.setSubject("Xác nhận đặt lại mật khẩu");
            // Thiết lập nội dung email
            message.setText("Mã OTP của bạn là: " + maOTP);

            // Gửi email
            Transport.send(message);

            // Hiển thị thông báo
            JOptionPane.showMessageDialog(this, "Mã OTP đã được gửi đến email của bạn.");

            // Lưu mã OTP vào cơ sở dữ liệu
            luuMaOTPVaoCSDL(email, maOTP);

        } catch (MessagingException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Có lỗi xảy ra khi gửi email. Vui lòng thử lại.");
        }
    }

    private void luuMaOTPVaoCSDL(String email, String maOTP) {
        String url = "jdbc:sqlserver://DESKTOP-ISEQ9P9\\MSSQLSERVER01;database=RestaurantManagement1;user=sa;password=songlong";
        String query = "UPDATE NhanVien SET MaOTP = ? WHERE Email = ?";

        try (Connection conn = DriverManager.getConnection(url); PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, maOTP);
            pstmt.setString(2, email);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void guiMaOTPNeuTonTai(String email) {

        if (kiemTraEmailTonTai(email)) {
            String maOTP = taoMaOTP();
            guiMaOTP(email, maOTP);
            // Lưu mã OTP vào cơ sở dữ liệu hoặc bộ nhớ tạm thời để kiểm tra sau này
        } else {
            // Hiển thị thông báo "Không có tài khoản với email này"
            System.out.println("Không có tài khoản với email " + email);
        }
    }
// Sửa lại phương thức xuLyXacNhan

    private void xuLyGuiOTP() {
        String email = txtEmail1.getText();
        if (kiemTraEmail(email)) {
            if (maOTP == null) { // Kiểm tra xem đã có mã OTP chưa
                maOTP = taoMaOTP();
                guiMaOTPNeuTonTai(email);
                JOptionPane.showMessageDialog(this, "Mã OTP đã được gửi đến email của bạn.");
            } else {
                JOptionPane.showMessageDialog(this, "Mã OTP đã được gửi. Vui lòng kiểm tra email của bạn.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Email không hợp lệ. Vui lòng kiểm tra lại.");
        }
    }

    private void xuLyXacNhanOTP() {
        String nhapOTP = txtMXN1.getText();
        if (kiemTraOTP()) {
            JOptionPane.showMessageDialog(this, "Xác nhận thành công. Bạn có thể đặt lại mật khẩu mới.");
            String matKhauMoi = taoMatKhauNgauNhien(); // Tạo mật khẩu mới ngẫu nhiên
            capNhatMatKhau();
            guiMatKhauMoiVaCapNhat(email, matKhauMoi); // Gửi mật khẩu mới và cập nhật vào database
            nhapOTP = null; // Gán giá trị null sau khi xác nhận thành công
            dispose();
        }else{
            JOptionPane.showMessageDialog(this, "Mã OTP không đúng!");
        }
    }

    private boolean kiemTraOTP() {
        String email = txtEmail1.getText();
        String url = "jdbc:sqlserver://DESKTOP-ISEQ9P9\\MSSQLSERVER01;database=RestaurantManagement1;user=sa;password=songlong";
        String query = "SELECT MaOTP FROM NhanVien WHERE Email = ?";
        String nhapOTP = txtMXN1.getText();

        try (Connection conn = DriverManager.getConnection(url); PreparedStatement pstmt = conn.prepareStatement(query)) {            
            pstmt.setString(1, email);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                String otpFromDB = rs.getString("MaOTP");
                if (nhapOTP.equals(otpFromDB)) {
                    // Nếu mã OTP khớp, gửi mật khẩu mới
                    guiMatKhauMoi(email, taoMatKhauNgauNhien());
                    return true;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
// Thêm phương thức taoMatKhauNgauNhien

    private String taoMatKhauNgauNhien() {
        // Tạo mật khẩu ngẫu nhiên, ví dụ: 8 ký tự
        String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder password = new StringBuilder();
        Random random = new Random();

        for (int i = 0; i < 8; i++) {
            int index = random.nextInt(CHARACTERS.length());
            password.append(CHARACTERS.charAt(index));
        }

        return password.toString();
    }

// Thêm phương thức guiMatKhauMoiVaCapNhat
    private void guiMatKhauMoiVaCapNhat(String email, String matKhauMoi) {
        // Gửi mật khẩu mới đến email (có thể sử dụng thư viện hoặc API gửi email)
        guiMatKhauMoi(email, matKhauMoi);

        // Cập nhật mật khẩu mới vào database
        capNhatMatKhau( email,matKhauMoi);
    }

// Thêm phương thức guiMatKhauMoi
    private void guiMatKhauMoi(String email, String matKhauMoi) {
        // Cài đặt thông tin máy chủ email (thay thế bằng thông tin của bạn)
        final String username = "quocntps31889@fpt.edu.vn";
        final String password = "gxllnzjydgnuvloh";

        // Cấu hình thông tin máy chủ email
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
                props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        // Tạo đối tượng Session
        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            // Tạo đối tượng MimeMessage
            Message message = new MimeMessage(session);
            // Thiết lập người gửi
            message.setFrom(new InternetAddress(username));
            // Thiết lập người nhận
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
            // Thiết lập tiêu đề
            message.setSubject("Mật khẩu mới");
            // Thiết lập nội dung email
            message.setText("Mật khẩu mới của bạn là: " + matKhauMoi);

            // Gửi email
            Transport.send(message);

            // Hiển thị thông báo
            JOptionPane.showMessageDialog(this, "Mật khẩu mới đã được gửi đến email của bạn.");
            luuMatKhauVaoCSDL(email, matKhauMoi);
        } catch (MessagingException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Có lỗi xảy ra khi gửi email. Vui lòng thử lại.");
        }
    }
private void capNhatMatKhau(String email, String matKhauMoi) {
    String url = "jdbc:sqlserver://DESKTOP-ISEQ9P9\\MSSQLSERVER01;database=RestaurantManagement1;user=sa;password=songlong";
    String query = "UPDATE NhanVien SET MatKhau = ?, MaOTP = NULL WHERE Email = ?";

    try (Connection conn = DriverManager.getConnection(url); PreparedStatement pstmt = conn.prepareStatement(query)) {
        pstmt.setString(1, matKhauMoi);
        pstmt.setString(2, email);
        int rowsAffected = pstmt.executeUpdate();

        if (rowsAffected > 0) {
            System.out.println("Mật khẩu đã được cập nhật thành công.");
        } else {
            System.out.println("Cập nhật mật khẩu không thành công. Không có bản ghi nào được cập nhật.");
        }
    } catch (SQLException ex) {
        ex.printStackTrace();
        System.out.println("SQL Error: " + ex.getMessage());
    }
}
private void luuMatKhauVaoCSDL(String email, String matkhaumoi) {
        String url = "jdbc:sqlserver://DESKTOP-ISEQ9P9\\MSSQLSERVER01;database=RestaurantManagement1;user=sa;password=songlong";
        String query = "UPDATE NhanVien SET MaOTP = ? WHERE Email = ?";

        try (Connection conn = DriverManager.getConnection(url); PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, matkhaumoi);
            pstmt.setString(2, email);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
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
        jLabel87 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        txtEmail1 = new javax.swing.JTextField();
        jLabel90 = new javax.swing.JLabel();
        btnXN1 = new javax.swing.JButton();
        btnSend1 = new javax.swing.JButton();
        btnThoat2 = new javax.swing.JButton();
        txtMXN1 = new javax.swing.JTextField();
        jLabel91 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel3.setBackground(new java.awt.Color(149, 35, 35));
        jPanel3.setForeground(new java.awt.Color(149, 35, 35));

        jLabel87.setBackground(new java.awt.Color(255, 255, 255));
        jLabel87.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        jLabel87.setForeground(new java.awt.Color(255, 255, 255));
        jLabel87.setText("Quên mật khẩu");

        jLabel1.setIcon(new javax.swing.ImageIcon("C:\\Users\\MS QUOC\\Documents\\NetBeansProjects\\mavenproject1\\src\\main\\java\\com\\edusys\\icon\\images\\logoMini.png")); // NOI18N

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(jLabel87, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(22, 22, 22))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel87)
                .addContainerGap(66, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 190, 290));

        jPanel4.setBackground(new java.awt.Color(218, 212, 181));

        jLabel90.setFont(new java.awt.Font("Segoe UI Black", 1, 18)); // NOI18N
        jLabel90.setForeground(new java.awt.Color(167, 49, 33));
        jLabel90.setText("Nhập mã OTP:");

        btnXN1.setBackground(new java.awt.Color(149, 35, 35));
        btnXN1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnXN1.setForeground(new java.awt.Color(255, 255, 255));
        btnXN1.setText("Xác nhận");
        btnXN1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXN1ActionPerformed(evt);
            }
        });

        btnSend1.setBackground(new java.awt.Color(149, 35, 35));
        btnSend1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnSend1.setForeground(new java.awt.Color(255, 255, 255));
        btnSend1.setText("Gửi");
        btnSend1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSend1ActionPerformed(evt);
            }
        });

        btnThoat2.setBackground(new java.awt.Color(0, 102, 102));
        btnThoat2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnThoat2.setForeground(new java.awt.Color(255, 255, 255));
        btnThoat2.setText("Quay lại ");
        btnThoat2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThoat2ActionPerformed(evt);
            }
        });

        jLabel91.setFont(new java.awt.Font("Segoe UI Black", 1, 18)); // NOI18N
        jLabel91.setForeground(new java.awt.Color(167, 49, 33));
        jLabel91.setText("Nhập Email:");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel90)
                    .addComponent(jLabel91)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(40, 40, 40)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(btnXN1, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnThoat2, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(txtEmail1, javax.swing.GroupLayout.PREFERRED_SIZE, 288, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnSend1, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(txtMXN1, javax.swing.GroupLayout.PREFERRED_SIZE, 375, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(101, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addComponent(jLabel91)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnSend1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtEmail1, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel90)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtMXN1, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnXN1, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnThoat2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(71, 71, 71))
        );

        getContentPane().add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 0, -1, 300));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnXN1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXN1ActionPerformed
        // TODO add your handling code here:
        xuLyXacNhanOTP();
    }//GEN-LAST:event_btnXN1ActionPerformed

    private void btnThoat2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThoat2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnThoat2ActionPerformed

    private void btnSend1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSend1ActionPerformed
        // TODO add your handling code here:
        String email = txtEmail1.getText();

        guiMaOTPNeuTonTai(email);

    }//GEN-LAST:event_btnSend1ActionPerformed

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
            java.util.logging.Logger.getLogger(QuenMatKhauJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(QuenMatKhauJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(QuenMatKhauJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(QuenMatKhauJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                QuenMatKhauJDialog dialog = new QuenMatKhauJDialog(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton btnSend1;
    private javax.swing.JButton btnThoat2;
    private javax.swing.JButton btnXN1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel87;
    private javax.swing.JLabel jLabel90;
    private javax.swing.JLabel jLabel91;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JTextField txtEmail1;
    private javax.swing.JTextField txtMXN1;
    // End of variables declaration//GEN-END:variables
}
