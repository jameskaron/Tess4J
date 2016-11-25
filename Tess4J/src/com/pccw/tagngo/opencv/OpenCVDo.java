package com.pccw.tagngo.opencv;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.highgui.Highgui;
import org.opencv.imgproc.Imgproc;

import com.pccw.tagngo.utils.OpencvXTess4jConfig;

import net.sourceforge.tess4j.util.ImageHelper;

public class OpenCVDo {

	public int count = 0;
	public String rootPath = OpencvXTess4jConfig.getDebugPath();
	public String tag = new SimpleDateFormat("yyyy_MM_dd-HH_mm_ss").format(new Date(System.currentTimeMillis()));
	public String testImageFilePath = rootPath + "/%s/%d_%s";
	public boolean debugModel = OpencvXTess4jConfig.isDebug.equalsIgnoreCase("YES");
	
	public void init(Mat srcImg){
		
		if(debugModel){
			new File(rootPath + tag).mkdirs();
			Highgui.imwrite(String.format(testImageFilePath, tag, count, "testuse-opencv-src.png"),srcImg);
			count++;
		}
		
	}

	public void beforeOCR(Mat scrMat, Mat destMat, int iterations) {
		adjustColor(scrMat, destMat, 1.5, 1.5);
		rGB2GRAY(destMat, destMat);
		int blockSize = 25;
		int constValue = 10;
		adaptiveThreshold(destMat, destMat, blockSize, constValue);
		if (iterations != 0) {
			erode(destMat, destMat, new Size(3, 3), new Point(-1, -1), iterations);
			dilate(destMat, destMat, new Size(3, 3), new Point(-1, -1), iterations);
		}
	}
	
	public void outputFileWithMat(Mat mat){
		if (debugModel) {
			Highgui.imwrite(String.format(testImageFilePath, tag, count, "testuse-opencv-output.png"), mat);
			count++;
		}
	}

	public void rGB2GRAY(Mat srcM, Mat destM) {
		try {
			Imgproc.cvtColor(srcM, destM, Imgproc.COLOR_RGB2GRAY);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Exception Num:" + count);
		}
		if (debugModel) {
			Highgui.imwrite(String.format(testImageFilePath, tag, count, "testuse-opencv-gray.png"), destM);
			count++;
		}
	}

	public void bGR2HSV(Mat srcM, Mat destM) {
		Imgproc.cvtColor(srcM, destM, Imgproc.COLOR_BGR2HSV);
		if (debugModel) {
			Highgui.imwrite(String.format(testImageFilePath, tag, count, "testuse-opencv-HSV.png"), destM);
			count++;
		}
	}

	public void cvtColor(Mat srcM, Mat destM, int colorType) {
		Imgproc.cvtColor(srcM, destM, colorType);
		if (debugModel) {
			Highgui.imwrite(String.format(testImageFilePath, tag, count, "testuse-opencv-defindColor.png"), destM);
			count++;
		}
	}

	public void adjustColor(Mat srcM, Mat destM, double alpha, double beat) {
		srcM.convertTo(destM, -1, alpha, beat);
		if (debugModel) {
			Highgui.imwrite(String.format(testImageFilePath, tag, count, "testuse-opencv-adjustColor.png"), destM);
			count++;
		}
	}

	public void sobel(Mat srcM, Mat destM, int ddepth, int dx, int dy) {
		Imgproc.Sobel(srcM, destM, ddepth, dx, dy);
		if (debugModel) {
			Highgui.imwrite(String.format(testImageFilePath, tag, count, "testuse-opencv-sobel.png"), destM);
			count++;
		}
	}

	public void threshold(Mat srcM, Mat destM) {
		Imgproc.threshold(srcM, destM, 0, 255, Imgproc.THRESH_BINARY | Imgproc.THRESH_OTSU);
		if (debugModel) {
			Highgui.imwrite(String.format(testImageFilePath, tag, count, "testuse-opencv-binary.png"), destM);
			count++;
		}
	}

	public void adaptiveThreshold(Mat srcM, Mat destM, int blockSize, int constValue) {
		Imgproc.adaptiveThreshold(srcM, destM, 255, Imgproc.ADAPTIVE_THRESH_MEAN_C, Imgproc.THRESH_BINARY_INV,
				blockSize, constValue);
		if (debugModel) {
			Highgui.imwrite(String.format(testImageFilePath, tag, count, "testuse-opencv-adaptiveThreshold.png"),
					destM);
			count++;
		}
	}

