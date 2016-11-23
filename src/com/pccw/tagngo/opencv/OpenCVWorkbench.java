package com.pccw.tagngo.opencv;

import java.awt.image.BufferedImage;
import java.awt.image.Kernel;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import javax.crypto.CipherInputStream;

import org.apache.xmlgraphics.image.loader.pipeline.ImageProviderPipeline;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.highgui.Highgui;
import org.opencv.imgproc.Imgproc;

import com.pccw.tagngo.tess4jocr.AllMatchTesseract;
import com.pccw.tagngo.tess4jocr.BirthDateTesseract;
import com.pccw.tagngo.tess4jocr.IdNoTesseract;
import com.pccw.tagngo.tess4jocr.PingYinNameTesseract;
import com.pccw.tagngo.tess4jocr.SeqNoTesseract;

import net.sourceforge.tess4j.util.ImageHelper;

public class OpenCVWorkbench {
	
	public final static double idRatio = 0.2D;
	public final static double cardW_H_Ratio = 0.627907D;
	public final static double seqnoBlock_W_Ratio = 0.116279D;
	public final static double seqnoBlock_H_Ratio = 0.059259D;
	public final static double seqno3Block_W_Ratio = 0.337209D;
	public final static double seqno3Block_H_Ratio = 0.059259D;
	public final static double seqno3Block_X_Ratio = 0.2953488D;
	public final static double seqno3Block_Y_Ratio = 0.3481482D;
	public final static double idnoBlock_W_Ratio = 0.340697D;
	public final static double idnoBlock_H_Ratio = 0.1060741D;
	public final static double idnoBlock_X_Ratio = 0.651162D;
	public final static double idnoBlock_Y_Ratio = 0.924259D;
	public final static double nameBlock_W_Ratio = 0.4186045D;
	public final static double nameBlock_H_Ratio = 0.0857209D;
	public final static double nameBlock_X_Ratio = 0.0158837D;
	public final static double nameBlock_Y_Ratio = 0.2629629D;	
	public final static double birthDayBlock_W_Ratio = 0.2488372D;
	public final static double birthDayBlock_H_Ratio = 0.0592593D;
	public final static double birthDayBlock_X_Ratio = 0.2953488D;
	public final static double birthDayBlock_Y_Ratio = 0.6018519D;


	public static int count = 0;
	public static String tag = new SimpleDateFormat("yyyy_MM_dd-HH_mm_ss").format(new Date(System.currentTimeMillis()));
	public static String testImageFilePath =  "F:\\UUI\\testdata\\16-11-23 TagAndGo\\%s\\%d_%s";

	public static void main(String[] args) {

		System.loadLibrary("opencv_java2413");
		System.out.println(Core.NATIVE_LIBRARY_NAME);
		
		new File("F:\\UUI\\testdata\\16-11-23 TagAndGo\\" + tag).mkdirs();
//
		Mat srcImg = Highgui.imread("F:\\UUI\\testdata\\sample.png");
		System.out.println(String.format(testImageFilePath, tag, count, "testuse-opencv-src.png"));
		Highgui.imwrite(String.format(testImageFilePath, tag, count, "testuse-opencv-src.png"),srcImg);
		count++;
//
//		Mat srcImg2 = new Mat();
//		Mat midImg = new Mat();
//		Mat destImg = new Mat();
//		Mat grayImg = new Mat();
//		Mat binaryMat = new Mat();
//		Mat dilateMat = new Mat();
//		Mat erodeMat = new Mat();
//		Mat dilateMat2 = new Mat();
//		Mat erodeMat2 = new Mat();
//		Mat dilateMat3 = new Mat();
//		Mat cannyMat = new Mat();
//
//		
//		
////		case1_findMaxContour(srcImg);
////		case2(srcImg);
//		
//		case6(srcImg);
//		// blur(srcImg, srcImg2, new Size(7, 7));

		case10(srcImg);
		
//
//		// toGray(srcImg, grayImg);
//		//
//		//
//		//
//		// canny(grayImg, midImg, 2, 5);
//		//
//		//
//		//
//		// adjustColor(grayImg, srcImg2, 0.5, 1.3 );
//		//
//		// Mat sobelImg = new Mat();
//		// sobel(grayImg, sobelImg, 0, 0, 1);
//		// threshold(grayImg, binaryMat);
//		//
//		// Mat kernel = Imgproc.getStructuringElement(Imgproc.CV_SHAPE_RECT,new
//		// Size(3, 1),new Point(1, 0));
//		// Point point = new Point(-1, -1);
//		//// Imgproc.dilate(binaryMat, dilateMat, kernel, point, 2);
//		//// Highgui.imwrite("D:\\project desktop\\16-11-11
//		// TagAndGo\\testuse-opencv-dilateMat.png", dilateMat);
//		//
//		// Imgproc.erode(binaryMat, erodeMat, kernel, point, 1);
//		// Highgui.imwrite("D:\\project desktop\\16-11-11
//		// TagAndGo\\testuse-opencv-erodeMat.png", erodeMat);
//		//
//		// Imgproc.dilate(erodeMat, dilateMat2, kernel, point, 3);
//		// Highgui.imwrite("D:\\project desktop\\16-11-11
//		// TagAndGo\\testuse-opencv-dilateMat2.png", dilateMat2);
//		//
//		//
//		// Mat kernel2 = Imgproc.getStructuringElement(Imgproc.CV_SHAPE_RECT,new
//		// Size(1, 3),new Point(0, 1));
//		//
//		// Imgproc.erode(dilateMat2, erodeMat2, kernel2, point, 1);
//		// Highgui.imwrite("D:\\project desktop\\16-11-11
//		// TagAndGo\\testuse-opencv-erodeMat2.png", erodeMat2);
//		//
//		// Imgproc.dilate(erodeMat2, dilateMat3, kernel2, point, 4);
//		// Highgui.imwrite("D:\\project desktop\\16-11-11
//		// TagAndGo\\testuse-opencv-dilateMat3.png", dilateMat3);
//
//		// canny(srcImg, midImg, 2, 5);
//
//		// Highgui.imwrite("D:\\project desktop\\16-11-11
//		// TagAndGo\\testuse-opencv-1.png", midImg);
//		//
//		// Imgproc.cvtColor(midImg, destImg, Imgproc.COLOR_GRAY2RGB);
//		//// cvtColor(midImage,dstImage, CV_GRAY2BGR);
//		// Highgui.imwrite("D:\\project desktop\\16-11-11
//		// TagAndGo\\testuse-opencv-2.png", destImg);
//		//
//		// Mat lines = new Mat();
//		//
//		//// Vector lines;
//		// Imgproc.HoughLines(midImg, lines, Math.PI/180.0 , 100, 100);
//		// System.out.println(lines.cols());
//		// Point[] points = new Point[]{ new Point(), new Point() };
//		// for (int x = 0; x < lines.cols(); x++) {
//		// double[] vec = lines.get(0, x);
//		// points[0].x = vec[0];
//		// points[0].y = vec[1];
//		// points[1].x = vec[2];
//		// points[1].y = vec[3];
//		// System.out.println(vec[0]+" "+vec[1]+" "+vec[2]+" "+ vec[3]);
//		// }
//
//		// HoughLinesP(midImage, lines, 1, CV_PI/180, 80, 50, 10 );
//		// Highgui.imwrite("D:\\project desktop\\16-11-11
//		// TagAndGo\\testuse-opencv-3.png", lines);
//		// HoughLines(midImage, lines, 1, CV_PI/180, 150, 0, 0 );
//		//
//		// Mat m = Mat.eye(3, 3, CvType.CV_8UC1);
		// System.out.println("m = " + m.dump());

	}

	public static void case1_findMaxContour(Mat srcMat) {

		Mat srcImg2 = new Mat();
		Mat midImg = new Mat();
		Mat destImg = new Mat();
		
		rGB2GRAY(srcMat, destImg);

		int thresh = Otsu(destImg);
		// threshold(destImg, binaryMat,thresh);
		
		canny(destImg, destImg, thresh, 200);

		// convertABS(binaryMat, destImg);

		System.out.println(thresh);

		Mat frameMat = new Mat(destImg.size(), CvType.CV_8SC1);
		List<MatOfPoint> contours = new ArrayList<>();
		findContours3(destImg, srcMat, contours);
//		findContours2(destImg, srcMat, contours, new Point(-1, -1));
		//
		for (MatOfPoint mop : contours) {

			double contourarea = Imgproc.contourArea(mop);
			if (contourarea >= 5000) {
				System.out.println(contourarea);
				// System.out.println(mop.cols() + " " + mop.rows());
				for (Point p : mop.toArray()) {
					System.out.println(p.x + " " + p.y);
				}

				Rect rect = new Rect();

				rect = boundingRect(mop);

				System.out.println(rect.x + " " + rect.y + "|" + rect.height + " " + rect.width);

			}
		}
	}
	
	
	public static void case2(Mat srcMat) {

		Mat srcImg2 = new Mat();
		srcMat.copyTo(srcImg2);
		Mat midImg = new Mat();
		Mat destImg = new Mat();
		
		// threshold(destImg, binaryMat,thresh);

		rGB2GRAY(srcMat, destImg);
		int thresh = Otsu(destImg);
		
		canny(destImg, destImg, thresh, 200);
		
		sobel(destImg, destImg, 0, 0, 1);
				
		 dilate(destImg, destImg, new Size(3, 3), new Point(1,0) , 5);
		 erode(destImg, destImg, new Size(3, 3), new Point(1,0) , 3);
		 dilate(destImg, destImg, new Size(3, 3), new Point(1,0) , 5);
		 
		 dilate(destImg, destImg, new Size(3, 3), new Point(0,1) , 3);
		 erode(destImg, destImg, new Size(3, 3), new Point(0,1) , 2);
		 dilate(destImg, destImg, new Size(3, 3), new Point(0,1) , 3);
		// convertABS(binaryMat, destImg);

		System.out.println(thresh);

		Mat frameMat = new Mat(destImg.size(), CvType.CV_8SC1);
		List<MatOfPoint> contours = new ArrayList<>();
//		findContours(destImg, srcMat, contours);
		findContours2(destImg, srcMat, contours, new Point(-1, -1));
		//
		for (MatOfPoint mop : contours) {

			double contourarea = Imgproc.contourArea(mop);
			if (contourarea >= 3000) {
				System.out.println(contourarea);
				// System.out.println(mop.cols() + " " + mop.rows());
				for (Point p : mop.toArray()) {
					System.out.println(p.x + " " + p.y);
				}

				Rect rect = new Rect();

				rect = boundingRect(mop);

				System.out.println(rect.x + " " + rect.y + "|" + rect.height + " " + rect.width);

			}
		}
	}
	
