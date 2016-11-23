package com.pccw.tagngo.service;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Size;
import org.opencv.highgui.Highgui;
import org.opencv.imgproc.Imgproc;

import com.pccw.tagngo.infotester.BirthDateTester;
import com.pccw.tagngo.infotester.HKIDTester;
import com.pccw.tagngo.infotester.PingYinNameTester;
import com.pccw.tagngo.infotester.SeqTester;
import com.pccw.tagngo.mode.HKIDCardInfo;
import com.pccw.tagngo.opencv.BlockOperation;
import com.pccw.tagngo.opencv.OpenCVDo;
import com.pccw.tagngo.opencv.OutlineOperation;
import com.pccw.tagngo.opencv.PrefectImageOperation;
import com.pccw.tagngo.tess4jocr.BirthDateTesseract;
import com.pccw.tagngo.tess4jocr.IdNoTesseract;
import com.pccw.tagngo.tess4jocr.PingYinNameTesseract;
import com.pccw.tagngo.tess4jocr.SeqNoTesseract;
import com.pccw.tagngo.utils.Constant;

public class HKIDCardOCRServiceImpl implements HKIDCardOCRService{
	
	static{
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
	}

	@Override
	public HKIDCardInfo commonOCR(String filePath) {
		
		long startTime = System.currentTimeMillis();
		
		Mat srcImg = Highgui.imread(filePath);
		
		HKIDCardInfo info = new HKIDCardInfo();
		
		OpenCVDo openCVDo = new OpenCVDo();
		openCVDo.init(srcImg);
		BlockOperation blockOperation = new BlockOperation(openCVDo);
		PrefectImageOperation prefectImageOperation = new PrefectImageOperation(openCVDo);
		OutlineOperation outlineOperation = new OutlineOperation(openCVDo);
		
		Mat srcImg2 = new Mat();
		srcImg.copyTo(srcImg2);
		Mat destImg = new Mat();
		int iterations = srcImg.width()/1000;
		
		prefectImageOperation.binarization(srcImg, destImg, iterations);

		List<MatOfPoint> contours = new ArrayList<>();
		openCVDo.findContours(destImg, srcImg2, contours);
		
		Map<Rect,Mat> possibleMatMap = outlineOperation.findPossibleRect(srcImg, contours, 0.05D, 0.85D);
		
		List<Rect> nArea = new ArrayList<>();
		List<Rect> sArea = new ArrayList<>();
		List<Rect> dArea = new ArrayList<>();
		List<Rect> iArea = new ArrayList<>();
		
		int fullW = srcImg.width();
		int fullH = srcImg.height();
		int halfW = fullW/2;
		int halfH = fullH/2;
		int one_sixH = fullH/6;
		int one_fourH = fullH/4;
		int one_fourW = fullW/4;
		
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
		
		String name = null;
		name = blockOperation.oCRMultiWithRectNMat(nArea, possibleMatMap, iterations, new PingYinNameTesseract(), new PingYinNameTester());
		info.setName(name);
		
		String seqNo = null;
		seqNo = blockOperation.oCRMultiWithRectNMat(sArea, possibleMatMap, iterations, new SeqNoTesseract(), new SeqTester());
		info.setSeqNo(seqNo);
		
		String birthDate = null; 
		birthDate = blockOperation.oCRSingleUseRadioWithRectNMat(dArea, possibleMatMap, iterations, Constant.block_birthday_Radio, new BirthDateTesseract(), new BirthDateTester());
		info.setBirthDate(birthDate);
		
		String hkid = null;
		hkid = blockOperation.oCRSingleUseRadioWithRectNMat(iArea, possibleMatMap, iterations, Constant.block_hkid_Radio, new IdNoTesseract(), new HKIDTester());
		info.setHkid(hkid);
		
		System.out.println(String.format("%d/%d/%d/%d", nArea.size(),sArea.size(),dArea.size(),iArea.size()));
		
		long endTime = System.currentTimeMillis();
		System.err.println("Use time:" + (endTime- startTime));
		
		return info;
	}

	@Override
	public HKIDCardInfo oCRafterCutCard(String filePath) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	

}
