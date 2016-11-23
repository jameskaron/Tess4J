package com.pccw.tagngo.service;

import com.pccw.tagngo.mode.HKIDCardInfo;

public interface HKIDCardOCRService {
	
	public HKIDCardInfo commonOCR(String filePath);
	
	public HKIDCardInfo oCRafterCutCard(String filePath);

}