	public static void case3(Mat srcMat){
		Mat blurred = new Mat();
		Mat blurred2 = new Mat();
		medianBlur(srcMat, blurred, 9);
		medianBlur(srcMat, blurred2, 5);
		
		List<MatOfPoint> contours = EdgeDetection.getDetectedSquareList(srcMat, blurred);
		
	}
	
	public static void case4(Mat srcMat) {
		
		long startTime = System.currentTimeMillis();

		Mat srcImg2 = new Mat();
		srcMat.copyTo(srcImg2);
		Mat midImg = new Mat();
		Mat destImg = new Mat();
		
		rGB2GRAY(srcMat, destImg);

		int thresh = Otsu(destImg);
		// threshold(destImg, binaryMat,thresh);
		
		canny(destImg, destImg, thresh, 200);
		
		sobel(destImg, destImg, 0, 0, 1);
				
		 dilate(destImg, destImg, new Size(3, 3), new Point(1,0) , 4);
		 erode(destImg, destImg, new Size(3, 3), new Point(1,0) , 2);
		 dilate(destImg, destImg, new Size(3, 3), new Point(1,0) , 4);
		 
		 dilate(destImg, destImg, new Size(3, 3), new Point(0,1) , 4);
		 erode(destImg, destImg, new Size(3, 3), new Point(0,1) , 2);
		 dilate(destImg, destImg, new Size(3, 3), new Point(0,1) , 4);
		// convertABS(binaryMat, destImg);
		 
//		 dilate(destImg, destImg, new Size(3, 3), new Point(1,0) , 6);
//		 erode(destImg, destImg, new Size(3, 3), new Point(1,0) , 4);
//		 dilate(destImg, destImg, new Size(3, 3), new Point(1,0) , 6);
//		 
//		 dilate(destImg, destImg, new Size(3, 3), new Point(1,0) , 6);
//		 erode(destImg, destImg, new Size(3, 3), new Point(1,0) , 4);
//		 dilate(destImg, destImg, new Size(3, 3), new Point(1,0) , 6);
		 
		 double area = srcMat.height() * srcMat.width();

		System.out.println(thresh);

		Mat frameMat = new Mat(destImg.size(), CvType.CV_8SC1);
		List<MatOfPoint> contours = new ArrayList<>();
		findContours(destImg, srcImg2, contours);
//		findContours2(destImg, srcMat, contours, new Point(-1, -1));
		//
		
		Map<Rect,Mat> possibleMatMap = new HashMap<>();
		
		for (MatOfPoint mop : contours) {

			double contourarea = Imgproc.contourArea(mop);
			if (contourarea / area   >= 0.005) {
				System.out.println(contourarea);
				// System.out.println(mop.cols() + " " + mop.rows());
//				for (Point p : mop.toArray()) {
//					System.out.println(p.x + " " + p.y);
//				}

				Rect rect = boundingRect(mop);
				

				
				double ratio = (double)rect.height/ (double)rect.width;
//				System.out.println("ratio: " + ratio);
				if(ratio>0.7){
//					System.out.println("Too Fat");
					continue;
				}
				
				Mat cmat = cutMat(srcMat, rect);
				Highgui.imwrite(String.format(testImageFilePath, tag, count, "testuse-opencv-cutMat.png"), cmat);
				count++;
					
				possibleMatMap.put(rect, cmat);
				System.out.println(rect.x + " " + rect.y + "|" + rect.height + " " + rect.width);

			}
		}
		
		List<Rect> list = new ArrayList<>();
		for(Rect r:possibleMatMap.keySet()){
			list.add(r);
		}
		
		Collections.sort(list, new Comparator<Rect>() {
			@Override
			public int compare(Rect o1, Rect o2) {
				return o1.y-o2.y;
			}
		});
		
		List<Mat> nameMat_list = new ArrayList<>();
		Map<Rect,Mat> seqNoMat_map = new HashMap();
		List<Mat> idMat_list = new ArrayList<>();
		
		int lastY = 0;
		int Y_maxE = 3;
		double radio_maxE = 0.1;
		int halfW = srcMat.width()/2;
		int halfH = srcMat.height()/2;
		
		for (int i = 0; i < list.size(); i++) {
			
			Rect r = list.get(i);
			
			double radio = (double)r.height/ (double)r.width;  
			System.out.println("radio=" + radio);
			
			if(r.x>halfW && r.y>halfH){
				if(radio>idRatio-radio_maxE && radio<idRatio+radio_maxE){
					idMat_list.add(possibleMatMap.get(r));
				}
			}else{
				
				
				if(lastY == 0)
					lastY = r.y;
				else{
					int currentY = r.y;
					if(Math.abs(lastY-currentY)<Y_maxE){
						seqNoMat_map.put(r,possibleMatMap.get(r));
						seqNoMat_map.put(list.get(i-1),possibleMatMap.get(list.get(i-1)));
					}
					lastY = currentY;
				}
				
			}
		}
		
		System.out.println(nameMat_list.size()+"/"+seqNoMat_map.size()+"/" +idMat_list.size());
		
		List<Rect> list2 = new ArrayList<>();
		List<Mat> list3 = new ArrayList<>();
		
		for(Rect rect:seqNoMat_map.keySet()){
			list2.add(rect);
		}
		
		Collections.sort(list2, new Comparator<Rect>() {
			@Override
			public int compare(Rect o1, Rect o2) {
				return o1.x-o2.x;
			}
		});
		
		for(Rect rect:list2){
			list3.add(seqNoMat_map.get(rect));
		}
		
		StringBuffer sb = new StringBuffer();
		
		for(Mat mat:list3){
			beforeOCR(mat,mat,1);
			sb.append(new SeqNoTesseract().ocrWithMat(mat).trim()).append(" ");
		}
		
		System.out.println("SeqNo: " + sb.toString());
		
		Rect rect = list2.get(1);
		
		double card_W = (double)rect.width/seqnoBlock_W_Ratio;
		double card_H = card_W * cardW_H_Ratio;
		
		int idNo_x = (int) (card_W * idnoBlock_X_Ratio);
		int idNo_y = (int) (card_H * idnoBlock_Y_Ratio);
		int idNo_w = (int) (card_W * idnoBlock_W_Ratio);
		int idNo_h = (int) (card_H * idnoBlock_H_Ratio);
		
//		System.out.println(String.format("%d/%d %d/%d/%d/%d", (int)card_W, (int)card_H, idNo_x,idNo_y,idNo_w,idNo_h));
		
		Mat cmat = cutMat(srcMat, new Rect(idNo_x, idNo_y, idNo_w, idNo_h));
		Highgui.imwrite(String.format(testImageFilePath, tag, count, "testuse-opencv-cutMat.png"), cmat);
		count++;
		
		beforeOCR(cmat,cmat,1);
		
		String idNo = new IdNoTesseract().ocrWithMat(cmat).trim();
		System.out.println("idNo: " + idNo.trim());
		
		int name_x = (int) (card_W * nameBlock_X_Ratio);
		int name_y = (int) (card_H * nameBlock_Y_Ratio);
		int name_w = (int) (card_W * nameBlock_W_Ratio);
		int name_h = (int) (card_H * nameBlock_H_Ratio);
		
		Mat name_mat = cutMat(srcMat, new Rect(name_x, name_y, name_w, name_h));
		Highgui.imwrite(String.format(testImageFilePath, tag, count, "testuse-opencv-cutMat.png"), name_mat);
		count++;
		
		beforeOCR(name_mat,name_mat,1);
		String name = new PingYinNameTesseract().ocrWithMat(name_mat);
		System.out.println("name: " + name.trim());
		
		int data_x = (int) (card_W * birthDayBlock_X_Ratio);
		int data_y = (int) (card_H * birthDayBlock_Y_Ratio);
		int data_w = (int) (card_W * birthDayBlock_W_Ratio);
		int data_h = (int) (card_H * birthDayBlock_H_Ratio);
		
		Mat data_mat = cutMat(srcMat, new Rect(data_x, data_y, data_w, data_h));
		Highgui.imwrite(String.format(testImageFilePath, tag, count, "testuse-opencv-cutMat.png"), data_mat);
		count++;
		
		beforeOCR(data_mat,data_mat,1);
		String data = new BirthDateTesseract().ocrWithMat(data_mat);
		System.out.println("date: " + data.trim());
		
		System.out.println();
		long endTime = System.currentTimeMillis();
		System.err.println("Use time:" + (endTime- startTime));
		System.out.println("Total Handle image count:" + count);

	}
	
