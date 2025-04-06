package com.seahorse.utils;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class ImageFromPath {
    public static BufferedImage GetBufferedImageFromPath(String path) {
        try{
            return ImageIO.read(new File(path));
        }catch(IOException e){
            System.out.println("Lỗi load hình ảnh: " + path);
            return null;
        }      
    }
}
