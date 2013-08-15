/**
 * 
 */
package simbio.se.sau.json;

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

}
