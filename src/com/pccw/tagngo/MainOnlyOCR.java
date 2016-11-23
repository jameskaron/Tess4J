package com.pccw.tagngo;

import com.pccw.tagngo.mode.HKIDCardInfo;
import com.pccw.tagngo.service.HKIDCardOCRService;
import com.pccw.tagngo.service.HKIDCardOCRServiceImpl;
import com.pccw.tagngo.tess4jocr.NoneHandleTesseract;

public class MainOnlyOCR {

	public static void main(String[] args) {
		
		NoneHandleTesseract noneHandleTesseract = new NoneHandleTesseract();
		System.out.println(noneHandleTesseract.ocrWithMat("D:\\project desktop\\16-11-11 TagAndGo\\TestUse\\readTest.jpg"));

	}

}
