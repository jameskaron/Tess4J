package com.pccw.tagngo.opencv;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Rect;
import org.opencv.highgui.Highgui;
import org.opencv.imgproc.Imgproc;

public class OutlineOperation {
	
	private OpenCVDo openCVDo;
	
	public OutlineOperation(OpenCVDo ocvDo){
		this.openCVDo = ocvDo;
	}
	
	public Map<Rect,Mat> findPossibleRect(Mat srcMat, List<MatOfPoint> contours, double minRatio, double maxRatio){

		Map<Rect,Mat> possibleMatMap = new HashMap<>();
		double area = srcMat.width() * srcMat.height();
		
		for (MatOfPoint mop : contours) {
			double contourarea = Imgproc.contourArea(mop);
			if (contourarea / area   >= 0.001) {
				Rect rect = openCVDo.boundingRect(mop);
				double ratio = (double)rect.height/ (double)rect.width;
				if(ratio>maxRatio || ratio<minRatio){
					continue;
				}
				
				Mat cmat = openCVDo.cutMat(srcMat, rect);
				if(openCVDo.debugModel){
					Highgui.imwrite(String.format(openCVDo.testImageFilePath, openCVDo.tag, openCVDo.count, "testuse-opencv-cutMat.png"), cmat);
					openCVDo.count++;
				}
				possibleMatMap.put(rect, cmat);
//				System.out.println(rect.x + " " + rect.y + "|" + rect.height + " " + rect.width);
			}
		}
		return possibleMatMap;
	}
	
	

}
