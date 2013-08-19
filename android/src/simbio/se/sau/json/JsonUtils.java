/**
 * 
 */
package simbio.se.sau.json;

import simbio.se.sau.log.SimbiLog;

import com.google.gson.Gson;

/**
 * This class provide with a static way methods to convert {@link Object} to Json {@link String} and vice versa. This class use {@link Gson} to do it, so for more details see {@link Gson}
 * 
 * @author Ademar Alves de Oliveira ademar111190@gmail.com
 * @date 2013-aŭg-15 00:44:14
 */
public class JsonUtils {

	/**
	 * The static instance of {@link Gson}
	 */
	protected static Gson gson = new Gson();

	/**
	 * This method convert a {@link Object} to a Json {@link String}, for more details see {@link Gson#toJson(Object)}
	 * 
	 * @param object
	 *            to be converted to Json {@link String}
	 * @return a {@link String} with Json format
	 */
	public static String toJson(Object object) {
		return gson.toJson(object);
	}

	/**
	 * This method convert a Json {@link String} to a {@link Object} and if get some error return the default object param, for more details see {@link Gson#fromJson(String, Class)}
	 * 
	 * @param json
	 *            a Json {@link String} mapping the object
	 * @param theClass
	 *            The {@link Class} of object mapped
	 * @param def
	 *            default object if it get some error
	 * @return a {@link Object} represented from json parameter and with class struct from theClass.
	 */
	public static Object fromJson(String json, Class<?> theClass, Object def) {
		Object object = def;
		try {
			object = gson.fromJson(json, theClass);
		} catch (Exception e) {
			SimbiLog.printException(e);
		}
		return object;
	}

	/**
	 * This method convert a Json {@link String} to a {@link Object} and if get some error return <code>null</code>, for more details see {@link Gson#fromJson(String, Class)}
	 * 
	 * @param json
	 *            a Json {@link String} mapping the object
	 * @param theClass
	 *            The {@link Class} of object mapped
	 * @return a {@link Object} represented from json parameter and with class struct from theClass or null if get some error.
	 */
	public static Object fromJsonOrNull(String json, Class<?> theClass) {
		return fromJson(json, theClass, null);
	}

}
