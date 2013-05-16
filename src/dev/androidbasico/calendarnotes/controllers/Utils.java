package dev.androidbasico.calendarnotes.controllers;

public class Utils {

	public static int date2int(String date){
		String[] sp = date.split("/");
		return Integer.parseInt(sp[0]+sp[1]+sp[2]);
	}
	
	public static int time2int(String time){
		String[] sp = time.split(":");
		return Integer.parseInt(sp[0]+sp[1]);
	}
	
	public static String int2date(int date){
		String aux = date+"";
		String year = aux.substring(aux.length()-4);
		aux = aux.substring(0,aux.length()-4);
		String month = aux.substring(aux.length()-2);
		aux = aux.substring(0,aux.length()-2);
		
		return aux+"/"+month+"/"+year;
	}
	
	public static String int2time(int time){
		String aux = time+"";
		String minute = aux.substring(aux.length()-2);
		aux = aux.substring(0,aux.length()-2);
		return aux+":"+minute;
	}
}
