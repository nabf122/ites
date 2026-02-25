package egovframework.com.ites.util.service;


public class TempPasswordUtil {
    
	public static void main(String[] arg) {
    	
    	String tempPw = "Ons123!@#";
    	String shaPw  = Sha256Util.sha256Hex(tempPw);
    	
    	System.out.println(shaPw);
    }
}