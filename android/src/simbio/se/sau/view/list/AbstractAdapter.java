package simbio.se.sau.view.list;

import simbio.se.sau.model.IAbstractModel;
import android.app.Application;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * This class is a abstract implementation of {@link ArrayAdapter} to do more easy customizations to {@link ListView} {@link Adapter}.
 * 
 * @author Ademar Alves de Oliveira ademar111190@gmail.com
 * @date 2013-aŭg-14 23:57:59
 */
public class AbstractAdapter<T extends IAbstractModel> extends ArrayAdapter<T> {

	/**
	 * Delegate with necessary things. Needs implements {@link IAbstractModel}.
	 */
	protected IAbstractAdapter<T> delegate;

	/**
	 * The layout id in you layout folder to be used on {@link ListView} items.
	 */
	protected int layoutId;

	/**
	 * Default constructor.
	 * 
	 * @param context
	 *            the {@link Application} {@link Context}
	 * @param delegate
	 *            your delegate implements {@link IAbstractAdapter}
	 * @param layoutId
	 *            The layout id in you layout folder to be used on {@link ListView} items.
	 */
	public AbstractAdapter(Context context, IAbstractAdapter<T> delegate, int layoutId) {
		super(context, layoutId);
		this.delegate = delegate;
		this.layoutId = layoutId;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.ArrayAdapter#getView(int, android.view.View, android.view.ViewGroup)
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = inflater.inflate(layoutId, parent, false);
		return view;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.ArrayAdapter#getCount()
	 */
	@Override
	public int getCount() {
		return delegate.getAdapterModels().size();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.ArrayAdapter#getItem(int)
	 */
	@Override
	public T getItem(int position) {
		return delegate.getAdapterModels().get(position);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.BaseAdapter#isEmpty()
	 */
	@Override
	public boolean isEmpty() {
		return delegate.getAdapterModels().isEmpty();
	}

}
