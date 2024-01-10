package com.edusys.utils;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class XImage {

    public static Image getAppIcon() {
        URL url = XImage.class.getResource("/com/edusys/icon/images/logoMini.png");
        return new ImageIcon(url).getImage();
    }

    public static boolean save(File src) {
        File dst = new File("src\\com\\edusys\\logos", src.getName());
        if (!dst.getParentFile().exists()) {
            dst.getParentFile().mkdirs();
        }
        try {
            Path from = Paths.get(src.getAbsolutePath());
            Path to = Paths.get(dst.getAbsolutePath());
            BufferedImage b;
            try {
                b = ImageIO.read(dst);
            } catch (IOException e) {
                // Handle IOException if necessary
            }
            Files.copy(from, to, StandardCopyOption.REPLACE_EXISTING);
            return true;
        } catch (IOException e) {
            // Handle IOException if necessary
            throw new RuntimeException(e);
        }
    }

    public static ImageIcon read(String fileName) {
        // Load the image from the resources folder
        URL url = XImage.class.getResource("/com/edusys/icon/images/" + fileName);
        ImageIcon icon = new ImageIcon(url);
        return new ImageIcon(icon.getImage().getScaledInstance(180, 180, Image.SCALE_DEFAULT));
    }
}
