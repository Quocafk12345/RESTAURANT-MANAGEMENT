/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.edusys.utils;

/**
 *
 * @author MS QUOC
 */
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

public class ImageRenderer extends DefaultTableCellRenderer {

    @Override
    public Component getTableCellRendererComponent(
            JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {

        JLabel label = new JLabel();
        label.setHorizontalAlignment(JLabel.CENTER);

        // Kiểm tra giá trị của cột để xác định là hình ảnh hay không
        if (value instanceof ImageIcon) {
            ImageIcon icon = (ImageIcon) value;
            label.setIcon(icon);
            
            //Thiết lập kích thước cho toàn bộ dòng
            table.setRowHeight(row, icon.getIconHeight());
        }

        return label;
    }
    
    private ImageIcon scaleImage(ImageIcon icon){
        //Sử dụng phương thức getImage để lấy image từ imageIcon
        Image image = icon.getImage();
        
        //Scale hình ảnh
        Image scaleImage = image.getScaledInstance(icon.getIconWidth(), icon.getIconHeight(), Image.SCALE_SMOOTH);
        
        //Tạo ImageIcon từ Image đã scale
        return new ImageIcon(scaleImage);
    }
}
