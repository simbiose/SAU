package simbio.se.sau.database;

import java.util.ArrayList;
import java.util.Set;

import simbio.se.sau.API;
import simbio.se.shiva.Shiva;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Handler;

/**
 * The SQLite Helper, extends it to use the helper
 * 
 * @author Ademar Alves de Oliveira ademar111190@gmail.com
 * @date 2013-sep-30 17:16:17
 * @since {@link API#Version_3_0_0}
 */
public abstract class DatabaseHelper extends SQLiteOpenHelper {

	// class fields
	protected DatabaseDelegate delegate;
	protected Handler handler;

	/**
	 * @return
	 */
	protected abstract Set<Class<?>> getModelsToBeCreatedOnDatabase();

	/**
	 * Default constructor
	 * 
	 * @param context
	 *            the {@link Context}
	 * @param delegate
	 *            the {@link DatabaseDelegate} to handle the events
	 * @param databaseName
	 *            an name to be used on database, not send <code>null</code>
	 * @param databaseVersion
	 *            the version of database, is a good idea start with 1 and add more 1 each time you need update the database structure
	 * @since {@link API#Version_3_0_0}
	 */
	public DatabaseHelper(Context context, DatabaseDelegate delegate, String databaseName, int databaseVersion) {
		this(context, delegate, databaseName, databaseVersion, null);
	}

