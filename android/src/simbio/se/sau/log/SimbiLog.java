package simbio.se.sau.log;

import simbio.se.sau.json.JsonUtils;
import android.util.Log;

/**
 * @author Ademar Alves de Oliveira ademar111190@gmail.com
 * @date 2013-aŭg-13 14:59:22
 * 
 */
public class SimbiLog {

	protected static String LOG_TOKEN = "simbiose";
	protected static String LOG_TOKEN_STACK_TRACE = "simbiose_st";

	public static void here() {
		print("Here");
	}

	public static void print(String msg) {
		Log.d(LOG_TOKEN, msg);
	}

	public static void print(Object... params) {
		print(JsonUtils.toJson(params));
	}

	public static void log(Object instance, Object... params) {
		Log.d(LOG_TOKEN_STACK_TRACE, "{\"Method\" : \"" + Thread.currentThread().getStackTrace()[4] + "\", \"instance\" : \"" + JsonUtils.toJson(instance) + "\", \"params\" : " + JsonUtils.toJson(params));
	}

	public static void printException(Exception e) {
		Log.e(LOG_TOKEN, e.toString());
	}

}