	public void threshold(Mat srcM, Mat destM, int thresh) {
		Imgproc.threshold(srcM, destM, thresh, 255, Imgproc.THRESH_BINARY);
		if (debugModel) {
			Highgui.imwrite(String.format(testImageFilePath, tag, count, "testuse-opencv-binary.png"), destM);
			count++;
		}
	}

	public void dilate(Mat srcM, Mat destM, Size size, Point point, int iterations) {
		Mat kernel = Imgproc.getStructuringElement(Imgproc.CV_SHAPE_RECT, size, point);
		Point point2 = new Point(-1, -1);
		Imgproc.dilate(srcM, destM, kernel, point2, iterations);
		if (debugModel) {
			Highgui.imwrite(String.format(testImageFilePath, tag, count, "testuse-opencv-dilate.png"), destM);
			count++;
		}
	}

	public void erode(Mat srcM, Mat destM, Size size, Point point, int iterations) {
		Mat kernel = Imgproc.getStructuringElement(Imgproc.CV_SHAPE_RECT, size, point);
		Point point2 = new Point(-1, -1);
		Imgproc.erode(srcM, destM, kernel, point2, iterations);
		if (debugModel) {
			Highgui.imwrite(String.format(testImageFilePath, tag, count, "testuse-opencv-erode.png"), destM);
			count++;
		}
	}

	public void canny(Mat srcM, Mat destM, double threshold1, double threshold2) {
		Imgproc.Canny(srcM, destM, threshold1, threshold2);
		if (debugModel) {
			Highgui.imwrite(String.format(testImageFilePath, tag, count, "testuse-opencv-canny.png"), destM);
			count++;
		}
	}

	public void convertABS(Mat srcM, Mat destM) {
		Core.convertScaleAbs(srcM, destM);
		if (debugModel) {
			Highgui.imwrite(String.format(testImageFilePath, tag, count, "testuse-opencv-convertABS.png"), destM);
			count++;
		}
	}

	public void blur(Mat srcM, Mat destM, Size size) {
		Imgproc.blur(srcM, destM, size);
		if (debugModel) {
			Highgui.imwrite(String.format(testImageFilePath, tag, count, "testuse-opencv-blur.png"), destM);
			count++;
		}
	}

	public void medianBlur(Mat srcM, Mat destM, int ksize) {
		Imgproc.medianBlur(srcM, destM, ksize);
		if (debugModel) {
			Highgui.imwrite(String.format(testImageFilePath, tag, count, "testuse-opencv-medianBlur.png"), destM);
			count++;
		}
	}

	public void morphologyEx() {

		// Imgproc.morphologyEx();

	}

	public void findContours(Mat srcM, Mat drawM, List<MatOfPoint> contours) {

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
		if (debugModel) {
			Highgui.imwrite(String.format(testImageFilePath, tag, count, "testuse-opencv-drawCountours.png"), drawM);
			count++;
		}
	}

	public void findContours2(Mat srcM, Mat drawM, List<MatOfPoint> contours, Point point) {

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
		if (debugModel) {
			Highgui.imwrite(String.format(testImageFilePath, tag, count, "testuse-opencv-drawCountours.png"), drawM);
			count++;
		}
	}

	public void findContours3(Mat srcM, Mat drawM, List<MatOfPoint> contours) {

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
				if (contourarea > 3000) {
					Imgproc.drawContours(drawM, contours, idx, new Scalar(250, 0, 0));
				}
			}
		}
		if (debugModel) {
			Highgui.imwrite(String.format(testImageFilePath, tag, count, "testuse-opencv-drawCountours.png"), drawM);
			count++;
		}
	}

	public Rect boundingRect(MatOfPoint points) {
		return Imgproc.boundingRect(points);

	}

	public Mat cutMat(Mat imgSrc, Rect rect) {
		Mat res = imgSrc.submat(rect);
		return res;
	}
	
	public void openOperation(Mat srcMat,Mat destMat, Size size, Point point, int iterations){
		
		dilate(srcMat, destMat, size, point, iterations);
		erode(destMat, destMat, size, point, iterations/2+1);
		dilate(destMat, destMat, size, point, iterations);
		
	}
	
	public void closeOperation(Mat srcMat,Mat destMat, Size size, Point point, int iterations){
		
		erode(srcMat, destMat, size, point, iterations);
		dilate(destMat, destMat, size, point, iterations/2+1);
		erode(destMat, destMat, size, point, iterations);
		
	}

	public int Otsu(Mat src) {

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
