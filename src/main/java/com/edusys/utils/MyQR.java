/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.edusys.utils;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.NotFoundException;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author MS QUOC
 */
public class MyQR {

    public static void createQR(String data, String path, String charset, Map hashMap, int height, int width) throws WriterException, IOException {
        BitMatrix matrix = new MultiFormatWriter().encode(new String(data.getBytes(charset), charset), BarcodeFormat.QR_CODE, width, height);
        MatrixToImageWriter.writeToFile(matrix, path.substring(path.lastIndexOf('.') + 1), new File(path));
    }

    //Drive code
    public static void main(String[] args)
            throws WriterException, IOException, NotFoundException {
        //dữ liệu mà qr code sẽ chứa
        String data = "www.geeksforgeeks.org";
        //Đường dẫn nơi hình ảnh sẽ được lưu
        String path = "C:\\Users\\MS QUOC\\Downloads\\demo.png";
        //Bộ mã hóa
        String charset = "UTF-8";
        Map<EncodeHintType, ErrorCorrectionLevel> hashMap = new HashMap<EncodeHintType, ErrorCorrectionLevel>();
        hashMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
        //Tạo QR code và lưu
        //Trong folder cụ thể
        //Như một jpg file
        createQR(data, path, charset, hashMap,200,200);
        System.out.println("QR Code Generated!!!");
    }
}
