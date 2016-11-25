package com.pccw.tagngo.tess4jocr;

import java.util.List;
import java.util.regex.Pattern;

import org.opencv.core.Mat;

import com.pccw.tagngo.mode.HKIDCardInfo;
import com.pccw.tagngo.mode.Tess4JRegExBool;
import com.pccw.tagngo.opencv.OpenCVWorkbench;
import com.pccw.tagngo.utils.DateLegalChecker;
import com.pccw.tagngo.utils.MyUtils;
import com.pccw.tagngo.utils.SystemConfig;

public class Tess4jCombineProcess{

	
	public HKIDCardInfo ocrWithMat(List<Mat> possibleMatList) {
		long startTime = System.currentTimeMillis();
		
		HKIDCardInfo info 				= new HKIDCardInfo();
		OpenCVWorkbench workbench 		= new OpenCVWorkbench();
		
		SeqNoTesseract seqNoTess		= new SeqNoTesseract();
		PingYinNameTesseract nameTess 	= new PingYinNameTesseract();
		IdNoTesseract idTess 			= new IdNoTesseract();
		BirthDateTesseract birthdayTess = new BirthDateTesseract();
		AllMatchTesseract tess4jProcess = new AllMatchTesseract();
		
		String seqNoResult 				= null;
		String nameResult 				= null;
		String idResult 				= null;
		String birthdayResult 			= null;
		String result = null;
		
		boolean isSeqId = false;
		boolean isName 	= false;
		boolean isHKId 	= false;
		boolean isBirthday = false;
		
		int count = 1;
		
		//
		boolean todoSeqNo	= true; 
		boolean todoname 	= true;
		boolean todoId 		= true;
		boolean todoBirth 	= true;
		
		Tess4JRegExBool regBool = new Tess4JRegExBool(false,false,false,false);
		
		for (Mat mat : possibleMatList) {
//			double contourArea = mat.height() * mat.width();
			
			/*seqNoResult = seqNoTess.ocrWithMat(mat);
			nameResult = nameTess.ocrWithMat(mat);
			birthdayResult = birthdayTess.ocrWithMat(mat);*/
			System.out.println("=================================");
			int iterations = mat.width()/1000;
			Mat newMat = new Mat();
			workbench.beforeOCR(mat, newMat, iterations);
			result = tess4jProcess.ocrWithMat(newMat);

			if(result == null || result.trim().length() <=0){
				continue;
			}
			System.out.println("check result : " + result);
			if(todoId){
				idResult = idTess.ocrWithMat(newMat);
				idResult = (idResult!=null&&idResult.trim().length()>0)?MyUtils.regularExpression(idResult,regBool):null;
				if(idResult != null && idResult.trim().length() > 0 ){
					
					isHKId = Pattern.matches(SystemConfig.getHKID_RegEx(), idResult);
					if(isHKId){
						info.setHkid(idResult);
						todoId = false;
					}
				}
			}
			
			result 	= (result!=null&&result.trim().length()>0)?MyUtils.regularExpression(result,regBool):null;
			
			if (result != null && result.trim().length() > 0) {
				isSeqId = Pattern.matches(SystemConfig.getHKID_Commercial_Code_RegEx(), result);
				isName = Pattern.matches(SystemConfig.getHKID_Name_RegEx(), result);
				isBirthday = DateLegalChecker.isDateLegal(result);
			}
			
			if (isSeqId) {
				info.setSeqNo(result);
				isSeqId = false;
			} else if (isName) {
				info.setName(result);
				isName = false;
			} else if(isBirthday){
				info.setBirthDate(result);
				isBirthday = false;
			}
			
			
			
			
			/*if(todoname){
				nameResult = nameTess.ocrWithMat(mat);
				nameResult = (nameResult!=null&&nameResult.trim().length()>0)?MyUtils.regularExpression(nameResult,regBool):null;
				if(nameResult != null && nameResult.trim().length() > 0 ){
					
					isName = Pattern.matches(SystemConfig.getHKID_Name_RegEx(),nameResult);
					if(isName){
						info.setName(nameResult);
						todoname = false;
					}
				}
			}
			
			if(todoSeqNo){
				seqNoResult = seqNoTess.ocrWithMat(mat);
				seqNoResult = (seqNoResult!=null&&seqNoResult.trim().length()>0) ?MyUtils.regularExpression(seqNoResult,regBool):null;
				if(seqNoResult != null && seqNoResult.trim().length() > 0 ){
					
					isSeqId = Pattern.matches(SystemConfig.getHKID_Commercial_Code_RegEx(), seqNoResult);
					if(isSeqId){
						info.setSeqNo(seqNoResult);
						todoSeqNo = false;
					}
				}
			}
			
			if(todoBirth){
				birthdayResult = birthdayTess.ocrWithMat(mat);
				birthdayResult = (birthdayResult!=null&&birthdayResult.trim().length()>0) ?MyUtils.regularExpression(birthdayResult,regBool):null;
				if(birthdayResult != null && birthdayResult.trim().length() > 0 ){
					
					isBirthday = DateLegalChecker.isDateLegal(birthdayResult);
					if(isBirthday){
						info.setBirthDate(birthdayResult);
						todoBirth = false;
					}
				}
			}*/
			
//			System.out.println("count : " + count);
			/*System.out.println("seq : " + seqNoResult);
			System.out.println("name : " + nameResult);
			System.out.println("id : " + idResult);
			System.out.println("birthday : " + birthdayResult);*/
			
			
			/*if(seqNoResult == null && nameResult == null && idResult == null && birthdayResult == null){
				continue;
			}*/
			
			/*seqNoResult 	= (seqNoTess.ocrWithMat(mat)!=null&&seqNoTess.ocrWithMat(mat).trim().length()>0) ?MyUtils.regularExpression(seqNoResult):null;
			nameResult 		= (nameTess.ocrWithMat(mat)!=null&&nameTess.ocrWithMat(mat).trim().length()>0)?MyUtils.regularExpression(nameResult):null;
			idResult 		= (idTess.ocrWithMat(mat)!=null&&idTess.ocrWithMat(mat).trim().length()>0)?MyUtils.regularExpression(idResult):null;
			birthdayResult 	= (birthdayTess.ocrWithMat(mat)!=null&&birthdayTess.ocrWithMat(mat).trim().length()>0)?MyUtils.regularExpression(birthdayResult):null;*/
			
//			System.out.println("result : " + result + ",id [" + todoId +"]result : " + idResult);
			
			/*if(todoId){
				idResult = (idResult!=null&&idResult.trim().length()>0)?MyUtils.regularExpression(idResult):null;
				isHKId = Pattern.matches(SystemConfig.getHKID_RegEx(), result);
				if(isHKId){
					info.setHkid(result);
					todoId = false;
				}
			}*/
			/*result 	= (result!=null&&result.trim().length()>0)?MyUtils.regularExpression(result):null;
			
			if (result != null && result.trim().length() > 0) {
				isSeqId = Pattern.matches(SystemConfig.getHKID_Commercial_Code_RegEx(), result);
				isName = Pattern.matches(SystemConfig.getHKID_Name_RegEx(), result);
//				isHKId = Pattern.matches(SystemConfig.getHKID_RegEx(), result);
				isBirthday = DateLegalChecker.isDateLegal(result);
			}
			
			if (isSeqId) {
				info.setSeqNo(result);
			} else if (isName) {
				info.setName(result);
			} else if(isBirthday){
				info.setBirthDate(result);
			}*/
			System.out.println("count  [" + count + "] bool : "  + isSeqId + "," + isName + "," + isHKId + "," + isBirthday);
			
			count++;
		}
		
		
		
		/*if (seqNoResult != null && seqNoResult.trim().length() > 0) {
			isSeqId = Pattern.matches(SystemConfig.getHKID_Commercial_Code_RegEx(), seqNoResult);
		} else if (nameResult != null && nameResult.trim().length() > 0) {
			 isName = Pattern.matches(SystemConfig.getHKID_Name_RegEx(), nameResult);
		} else if (idResult != null && idResult.trim().length() > 0) {
			 isHKId = Pattern.matches(SystemConfig.getHKID_RegEx(), idResult);
		} else if (birthdayResult != null && birthdayResult.trim().length() > 0) {
			 isBirthday = DateLegalChecker.isDateLegal(birthdayResult);
		}*/
		
		/*if (result != null && result.trim().length() > 0) {
			isSeqId = Pattern.matches(SystemConfig.getHKID_Commercial_Code_RegEx(), result);
			isName = Pattern.matches(SystemConfig.getHKID_Name_RegEx(), result);
			isHKId = Pattern.matches(SystemConfig.getHKID_RegEx(), result);
			isBirthday = DateLegalChecker.isDateLegal(result);
		}
		
		if (isSeqId) {
			info.setSeqNo(result);
		} else if (isName) {
			info.setName(result);
		} else if (isHKId) {
			info.setHkid(result);
		} else if(isBirthday){
			info.setBirthDate(result);
		}*/
		 
		 
		
		
//		BufferedImage bi = new MatToBufImg(mat,".png").getImage();
		
		/*try {
			result = instance.doOCR(bi);
			result = result.trim();
		} catch (TesseractException e) {
			e.printStackTrace();
		}*/
		long endTime = System.currentTimeMillis();
		System.err.println("Use time:" + (endTime- startTime));
		return info;
	}
	

	
}
