package com.pccw.tagngo.opencv;

import java.util.ArrayList;
import java.util.List;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfInt;
import org.opencv.core.MatOfPoint;
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

public class EdgeDetectionKeithVer {
	
	Mat src = new Mat();
	Mat dst = new Mat();
	Mat dst_x = new Mat();
	Mat dst_y = new Mat();
	Mat edges = new Mat();
	Size s = new Size(3, 3);
//	Bitmap destImage = null;
//	Bitmap sourceImage = null;
	int ddepth = CvType.CV_16S;
	String filePath;
	int ratio = 3;
	int threshold_low = 100;
	int kernel_size = 3;
	
	//CONSTANTS
	private static final double ARCLENGTH_FACTOR = 0.02;
	private static final int AREA_THRESHOLD = 10000;
	//private static final int AREA_THRESHOLD = 1000;
	private static final double MAX_AREA_PERCENTAGE = 0.85;
	private static final double COSINE_THRESHOLD = 0.3;


	//Calculates angles between adjacent sides given three points
	public static double findAngle( Point point1, Point point2, Point point0 ) {
	    double dx1 = point1.x - point0.x;
	    double dy1 = point1.y - point0.y;
	    double dx2 = point2.x - point0.x;
	    double dy2 = point2.y - point0.y;
	    return (dx1*dx2 + dy1*dy2)/Math.sqrt((dx1*dx1 + dy1*dy1)*(dx2*dx2 + dy2*dy2) + 1e-10);
	}

	//Main Edge Detection Method
	public static MatOfPoint detectEdge(Mat source) {
		Mat blurred = new Mat();
		Mat blurred2 = new Mat();
		source.copyTo(blurred);
		source.copyTo(blurred2);
		System.out.println( "Blurred Matrix! : " + blurred.total() );
		
//		float alpha = 1.3f;
//		float beta = 0.15f;
//		blurred.convertTo(blurred, -1, alpha, beta);
		
		Imgproc.medianBlur(source, blurred, 9);
		System.out.println( "Median Blur Done! - 1");
		List<MatOfPoint> squares = getDetectedSquareList(source, blurred);
	    
		Imgproc.medianBlur(source, blurred2, 5);
		System.out.println( "Median Blur Done! - 2");
		List<MatOfPoint> squares2 = getDetectedSquareList(source, blurred2);
		
		squares.addAll(squares2);
		
	    //Find rectangular contour with biggest area
	    double maxarea = 0;
		double secmaxarea = 0;
		int maxareaidx = 0;
		for(int idx =0; idx< squares.size();++idx)
		{
			Mat contour = squares.get(idx);
			double area = Math.abs(Imgproc.contourArea(contour));
			if(area > maxarea)
			{
				maxarea = area;
				maxareaidx = idx;
			}
		}
		
		for(int idx =0; idx< squares.size();++idx)
		{
			Mat contour = squares.get(idx);
			double area = Imgproc.contourArea(contour);
			if(area > secmaxarea && area < maxarea)
			{
				secmaxarea = area;
			}
		}
		
		System.out.println( "Max Area calculated!" + maxarea);
		System.out.println( "Biggest Contour Index" + maxareaidx);
		System.out.println( "Second Biggest Area " + secmaxarea);

		if(!(squares.isEmpty()))
		{
			return squares.get(maxareaidx);
		}
		else
		{
			MatOfPoint originalMatOfPoint = new MatOfPoint(new Point[]{new Point(0, 0), new Point(source.width(), 0), new Point(source.width(), source.height()), new Point(0, source.height()) });
			return originalMatOfPoint;
		}
	}

