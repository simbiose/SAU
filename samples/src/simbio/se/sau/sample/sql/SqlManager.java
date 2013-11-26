/**
 * 
 */
package simbio.se.sau.sample.sql;

import java.util.ArrayList;
import java.util.List;

import simbio.se.sau.database.DatabaseDelegate;
import simbio.se.sau.database.DatabaseHelper;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * You need extend the {@link DatabaseHelper} and the method {@link DatabaseHelper#getModelsToBeCreatedOnDatabase()} is very importante
 * 
 * @author Ademar Alves de Oliveira (ademar111190@gmail.com)
 * @date Nov 26, 2013 5:18:34 PM
 * 
 */
public class SqlManager extends DatabaseHelper {

	public SqlManager(Context context, DatabaseDelegate delegate) {
		super(context, delegate, "sauExample.sqlite", 1);
	}

	@Override
	protected List<Class<?>> getModelsToBeCreatedOnDatabase() {
		List<Class<?>> clazzez = new ArrayList<Class<?>>();
		clazzez.add(SqlFooModel.class);
		return clazzez;
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	}

}
