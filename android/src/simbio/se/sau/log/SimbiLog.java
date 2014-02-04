package simbio.se.sau.log;

import simbio.se.sau.API;
import android.util.Log;

/**
 * This class provide methods to log on {@link Log} messages with Json format and methods with easy and flexible inputs
 * 
 * @author Ademar Alves de Oliveira ademar111190@gmail.com
 * @date 2013-aŭg-13 14:59:22
 * @since {@link API#Version_1_0_0}
 */
public class SimbiLog {

	/**
	 * The token to {@link Log}, you can change it
	 */
	public static String LOG_TOKEN = "simbiose";

	/**
	 * The token to {@link Log} when logging in stack trace mode, you can change it
	 */
	public static String LOG_TOKEN_STACK_TRACE = "simbiose_st";

	/**
	 * Prints a "here" message to verify thinks like "My code passed through here?"
	 * 
	 * @since {@link API#Version_1_0_0}
	 */
	public static void here() {
		print("Here");
	}

	/**
	 * @param params
	 *            a list with any {@link Object}
	 * @return a {@link String} to be logged
	 * @since {@link API#Version_3_1_3}
	 */
	public static String makeStringToLog(Object[] params) {
		StringBuilder stringBuilder = new StringBuilder("{");
		if (params == null)
			stringBuilder.append("\"params\":null}");
		else {
			int count = 0;
			for (Object object : params) {
				stringBuilder.append(String.format("\"param_%d\":", ++count));
				if (object == null)
					stringBuilder.append("null,");
				else
					stringBuilder.append(String.format("\"%s\",", (object + "").replace("\"", "\\\"")));
			}
			stringBuilder.delete(stringBuilder.length() - 1, stringBuilder.length());
			stringBuilder.append("}");
		}
		return stringBuilder.toString();
	}

	/**
	 * Log a plain text, no json formatted
	 * 
	 * @param params
	 *            to log
	 * @since {@link API#Version_1_0_0}
	 */
	public static void print(Object... params) {
		Log.d(LOG_TOKEN, makeStringToLog(params));
	}

	/**
	 * This is a stack trace method, if you want a normal log use {@link SimbiLog#print(Object...)} method. This method print the instance, instance class, parameters and stack trace from {@link Thread} in a json format.
	 * 
	 * @param instance
	 *            the instance, normally use this reference.
	 * @param params
	 *            anything you want see besides the instance and stack trace, this support a variable number of parameters.
	 * @since {@link API#Version_1_0_0}
	 */
	public static void log(Object instance, Object... params) {
		Log.v(LOG_TOKEN_STACK_TRACE, String.format("{\"instance\":\"%s\", \"params\":%s, \"stackTrace\":%s}", instance, makeStringToLog(params), makeStringToLog(Thread.currentThread().getStackTrace())));
	}

	/**
	 * This method prints a {@link Exception} with {@link Log#e(String, String)} method on Json format.
	 * 
	 * @param e
	 *            the {@link Exception} you want log
	 * @since {@link API#Version_1_0_0}
	 */
	public static void printException(Exception e) {
		if (e == null)
			Log.e(LOG_TOKEN, "null Exception");
		else
			Log.e(LOG_TOKEN, String.format("{\"string\":\"%s\", \"cause\":\"%s\", \"message\":\"%s\", \"localizedMessage\":\"%s\", \"stackTrace\":\"%s\"}", e.toString(), e.getCause(), e.getMessage(), e.getLocalizedMessage(), e.getStackTrace()));
	}

}
