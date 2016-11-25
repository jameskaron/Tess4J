package com.pccw.tagngo.scanner;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.Buffer;

import javax.imageio.ImageIO;

import com.lowagie.text.Image;
import com.sun.media.jfxmedia.events.NewFrameEvent;

import net.sourceforge.tess4j.*;
import net.sourceforge.tess4j.util.ImageHelper;

public class TesseractExample {

	public static void main(String[] args) throws IOException {

        File imageFile = new File("D:\\project desktop\\16-11-11 TagAndGo\\test3-1.png");
        
        
        
        ITesseract instance = new Tesseract();  // JNA Interface Mapping
//         ITesseract instance = new Tesseract1(); // JNA Direct Mapping
        
        instance.setTessVariable("tessedit_char_whitelist", "0123456789()ABCDEFGHIJKLMNOPQRSTUVWXYZ");
//      instance.setTessVariable("tessedit_char_whitelist", "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz,");
//		instance.setTessVariable("tessedit_char_whitelist", "0123456789");        
        
        BufferedImage bi  = ImageIO.read(imageFile);
        
        BufferedImage textImage = ImageHelper.getScaledInstance(bi, bi.getWidth() * 10, bi.getHeight() * 10);
        
//        BufferedImage binary_i =  ImageHelper.convertImageToBinary(bi);
        
        ImageIO.write(textImage, "png", new File("D:\\project desktop\\16-11-11 TagAndGo\\test_big.png"));
        
//        BufferedImage binary_i_1 =  ImageHelper.replaceWithWhiteColor(binary_i);
//        
//        ImageIO.write(binary_i_1, "png", new File("D:\\project desktop\\16-11-11 TagAndGo\\testuse_1.png"));

        
        
        ImageHelper.keithTest001(bi);
        
//      BufferedImage binary_i_1 =  ImageHelper.keithTest(bi);
//      
//      ImageIO.write(binary_i_1, "png", new File("D:\\project desktop\\16-11-11 TagAndGo\\testkeith_1.png"));
//
        try {
        	
        	
        	
            String result = instance.doOCR(textImage);
            System.out.println(result);
        } catch (TesseractException e) {
            System.err.println(e.getMessage());
        }
		
	}

}
