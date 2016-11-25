package com.pccw.tagngo.tess4jocr;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import org.opencv.core.Mat;

import com.pccw.tagngo.opencv.MatToBufImg;
import com.pccw.tagngo.utils.OpencvXTess4jConfig;

import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

public class IdNoTesseract implements TesseractOCR{
	
	@Override
	public String ocrWithMat(Mat mat) {
		Tesseract instance = new Tesseract();
		if(OpencvXTess4jConfig.tessdataPath!=null){
			instance.setDatapath(OpencvXTess4jConfig.tessdataPath);
		}
		instance.setTessVariable("tessedit_char_whitelist", "0123456789()ABCDEFGHIJKLMNOPQRSTUVWXYZ");
		BufferedImage bi = new MatToBufImg(mat,".png").getImage();
		String result =null;
		
		try {
			result = instance.doOCR(bi);
			result = result.trim();
		} catch (TesseractException e) {
			e.printStackTrace();
		}
		
		if(result!=null && result.length()!=0){
			result = result.replaceAll(" ", "");
		}else{
			return null;
		}
		System.out.println(result);
		int l = result.length();
		System.out.println(l);
		if(l!=0){
			List<String> list = new ArrayList<String>();
			for (int i = 0; i < result.length(); i++) {
				list.add(String.valueOf(result.charAt(i)));
			}
			if(list.get(0).equals("(")){
				list.set(0, "C");
			}
			if((l==10||l==11)&&!list.get(l-1).equals(")")){
				list.set(l-1, ")");
			}
			if((l==10||l==11)&&!list.get(l-3).equals("(")){
				list.set(l-3, "(");
			}
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < l; i++) {
				sb.append(list.get(i));
			}
			result = sb.toString();
		}else{
			return null;
		}
		
		return result;
	}

}
