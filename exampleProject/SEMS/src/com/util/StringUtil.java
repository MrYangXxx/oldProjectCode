package com.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {

	public static boolean isEmpty(String str){
		if("".equals(str)||str==null){
			return true;
		}else{
			return false;
		}
	}
	public static boolean isEmpty(int i){
		if("".equals(i)||i==0){
			return true;
		}else{
			return false;
		}
	}

	public static boolean isNotEmpty(String str){
		if(str!=null&&!str.trim().equals("")){
			return true;
		}else{
			return false;
		}
	}

	public static boolean isNotEmpty(int i){
		if(!"".equals(i)&&i!=0){
			return true;
		}else{
			return false;
		}
	}
	
	public static boolean isNumeric(String str){ 
		   Pattern pattern = Pattern.compile("[0-9]*"); 
		   Matcher isNum = pattern.matcher(str);
		   if( !isNum.matches() ){
		       return false; 
		   } 
		   return true; 
		}


}
