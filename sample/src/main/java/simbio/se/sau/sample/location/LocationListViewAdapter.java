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
package simbio.se.sau.sample.location;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import simbio.se.sau.adapters.AbstractAdapter;
import simbio.se.sau.adapters.IAbstractAdapter;
import simbio.se.sau.model.location.GeoLocationModel;
import simbio.se.sau.sample.R;

/**
 * @author Ademar Alves de Oliveira (ademar111190@gmail.com)
 * @date 1-May-2014
 */
@SuppressLint("ViewConstructor")
public class LocationListViewAdapter extends AbstractAdapter<GeoLocationModel> {

    public LocationListViewAdapter(Context context, IAbstractAdapter<GeoLocationModel> delegate) {
        super(context, delegate, R.layout.list_item_location);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = super.getView(position, convertView, parent);

        if (view == null)
            return null;

        GeoLocationModel geoLocationModel = getItem(position);
        TextView textView = (TextView) view.findViewById(R.id.list_item_location_text);
        textView.setText(String.format(
                "%s",
                geoLocationModel.getFormattedAddress()
        ));

        return view;
    }

}
