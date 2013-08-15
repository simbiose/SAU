/**
 * 
 */
package simbio.se.sau.json;

import simbio.se.sau.log.SimbiLog;

import com.google.gson.Gson;

/**
 * @author Ademar Alves de Oliveira ademar111190@gmail.com
 * @date 2013-aŭg-15 00:44:14
 * 
 */
public class JsonUtils {

	protected static Gson gson = new Gson();

	/**
	 * @param object
	 * @return
	 */
	public static String toJson(Object object) {
		return gson.toJson(object);
	}

	public static Object fromJson(String json, Class<Object> theClass) {
		return gson.fromJson(json, theClass);
	}

	public static Object fromJsonOrNull(String json, Class<Object> theClass) {
		Object iAbstractModel = null;
		try {
			iAbstractModel = fromJson(json, theClass);
		} catch (Exception e) {
			SimbiLog.printException(e);
		}
		return iAbstractModel;
	}

}