	public static void case4_modal(Mat srcMat){
		
		long startTime = System.currentTimeMillis();

		Mat srcImg2 = new Mat();
		srcMat.copyTo(srcImg2);

		//TODO
		
		long endTime = System.currentTimeMillis();
		System.err.println("Use time:" + (endTime- startTime));
		System.out.println("Total Handle image count:" + count);
		
	}
	
	public static void case5(Mat srcMat){
		
		long startTime = System.currentTimeMillis();

		Mat srcImg2 = new Mat();
		srcMat.copyTo(srcImg2);
		Mat destImg = new Mat();
		
		rGB2GRAY(srcMat, destImg);
		
		adaptiveThreshold(destImg, destImg, 25, 10);
		
		erode(destImg, destImg, new Size(3, 3), new Point(1,0) , 2);
		dilate(destImg, destImg, new Size(3, 3), new Point(1,0) , 2);
//		dilate(destImg, destImg, new Size(3, 1), new Point(1,0) , 8);
//		dilate(destImg, destImg, new Size(3, 1), new Point(1,0) , 8);
		
		String result = new AllMatchTesseract().ocrWithMat(destImg);
		System.out.println(result);
		
		long endTime = System.currentTimeMillis();
		System.err.println("Use time:" + (endTime- startTime));
		System.out.println("Total Handle image count:" + count);
		
	}
	
	public static void case6(Mat srcMat){
		
		long startTime = System.currentTimeMillis();

		Mat srcImg2 = new Mat();
		srcMat.copyTo(srcImg2);
		Mat destImg = new Mat();
		
		rGB2GRAY(srcMat, destImg);
		adaptiveThreshold(destImg, destImg, 25, 10);
		
		int iterations = srcMat.width()/1000;
		
		if(iterations!=0){
			erode(destImg, destImg, new Size(3, 3), new Point(1,0) , iterations);
			dilate(destImg, destImg, new Size(3, 3), new Point(1,0) , iterations);
		}

		openOperation(destImg, destImg, new Size(3,1) , new Point(1,0), (iterations+1)*3);
		openOperation(destImg, destImg, new Size(3,1) , new Point(1,0), (iterations+1)*3);
		
		double area = srcMat.height() * srcMat.width();
		System.out.println(area);

		List<MatOfPoint> contours = new ArrayList<>();
		findContours(destImg, srcImg2, contours);
		
		Map<Rect,Mat> possibleMatMap = new HashMap<>();
		
		for (MatOfPoint mop : contours) {
			double contourarea = Imgproc.contourArea(mop);
			if (contourarea / area   >= 0.002) {
				System.out.println(contourarea);
				// System.out.println(mop.cols() + " " + mop.rows());
//				for (Point p : mop.toArray()) {
//					System.out.println(p.x + " " + p.y);
//				}
				Rect rect = boundingRect(mop);
			
				double ratio = (double)rect.height/ (double)rect.width;
				if(ratio>0.7 || ratio<0.05){
					continue;
				}
				
				Mat cmat = cutMat(srcMat, rect);
				Highgui.imwrite(String.format(testImageFilePath, tag, count, "testuse-opencv-cutMat.png"), cmat);
				count++;
					
				possibleMatMap.put(rect, cmat);
				System.out.println(rect.x + " " + rect.y + "|" + rect.height + " " + rect.width);

			}
		}
		
		List<Rect> nArea = new ArrayList<>();
		List<Rect> sArea = new ArrayList<>();
		List<Rect> dArea = new ArrayList<>();
		List<Rect> iArea = new ArrayList<>();
		
		int lastY = 0;
		int Y_maxE = 3;
		double radio_maxE = 0.1;
		int halfW = srcMat.width()/2;
		int halfH = srcMat.height()/2;
		int one_sixH = srcMat.height()/6;
		int one_fourH = srcMat.height()/4;
		int one_fourW = srcMat.width()/4;
		
		for(Rect r:possibleMatMap.keySet()){
			
			if(new Rect(0, one_sixH, halfW, one_sixH).contains(new Point(r.x, r.y))){
				nArea.add(r);
			}
			
			if(new Rect(one_fourW, one_fourH, halfW, one_fourH).contains(new Point(r.x, r.y))){
				sArea.add(r);
			}
			
			if(new Rect(one_fourW, halfH, one_fourW, one_fourH).contains(new Point(r.x, r.y))){
				dArea.add(r);
			}
			
			if(new Rect(halfW, halfH+one_fourH, halfW, one_fourH).contains(new Point(r.x, r.y))){
				iArea.add(r);
			}
			
		}
		
		System.out.println(String.format("%d/%d/%d/%d", nArea.size(),sArea.size(),dArea.size(),iArea.size()));

		//Handle HKID area
		String result = null; 
		if(iArea.size()==0){
			System.out.println("Not found form HKID area");
		}else if(iArea.size()==1){
			System.out.println("Only one. OCR for the Mat");
			Mat mat = possibleMatMap.get(iArea.get(0));
			Mat newMat = new Mat();
			beforeOCR(mat, newMat, iterations);
			result = new IdNoTesseract().ocrWithMat(newMat);
		}else{
			Collections.sort(iArea, new Comparator<Rect>() {
				@Override
				public int compare(Rect o1, Rect o2) {
					double o1_radio = (double)o1.height / (double) o1.width;
					double o2_radio = (double)o2.height / (double) o2.width;
					return Math.abs((0.2D - o1_radio)) < Math.abs((0.2D - o2_radio))?-1:1;
				}
			});
			System.out.println("Select best match. OCR for the Mat");
			for(Rect r:iArea){
				Mat mat = possibleMatMap.get(r);
				Mat newMat = new Mat();
				beforeOCR(mat, newMat, iterations);
				result = new IdNoTesseract().ocrWithMat(newMat);
				if(result!=null&&result.trim()!=""){
					break;
				}
			}
		}
		System.out.println("HKID:" + result.trim());
	
		
		//Handle Date area
		String date_result = null; 
		if(dArea.size()==0){
			System.out.println("Not found form HKID area");
		}else if(dArea.size()==1){
			System.out.println("Only one. OCR for the Mat");
			Mat mat = possibleMatMap.get(dArea.get(0));
			Mat newMat = new Mat();
			beforeOCR(mat, newMat, iterations);
			date_result = new BirthDateTesseract().ocrWithMat(newMat);
		}else{
			Collections.sort(dArea, new Comparator<Rect>() {
				@Override
				public int compare(Rect o1, Rect o2) {
					double o1_radio = (double)o1.height / (double) o1.width;
					double o2_radio = (double)o2.height / (double) o2.width;
					return Math.abs((0.13333D - o1_radio)) < Math.abs((0.13333D - o2_radio))?-1:1;
				}
			});
			System.out.println("Select best match. OCR for the Mat");
			for(Rect r:dArea){
				Mat mat = possibleMatMap.get(r);
				Mat newMat = new Mat();
				beforeOCR(mat, newMat, iterations);
				date_result = new BirthDateTesseract().ocrWithMat(newMat);
				if(result!=null&&result.trim()!=""){
					break;
				}
			}
		}
		System.out.println("BirthDay:" + date_result.trim());
		
		//Handle seq area
		String seq_result = null; 
		if(sArea.size()==0){
			System.out.println("Not found form HKID area");
		}else if(sArea.size()==1){
			System.out.println("Only one. OCR for the Mat");
			Mat mat = possibleMatMap.get(sArea.get(0));
			Mat newMat = new Mat();
			beforeOCR(mat, newMat, iterations);
			seq_result = new BirthDateTesseract().ocrWithMat(newMat);
		}else{
			Collections.sort(sArea, new Comparator<Rect>() {
				@Override
				public int compare(Rect o1, Rect o2) {
//					double o1_radio = (double)o1.height / (double) o1.width;
//					double o2_radio = (double)o2.height / (double) o2.width;
//					return Math.abs((0.13333D - o1_radio)) < Math.abs((0.13333D - o2_radio))?-1:1;
					return o1.x-o2.x;
				}
			});
			System.out.println("Select best match. OCR for the Mat");
			for(Rect r:sArea){
				Mat mat = possibleMatMap.get(r);
				Mat newMat = new Mat();
				beforeOCR(mat, newMat, iterations);
				seq_result = new SeqNoTesseract().ocrWithMat(newMat);
				System.out.println(seq_result.trim());
//				if(result!=null&&result.trim()!=""){
//					break;
//				}
			}
		}
		System.out.println("Seq:" + seq_result.trim());
		
		String name_result = null; 
		if(nArea.size()==0){
			System.out.println("Not found form HKID area");
		}else if(nArea.size()==1){
			System.out.println("Only one. OCR for the Mat");
			Mat mat = possibleMatMap.get(nArea.get(0));
			Mat newMat = new Mat();
			beforeOCR(mat, newMat, iterations);
			name_result = new BirthDateTesseract().ocrWithMat(newMat);
		}else{
			Collections.sort(nArea, new Comparator<Rect>() {
				@Override
				public int compare(Rect o1, Rect o2) {
//					double o1_radio = (double)o1.height / (double) o1.width;
//					double o2_radio = (double)o2.height / (double) o2.width;
//					return Math.abs((0.13333D - o1_radio)) < Math.abs((0.13333D - o2_radio))?-1:1;
					return o1.x-o2.x;
				}
			});
			System.out.println("Select best match. OCR for the Mat");
			for(Rect r:nArea){
				Mat mat = possibleMatMap.get(r);
				Mat newMat = new Mat();
				beforeOCR(mat, newMat, iterations);
				name_result = new PingYinNameTesseract().ocrWithMat(newMat);
				System.out.println(name_result.trim());
//				if(result!=null&&result.trim()!=""){
//					break;
//				}
			}
		}
		System.out.println("Name:" + name_result.trim());
		
		
		
//		for (int i = 0; i < list.size(); i++) {
//			
//			Rect r = list.get(i);
//			
//			double radio = (double)r.height/ (double)r.width;  
//			System.out.println("radio=" + radio);
//			
//			if(r.x>halfW && r.y>halfH){
//				if(radio>idRatio-radio_maxE && radio<idRatio+radio_maxE){
//					idMat_list.add(possibleMatMap.get(r));
//				}
//			}else{
//				
//				
//				if(lastY == 0)
//					lastY = r.y;
//				else{
//					int currentY = r.y;
//					if(Math.abs(lastY-currentY)<Y_maxE){
//						seqNoMat_map.put(r,possibleMatMap.get(r));
//						seqNoMat_map.put(list.get(i-1),possibleMatMap.get(list.get(i-1)));
//					}
//					lastY = currentY;
//				}
//				
//			}
//		}
//		
//		System.out.println(nameMat_list.size()+"/"+seqNoMat_map.size()+"/" +idMat_list.size());
//		
//		List<Rect> list2 = new ArrayList<>();
//		List<Mat> list3 = new ArrayList<>();
//		
//		for(Rect rect:seqNoMat_map.keySet()){
//			list2.add(rect);
//		}
//		
//		Collections.sort(list2, new Comparator<Rect>() {
//			@Override
//			public int compare(Rect o1, Rect o2) {
//				return o1.x-o2.x;
//			}
//		});
//		
//		for(Rect rect:list2){
//			list3.add(seqNoMat_map.get(rect));
//		}
//		
//		StringBuffer sb = new StringBuffer();
//		
//		for(Mat mat:list3){
//			beforeOCR(mat,mat);
//			sb.append(SeqNoTesseract.digitalOCRwithMat(mat).trim()).append(" ");
//		}
//		
//		System.out.println("SeqNo: " + sb.toString());
		
		long endTime = System.currentTimeMillis();
		System.err.println("Use time:" + (endTime- startTime));
		System.out.println("Total Handle image count:" + count);
		
	}
	
