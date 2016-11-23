package com.pccw.tagngo.opencv;

import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Size;

public class PrefectImageOperation {
	
	private OpenCVDo openCVDo;
	
	public PrefectImageOperation(OpenCVDo ocvDo){
		this.openCVDo = ocvDo;
	}
	
	public void binarization(Mat srcMat, Mat destImg, int iterations){
		
		openCVDo.rGB2GRAY(srcMat, destImg);
		openCVDo.adaptiveThreshold(destImg, destImg, 25, 10);
		
		
		if(iterations!=0){
			openCVDo.erode(destImg, destImg, new Size(3, 3), new Point(1,0) , iterations);
			openCVDo.dilate(destImg, destImg, new Size(3, 3), new Point(1,0) , iterations);
		}

		openCVDo.openOperation(destImg, destImg, new Size(3,1) , new Point(1,0), (iterations+1)*3);
		openCVDo.openOperation(destImg, destImg, new Size(3,1) , new Point(1,0), (iterations+1)*3);
		
	}

}
