package site.jimblog.util;

import org.apache.shiro.crypto.hash.Md5Hash;

public class CryptographyUtil {

	
	public static String md5(String str,String salt){
		return new Md5Hash(str,salt).toString();
	}
	
	public static void main(String[] args) {
		String password="e1741c51ba812fe30b2bfbd1a6beeed1";
		
		System.out.println("MD5 lock:"+CryptographyUtil.md5(password, "jimblog"));
	}
}