	public static void case7(Mat srcMat){
		
		long startTime = System.currentTimeMillis();
		Mat srcImg2 = new Mat();
		Mat srcImg3 = new Mat();
		Mat srcImg4 = new Mat();
		Mat srcImg5 = new Mat();
		Mat srcImg6 = new Mat();
		
		srcMat.copyTo(srcImg2);
		srcMat.copyTo(srcImg3);
		srcMat.copyTo(srcImg4);

	    Mat destMat = new Mat();
	    Mat destMat2 = new Mat();
	    Mat destMat3 = new Mat();
	    
	    medianBlur(srcMat, destMat, 9);
	    
	    
//	    cvtColor(destMat, destMat, Imgproc.COLOR_RGB2GRAY, 4);
	    rGB2GRAY(destMat, destMat);
	    
//	    adjustColor(destMat, destMat, 0.8, 1.2);
	    
//	    Imgproc.cvtColor(srcMat, destMat, Imgproc.COLOR_RGB2GRAY, 4);
//	    canny(destMat, destMat, 80, 100);
	    
//	    System.out.println("Canny Start!");
//	    Mat cannyMat = new Mat();
//	    canny(destMat, cannyMat, 10, 20, 3, false);
//	    System.out.println("Canny End!");
	    
	   
//		Highgui.imwrite(String.format(testImageFilePath, tag, count, "testuse-opencv-src.png"),cannyMat);
//		count++;
	    
	    adaptiveThreshold(destMat, destMat, 25, 5);
	    
	    erode(destMat, destMat, new Size(3,3), new Point(1,1), 1);
	    dilate(destMat, destMat, new Size(3,3),  new Point(1,1), 1);
	    
//	    openOperation(destMat, destMat, new Size(3,3), new Point(1,1), 1);
	    
//	    List<MatOfPoint> contours = new ArrayList<>(); 
//	    findContours(destMat, srcImg2, contours, Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE);
////	    findContours(destMat, srcImg3, contours, Imgproc.RETR_CCOMP, Imgproc.CHAIN_APPROX_SIMPLE);
//	    findContours(destMat, srcImg4, contours, Imgproc.RETR_LIST, Imgproc.CHAIN_APPROX_SIMPLE);
////	    findContours(destMat, srcImg5, contours, Imgproc.RETR_TREE, Imgproc.CHAIN_APPROX_SIMPLE);
////	    findContours(destMat, srcImg6, contours, Imgproc.RETR_FLOODFILL, Imgproc.CHAIN_APPROX_SIMPLE);
//	    
//		Highgui.imwrite(String.format(testImageFilePath, tag, count, "testuse-opencv-src.png"),srcImg2);
//		count++;
//		Highgui.imwrite(String.format(testImageFilePath, tag, count, "testuse-opencv-src.png"),srcImg4);
//		count++;

		
	    
	    
	    Mat lines = new Mat();
	    int threshold = 50;
	    int minLineSize = 200;
	    int lineGap = 20;
	    
	    int shortLine = srcMat.width()>srcMat.height()?srcMat.height():srcMat.width();  
	    Imgproc.HoughLinesP(destMat, lines, 1, Math.PI/180, threshold, minLineSize, lineGap);
	    
	    int line_count = 0;

	    List<Double> angles = new ArrayList<>();
	    
	    List<double[]> horizontalLinePointList = new ArrayList<>();
	    List<double[]> verticalLinePointList = new ArrayList<>();
	   
	    Mat newMat = new Mat(srcMat.size(), srcMat.type());
	    
	    double minY_horizontal = srcMat.height();
	    double maxY_horizontal = 0;
	    
	    double minX_vertical = srcMat.width();
	    double maxX_vertical = 0;
	     
	    for (int x = 0; x < lines.cols(); x++) 
	    {
	          double[] vec = lines.get(0, x);
	          double x1 = vec[0], 
	                 y1 = vec[1],
	                 x2 = vec[2],
	                 y2 = vec[3];
	          Point start = new Point(x1, y1);
	          Point end = new Point(x2, y2);
	          if(calculateLineLength(start,end)>(shortLine/5)){
	        	  double angle = calculateLineHorizontalAngle(start, end);
	        	  if(Math.abs(angle)<15){
	        		  System.out.println(angle);
	        		  Core.line(srcImg2, start, end, new Scalar(255,0,0), 5);
//	        		  Core.line(destMat, start, end, new Scalar(255,255,255), 5);
	        		  Core.line(newMat, start, end, new Scalar(255,255,255), 5);
	        		  angles.add(angle);
	        		  line_count++;
	        		  continue;
	        	  }
	        	  angle = Math.abs(angle)-90;
	        	  if(Math.abs(angle)<15){
	        		  System.out.println(angle);
	        		  Core.line(srcImg2, start, end, new Scalar(255,0,0), 5);
//	        		  Core.line(destMat, start, end, new Scalar(255,255,255), 5);
	        		  Core.line(newMat, start, end, new Scalar(255,255,255), 5);
	        		  angles.add(angle);
	        		  line_count++;
	        	  }
	          }
	    }
	    
	    
//	    Collections.sort(horizontalLinePointList, new Comparable<double[]>() {
//			@Override
//			public int compareTo(double[] o) {
//				// TODO Auto-generated method stub
//				return 0;
//			}
//		});
	    
	    
	    
	    System.out.println(line_count);
	    
		Highgui.imwrite(String.format(testImageFilePath, tag, count, "testuse-opencv-src.png"),destMat);
		count++;
	    
		Highgui.imwrite(String.format(testImageFilePath, tag, count, "testuse-opencv-src.png"),newMat);
		count++;

		dilate(destMat, destMat, new Size(3,3), new Point(1,1), 1);
		erode(destMat, destMat, new Size(3,3), new Point(1,1), 1);
		dilate(destMat, destMat, new Size(3,3), new Point(1,1), 1);
		
		System.out.println(destMat.type());
		
	    double averageAngle = calculateAverageWithArrayList(angles);
	    System.out.println(averageAngle);
	    
	    List<MatOfPoint> contours = new ArrayList<>(); 
	    findAndFillContours(destMat, srcImg3, contours, Imgproc.RETR_LIST, Imgproc.CHAIN_APPROX_SIMPLE);
//	    findContours(destMat, srcMat, contours);
	    
		Highgui.imwrite(String.format(testImageFilePath, tag, count, "testuse-opencv-src.png"),srcImg3);
		count++;
	    
	    
	    
	    warpAffine(srcImg2, destMat2, averageAngle, 1);
	    warpAffine(newMat, newMat, averageAngle, 1);
	    
	    
	    
//	    Mat newMat2 = new
	    
	    Mat CV_8SC1_MAT = new Mat(newMat.size(), CvType.CV_8SC1);
	    newMat.copyTo(CV_8SC1_MAT);
	    System.out.println(CV_8SC1_MAT.type());
	    
//	    System.out.println(CV_8SC1_MAT.type());
//	    newMat.convertTo(CV_8SC1_MAT, 
////	    System.out.println(CV_8SC1_MAT.type());
//	    CV
	    
//	    rGB2GRAY(newMat, newMat);
//	    
//	    adaptiveThreshold(newMat, newMat, 25, 10);
//	    
//	    
//	    Mat newMat2 = new Mat(srcMat.size(), srcMat.type());
//	    Mat newMat3 = new Mat(srcMat.size(), srcMat.type());
//	    Mat newMat4 = new Mat(srcMat.size(), srcMat.type());
//	    Mat newMat5 = new Mat(srcMat.size(), srcMat.type());
//	    Mat newMat6 = new Mat(srcMat.size(), srcMat.type());
//	    
//	    
//	    
//	    List<MatOfPoint> contours = new ArrayList<>();
//	    findContours(newMat, newMat2, contours, Imgproc.RETR_CCOMP, Imgproc.CHAIN_APPROX_TC89_L1);
//	    
//	    List<MatOfPoint> contours2 = new ArrayList<>();
//	    findContours(newMat, newMat3, contours2, Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_TC89_L1);
//	    
////	    List<MatOfPoint> contours3 = new ArrayList<>();
////    findContours(newMat, newMat4, contours3, Imgproc.RETR_FLOODFILL, Imgproc.CHAIN_APPROX_TC89_L1);
//	    
//	    List<MatOfPoint> contours4 = new ArrayList<>();
//	    findContours(newMat, newMat5, contours4, Imgproc.RETR_LIST, Imgproc.CHAIN_APPROX_TC89_L1);
//	    
//	    List<MatOfPoint> contours5 = new ArrayList<>();
//	    findContours(newMat, newMat6, contours5, Imgproc.RETR_TREE, Imgproc.CHAIN_APPROX_TC89_L1);
//	    
//		Highgui.imwrite(String.format(testImageFilePath, tag, count, "testuse-opencv-src.png"),newMat2);
//		count++;
//		
//		Highgui.imwrite(String.format(testImageFilePath, tag, count, "testuse-opencv-src.png"),newMat3);
//		count++;
//		
//		Highgui.imwrite(String.format(testImageFilePath, tag, count, "testuse-opencv-src.png"),newMat4);
//		count++;
//		
//		Highgui.imwrite(String.format(testImageFilePath, tag, count, "testuse-opencv-src.png"),newMat5);
//		count++;
//		
//		Highgui.imwrite(String.format(testImageFilePath, tag, count, "testuse-opencv-src.png"),newMat6);
//		count++;
//		
////	    
////		Highgui.imwrite(String.format(testImageFilePath, tag, count, "testuse-opencv-src.png"),destMat3);
////		count++;
//		
		

		
		
		long endTime = System.currentTimeMillis();
		System.err.println("Use time:" + (endTime- startTime));
		System.out.println("Total Handle image count:" + count);
		 

		 
		// Vector lines;
//		 Imgproc.HoughLines(midImg, lines, Math.PI/180.0 , 100, 100);
//		 System.out.println(lines.cols());
//		 Point[] points = new Point[]{ new Point(), new Point() };
//		 for (int x = 0; x < lines.cols(); x++) {
//		 double[] vec = lines.get(0, x);
//		 points[0].x = vec[0];
//		 points[0].y = vec[1];
//		 points[1].x = vec[2];
//		 points[1].y = vec[3];
//		 System.out.println(vec[0]+" "+vec[1]+" "+vec[2]+" "+ vec[3]);
//		 }
//
//		 HoughLinesP(midImage, lines, 1, CV_PI/180, 80, 50, 10 );
//		 Highgui.imwrite("D:\\project desktop\\16-11-11
//		 TagAndGo\\testuse-opencv-3.png", lines);
//		 HoughLines(midImage, lines, 1, CV_PI/180, 150, 0, 0 );
//		
//		 Mat m = Mat.eye(3, 3, CvType.CV_8UC1);
//		 System.out.println("m = " + m.dump());

		
	}
	
