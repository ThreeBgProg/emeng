package com.huiming.emeng.dto;

import java.text.ParseException;
import java.util.Date;


public class StringToDate {

	public static Date StringToDate(String string){
		
	    Date birthday = new Date();
	    try{
	           java.text.SimpleDateFormat sdf=new java.text.SimpleDateFormat("yyyy/MM/dd");

	           birthday = sdf.parse(string);

	           } catch (ParseException e){

	          System.out.println("String to Date error");

	           }
	    return birthday;
	}
}

	