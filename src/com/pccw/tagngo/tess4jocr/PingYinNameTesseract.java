package com.pccw.tagngo.tess4jocr;

import java.awt.image.BufferedImage;

import org.opencv.core.Mat;

import com.pccw.tagngo.opencv.MatToBufImg;
import com.pccw.tagngo.utils.OpencvXTess4jConfig;

import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

public class PingYinNameTesseract implements TesseractOCR{

	@Override
	public String ocrWithMat(Mat mat) {
		Tesseract instance = new Tesseract();
		if(OpencvXTess4jConfig.tessdataPath!=null){
			instance.setDatapath(OpencvXTess4jConfig.tessdataPath);
		}
		instance.setTessVariable("tessedit_char_whitelist", "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz,");
		BufferedImage bi = new MatToBufImg(mat,".png").getImage();
		String result =null;
		
		try {
			result = instance.doOCR(bi);
			result = result.trim();
		} catch (TesseractException e) {
			e.printStackTrace();
		}
		return result;
	}

}