	public static void case8(Mat srcMat){
		
		long startTime = System.currentTimeMillis();

		Mat srcImg2 = new Mat();
		Mat srcImg3 = new Mat();
		srcMat.copyTo(srcImg2);
		srcMat.copyTo(srcImg3);
		
		Mat destMat = new Mat();
		Mat blurred = new Mat();
		Mat blurred2 = new Mat();

		medianBlur(srcMat, blurred, 9);
		medianBlur(srcMat, blurred2, 5);
		
		List<MatOfPoint> list = EdgeDetection.getDetectedSquareList(srcImg2, blurred);
//		List<MatOfPoint> list2 = EdgeDetection.getDetectedSquareList(srcImg3, blurred2);
		
		System.out.println(list.size());
//		System.out.println(list2.size());
		
		Mat hierarchy = new Mat(new Size(srcMat.width(), srcMat.height()), CvType.CV_8UC1);
		// find contours
//		Imgproc.findContours(srcM, contours, hierarchy, retrType, method);
		// if any contour exist...
		if (hierarchy.size().height > 0 && hierarchy.size().width > 0) {
			// for each contour, display it in blue
			System.out.println("Here");
//			for (int idx = 0; idx >= 0; idx = (int) hierarchy.get(0, idx)[0]) {
//				System.out.println(idx);
				Imgproc.drawContours(srcImg2, list, 0, new Scalar(250, 0, 0));
				Imgproc.drawContours(srcImg2, list, 1, new Scalar(250, 0, 0));
				Imgproc.drawContours(srcImg2, list, 2, new Scalar(250, 0, 0));
				Imgproc.drawContours(srcImg2, list, 3, new Scalar(250, 0, 0));
//			}
		}
		
//		if (hierarchy.size().height > 0 && hierarchy.size().width > 0) {
//			// for each contour, display it in blue
//			for (int idx = 0; idx >= 0; idx = (int) hierarchy.get(0, idx)[0]) {
//				Imgproc.drawContours(srcImg3, list2, idx, new Scalar(250, 0, 0));
//			}
//		}
		
//		Highgui.imwrite(String.format(testImageFilePath, tag, count, "testuse-opencv-drawMat1.png"), srcImg2);
//		count++;
		
		Highgui.imwrite(String.format(testImageFilePath, tag, count, "testuse-opencv-drawMat2.png"), srcImg2);
		count++;
		
		
		
		long endTime = System.currentTimeMillis();
		System.err.println("Use time:" + (endTime- startTime));
		System.out.println("Total Handle image count:" + count);
		
	}
	
	public static void case9(Mat srcMat){
		
		long startTime = System.currentTimeMillis();

		Mat srcImg2 = new Mat();
		srcMat.copyTo(srcImg2);
		
		Mat grayMat = new Mat();
		cvtColor(srcMat, grayMat, Imgproc.COLOR_RGB2GRAY, 4);
		
		Mat cannyMat = new Mat();
		canny(grayMat, cannyMat, 20, 60, 3, false);
		
		Mat thresholdMat = new Mat();
		adaptiveThreshold(grayMat, thresholdMat, 25, 5);
		
		thresholdMat.copyTo(cannyMat);
	    Highgui.imwrite(String.format(testImageFilePath, tag, count, "testuse-opencv-new_image.png"), cannyMat);
	    count++;
	    
	    

		

		//TODO
		
		long endTime = System.currentTimeMillis();
		System.err.println("Use time:" + (endTime- startTime));
		System.out.println("Total Handle image count:" + count);
		
	}
	
	
	public static void case10(Mat srcMat){
		
		long startTime = System.currentTimeMillis();

		Mat srcImg2 = new Mat();
		srcMat.copyTo(srcImg2);
		
	    Mat destMat = new Mat();
	    Mat destMat2 = new Mat();
	    Mat destMat3 = new Mat();
	    
	    int srcW = srcMat.width();
	    int srcH = srcMat.height();

	    int longline = srcW>srcH?srcW:srcH;
	    int baiscCount = longline / 1000;
	    
	    int nowW = srcW/baiscCount;
	    int nowH = srcH/baiscCount;
	    
	    reSize(srcMat, destMat, new Size(nowW,nowH));
	    destMat.copyTo(destMat3);
	    

		MatOfPoint mat = EdgeDetection.detectEdge(destMat);
		
//		    Imgproc.drawContours(new_image, contours, maxAreaIdx, new Scalar(255, 255, 255), 1); //will draw the largest square/rectangle

		    double temp_double[] = mat.get(0, 0);
		    Point p1 = new Point(temp_double[0], temp_double[1]);
		    Core.circle(destMat3, new Point(p1.x, p1.y), 20, new Scalar(255, 0, 0), 5); //p1 is colored red
		    String temp_string = "Point 1: (" + p1.x + ", " + p1.y + ")";

		    temp_double = mat.get(1, 0);
		    Point p2 = new Point(temp_double[0], temp_double[1]);
		    Core.circle(destMat3, new Point(p2.x, p2.y), 20, new Scalar(0, 255, 0), 5); //p2 is colored green
		    temp_string += "\nPoint 2: (" + p2.x + ", " + p2.y + ")";

		    temp_double = mat.get(2, 0);       
		    Point p3 = new Point(temp_double[0], temp_double[1]);
		    Core.circle(destMat3, new Point(p3.x, p3.y), 20, new Scalar(0, 0, 255), 5); //p3 is colored blue
		    temp_string += "\nPoint 3: (" + p3.x + ", " + p3.y + ")";

		    temp_double = mat.get(3, 0);
		    Point p4 = new Point(temp_double[0], temp_double[1]);
		    Core.circle(destMat3, new Point(p4.x, p4.y), 20, new Scalar(0, 255, 255), 5); //p1 is colored violet
		    temp_string += "\nPoint 4: (" + p4.x + ", " + p4.y + ")";

		    Highgui.imwrite(String.format(testImageFilePath, tag, count, "testuse-opencv-new_image.png"), destMat3);
		    count++;


		    System.out.println(temp_string);
		    
		//TODO
		
		long endTime = System.currentTimeMillis();
		System.err.println("Use time:" + (endTime- startTime));
		System.out.println("Total Handle image count:" + count);
		
	}
	
