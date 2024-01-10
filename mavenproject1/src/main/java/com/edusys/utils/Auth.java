/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.edusys.utils;

import com.edusys.entity.NhanVien;

/**
 *
 * @author MS QUOC
 */
public class Auth {
    // Đối tượng chứa thông tin người dùng sau khi đăng nhập
    public static NhanVien user = null;
    public static String role = null;

    // Xóa thông tin của người sử dụng khi có yêu cầu đăng xuất
    public static void clear() {
        Auth.user = null;
        Auth.role = null;
    }

    // Kiểm tra xem người dùng đã đăng nhập hay chưa
    public static boolean isAuthenticated() {
        return Auth.user != null;
    }

    // Phương thức để cho phép sử dụng các form khi đăng nhập
    public static boolean isQuanLy(String requiredRole) {
        return isAuthenticated() && requiredRole.equals(Auth.role);
    }

     public static boolean isLogin(){
         return Auth.user != null;
     }
     public static boolean isManager(){
         return Auth.isLogin()&&user.isVaiTro();
     }


}