	/**
	 * Constructor with {@link CursorFactory}
	 * 
	 * @param context
	 *            the {@link Context}
	 * @param delegate
	 *            the {@link DatabaseDelegate} to handle the events
	 * @param databaseName
	 *            an name to be used on database, not send <code>null</code>
	 * @param databaseVersion
	 *            the version of database, is a good idea start with 1 and add more 1 each time you need update the database structure, more details see {@link SQLiteOpenHelper#onUpgrade(SQLiteDatabase, int, int)} and
	 *            {@link SQLiteOpenHelper#onDowngrade(SQLiteDatabase, int, int)}
	 * @param cursorFactory
	 *            the {@link CursorFactory}, can be <code>null</code>
	 * @since {@link API#Version_3_0_0}
	 */
	public DatabaseHelper(Context context, DatabaseDelegate delegate, String databaseName, int databaseVersion, CursorFactory cursorFactory) {
		super(context, databaseName, cursorFactory, databaseVersion);
		this.delegate = delegate;
		this.handler = new Handler();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.database.sqlite.SQLiteOpenHelper#onCreate(android.database.sqlite.SQLiteDatabase)
	 */
	@Override
	public void onCreate(SQLiteDatabase db) {
		Set<Class<?>> clazzez = getModelsToBeCreatedOnDatabase();
		if (clazzez != null)
			for (Class<?> clazz : clazzez)
				onCreateHelper(db, clazz);
	}

	/**
	 * Only a {@link DatabaseHelper#onCreate(SQLiteDatabase)} macro helper
	 * 
	 * @param db
	 *            the {@link SQLiteDatabase} to call the {@link SQLiteDatabase#execSQL(String)} method
	 * @param clazz
	 *            a {@link Class} to get sql create query with {@link Shiva#toCreateTableQuery(Class)}
	 * @since {@link API#Version_3_0_0}
	 */
	protected void onCreateHelper(SQLiteDatabase db, Class<?> clazz) {
		db.execSQL(Shiva.toCreateTableQuery(clazz));
	}

	/**
	 * This method send to delegate the return error of sqlite, it means, the sqlite query will run on backgrounds {@link Thread}, so this method return to main {@link Thread} and send the return
	 * 
	 * @param requestId
	 *            the request id
	 * @param exception
	 *            the {@link Exception}
	 * @see DatabaseDelegate#onRequestFail(DatabaseHelper, int, Exception)
	 * @since {@link API#Version_3_0_0}
	 */
	protected void sendRequestFail(final int requestId, final Exception exception) {
		if (delegate != null)
			handler.post(new Runnable() {
				@Override
				public void run() {
					delegate.onRequestFail(DatabaseHelper.this, requestId, exception);
				}
			});
	}

	/**
	 * This method send to delegate the return success of sqlite, it means, the sqlite query will run on backgrounds {@link Thread}, so this method return to main {@link Thread} and send the return
	 * 
	 * @param requestId
	 *            the request id
	 * @param object
	 *            the {@link Object} returned
	 * @see DatabaseDelegate#onRequestSuccess(DatabaseHelper, int, Object)
	 * @since {@link API#Version_3_0_0}
	 */
	public void sendRequestSuccess(final int requestId, final Object object) {
		if (delegate != null)
			handler.post(new Runnable() {
				@Override
				public void run() {
					delegate.onRequestSuccess(DatabaseHelper.this, requestId, object);
				}
			});
	}

	/**
	 * Inserta {@link WritableSql} on database, this will run on a background {@link Thread}
	 * 
	 * @param object
	 *            the {@link Object} to be saved
	 * @param requestId
	 *            the id of request to be handled on {@link DatabaseDelegate#onRequestSuccess(DatabaseHelper, int, Object)} or {@link DatabaseDelegate#onRequestFail(DatabaseHelper, int, Exception)}
	 * @since {@link API#Version_3_0_0}
	 */
	public void insert(Object object, int requestId) {
		if (object == null)
			sendRequestFail(requestId, new NullPointerException());
		else {
			ArrayList<Object> objects = new ArrayList<Object>();
			objects.add(object);
			insert(objects, requestId);
		}
	}

	/**
	 * Inserta {@link WritableSql} on database, this will run on a background {@link Thread}
	 * 
	 * @param objects
	 *            an {@link ArrayList} of {@link Object} to be saved
	 * @param requestId
	 *            the id of request to be handled on {@link DatabaseDelegate#onRequestSuccess(DatabaseHelper, int, Object)} or {@link DatabaseDelegate#onRequestFail(DatabaseHelper, int, Exception)}
	 * @since {@link API#Version_3_0_0}
	 */
	public void insert(final ArrayList<Object> objects, final int requestId) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				if (objects == null)
					sendRequestFail(requestId, new NullPointerException());
				else {
					SQLiteDatabase sqLiteDatabase = getWritableDatabase();
					for (Object object : objects)
						if (object != null)
							sqLiteDatabase.insert(Shiva.toTableName(object), null, ShivaAndroidUtils.getContentValuesFromObject(object));
					sendRequestSuccess(requestId, objects);
				}
			}
		}).start();
	}

	/**
	 * This method run a select query on database
	 * 
	 * @param clazz
	 *            the {@link Class} to be selected, it means, the return will be a list of that objects. a <code>null</code> causes {@link DatabaseDelegate#onRequestFail(DatabaseHelper, int, Exception)} with {@link NullPointerException}
	 * @param tableColumns
	 *            A list of which columns to return. Passing null will return all columns, which is discouraged to prevent reading data from storage that isn't going to be used.
	 * @param whereClause
	 *            A filter declaring which rows to return, formatted as an SQL WHERE clause (excluding the WHERE itself). Passing null will return all rows for the given table.
	 * @param whereArgs
	 *            You may include ?s in selection, which will be replaced by the values from selectionArgs, in order that they appear in the selection. The values will be bound as Strings.
	 * @param orderBy
	 *            A filter declaring how to group rows, formatted as an SQL GROUP BY clause (excluding the GROUP BY itself). Passing null will cause the rows to not be grouped.
	 * @param requestId
	 *            the id of request to be handled on {@link DatabaseDelegate#onRequestSuccess(DatabaseHelper, int, Object)} or {@link DatabaseDelegate#onRequestFail(DatabaseHelper, int, Exception)}
	 * @see SQLiteDatabase#query(String, String[], String, String[], String, String, String)
	 * @since {@link API#Version_3_0_0}
	 */
	public void select(final Class<?> clazz, final String[] tableColumns, final String whereClause, final String[] whereArgs, final String orderBy, final int requestId) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				if (clazz == null) {
					sendRequestFail(requestId, new NullPointerException());
					return;
				}
				ArrayList<Object> objects = new ArrayList<Object>();
				SQLiteDatabase sqLiteDatabase = getReadableDatabase();
				Cursor cursor = sqLiteDatabase.query(Shiva.toTableName(clazz), tableColumns, whereClause, whereArgs, null, null, orderBy);
				if (cursor.moveToFirst())
					do {
						objects.add(ShivaAndroidUtils.getObjectFromCursor(clazz, cursor, tableColumns));
					} while (cursor.moveToNext());
				sendRequestSuccess(requestId, objects);
				cursor.close();
			}
		}).start();
	}

	/**
	 * This method just calls the {@link DatabaseHelper#select(WritableSql, String[], String, String[], String, int)} method with null params where it cause an all selection
	 * 
	 * @param example
	 *            the {@link Class} of example, it means, the return will be a list of that objects. a <code>null</code> causes {@link DatabaseDelegate#onRequestFail(DatabaseHelper, int, Exception)} with {@link NullPointerException}
	 * @param requestId
	 *            the id of request to be handled on {@link DatabaseDelegate#onRequestSuccess(DatabaseHelper, int, Object)} or {@link DatabaseDelegate#onRequestFail(DatabaseHelper, int, Exception)}
	 * @see {@link DatabaseHelper#select(WritableSql, String[], String, String[], String, int)}
	 * @since {@link API#Version_3_0_0}
	 */
	public void selectAll(Class<?> clazz, int requestId) {
		select(clazz, null, null, null, null, requestId);
	}

	/**
	 * Delete all data, Caution!
	 * 
	 * @param requestId
	 *            the reques id
	 * @param clazzez
	 *            the {@link Class} type to be deleted on database
	 * @since {@link API#Version_3_0_0}
	 */
	public void clearTables(final int requestId, final ArrayList<Class<?>> clazzez) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				SQLiteDatabase sqLiteDatabase = getWritableDatabase();

				for (Class<?> clazz : clazzez)
					sqLiteDatabase.delete(Shiva.toTableName(clazz), null, null);

				sendRequestSuccess(requestId, null);
			}
		}).start();
	}

	/**
	 * Delete {@link WritableSql} on database, this will run on a background {@link Thread}
	 * 
	 * @param object
	 *            the {@link Object} to be saved
	 * @param whereClause
	 *            A filter declaring which rows to return, formatted as an SQL WHERE clause (excluding the WHERE itself). Passing null will return all rows for the given table.
	 * @param whereArgs
	 *            You may include ?s in selection, which will be replaced by the values from selectionArgs, in order that they appear in the selection. The values will be bound as Strings.
	 * @param requestId
	 *            the id of request to be handled on {@link DatabaseDelegate#onRequestSuccess(DatabaseHelper, int, Object)} or {@link DatabaseDelegate#onRequestFail(DatabaseHelper, int, Exception)}
	 * @since {@link API#Version_3_0_0}
	 */
	public void delete(Object object, final String whereClause, final String[] whereArgs, int requestId) {
		if (object == null)
			sendRequestFail(requestId, new NullPointerException());
		else {
			ArrayList<Object> objects = new ArrayList<Object>();
			objects.add(object);
			delete(objects, whereClause, whereArgs, requestId);
		}
	}

	/**
	 * Delete {@link WritableSql} on database, this will run on a background {@link Thread}
	 * 
	 * @param objects
	 *            an {@link ArrayList} of {@link Object} to be deleted
	 * @param whereClause
	 *            A filter declaring which rows to return, formatted as an SQL WHERE clause (excluding the WHERE itself). Passing null will return all rows for the given table.
	 * @param whereArgs
	 *            You may include ?s in selection, which will be replaced by the values from selectionArgs, in order that they appear in the selection. The values will be bound as Strings.
	 * @param requestId
	 *            the id of request to be handled on {@link DatabaseDelegate#onRequestSuccess(DatabaseHelper, int, Object)} or {@link DatabaseDelegate#onRequestFail(DatabaseHelper, int, Exception)}
	 * @since {@link API#Version_3_0_0}
	 */
	public void delete(final ArrayList<Object> objects, final String whereClause, final String[] whereArgs, final int requestId) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				if (objects == null)
					sendRequestFail(requestId, new NullPointerException());
				else {
					SQLiteDatabase sqLiteDatabase = getWritableDatabase();
					for (Object object : objects)
						if (object != null)
							sqLiteDatabase.delete(Shiva.toTableName(object), whereClause, whereArgs);
					sendRequestSuccess(requestId, objects);
				}
			}
		}).start();
	}

	/**
	 * Update {@link WritableSql} on database, this will run on a background {@link Thread}
	 * 
	 * @param object
	 *            the {@link Object} to be saved
	 * @param whereClause
	 *            A filter declaring which rows to return, formatted as an SQL WHERE clause (excluding the WHERE itself). Passing null will return all rows for the given table.
	 * @param whereArgs
	 *            You may include ?s in selection, which will be replaced by the values from selectionArgs, in order that they appear in the selection. The values will be bound as Strings.
	 * @param requestId
	 *            the id of request to be handled on {@link DatabaseDelegate#onRequestSuccess(DatabaseHelper, int, Object)} or {@link DatabaseDelegate#onRequestFail(DatabaseHelper, int, Exception)}
	 * @since {@link API#Version_3_0_0}
	 */
	public void update(Object object, final String whereClause, final String[] whereArgs, int requestId) {
		if (object == null)
			sendRequestFail(requestId, new NullPointerException());
		else {
			ArrayList<Object> objects = new ArrayList<Object>();
			objects.add(object);
			update(objects, whereClause, whereArgs, requestId);
		}
	}

	/**
	 * Update {@link WritableSql} on database, this will run on a background {@link Thread}
	 * 
	 * @param objects
	 *            an {@link ArrayList} of {@link Object} to be saved
	 * @param whereClause
	 *            A filter declaring which rows to return, formatted as an SQL WHERE clause (excluding the WHERE itself). Passing null will return all rows for the given table.
	 * @param whereArgs
	 *            You may include ?s in selection, which will be replaced by the values from selectionArgs, in order that they appear in the selection. The values will be bound as Strings.
	 * @param requestId
	 *            the id of request to be handled on {@link DatabaseDelegate#onRequestSuccess(DatabaseHelper, int, Object)} or {@link DatabaseDelegate#onRequestFail(DatabaseHelper, int, Exception)}
	 * @since {@link API#Version_3_0_0}
	 */
	public void update(final ArrayList<?> objects, final String whereClause, final String[] whereArgs, final int requestId) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				if (objects == null)
					sendRequestFail(requestId, new NullPointerException());
				else {
					SQLiteDatabase sqLiteDatabase = getWritableDatabase();
					for (Object object : objects)
						if (object != null)
							sqLiteDatabase.update(Shiva.toTableName(object), ShivaAndroidUtils.getContentValuesFromObject(object), whereClause, whereArgs);
					sendRequestSuccess(requestId, objects);
				}
			}
		}).start();
	}

}