	public static void case11(Mat srcMat){
		
		long startTime = System.currentTimeMillis();
		Mat srcImg2 = new Mat();
		Mat srcImg3 = new Mat();
		Mat srcImg4 = new Mat();
		
		Mat srcImg5 = new Mat();
		Mat srcImg6 = new Mat();
		
		srcMat.copyTo(srcImg2);
		srcMat.copyTo(srcImg3);
		srcMat.copyTo(srcImg4);

	    Mat destMat = new Mat();
	    Mat destMat2 = new Mat();
	    Mat destMat3 = new Mat();
	    
	    int srcW = srcMat.width();
	    int srcH = srcMat.height();

	    int longline = srcW>srcH?srcW:srcH;
	    int baiscCount = longline / 1000;
	    
	    int nowW = srcW/baiscCount;
	    int nowH = srcH/baiscCount;
	    
	    reSize(srcMat, destMat, new Size(nowW,nowH));
	    
	    destMat.copyTo(srcImg5);
	    
	    medianBlur(destMat, destMat, 5);
	    
//	    gaussianBlur(srcMat, destMat, new Size(3,3), 0, 0, Imgproc.BORDER_DEFAULT);
	    
	    
//	    cvtColor(destMat, destMat, Imgproc.COLOR_RGB2GRAY, 4);
	    rGB2GRAY(destMat, destMat);
	    

		
//		sobel(destMat, destMat, 0, 0, 1);
	    
//	    adjustColor(destMat, destMat, 0.8, 1.2);
	    
//	    Imgproc.cvtColor(srcMat, destMat, Imgproc.COLOR_RGB2GRAY, 4);
//	    canny(destMat, destMat, 80, 100);
	    Mat cannyMat = new Mat();
	    System.out.println("Canny Start!");
	    canny(destMat, cannyMat, 10, 30, 3, false);
//		int thresh = Otsu(destMat);
//		canny(destMat, destMat, 20, 40);
	    System.out.println("Canny End!");
	    
	    Mat adaptMat = new Mat();
//	    adaptiveThreshold(destMat, adaptMat, 21, 3);
	    adaptiveThreshold(destMat, adaptMat, 25, 4);
	    
//	    erode(adaptMat, adaptMat, new Size(3,3), new Point(1,1), 1);
//	    dilate(adaptMat, adaptMat, new Size(3,3),  new Point(1,1), 1);
//	    erode(adaptMat, adaptMat, new Size(3,3), new Point(1,1), 1);
	    
	    adaptMat = combineThresholdMat(cannyMat, adaptMat);
	    
//	    dilate(adaptMat, adaptMat, new Size(3,3),  new Point(1,1), 1);

//	    
//	    adaptMat = adaptMat.cross(cannyMat);
//	    cannyMat.copyTo(adaptMat);
	    
		Highgui.imwrite(String.format(testImageFilePath, tag, count, "hebing.png"),adaptMat);
		count++;
	    
	    
	    System.out.println("FindContours Start!");
	    List<MatOfPoint> contours = new ArrayList<>();
	    findContours(adaptMat, srcImg5, contours, Imgproc.RETR_LIST, Imgproc.CHAIN_APPROX_SIMPLE);
//	    
	    
	    System.out.println("FindContours End!");
	    
		Highgui.imwrite(String.format(testImageFilePath, tag, count, "testuse-opencv-src.png"),srcImg5);
		count++;
		
		double area = nowW * nowH;
				
		MatOfPoint maxCointour = null;
		double maxContourarea = 0;
		int maxIdx = 0;
		boolean flag = false;
	    for (int i = 0; i < contours.size(); i++) {
	    	MatOfPoint mop = contours.get(i);
	    	double contourarea = Imgproc.contourArea(mop);
	    	if(i==0){
	    		maxContourarea = contourarea;
	    	}
	    	if (contourarea / area   >= 0.05) {
	    		
	    		
	    		if(contourarea>=maxContourarea){
	    			maxContourarea = contourarea;
	    			maxIdx = i;
	    			flag = true;
	    		}
	    		
	    	}
		}
	    
	    
	    
	    if(flag){
	    	System.out.println("Find!!");
	    	maxCointour = contours.get(maxIdx);
	    	System.out.println("Area:" + Imgproc.contourArea(maxCointour));
	    	
            MatOfPoint2f approx = new MatOfPoint2f();
            MatOfPoint2f mMOP2f1 = new MatOfPoint2f();
            MatOfPoint mMOP = new MatOfPoint();
	    	
	    	maxCointour.convertTo(mMOP2f1, CvType.CV_32FC2);
        	Imgproc.approxPolyDP(mMOP2f1, approx, Imgproc.arcLength(mMOP2f1, true)*0.03, true);
        	
        	System.out.println("approx.rows(): " + approx.rows());
        	
        	approx.convertTo(mMOP, CvType.CV_32S);
	    	List<MatOfPoint> list = new ArrayList<>();
	    	list.add(mMOP);
	    	
	    	Imgproc.drawContours(srcImg5, list, 0, new Scalar(250, 0, 0));
			Highgui.imwrite(String.format(testImageFilePath, tag, count, "testuse-opencv-src.png"),srcImg5);
			count++;
        	
        	System.out.println("mMOP.rows(): " + mMOP.rows());
	    	
	    	
	    	Point[] points = mMOP.toArray();
	    	for (int i = 0; i < points.length; i++) {
				points[i].x *=4;
				points[i].y *=4;
			}
	    	MatOfPoint realContours = new MatOfPoint(points);
	    	list = new ArrayList<>();
	    	list.add(realContours);
	    	
	    	
	    	Imgproc.drawContours(srcImg2, list, 0, new Scalar(250, 0, 0));
			Highgui.imwrite(String.format(testImageFilePath, tag, count, "testuse-opencv-src.png"),srcImg2);
			count++;
	    	
	    	Quadrangle q = Quadrangle.fromContour(realContours);
	    	Mat resultMat = q.warp(srcImg3);
			Highgui.imwrite(String.format(testImageFilePath, tag, count, "testuse-opencv-src.png"),resultMat);
			count++;

	    }else{
	    	System.out.println("Not found any vail contour ");
	    }
		
	    
	    
	    
	    
//	    erode(destMat, destMat, new Size(1,1), new Point(1,1), 2);
//	    dilate(destMat, destMat, new Size(1,1),  new Point(1,1), 1);
//	    erode(destMat, destMat, new Size(1,1), new Point(1,1), 2);
//	    openOperation(destMat, destMat, new Size(3,3), new Point(1,1), 1);
	    
//	    List<MatOfPoint> contours = new ArrayList<>(); 
//	    findContours(destMat, srcImg2, contours, Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE);
////	    findContours(destMat, srcImg3, contours, Imgproc.RETR_CCOMP, Imgproc.CHAIN_APPROX_SIMPLE);
//	    findContours(destMat, srcImg4, contours, Imgproc.RETR_LIST, Imgproc.CHAIN_APPROX_SIMPLE);
////	    findContours(destMat, srcImg5, contours, Imgproc.RETR_TREE, Imgproc.CHAIN_APPROX_SIMPLE);
////	    findContours(destMat, srcImg6, contours, Imgproc.RETR_FLOODFILL, Imgproc.CHAIN_APPROX_SIMPLE);
//	    
//		Highgui.imwrite(String.format(testImageFilePath, tag, count, "testuse-opencv-src.png"),srcImg2);
//		count++;
//		Highgui.imwrite(String.format(testImageFilePath, tag, count, "testuse-opencv-src.png"),srcImg4);
//		count++;

		
	    
	    
//	    Mat lines = new Mat();
//	    int threshold = 50;
//	    int minLineSize = 200;
//	    int lineGap = 20;
//	    
//	    int shortLine = srcMat.width()>srcMat.height()?srcMat.height():srcMat.width();  
//	    Imgproc.HoughLinesP(destMat, lines, 1, Math.PI/180, threshold, minLineSize, lineGap);
	    
		
//		System.out.println(destMat.type());
//		
//	    double averageAngle = calculateAverageWithArrayList(angles);
//	    System.out.println(averageAngle);
//	    
//	    List<MatOfPoint> contours = new ArrayList<>(); 
//	    findAndFillContours(destMat, srcImg3, contours, Imgproc.RETR_LIST, Imgproc.CHAIN_APPROX_SIMPLE);
////	    findContours(destMat, srcMat, contours);
//	    
//		Highgui.imwrite(String.format(testImageFilePath, tag, count, "testuse-opencv-src.png"),srcImg3);
//		count++;
//	    
//	    
//	    
//	    warpAffine(srcImg2, destMat2, averageAngle, 1);
//	    warpAffine(newMat, newMat, averageAngle, 1);
//	    
//	    
//	    
////	    Mat newMat2 = new
//	    
//	    Mat CV_8SC1_MAT = new Mat(newMat.size(), CvType.CV_8SC1);
//	    newMat.copyTo(CV_8SC1_MAT);
//	    System.out.println(CV_8SC1_MAT.type());
	    

		

		
		
		long endTime = System.currentTimeMillis();
		System.err.println("Use time:" + (endTime- startTime));
		System.out.println("Total Handle image count:" + count);
		 

	}

