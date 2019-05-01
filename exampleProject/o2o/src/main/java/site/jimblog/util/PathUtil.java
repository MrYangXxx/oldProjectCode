package site.jimblog.util;

/**
 * <p>
 * Title: PathUtil
 * </p>
 * <p>
 * Description:
 * </p>
 * 
 * @author Jim
 * @date Jun 14, 2018
 * 
 */
public class PathUtil {

	private static String seperator = System.getProperty("file.separator");

	public static String getImgBasePath() {
		String os = System.getProperty("os.name");
		String basePath = "";
		if (os.toLowerCase().startsWith("win")) {
			basePath = "D:\\JAVA\\Struts2Project\\o2o";
		} else {
			basePath = "";
		}
		basePath = basePath.replace("/", seperator);
		return basePath;
	}

	public static String getShopImgPath(long shopId) {
		String imagePath = "/upload/item/shop/" + shopId + "/";
		return imagePath.replace("/", seperator);
	}
}
