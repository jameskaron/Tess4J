package com.pccw.tagngo.opencv;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import org.opencv.core.Mat;
import org.opencv.core.Rect;

import com.pccw.tagngo.infotester.InfoTester;
import com.pccw.tagngo.tess4jocr.TesseractOCR;
import com.pccw.tagngo.utils.Constant;

public class BlockOperation {
	
	private OpenCVDo openCVDo;
	
	public BlockOperation(OpenCVDo ocvDo){
		this.openCVDo = ocvDo;
	}
	
	public String oCRSingleUseRadioWithRectNMat(List<Rect> rectList, Map<Rect,Mat> matMap, int iterations, double referenceRadio, TesseractOCR tess4j, InfoTester infoTester){
		
		String result = null; 
		if(rectList.size()==0){
//			System.out.println("Not found form this List");
		}else if(rectList.size()==1){
//			System.out.println("Only one. OCR for the Mat");
			Mat mat = matMap.get(rectList.get(0));
			Mat newMat = new Mat();
			openCVDo.beforeOCR(mat, newMat, iterations);
			openCVDo.outputFileWithMat(newMat);
			result = tess4j.ocrWithMat(newMat);
			if(!infoTester.test(result, Constant.Tester_DEFAULT_Case))
				result = null;
		}else{
			Collections.sort(rectList, new Comparator<Rect>() {
				@Override
				public int compare(Rect o1, Rect o2) {
					double o1_radio = (double)o1.height / (double) o1.width;
					double o2_radio = (double)o2.height / (double) o2.width;
					return Math.abs((referenceRadio - o1_radio)) < Math.abs((referenceRadio - o2_radio))?-1:1;
				}
			});
//			System.out.println("Select best match. OCR for the Mat");
			for(Rect r:rectList){
				Mat mat = matMap.get(r);
				Mat newMat = new Mat();
				openCVDo.beforeOCR(mat, newMat, iterations);
				openCVDo.outputFileWithMat(newMat);
				result = tess4j.ocrWithMat(newMat);
				if(infoTester.test(result, Constant.Tester_DEFAULT_Case)){
					break;
				}else{
					result = null;
				}
			}
		}
//		System.out.println("Result:" + result);
		return result;
	}
	
	public String oCRMultiWithRectNMat(List<Rect> rectList, Map<Rect,Mat> matMap, int iterations, TesseractOCR tess4j, InfoTester infoTester){
		
		String result = null; 
		if(rectList.size()==0){
//			System.out.println("Not found form this List");
		}else if(rectList.size()==1){
//			System.out.println("Only one. OCR for the Mat");
			Mat mat = matMap.get(rectList.get(0));
			Mat newMat = new Mat();
			openCVDo.outputFileWithMat(newMat);
			openCVDo.beforeOCR(mat, newMat, iterations);
			result = tess4j.ocrWithMat(newMat);
			if(!infoTester.test(result, Constant.Tester_Full_Case))
				result = null;
		}else{
			List<List<Rect>> list = groupingMatWithRow(rectList, iterations * 5);
			System.out.println("Select best match. OCR for the Mat");
			System.out.println("Rows size:" + list.size());
			for(List<Rect> rlist : list){
				if(rlist.size()==1){
					Mat mat = matMap.get(rlist.get(0));
					Mat newMat = new Mat();
					openCVDo.beforeOCR(mat, newMat, iterations);
					openCVDo.outputFileWithMat(newMat);
					result = tess4j.ocrWithMat(newMat);
					System.out.println(result);
					if(infoTester.test(result, Constant.Tester_Full_Case))
						break;
					else
						result=null;
				}else{
					StringBuffer result_sb = new StringBuffer();
					Collections.sort(rlist, new Comparator<Rect>() {
						@Override
						public int compare(Rect o1, Rect o2) {
							return o1.x-o2.x;
						}
					});
					for (int i = 0; i < rlist.size() ; i++) {
						
						Mat mat = matMap.get(rlist.get(i));
						Mat newMat = new Mat();
						openCVDo.beforeOCR(mat, newMat, iterations);
						openCVDo.outputFileWithMat(newMat);
						String result_str = tess4j.ocrWithMat(newMat);
						System.out.println(result_str);
						if(infoTester.test(result_str, Constant.Tester_Part_Case))
							result_sb.append(result_str).append(" ");
						
					}
					if(infoTester.test(result_sb.toString().trim(), Constant.Tester_Full_Case)){
							result = result_sb.toString();
							break;
					}else{
						result =  null;
					}
				}
			}
		}
		System.out.println("Result:" + result);
		return result;
	}

	public List<List<Rect>> groupingMatWithRow(List<Rect> rectList, int errorRange) {
		List<List<Rect>> result = new ArrayList<>();

		Collections.sort(rectList, new Comparator<Rect>() {
			@Override
			public int compare(Rect o1, Rect o2) {
				return o1.y - o2.y;
			}
		});
		
		int lastY = 0;
		List<Rect> lastRectList = null;
		for (int i = 0; i < rectList.size(); i++) {
			
			Rect r = rectList.get(i);
			if(i==0){
				lastRectList = new ArrayList<>();
				lastRectList.add(r);
			}else{
				if(Math.abs(r.y-lastY)<=errorRange){
					lastRectList.add(r);
				}else{
					result.add(lastRectList);
					lastRectList = new ArrayList<>();
					lastRectList.add(r);
				}
			}
			lastY = r.y;
		}
		result.add(lastRectList);		
		return result;
	}
	
	

}