	public static void warpAffine(Mat srcMat, Mat destMat, double angle, double scale){
		
		Mat rotateMat = Imgproc.getRotationMatrix2D(new Point(srcMat.width()/2, srcMat.height()/2), angle, scale);
		Imgproc.warpAffine(srcMat, destMat, rotateMat, srcMat.size());
		Highgui.imwrite(String.format(testImageFilePath, tag, count, "testuse-opencv-warpAffine.png"), destMat);
		count++;
		
	}
	
	public static Mat combineThresholdMat(Mat srcMat1,Mat srcMat2){
		
		Mat destMat = new Mat();
		Core.add(srcMat1, srcMat2, destMat);
		Highgui.imwrite(String.format(testImageFilePath, tag, count, "testuse-opencv-combineThresholdMat.png"), destMat);
		count++;
		return destMat;
		
	}
	
	public static double calculateLineLength(Point p1,Point p2){
		return Math.sqrt(Math.abs(p1.x-p2.x)*Math.abs(p1.x-p2.x)+Math.abs(p1.y-p2.y)*Math.abs(p1.y-p2.y));
	}
	
	public static double calculateLineHorizontalAngle(Point p1,Point p2){
		double angle = Math.atan((Math.abs(p1.y-p2.y)/Math.abs(p1.x-p2.x))) / Math.PI*180;
		if(p1.y-p2.y>0){
			angle = angle * -1;
		}
		return angle;
	}
	
	public static double calculateAverageWithArrayList(List<Double> list){
		if(list==null||list.size()==0){
			return 0;
		}
		double sum = 0;
		for(double d : list){
			sum += d;
		}
		return sum/list.size();
	}

	public static void openOperation(Mat srcMat,Mat destMat, Size size, Point point, int iterations){
		
		dilate(srcMat, destMat, size, point, iterations);
		erode(destMat, destMat, size, point, iterations/2+1);
		dilate(destMat, destMat, size, point, iterations);
		
	}
	
	public static void closeOperation(Mat srcMat,Mat destMat, Size size, Point point, int iterations){
		
		erode(srcMat, destMat, size, point, iterations);
		dilate(destMat, destMat, size, point, iterations/2+1);
		erode(destMat, destMat, size, point, iterations);
		
	}
	
	public static void beforeOCR(Mat scrMat, Mat destMat, int iterations){
		
		adjustColor(scrMat, destMat, 1.5, 1.5);
		rGB2GRAY(destMat, destMat);
//		int thresh = Otsu(destMat);
//		int thresh = 60;
//		System.out.println(thresh);
		int blockSize = 25;
		int constValue = 10;
		adaptiveThreshold(destMat, destMat,blockSize,constValue);
		if(iterations!=0){
			erode(destMat, destMat, new Size(3,3) ,new Point(-1, -1), iterations);
			dilate(destMat, destMat, new Size(3,3) ,new Point(-1, -1), iterations);
		}
	}
	

	private Mat doCanny(Mat frame) {
		// init
		Mat grayImage = new Mat();
		Mat detectedEdges = new Mat();
		// convert to grayscale
		Imgproc.cvtColor(frame, grayImage, Imgproc.COLOR_BGR2GRAY);
		// reduce noise with a 3x3 kernel
		Imgproc.blur(grayImage, detectedEdges, new Size(3, 3));
		// canny detector, with ratio of lower:upper threshold of 3:1
		// Imgproc.Canny(detectedEdges, detectedEdges,
		// this.threshold.getValue(), this.threshold.getValue() * 3);
		// using Canny's output as a mask, display the result
		Mat dest = new Mat();
		frame.copyTo(dest, detectedEdges);
		return dest;
	}

	public static void rGB2GRAY(Mat srcM, Mat destM) {
		try{
			Imgproc.cvtColor(srcM, destM, Imgproc.COLOR_RGB2GRAY);
		}catch (Exception e){
			e.printStackTrace();
			System.out.println("Exception Num:" + count);
			Highgui.imwrite(String.format(testImageFilePath, tag, count, "testuse-opencv-exception.png"), srcM);
		}
		Highgui.imwrite(String.format(testImageFilePath, tag, count, "testuse-opencv-gray.png"), destM);
		count++;
	}

	public static void bGR2HSV(Mat srcM, Mat destM) {
		Imgproc.cvtColor(srcM, destM, Imgproc.COLOR_BGR2HSV);
		Highgui.imwrite(String.format(testImageFilePath, tag, count, "testuse-opencv-HSV.png"), destM);
		count++;
	}

	public static void cvtColor(Mat srcM, Mat destM, int colorType) {
		Imgproc.cvtColor(srcM, destM, colorType);
		Highgui.imwrite(String.format(testImageFilePath, tag, count, "testuse-opencv-defindColor.png"), destM);
		count++;
	}
	
	public static void cvtColor(Mat srcM, Mat destM, int colorType,int dstCn) {
		Imgproc.cvtColor(srcM, destM, colorType, dstCn);
		Highgui.imwrite(String.format(testImageFilePath, tag, count, "testuse-opencv-defindColor.png"), destM);
		count++;
	}

	public static void adjustColor(Mat srcM, Mat destM, double alpha, double beat) {
		srcM.convertTo(destM, -1, alpha, beat);
		Highgui.imwrite(String.format(testImageFilePath, tag, count, "testuse-opencv-adjustColor.png"), destM);
		count++;
	}

	public static void sobel(Mat srcM, Mat destM, int ddepth, int dx, int dy) {
		Imgproc.Sobel(srcM, destM, ddepth, dx, dy);
		Highgui.imwrite(String.format(testImageFilePath, tag, count, "testuse-opencv-sobel.png"), destM);
		count++;
	}

	public static void threshold(Mat srcM, Mat destM) {
		Imgproc.threshold(srcM, destM, 0, 255, Imgproc.THRESH_BINARY | Imgproc.THRESH_OTSU);
		Highgui.imwrite(String.format(testImageFilePath, tag, count, "testuse-opencv-binary.png"), destM);
		count++;
	}
	
	public static void adaptiveThreshold(Mat srcM, Mat destM, int blockSize, int constValue) {
		Imgproc.adaptiveThreshold(srcM, destM, 255, Imgproc.ADAPTIVE_THRESH_MEAN_C, Imgproc.THRESH_BINARY_INV, blockSize, constValue);
		Highgui.imwrite(String.format(testImageFilePath, tag, count, "testuse-opencv-adaptiveThreshold.png"),destM);
		count++;
	}

	public static void threshold(Mat srcM, Mat destM, int thresh) {
		Imgproc.threshold(srcM, destM, thresh, 255, Imgproc.THRESH_BINARY);
		Highgui.imwrite(String.format(testImageFilePath, tag, count, "testuse-opencv-binary.png"),destM);
		count++;
	}

	public static void dilate(Mat srcM, Mat destM, Size size, Point point, int iterations ) {
		Mat kernel = Imgproc.getStructuringElement(Imgproc.CV_SHAPE_RECT, size, point);
		Point point2 = new Point(-1, -1);
		Imgproc.dilate(srcM, destM, kernel, point2, iterations );
		Highgui.imwrite(String.format(testImageFilePath, tag, count, "testuse-opencv-dilate.png"), destM);
		count++;
	}

	public static void erode(Mat srcM, Mat destM, Size size, Point point, int iterations ) {
		Mat kernel = Imgproc.getStructuringElement(Imgproc.CV_SHAPE_RECT, size, point);
		Point point2 = new Point(-1, -1);
		Imgproc.erode(srcM, destM, kernel, point2, iterations );
		Highgui.imwrite(String.format(testImageFilePath, tag, count, "testuse-opencv-erode.png"), destM);
		count++;
	}

	public static void canny(Mat srcM, Mat destM, double threshold1, double threshold2) {
		Imgproc.Canny(srcM, destM, threshold1, threshold2);
		Highgui.imwrite(String.format(testImageFilePath, tag, count, "testuse-opencv-canny.png"), destM);
		count++;
	}
	
	public static void canny(Mat srcM, Mat destM, double threshold1, double threshold2, int apertureSize, boolean l2gradient) {
		Imgproc.Canny(srcM, destM, threshold1, threshold2, apertureSize, l2gradient);
		Highgui.imwrite(String.format(testImageFilePath, tag, count, "testuse-opencv-canny.png"), destM);
		count++;
	}

	public static void convertABS(Mat srcM, Mat destM) {
		Core.convertScaleAbs(srcM, destM);
		Highgui.imwrite(String.format(testImageFilePath, tag, count, "testuse-opencv-convertABS.png"), destM);
		count++;
	}

