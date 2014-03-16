package simbio.se.sau.persistense;

import java.lang.reflect.Type;

import simbio.se.sau.API;
import simbio.se.sau.json.JsonUtils;
import simbio.se.sau.log.SimbiLog;
import android.R.string;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * A helper to use {@link SharedPreferences}
 * 
 * @author Ademar Alves de Oliveira ademar111190@gmail.com
 * @date 2013-aŭg-15 07:28:37
 * @since {@link API#Version_1_0_0}
 */
public class PreferencesHelper {

	/**
	 * the {@link SharedPreferences} instance
	 */
	protected SharedPreferences sharedPreferences;

	/**
	 * the your preferences key, is a good idea change it to your own package name.
	 */
	public String preferencesKey = "simbio.se.sau.persistense";

	/**
	 * The default constructor
	 * 
	 * @param context
	 *            a {@link Context} needed to access the {@link SharedPreferences} data.
	 * @since {@link API#Version_1_0_0}
	 */
	public PreferencesHelper(Context context) {
		sharedPreferences = context.getSharedPreferences(preferencesKey, Context.MODE_PRIVATE);
	}

	/**
	 * Put a {@link Boolean} value with {@link SharedPreferences.Editor#putBoolean(String, boolean)} method and committing it with {@link SharedPreferences.Editor#commit()}.
	 * 
	 * @param key
	 *            The name of the preference to modify.
	 * @param value
	 *            The new value for the preference.
	 * @since {@link API#Version_1_0_0}
	 */
	public void put(String key, boolean value) {
		sharedPreferences.edit().putBoolean(key, value).commit();
	}

	/**
	 * Put a {@link Float} value with {@link SharedPreferences.Editor#putFloat(String, float)} method and committing it with {@link SharedPreferences.Editor#commit()}.
	 * 
	 * @param key
	 *            The name of the preference to modify.
	 * @param value
	 *            The new value for the preference.
	 * @since {@link API#Version_1_0_0}
	 */
	public void put(String key, float value) {
		sharedPreferences.edit().putFloat(key, value).commit();
	}

	/**
	 * Put a {@link Integer} value with {@link SharedPreferences.Editor#putInt(String, int)} method and committing it with {@link SharedPreferences.Editor#commit()}.
	 * 
	 * @param key
	 *            The name of the preference to modify.
	 * @param value
	 *            The new value for the preference.
	 * @since {@link API#Version_1_0_0}
	 */
	public void put(String key, int value) {
		sharedPreferences.edit().putInt(key, value).commit();
	}

	/**
	 * Put a {@link Long} value with {@link SharedPreferences.Editor#putLong(String, long)} method and committing it with {@link SharedPreferences.Editor#commit()}.
	 * 
	 * @param key
	 *            The name of the preference to modify.
	 * @param value
	 *            The new value for the preference.
	 * @since {@link API#Version_1_0_0}
	 */
	public void put(String key, long value) {
		sharedPreferences.edit().putLong(key, value).commit();
	}

	/**
	 * Put a {@link String} value with {@link SharedPreferences.Editor#putString(String, String)} method and committing it with {@link SharedPreferences.Editor#commit()}.
	 * 
	 * @param key
	 *            The name of the preference to modify.
	 * @param value
	 *            The new value for the preference.
	 * @since {@link API#Version_1_0_0}
	 */
	public void put(String key, String value) {
		sharedPreferences.edit().putString(key, value).commit();
	}

	/**
	 * Put a {@link Object} value with {@link SharedPreferences.Editor#putString(String, String)} method and committing it with {@link SharedPreferences.Editor#commit()}. Note the object will be transformed in a Json {@link String} with the method
	 * {@link JsonUtils#toJson(Object)} and saved like a {@link string}.
	 * 
	 * @param key
	 *            The name of the preference to modify.
	 * @param value
	 *            The new value for the preference.
	 * @since {@link API#Version_1_0_0}
	 */
	public void put(String key, Object value) {
		sharedPreferences.edit().putString(key, JsonUtils.toJson(value)).commit();
	}

	/**
	 * Retrieve a {@link Boolean} value from the preferences with the {@link SharedPreferences#getBoolean(String, boolean)} method.
	 * 
	 * @param key
	 *            The name of the preference to retrieve.
	 * @param def
	 *            Value to return if this preference does not exist.
	 * @return Returns the preference value if it exists, or the def value.
	 * @since {@link API#Version_1_0_0}
	 */
	public boolean getBoolean(String key, boolean def) {
		boolean value = def;
		try {
			value = sharedPreferences.getBoolean(key, def);
		} catch (Exception e) {
			SimbiLog.printException(e);
		}
		return value;
	}

	/**
	 * Retrieve a {@link Boolean} value from the preferences with the {@link SharedPreferences#getBoolean(String, boolean)} method.
	 * 
	 * @param key
	 *            The name of the preference to retrieve.
	 * @return Returns the preference value if it exists, or true.
	 * @since {@link API#Version_1_0_0}
	 */
	public boolean getBooleanOrTrue(String key) {
		return getBoolean(key, true);
	}

	/**
	 * Retrieve a {@link Boolean} value from the preferences with the {@link SharedPreferences#getBoolean(String, boolean)} method.
	 * 
	 * @param key
	 *            The name of the preference to retrieve.
	 * @return Returns the preference value if it exists, or false.
	 * @since {@link API#Version_1_0_0}
	 */
	public boolean getBooleanOrFalse(String key) {
		return getBoolean(key, false);
	}

	/**
	 * Retrieve a {@link Float} value from the preferences with the {@link SharedPreferences#getFloat(String, float)} method.
	 * 
	 * @param key
	 *            The name of the preference to retrieve.
	 * @param def
	 *            Value to return if this preference does not exist.
	 * @return Returns the preference value if it exists, or the def value.
	 * @since {@link API#Version_1_0_0}
	 */
	public float getFloat(String key, float def) {
		float value = def;
		try {
			value = sharedPreferences.getFloat(key, def);
		} catch (Exception e) {
			SimbiLog.printException(e);
		}
		return value;
	}

	/**
	 * Retrieve a {@link Float} value from the preferences with the {@link SharedPreferences#getFloat(String, float)} method.
	 * 
	 * @param key
	 *            The name of the preference to retrieve.
	 * @return Returns the preference value if it exists, or zero.
	 * @since {@link API#Version_1_0_0}
	 */
	public float getFloatOrZero(String key) {
		return getFloat(key, 0.0f);
	}

	/**
	 * Retrieve a {@link Integer} value from the preferences with the {@link SharedPreferences#getInt(String, int)} method.
	 * 
	 * @param key
	 *            The name of the preference to retrieve.
	 * @param def
	 *            Value to return if this preference does not exist.
	 * @return Returns the preference value if it exists, or the def value.
	 * @since {@link API#Version_1_0_0}
	 */
	public int getInt(String key, int def) {
		int value = def;
		try {
			value = sharedPreferences.getInt(key, def);
		} catch (Exception e) {
			SimbiLog.printException(e);
		}
		return value;
	}

	/**
	 * Retrieve a {@link Integer} value from the preferences with the {@link SharedPreferences#getInt(String, int)} method.
	 * 
	 * @param key
	 *            The name of the preference to retrieve.
	 * @return Returns the preference value if it exists, or zero.
	 * @since {@link API#Version_1_0_0}
	 */
	public int getIntOrZero(String key) {
		return getInt(key, 0);
	}

	/**
	 * Retrieve a {@link Long} value from the preferences with the {@link SharedPreferences#getLong(String, long)} method.
	 * 
	 * @param key
	 *            The name of the preference to retrieve.
	 * @param def
	 *            Value to return if this preference does not exist.
	 * @return Returns the preference value if it exists, or the def value.
	 * @since {@link API#Version_1_0_0}
	 */
	public long getLong(String key, long def) {
		long value = def;
		try {
			value = sharedPreferences.getLong(key, def);
		} catch (Exception e) {
			SimbiLog.printException(e);
		}
		return value;
	}

	/**
	 * Retrieve a {@link Long} value from the preferences with the {@link SharedPreferences#getLong(String, long)} method.
	 * 
	 * @param key
	 *            The name of the preference to retrieve.
	 * @return Returns the preference value if it exists, or zero.
	 * @since {@link API#Version_1_0_0}
	 */
	public long getLongOrZero(String key) {
		return getLong(key, 0l);
	}

	/**
	 * Retrieve a {@link String} value from the preferences with the {@link SharedPreferences#getString(String, String)} method.
	 * 
	 * @param key
	 *            The name of the preference to retrieve.
	 * @param def
	 *            Value to return if this preference does not exist.
	 * @return Returns the preference value if it exists, or the def value.
	 * @since {@link API#Version_1_0_0}
	 */
	public String getString(String key, String def) {
		String value = def;
		try {
			value = sharedPreferences.getString(key, def);
		} catch (Exception e) {
			SimbiLog.printException(e);
		}
		return value;
	}

	/**
	 * Retrieve a {@link String} value from the preferences with the {@link SharedPreferences#getString(String, String)} method.
	 * 
	 * @param key
	 *            The name of the preference to retrieve.
	 * @return Returns the preference value if it exists, or <code>null</code>.
	 * @since {@link API#Version_1_0_0}
	 */
	public String getStringOrNull(String key) {
		return getString(key, null);
	}

	/**
	 * Retrieve a {@link String} value from the preferences with the {@link SharedPreferences#getString(String, String)} method.
	 * 
	 * @param key
	 *            The name of the preference to retrieve.
	 * @return Returns the preference value if it exists, or a empty {@link String}.
	 * @since {@link API#Version_1_0_0}
	 */
	public String getStringOrEmpty(String key) {
		return getString(key, "");
	}

	/**
	 * Retrieve a {@link Object} value from the preferences with the {@link SharedPreferences#getString(String, String)} method. Note this will be retrive a {@link String} and try convert it to your {@link Object} with the method
	 * {@link JsonUtils#fromJsonOrNull(String, Class)}.
	 * 
	 * @param key
	 *            The name of the preference to retrieve.
	 * @param theClass
	 *            The {@link Class} of object retrieved
	 * @param def
	 *            Value to return if this preference does not exist.
	 * @return Returns the preference value if it exists, or the def value.
	 * @since {@link API#Version_1_0_0}
	 */
	public Object getObject(String key, Class<?> theClass, Object def) {
		String string = getStringOrNull(key);
		if (string == null)
			return def;
		Object object = JsonUtils.fromJsonOrNull(string, theClass);
		if (object == null)
			return def;
		return object;
	}

	/**
	 * Retrieve a {@link Object} value from the preferences with the {@link SharedPreferences#getString(String, String)} method. Note this will be retrive a {@link String} and try convert it to your {@link Object} with the method
	 * {@link JsonUtils#fromJsonOrNull(String, Class)}.
	 * 
	 * @param key
	 *            The name of the preference to retrieve.
	 * @param theClass
	 *            The {@link Class} of object retrieved
	 * @return Returns the preference value if it exists, or <code>null</code>.
	 * @since {@link API#Version_1_0_0}
	 */
	public Object getObjectOrNull(String key, Class<?> theClass) {
		return getObject(key, theClass, null);
	}

	/**
	 * Retrieve a {@link Object} value from the preferences with the {@link SharedPreferences#getString(String, String)} method. Note this will be retrive a {@link String} and try convert it to your {@link Object} with the method
	 * {@link JsonUtils#fromJsonOrNull(String, Class)}.
	 * 
	 * @param key
	 *            The name of the preference to retrieve.
	 * @param type
	 *            The {@link Type} of object retrieved
	 * @param def
	 *            Value to return if this preference does not exist.
	 * @return Returns the preference value if it exists, or the def value.
	 * @since {@link API#Version_2_0_0}
	 */
	public Object getObject(String key, Type type, Object def) {
		String string = getStringOrNull(key);
		if (string == null)
			return def;
		Object object = JsonUtils.fromJsonWithTypeOrNull(string, type);
		if (object == null)
			return def;
		return object;
	}

	/**
	 * Retrieve a {@link Object} value from the preferences with the {@link SharedPreferences#getString(String, String)} method. Note this will be retrive a {@link String} and try convert it to your {@link Object} with the method
	 * {@link JsonUtils#fromJsonOrNull(String, Class)}.
	 * 
	 * @param key
	 *            The name of the preference to retrieve.
	 * @param type
	 *            The {@link Type} of object retrieved
	 * @return Returns the preference value if it exists, or <code>null</code>.
	 * @since {@link API#Version_2_0_0}
	 */
	public Object getObjectOrNull(String key, Type type) {
		return getObject(key, type, null);
	}
}
