package simbio.se.sau.log;

import simbio.se.sau.API;
import simbio.se.sau.json.JsonUtils;
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
	 * A flexible log, here you can send a variable number of parameters and get a log formated with Json style. if you want a stack trace see {@link SimbiLog#log(Object, Object...)} method.
	 * 
	 * @param params
	 *            to log
	 * @since {@link API#Version_1_0_0}
	 */
	public static void print(Object... params) {
		Log.d(LOG_TOKEN, JsonUtils.toJson(params));
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
		Log.d(LOG_TOKEN_STACK_TRACE,
				String.format("{\"instance\":\"%s\",\"instanceClass\":\"%s\",\"params\":%s,\"stack\":%s}", instance, (instance == null ? "Null Instance" : instance.getClass()), JsonUtils.toJson(params),
						JsonUtils.toJson(Thread.currentThread().getStackTrace())));
	}

	/**
	 * This method prints a {@link Exception} with {@link Log#e(String, String)} method on Json format.
	 * 
	 * @param e
	 *            the {@link Exception} you want log
	 * @since {@link API#Version_1_0_0}
	 */
	public static void printException(Exception e) {
		Log.e(LOG_TOKEN, JsonUtils.toJson(new Object[] { e.toString(), e.getCause(), e.getMessage(), e.getLocalizedMessage(), e.getStackTrace() }));
	}

}