	public static void blur(Mat srcM, Mat destM, Size size) {
		Imgproc.blur(srcM, destM, size);
		Highgui.imwrite(String.format(testImageFilePath, tag, count, "testuse-opencv-blur.png"), destM);
		count++;
	}
	
	public static void medianBlur(Mat srcM, Mat destM, int ksize) {
		Imgproc.medianBlur(srcM, destM, ksize);
		Highgui.imwrite(String.format(testImageFilePath, tag, count, "testuse-opencv-medianBlur.png"), destM);
		count++;
	}
	
	public static void gaussianBlur(Mat srcM, Mat destM, Size size, double sigmaX) {
		Imgproc.GaussianBlur(srcM, destM, size,sigmaX);
		Highgui.imwrite(String.format(testImageFilePath, tag, count, "testuse-opencv-GaussianBlur.png"), destM);
		count++;
	}
	
	public static void gaussianBlur(Mat srcM, Mat destM, Size size, double sigmaX, double sigmaY, int borderType) {
		Imgproc.GaussianBlur(srcM, destM, size, sigmaX, sigmaY, borderType);
		Highgui.imwrite(String.format(testImageFilePath, tag, count, "testuse-opencv-GaussianBlur.png"), destM);
		count++;
	}
	
	
	
	public static void approxPolyDP(){
		
//		Imgproc.approxPolyDP(curve, approxCurve, epsilon, closed);
		
	}
	
	public static void findContours(Mat srcM, Mat drawM, List<MatOfPoint> contours,int retrType, int method) {

		// init

		Mat hierarchy = new Mat(new Size(srcM.width(), srcM.height()), CvType.CV_8UC1);
		// find contours
		Imgproc.findContours(srcM, contours, hierarchy, retrType, method);
		// if any contour exist...
		if (hierarchy.size().height > 0 && hierarchy.size().width > 0) {
			// for each contour, display it in blue
			for (int idx = 0; idx >= 0; idx = (int) hierarchy.get(0, idx)[0]) {
				 
				Imgproc.drawContours(drawM, contours, idx, new Scalar(250, 0, 0));
			}
		}

		Highgui.imwrite(
				"D:\\project desktop\\16-11-11 TagAndGo\\" + tag + "\\" + count + "testuse-opencv-drawCountours.png",
				drawM);
		count++;
	}
	
	public static void findAndFillContours(Mat srcM, Mat drawM, List<MatOfPoint> contours,int retrType, int method) {

		// init

		Mat hierarchy = new Mat(new Size(srcM.width(), srcM.height()), CvType.CV_8UC1);
		// find contours
		Imgproc.findContours(srcM, contours, hierarchy, retrType, method);
		// if any contour exist...
		if (hierarchy.size().height > 0 && hierarchy.size().width > 0) {
			// for each contour, display it in blue
			for (int idx = 0; idx >= 0; idx = (int) hierarchy.get(0, idx)[0]) {
				Imgproc.drawContours(drawM, contours, idx, new Scalar(255, 255, 255),2, Core.FILLED,hierarchy ,8, new Point(0, 0));
			}
		}

		Highgui.imwrite(
				"D:\\project desktop\\16-11-11 TagAndGo\\" + tag + "\\" + count + "testuse-opencv-drawCountours.png",
				drawM);
		count++;
	}


	public static void findContours(Mat srcM, Mat drawM, List<MatOfPoint> contours) {

		// init

		Mat hierarchy = new Mat(new Size(srcM.width(), srcM.height()), CvType.CV_8UC1);
		// find contours
		Imgproc.findContours(srcM, contours, hierarchy, Imgproc.RETR_LIST, Imgproc.CHAIN_APPROX_SIMPLE);
		// if any contour exist...
		if (hierarchy.size().height > 0 && hierarchy.size().width > 0) {
			// for each contour, display it in blue
			for (int idx = 0; idx >= 0; idx = (int) hierarchy.get(0, idx)[0]) {
				Imgproc.drawContours(drawM, contours, idx, new Scalar(250, 0, 0));
			}
		}

		Highgui.imwrite(
				"D:\\project desktop\\16-11-11 TagAndGo\\" + tag + "\\" + count + "testuse-opencv-drawCountours.png",
				drawM);
		count++;
	}
	
	
	public static void findContours2(Mat srcM, Mat drawM, List<MatOfPoint> contours, Point point) {

		// init

		Mat hierarchy = new Mat(new Size(srcM.width(), srcM.height()), CvType.CV_8UC1);
		// find contours
		Imgproc.findContours(srcM, contours, hierarchy, Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE, point);
		// if any contour exist...
		if (hierarchy.size().height > 0 && hierarchy.size().width > 0) {
			// for each contour, display it in blue
			for (int idx = 0; idx >= 0; idx = (int) hierarchy.get(0, idx)[0]) {
				Imgproc.drawContours(drawM, contours, idx, new Scalar(250, 0, 0));
			}
		}

		Highgui.imwrite(
				"D:\\project desktop\\16-11-11 TagAndGo\\" + tag + "\\" + count + "testuse-opencv-drawCountours.png",
				drawM);
		count++;
	}
	
	public static void findContours3(Mat srcM, Mat drawM, List<MatOfPoint> contours) {

		// init

		Mat hierarchy = new Mat(new Size(srcM.width(), srcM.height()), CvType.CV_8UC1);
		// find contours
		Imgproc.findContours(srcM, contours, hierarchy, Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE);
		// if any contour exist...
		if (hierarchy.size().height > 0 && hierarchy.size().width > 0) {
			// for each contour, display it in blue
			for (int idx = 0; idx >= 0; idx = (int) hierarchy.get(0, idx)[0]) {
				MatOfPoint mop = contours.get(idx);
				double contourarea = Imgproc.contourArea(mop);
				if(contourarea>3000){
					Imgproc.drawContours(drawM, contours, idx, new Scalar(250, 0, 0));
				}
			}
		}

		Highgui.imwrite(
				"D:\\project desktop\\16-11-11 TagAndGo\\" + tag + "\\" + count + "testuse-opencv-drawCountours.png",
				drawM);
		count++;
	}

	public static Rect boundingRect(MatOfPoint points) {
		return Imgproc.boundingRect(points);

	}
	
    public static Mat cutMat(Mat imgSrc, Rect rect)  
    {  
    	Mat res= imgSrc.submat(rect);
        return res;  
    }
    
	
    public static Mat cutMat(Mat imgSrc)  
    {  
        int a = 0, b = 0;//保存有效行号  
        int h = 0;  
        int state = 0;//标志位，0则表示还未到有效行，1则表示到了有效行,2表示搜寻完毕  
        for (int y = 0; y < imgSrc.height(); y++)  
        {  
            int count = 0;  
            for (int x = 0; x < imgSrc.width(); x++)  
            {  
                //System.out.println("ok");  
                byte[] data = new byte[1];  
                imgSrc.get(y, x, data);  
                //System.out.println("ok2");  
                if (data[0] == 0)  
                    count = count + 1;  
            }  
            if (state == 0)//还未到有效行  
            {  
                if (count >= 10)//找到了有效行  
                    {//有效行允许十个像素点的噪声  
                        a = y;  
                        state = 1;  
                    }  
            }  
            else if (state == 1)  
            {  
                if (count <= 3)//找到了有效行  
                    {//有效行允许十个像素点的噪声  
                        b = y;  
                        state = 2;  
                        break;  
                    }  
            }  
        }  
        Rect roi = new Rect(0, a, imgSrc.width(), b - a);   
        Mat res = new Mat(new Size(roi.width, roi.height),CvType.CV_8UC1);  
        res = imgSrc.submat(roi);  
        return res;  
    }  
    
    public static void reSize(Mat srcMat, Mat destMat, Size ksize){
    	Imgproc.resize(srcMat, destMat, ksize);
		Highgui.imwrite(
				"D:\\project desktop\\16-11-11 TagAndGo\\" + tag + "\\" + count + "testuse-opencv-resize.png",
				destMat);
		count++;
    }

	public static int Otsu(Mat src) {

		int threshold = 0;
		int h = src.height();
		int w = src.width();

		// 灰度化
		int[][] gray = new int[w][h];
		for (int x = 0; x < w; x++) {
			for (int y = 0; y < h; y++) {
				double[] ds = src.get(x, y);
				// int argb
				// 图像加亮（调整亮度识别率非常高）
				if (ds == null)
					continue;

				int r = 255, g = 255, b = 255;

				if (ds.length == 1) {
					r = (int) (ds[0] * 1.1 + 30);
					g = (int) (ds[0] * 1.1 + 30);
					b = (int) (ds[0] * 1.1 + 30);
				} else {
					r = (int) (ds[1] * 1.1 + 30);
					g = (int) (ds[2] * 1.1 + 30);
					b = (int) (ds[3] * 1.1 + 30);
				}

				if (r >= 255) {
					r = 255;
				}
				if (g >= 255) {
					g = 255;
				}
				if (b >= 255) {
					b = 255;
				}
				gray[x][y] = (int) Math.pow(
						(Math.pow(r, 2.2) * 0.2973 + Math.pow(g, 2.2) * 0.6274 + Math.pow(b, 2.2) * 0.0753), 1 / 2.2);
			}
		}

		return ImageHelper.ostu(gray, w, h);
	}

}
