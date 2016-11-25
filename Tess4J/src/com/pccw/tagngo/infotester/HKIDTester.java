package com.pccw.tagngo.infotester;

import com.pccw.tagngo.utils.Constant;

public class HKIDTester implements InfoTester {

	@Override
	public boolean test(String str, int case_id) {
		
		boolean flag = false;
		if(str==null){
			return flag;
		}
		switch (case_id) {
		case Constant.Tester_DEFAULT_Case:
			if(str.length()==10||str.length()==11){
				flag = true;
			}
			break;

		case Constant.Tester_Part_Case:
			break;
		
		case Constant.Tester_Full_Case:
			break;
			
		}
		
		return flag;
	}

}
