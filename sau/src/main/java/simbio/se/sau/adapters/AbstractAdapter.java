/**
 * Copyright 2013-2014
 * Ademar Alves de Oliveira <ademar111190@gmail.com /> Simbio.se
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package simbio.se.sau.adapters;

import android.app.Application;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import simbio.se.sau.API;
import simbio.se.sau.model.IAbstractModel;

/**
 * This class is a abstract implementation of {@link ArrayAdapter} to do more easy customizations
 * to {@link ListView} {@link Adapter}.
 *
 * @author Ademar Alves de Oliveira ademar111190@gmail.com
 * @date 2013-aŭg-14 23:57:59
 * @since {@link API#Version_1_0_0}
 */
public abstract class AbstractAdapter<T extends IAbstractModel> extends ArrayAdapter<T> {

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
     * @param context  the {@link Application} {@link Context}
     * @param delegate your delegate implements {@link IAbstractAdapter}
     * @param layoutId The layout id in you layout folder to be used on {@link ListView} items.
     * @since {@link API#Version_1_0_0}
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
        LayoutInflater inflater = (LayoutInflater) getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (inflater == null)
            return null;
        return inflater.inflate(layoutId, parent, false);
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
