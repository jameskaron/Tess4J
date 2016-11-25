package com.test.opencv;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.highgui.Highgui;

import com.pccw.tagngo.opencv.OpenCVDo;

public class CheckImageMargin {

	public static void main(String[] args) {
		Mat srcImg = Highgui.imread("C:\\Users\\80575749\\Desktop\\TestUse\\test2.png");
		checkImageMargin(srcImg);
	}
	
	
	static{
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
	}
	
	
	
	public static void checkImageMargin(Mat imageMat){
		
		
		Mat srcImg2 = new Mat();
		Mat midImg = new Mat();
		Mat destImg = new Mat();
		
		
		
		OpenCVDo openCVDo = new OpenCVDo();
		openCVDo.init(imageMat);
		
		openCVDo.rGB2GRAY(imageMat, destImg);
		int thresh = openCVDo.Otsu(destImg);
		
		openCVDo.canny(destImg, destImg, thresh, 200);
		
	}
	
	
	
}
