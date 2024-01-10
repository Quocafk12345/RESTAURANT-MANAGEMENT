/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.edusys.utils;

import java.util.Date;
import javax.swing.ImageIcon;

import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Admin
 */
public class SanphamTableModel extends DefaultTableModel {

    public SanphamTableModel() {
    }

    public SanphamTableModel(Object[][] data, Object[] columnNames) {
        super(data, columnNames);
    }

    @Override
    public Class getColumnClass(int col) {
        Class temp = null;
        switch (col) {
            case 0:
                temp = Integer.class;
                break;
            case 1:
                temp = ImageIcon.class;
                //temp = Blob.class;
                break;
            case 2:
                temp = String.class;
                break;
            case 3:
                temp = Double.class;
                break;
            case 4:
                temp = Date.class;
                break;
            case 5:
                temp = String.class;
                break;
            case 6:
                temp = String.class;
                break;
            default:
                break;
        }
        return temp;
    }

}
