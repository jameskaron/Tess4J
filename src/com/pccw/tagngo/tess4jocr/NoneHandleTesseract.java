package com.pccw.tagngo.tess4jocr;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.opencv.core.Mat;

import com.pccw.tagngo.opencv.MatToBufImg;
import com.pccw.tagngo.utils.OpencvXTess4jConfig;

import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

public class NoneHandleTesseract {
	
	public String ocrWithMat(String filename){
		String result = null;
		Tesseract instance = new Tesseract();
		if(OpencvXTess4jConfig.tessdataPath!=null){
			instance.setDatapath(OpencvXTess4jConfig.tessdataPath);
		}
		try {
			File imageFile = new File(filename);
			BufferedImage bi  = ImageIO.read(imageFile);			
			result = instance.doOCR(bi);
//			result = result.trim();
		} catch (TesseractException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

}
