package com.pccw.tagngo.infotester;

import com.pccw.tagngo.utils.Constant;

public class PingYinNameTester implements InfoTester {

	@Override
	public boolean test(String str, int case_id) {
		
		boolean flag = false;
		if(str==null){
			return flag;
		}
		switch (case_id) {
		case Constant.Tester_DEFAULT_Case:
			if(str!=null&&!str.trim().equals("")&&str.length()>1){
				flag = true;
			}
			break;

		case Constant.Tester_Part_Case:
			if(str!=null&&!str.trim().equals("")&&str.length()>1){
				flag = true;
			}
			break;
		case Constant.Tester_Full_Case:
			if(str.contains(",")){
				flag = true;
			}
			break;
		}
		return flag;
	}

}