	public static List<MatOfPoint> getDetectedSquareList(Mat source, Mat blurred) {
		Mat gray_temp = new Mat(blurred.size(), blurred.type());
		Imgproc.cvtColor(gray_temp, gray_temp, Imgproc.COLOR_RGB2GRAY);
		Mat gray = new Mat();
		
		Imgproc.equalizeHist(gray_temp, gray_temp);
		
		System.out.println( "gray_temp Matrix! : " + gray_temp.total() );
		System.out.println( "Gray Matrix! : " + gray.total() );
		
		List<MatOfPoint> contours = new ArrayList<MatOfPoint>();
		List<MatOfPoint> squares = new ArrayList<MatOfPoint>();
		
		// Iterate through each of the three colour spaces and through original image as well
	    for (int color_space = 0; color_space < 4; color_space++)
		{
	    	System.out.println("Color Space Entering Iteration: " + color_space);
	    	if(color_space==3){
	    		Mat destImageMat = new Mat();
	    		Imgproc.cvtColor(source, destImageMat, Imgproc.COLOR_RGB2GRAY);
	    		destImageMat.copyTo(gray_temp);
	    	}
	    	
	    	else{
	    		System.out.println( "Mix Channels Started! : " + gray_temp.total());
		        int ch[] = {color_space, 0};
		        MatOfInt fromto = new MatOfInt(ch);
		        List<Mat> blurredlist = new ArrayList<Mat>();
		        List<Mat> graylist = new ArrayList<Mat>();
		        blurredlist.add(0, blurred);
		        graylist.add(0, gray_temp);
		        Core.mixChannels(blurredlist, graylist, fromto);
		        gray_temp = graylist.get(0);
		        System.out.println( "Mix Channels Done! : " + gray_temp.total() );
	    	}

	    	//For each colour space perform both Canny Detection and Adaptive Thresholding
	        int iteration_count = 2;
	        for (int iteration = 0; iteration < iteration_count; iteration++)
	        {
	        	System.out.println("Iteration : " + iteration);
	        	
	            if (iteration == 0)
	            {
	            	
	                //Imgproc.Canny(gray_temp, gray, 20, 30); //original one
	            	//Imgproc.Canny(gray_temp, gray, 10, 20, 3, true); 
	                //Imgproc.Canny(gray_temp, gray, 16, 18, 3, true); 
	                Imgproc.Canny(gray_temp, gray, 18, 30, 3, true);
//	                Imgproc.dilate(gray, gray, Mat.ones(new Size(3,3),0));
//	                Mat kernel = Imgproc.getStructuringElement(Imgproc.CV_SHAPE_RECT, new Size(), new Point(-1,-1));
	                Imgproc.dilate(gray, gray, new Mat(),new Point(-1, -1),2,1, new Scalar(1));
	                
	            }
	            else
	            {
	            	Mat final_dest_mat = Mat.zeros(source.size(), source.type());
	        		Imgproc.adaptiveThreshold(gray_temp, final_dest_mat, 255, Imgproc.ADAPTIVE_THRESH_MEAN_C, Imgproc.THRESH_BINARY, 5, 5);
	        		
	        		//Perform erode operation
	        		if(color_space == 0){
	    	    		int matrix_size = 2;
	    	    		Size size = new Size(matrix_size,matrix_size);
	    	    		
	    	    		Mat temp_erode_1 = Mat.zeros(final_dest_mat.size(), final_dest_mat.type());
	    				Imgproc.erode( final_dest_mat, temp_erode_1, Mat.ones(size,0));
	    				Mat temp_erode_2 = Mat.zeros(temp_erode_1.size(), temp_erode_1.type());
	    				Imgproc.erode( temp_erode_1, temp_erode_2, Mat.ones(size,0));
	    				
	    				temp_erode_2.copyTo(gray);
	        		}
//	        		else{
//	        			final_dest_mat.copyTo(gray);
//	        		}
	            }

	            System.out.println( "Canny (or Thresholding) Done!");
	            System.out.println( "Gray Matrix (after)! : " + gray.total() );
	    		
	            // Retrieve all the contours and store them in a list
	            Imgproc.findContours(gray, contours, new Mat(), Imgproc.RETR_LIST, Imgproc.CHAIN_APPROX_SIMPLE);
	            System.out.println( "Contours Found!");
	            
	            MatOfPoint2f approx = new MatOfPoint2f();
	            MatOfPoint2f mMOP2f1 = new MatOfPoint2f();
	            MatOfPoint mMOP = new MatOfPoint();
	            for( int i = 0; i < contours.size(); i++ )
	            {
	            	contours.get(i).convertTo(mMOP2f1, CvType.CV_32FC2);
	            	Imgproc.approxPolyDP(mMOP2f1, approx, Imgproc.arcLength(mMOP2f1, true)*ARCLENGTH_FACTOR, true);
	            	approx.convertTo(mMOP, CvType.CV_32S);
	                  
	            	//Check for basic card conditions in each closed contour
	                if(approx.rows()== 4 && Math.abs(Imgproc.contourArea(approx)) > AREA_THRESHOLD && Imgproc.isContourConvex(mMOP))
	                {
	                	
	                	System.out.println("Passes Conditions! " + approx.size().toString());
	                	double maxcosine = 0;
	                	Point[] list = approx.toArray();
	                	int rectangle_flag = 0;
	                	double temp_cosine = 0;
	                	 for (int j = 2; j < 5; j++)
	                	 {	 
	                		 double cosine =Math.abs(findAngle(list[j%4], list[j-2], list[j-1]));
	                		 if(j == 2){
	                			 temp_cosine = cosine;
	                		 }
	                		 else{
	                			 //if((cosine > (temp_cosine + 0.05))||(cosine < (temp_cosine - 0.05))){
	                			 if((cosine > (temp_cosine + 0.15))||(cosine < (temp_cosine - 0.15))){
	                				 rectangle_flag = 1;
	                			 }
	                		 }
	                		 System.out.println("Cosine of side " + j + " = " + cosine);
	                		 maxcosine = Math.max(maxcosine, cosine);
                         }
	                	 
	                	 if( maxcosine < COSINE_THRESHOLD ) {
	                		 System.out.println( "maxcosine < COSINE_THRESHOLD! : " + maxcosine);
	                		 MatOfPoint temp = new MatOfPoint();
	                		 approx.convertTo(temp, CvType.CV_32S);
	                		 
	                		 if((Imgproc.contourArea(approx)/source.total())<MAX_AREA_PERCENTAGE){
	                			 if(rectangle_flag == 0){
		                			 squares.add(temp);
		                			 System.out.println( "Squares Added to List! : " + squares.size());
		                		 }
	                		 }
	                     }
	                }
	            }
	        }
	    }
	    System.out.println("Point 1");
		return squares;
	}	

}