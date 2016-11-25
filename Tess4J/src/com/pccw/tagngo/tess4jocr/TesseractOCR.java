package com.pccw.tagngo.tess4jocr;

import org.opencv.core.Mat;

public interface TesseractOCR {
	
	public String ocrWithMat(Mat mat);

}
