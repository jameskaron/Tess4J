package com.pccw.tagngo.infotester;

import com.pccw.tagngo.utils.Constant;

public class SeqTester implements InfoTester {

	@Override
	public boolean test(String str, int case_id) {
		
		System.out.println("Test str: [" + str + "], case: " + case_id);

		boolean flag = false;
		if(str==null){
			return flag;
		}
		switch (case_id) {
		case Constant.Tester_DEFAULT_Case:
			if(str.length()>=4){
				flag = true;
			}
			break;
		case Constant.Tester_Part_Case:
			if(str.length()==4){
				flag = true;
			}
			break;
		case Constant.Tester_Full_Case:
			if(str.split(" ").length==3){
				flag = true;
			}
			break;
		}
		System.out.println("Flag: "+ flag);
		return flag;
		
	}
	
	public static void main(String[] args) {
		String str = "2621 2535 5174";
		System.out.println(str.split(" ").length);
	}

}
