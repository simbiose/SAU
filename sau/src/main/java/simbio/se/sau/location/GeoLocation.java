package simbio.se.sau.location;

import android.location.Location;

/**
 * Created by Ademar Oliveira <ademar111190@gmail.com> on 4/30/14.
 */
public class GeoLocation {

    protected GeoLocationListener listener;

    public GeoLocation(GeoLocationListener listener) {
        this.listener = listener;
    }

    public void getGeoLocation(Location location) {
        if (listener == null) {
            return;
        }

        //
    }
}
