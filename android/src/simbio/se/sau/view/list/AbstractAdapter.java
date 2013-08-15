package simbio.se.sau.view.list;

import simbio.se.sau.model.IAbstractModel;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

/**
 * @author Ademar Alves de Oliveira ademar111190@gmail.com
 * @date 2013-aŭg-14 23:57:59
 * 
 */
public class AbstractAdapter extends ArrayAdapter<IAbstractModel> {

	protected IAbstractAdapter delegate;
	protected int layoutId;

	public AbstractAdapter(Context context, IAbstractAdapter delegate, int layoutId) {
		super(context, layoutId);
		this.delegate = delegate;
		this.layoutId = layoutId;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = inflater.inflate(layoutId, parent, false);
		return view;
	}

	@Override
	public int getCount() {
		return delegate.getAdapterModels().size();
	}

	@Override
	public IAbstractModel getItem(int position) {
		return delegate.getAdapterModels().get(position);
	}

	@Override
	public boolean isEmpty() {
		return delegate.getAdapterModels().isEmpty();
	}

}
