/**
 * Copyright 2014
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

import android.app.Activity;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import simbio.se.sau.exceptions.location.AbstractLocationException;
import simbio.se.sau.location.LastKnowLocation;
import simbio.se.sau.location.LastKnowLocationListener;
import simbio.se.sau.sample.R;

/**
 * @author Ademar Alves de Oliveira (ademar111190@gmail.com)
 * @date Nov 27, 2013 3:38:42 PM
 */
public class ActivityLocation extends Activity implements LastKnowLocationListener {

    protected TextView textViewLocation;
    protected ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        textViewLocation = (TextView) findViewById(R.id.textview_location);
        progressBar = (ProgressBar) findViewById(R.id.location_progressBar);
    }

    public void refreshLocation(View view) {
        textViewLocation.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.VISIBLE);

        new LastKnowLocation(this).getLastKnowLocation(getApplicationContext());
    }

    @Override
    public void gotLastKnowLocation(Location location) {
        textViewLocation.setText(
                String.format(
                        getString(R.string.latlng),
                        location.getLatitude(),
                        location.getLongitude()
                )
        );

        textViewLocation.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void couldNotGetLastKnowLocation(AbstractLocationException because) {
        textViewLocation.setText(String.format("%s", because));

        textViewLocation.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.INVISIBLE);
    }
}
