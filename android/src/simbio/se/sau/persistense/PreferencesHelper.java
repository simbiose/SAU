package simbio.se.sau.persistense;

import simbio.se.sau.json.JsonUtils;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * @author Ademar Alves de Oliveira ademar111190@gmail.com
 * @date 2013-aŭg-15 07:28:37
 * 
 */
public class PreferencesHelper {

	protected SharedPreferences sharedPreferences;
	protected String preferencesKey = "simbio.se.sau.persistense";

	public PreferencesHelper(Context context) {
		sharedPreferences = context.getSharedPreferences(preferencesKey, Context.MODE_PRIVATE);
	}

	public void put(String key, boolean value) {
		sharedPreferences.edit().putBoolean(key, value).commit();
	}

	public void put(String key, float value) {
		sharedPreferences.edit().putFloat(key, value).commit();
	}

	public void put(String key, int value) {
		sharedPreferences.edit().putInt(key, value).commit();
	}

	public void put(String key, long value) {
		sharedPreferences.edit().putLong(key, value).commit();
	}

	public void put(String key, String value) {
		sharedPreferences.edit().putString(key, value).commit();
	}

	public void put(String key, Object value) {
		sharedPreferences.edit().putString(key, JsonUtils.toJson(value)).commit();
	}

	public boolean getBoolean(String key, boolean def) {
		return sharedPreferences.getBoolean(key, def);
	}

	public boolean getBooleanOrTrue(String key) {
		return getBoolean(key, true);
	}

	public boolean getBooleanOrFalse(String key) {
		return getBoolean(key, false);
	}

	public float getFloat(String key, float def) {
		return sharedPreferences.getFloat(key, def);
	}

	public float getFloatOrZero(String key) {
		return getFloat(key, 0.0f);
	}

	public int getInt(String key, int def) {
		return sharedPreferences.getInt(key, def);
	}

	public int getIntOrZero(String key) {
		return getInt(key, 0);
	}

	public long getLong(String key, long def) {
		return sharedPreferences.getLong(key, def);
	}

	public long getLongOrZero(String key) {
		return getLong(key, 0l);
	}

	public String getString(String key, String def) {
		return sharedPreferences.getString(key, def);
	}

	public String getStringOrNull(String key) {
		return getString(key, null);
	}

	public String getStringOrEmpty(String key) {
		return getString(key, "");
	}

	public Object getObject(String key, Class<Object> theClass, Object def) {
		String string = getStringOrNull(key);
		if (string == null)
			return def;
		Object object = JsonUtils.fromJsonOrNull(string, theClass);
		if (object == null)
			return def;
		return object;
	}

	public Object getObjectOrNull(String key, Class<Object> theClass) {
		return getObject(key, theClass, null);
	}
}
