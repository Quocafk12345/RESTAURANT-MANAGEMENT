/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.edusys.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author MS QUOC
 */
public class JdbcHelper {

    private static String driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    private static String url = "jdbc:sqlserver://DESKTOP-ISEQ9P9\\MSSQLSERVER01;database=RestaurantManagement4";
    private static String user = "sa";
    private static String pass = "songlong";

    /*
    Nạp driver
     */
    static {
        try {
            Class.forName(driver);

        } catch (ClassNotFoundException ex) {
            throw new RuntimeException(ex);
        }
    }

    /*
    Xây dựng PreparedStatement
    @param sql là câu lệnh sql có thể chứa tham số. Nó có thể là một lời gọi thủ tực lưu
    @param args là danh sách các giá trị được cung cấp cho các tham số trong câu lệnh sql
    @return PreparedStatemenet tạo được
    @throws java.sql.SQLException lỗi sai cú pháp
     */
    public static PreparedStatement JdbcHelper(String sql, Object... args) throws SQLException {
        Connection connection = DriverManager.getConnection(url, user, pass);
        PreparedStatement pstm = null;
        if (sql.trim().startsWith("{")) {
            pstm = connection.prepareCall(sql);
        }
        else{
            pstm = connection.prepareStatement(sql);
        }
        for (int i = 0; i < args.length; i++) {
            pstm.setObject(i + 1, args[i]);
        }
        return pstm;
    }
    
    public static int excuteUpdate(String sql, Object... args) {
        try {
            PreparedStatement stmt = JdbcHelper(sql, args);
            try {
                return stmt.executeUpdate();
            } finally {
                stmt.getConnection().close();
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    /*Thực hiện câu lệnh sql truy vấn (select) hoặc thủ tục lưu trữ dữ liệu
    @param sql là câu lệnh sql có thể chưa tham số. Nó có thể là một lời gọi thủ tục được lưu
    @param args là danh sách cá giá trị được cung cấp cho các tham số trong câu lệnh sql
     */
    public static ResultSet excuteQuery(String sql, Object... args) {
        try {
            PreparedStatement stmt = JdbcHelper(sql, args);
            return stmt.executeQuery();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

     public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, user, pass);
    }
     
    public static Object value(String sql, Object... args) {
        try {
            ResultSet rs = excuteQuery(sql, args);
            if (rs.next()) {
                return rs.getObject(1);
            }
            rs.getStatement().getConnection().close();
        } catch (Exception e) {
            throw new RuntimeException();
        }
        return null;
    }

}
