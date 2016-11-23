package com.pccw.tagngo;

import com.pccw.tagngo.mode.HKIDCardInfo;
import com.pccw.tagngo.service.HKIDCardOCRService;
import com.pccw.tagngo.service.HKIDCardOCRServiceImpl;

public class Main {

	public static void main(String[] args) {
		
//		System.loadLibrary("opencv_java2413");
		
		HKIDCardOCRService service = new HKIDCardOCRServiceImpl();
		HKIDCardInfo info = service.commonOCR("D:\\project desktop\\16-11-11 TagAndGo\\TestUse\\readTest2.jpg");
		
		System.out.println(info.toString());

	}

}
