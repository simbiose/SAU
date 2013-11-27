/**
 * 
 */
package simbio.se.sau.sample;

import java.util.ArrayList;

import simbio.se.sau.database.DatabaseDelegate;
import simbio.se.sau.database.DatabaseHelper;
import simbio.se.sau.sample.sql.SqlFooModel;
import simbio.se.sau.sample.sql.SqlListViewAdapter;
import simbio.se.sau.sample.sql.SqlListViewAdapter.SqlAdapterDelegate;
import simbio.se.sau.sample.sql.SqlManager;
import simbio.se.sau.widget.ToastMaker;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;

/**
 * Example of sql and list view
 * 
 * @author Ademar Alves de Oliveira (ademar111190@gmail.com)
 * @date Nov 26, 2013 4:40:14 PM
 * 
 */
public class SqlExampleActivity extends Activity implements DatabaseDelegate, SqlAdapterDelegate {

	public static final int REQUEST_ADD = 0x00000001;
	public static final int REQUEST_DELETE = 0x00000002;
	public static final int REQUEST_LOAD = 0x00000003;
	public static final int REQUEST_DELETE_ALL = 0x00000004;

	private ListView listView;
	private ProgressBar progressBar;
	private EditText editText;

	private SqlListViewAdapter adapter;
	private ArrayList<SqlFooModel> data = new ArrayList<SqlFooModel>();

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sql);

		// loadViews
		listView = (ListView) findViewById(R.id.sql_list_view);
		progressBar = (ProgressBar) findViewById(R.id.sql_progressBar);
		editText = (EditText) findViewById(R.id.sql_edt_new_text);

		// create managers
		adapter = new SqlListViewAdapter(getApplicationContext(), this);

		listView.setEmptyView(findViewById(R.id.sql_textView_empty_view));
		listView.setAdapter(adapter);

		reloadData(null);
	}

	public void reloadData(View view) {
		listView.setVisibility(View.INVISIBLE);
		progressBar.setVisibility(View.VISIBLE);
		new SqlManager(getApplicationContext(), this).select(SqlFooModel.class, REQUEST_LOAD);
	}

	public void addText(View view) {
		new SqlManager(getApplicationContext(), this).insert(new SqlFooModel(editText.getText().toString()), REQUEST_ADD);
	}

	public void clearData(View view) {
		new SqlManager(getApplicationContext(), this).clearTable(REQUEST_DELETE_ALL, SqlFooModel.class);
	}

	@Override
	public void delete(int index) {
		new SqlManager(getApplicationContext(), this).delete(getAdapterModels().get(index), REQUEST_DELETE);
	}

	@Override
	public void onRequestFail(DatabaseHelper databaseHelper, int requestId, Exception exception) {
		ToastMaker.toast(getApplicationContext(), exception.toString());
	}

	@SuppressWarnings("unchecked")
	@Override
	public void onRequestSuccess(DatabaseHelper databaseHelper, int requestId, Object object) {
		// close the database, it is important to performance issues
		databaseHelper.close();

		switch (requestId) {
		case REQUEST_LOAD:
			data.clear();
		case REQUEST_ADD:
			data.addAll((ArrayList<SqlFooModel>) object);
			adapter.notifyDataSetChanged();
			break;
		case REQUEST_DELETE:
		case REQUEST_DELETE_ALL:
			reloadData(null);
			break;
		}

		listView.setVisibility(View.VISIBLE);
		progressBar.setVisibility(View.INVISIBLE);
	}

	@Override
	public ArrayList<SqlFooModel> getAdapterModels() {
		return data;
	}

}
