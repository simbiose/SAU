/**
 * 
 */
package simbio.se.sau.database;

import simbio.se.sau.API;
import simbio.se.sau.log.SimbiLog;
import simbio.se.shiva.Shiva;
import android.content.ContentValues;
import android.database.Cursor;
import android.widget.TableLayout;

/**
 * A lot of utilities to do {@link Shiva} more easy on android
 * 
 * @author Ademar Alves de Oliveira (ademar111190@gmail.com)
 * @date Nov 26, 2013 3:36:23 PM
 * @since {@link API#Version_3_0_0}
 */
public class ShivaAndroidUtils {

	/**
	 * @param object
	 *            the {@link Object} to provide the {@link ContentValues} data
	 * @return a {@link ContentValues} of {@link Object}
	 * @since {@link API#Version_3_0_0}
	 */
	public static ContentValues getContentValuesFromObject(Object object) {
		if (object == null)
			return null;

		ContentValues contentValues = new ContentValues();
		// TODO
		return contentValues;
	}

	/**
	 * @param clazz
	 *            the {@link Class} to be used as model
	 * @param cursor
	 *            the {@link Cursor} with data from sql
	 * @param tableColumns
	 *            the {@link TableLayout} columns names
	 * @return the {@link Object} instance from clazz variable with {@link Cursor} values
	 * @since {@link API#Version_3_0_0}
	 */
	public static Object getObjectFromCursor(Class<?> clazz, Cursor cursor, String[] tableColumns) {
		Object object;
		try {
			object = clazz.newInstance();
		} catch (Exception e) {
			SimbiLog.printException(e);
			return null;
		}
		// TODO
		return object;
	}

}
