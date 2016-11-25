package com.test;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import net.sourceforge.tess4j.util.ImageHelper;

public class TesseractExample {

	public static void main(String[] args) {
        // ImageIO.scanForPlugins(); // for server environment
        File imageFile = new File("test/resources/test-data/sample.png");
//        File imageFile = new File("test/resources/test-data/test2.jpg");
        ITesseract instance = new Tesseract(); // JNA Interface Mapping
        // ITesseract instance = new Tesseract1(); // JNA Direct Mapping
        // instance.setDatapath("&lt;parentPath&gt;"); // replace &lt;parentPath&gt; with path to parent directory of tessdata
        // instance.setLanguage("eng");
        
//        instance.setTessVariable("tessedit_char_whitelist", "012456789A-Z");
        BufferedImage bi = imageIO(imageFile);
        
        bi = imageManager(bi,4);
        
        
        try {
//            String result = instance.doOCR(imageFile);
            String result = instance.doOCR(bi);
            System.out.println(result);
        } catch (TesseractException e) {
            System.err.println(e.getMessage());
        }
    }
	
	public static BufferedImage imageManager(BufferedImage bi,int op){
		BufferedImage textImage = null;
		
		switch (op) {
		case 1:
			// �����ͼƬ�ڰ״���,��ǿʶ����.������ͨ����ͼ,��ȡͼƬ����Ҫʶ��Ĳ���  
//			textImage = ImageHelper.convertImageToGrayscale(ImageHelper.getSubImage(bi, 0, 0, 0, 0));  
			// ͼƬ��,�Լ�ʹ����Ӱ��ʶ���ʵ���Ҫ��������ʽ��ӡ���ּ�������,�����񻯷�������ʶ����  
			// textImage = ImageHelper.convertImageToBinary(textImage);  
			// ͼƬ�Ŵ�5��,��ǿʶ����(�ܶ�ͼƬ�����޷�ʶ��,�Ŵ�5��ʱ�Ϳ�������ʶ,���ǿ��˵��ͻ��������õ�,��ʽ��ӡ����ӡ�����������,����ͷŴ�5��)  
			textImage = ImageHelper.getScaledInstance(bi, bi.getWidth() * 10, bi.getHeight() * 10);
			break;
			
		case 2:
			textImage = ImageHelper.getScaledInstance(bi, bi.getWidth() * 10, bi.getHeight() * 10);
			textImage = ImageHelper.convertImageToBinary(textImage);

	        try {
				ImageIO.write(textImage, "png", new File("C:\\Users\\80575749\\Desktop\\Tag&Go\\Tag&Gotestuse_0.png"));
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
	        break;
	        
		case 3:
//			textImage = ImageHelper.getScaledInstance(bi, bi.getWidth() * 20, bi.getHeight() * 20);
			textImage = ImageHelper.convertImageToGrayscale(bi);
			try {
				ImageIO.write(textImage, "png", new File("C:\\Users\\80575749\\Desktop\\Tag&Go\\Tag&Gotestuse_0.png"));
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			break;
			
		case 4:
			textImage = toGrey(bi);
			try {
				ImageIO.write(textImage, "png", new File("C:\\Users\\80575749\\Desktop\\Tag&Go\\Tag&Gotestuse_0.png"));
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			break;
			
		default:
			break;
		}
		
		return textImage;
	}
	
	public static BufferedImage imageIO(File file){
		InputStream is;
		BufferedImage bi = null;
		try {
			is = new FileInputStream(file);
			bi=ImageIO.read(is);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return bi;
	}
	
	public static BufferedImage toGrey(BufferedImage bi){
		int width = bi.getWidth();
		int height = bi.getHeight();
		BufferedImage grayImage = new BufferedImage(width, height, BufferedImage.TYPE_USHORT_GRAY);  
	    for(int i= 0 ; i < width ; i++){  
	        for(int j = 0 ; j < height; j++){  
	        int rgb = bi.getRGB(i, j);
	        grayImage.setRGB(i, j, rgb);  
	        }  
	    }
	    return grayImage;
	}
	
}
