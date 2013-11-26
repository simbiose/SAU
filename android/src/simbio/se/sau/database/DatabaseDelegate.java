package simbio.se.sau.database;

import simbio.se.sau.API;

/**
 * @author Ademar Alves de Oliveira ademar111190@gmail.com
 * @date 2013-aŭg-14 23:57:59
 * @since {@link API#Version_3_0_0}
 */
public interface DatabaseDelegate {

	/**
	 * This method will called when some error occurr on sql query
	 * 
	 * @param databaseHelper
	 *            the {@link DatabaseHelper} responsible
	 * @param requestId
	 *            the request id
	 * @param exception
	 *            the {@link Exception} occured
	 * @since {@link API#Version_3_0_0}
	 */
	public void onRequestFail(DatabaseHelper databaseHelper, int requestId, Exception exception);

	/**
	 * This method will called when some error occurr on sql query
	 * 
	 * @param databaseHelper
	 *            the {@link DatabaseHelper} responsible
	 * @param requestId
	 *            the request id
	 * @param object
	 *            the {@link Object} returned or null if is a void request like insert
	 * @since {@link API#Version_3_0_0}
	 */
	public void onRequestSuccess(DatabaseHelper databaseHelper, int requestId, Object object);

}
