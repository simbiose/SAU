package simbio.se.sau.sample.sql;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import simbio.se.sau.adapters.AbstractAdapter;
import simbio.se.sau.adapters.IAbstractAdapter;
import simbio.se.sau.log.SimbiLog;
import simbio.se.sau.sample.R;
import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * @author Ademar Alves de Oliveira (ademar111190@gmail.com)
 * @date Nov 26, 2013 5:34:32 PM
 * 
 */
@SuppressLint("ViewConstructor")
public class SqlListViewAdapter extends AbstractAdapter<SqlFooModel> implements OnClickListener {

	/**
	 * An example how use the {@link IAbstractAdapter}
	 */
	public interface SqlAdapterDelegate extends IAbstractAdapter<SqlFooModel> {
		public void delete(int index);
	}

	private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.FRENCH);

	public SqlListViewAdapter(Context context, SqlAdapterDelegate delegate) {
		super(context, delegate, R.layout.list_item_sql);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = super.getView(position, convertView, parent);

		TextView textViewText = (TextView) view.findViewById(R.id.text_sql_item);
		TextView textViewDate = (TextView) view.findViewById(R.id.text_sql_item_date);
		Button buttonDelete = (Button) view.findViewById(R.id.button_delete_it);

		SqlFooModel sqlFooModel = delegate.getAdapterModels().get(position);

		textViewText.setText(sqlFooModel.getText());
		textViewDate.setText(String.format(Locale.FRENCH, getContext().getString(R.string.added_on), simpleDateFormat.format(new Date(sqlFooModel.getTime()))));

		buttonDelete.setTag(position); // we will get the position to send to delegate
		buttonDelete.setOnClickListener(this);

		return view;
	}

	@Override
	public void onClick(View v) {
		if (v == null || v.getTag() == null)
			return;
		try {
			int index = Integer.parseInt(v.getTag().toString());
			((SqlAdapterDelegate) delegate).delete(index);
		} catch (Exception e) {
			SimbiLog.printException(e);
		}
	}
}
