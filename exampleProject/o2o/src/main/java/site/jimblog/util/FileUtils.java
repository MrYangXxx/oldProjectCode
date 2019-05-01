package site.jimblog.util;

import java.io.File;

import site.jimblog.entity.Product;
import site.jimblog.entity.Shop;

/**
 * <p>Title: FileUtils</p>  
 * <p>Description: </p>  
 * @author Jim
 * @date Aug 14, 2018  
 * 
 */
public class FileUtils {
	
	public static void deleteFileOrPath(String addr){
		File file=new File(PathUtil.getImgBasePath()+addr);
		if(file.exists()){
			file.delete();
		}
	}
}
