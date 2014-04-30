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
package simbio.se.sau.location;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Ademar Oliveira <ademar111190@gmail.com> on 4/30/14.
 */
public class LastKnowLocation extends TimerTask {

    protected LastKnowLocationListener listener;
    protected LocationManager locationManager;
    protected boolean gpsEnabled = false;
    protected boolean networkEnabled = false;
    protected Handler handler;

    public LastKnowLocation(LastKnowLocationListener listener) {
        this.listener = listener;
        this.handler = new Handler();
    }

    public void getLastKnowLocation(Context context) {
        if (listener == null) {
            return;
        }

        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        if (locationManager == null) {
            return;
        }

        try {
            gpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch (Exception ex) {
            gpsEnabled = false;
        }

        try {
            networkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        } catch (Exception ex) {
            networkEnabled = false;
        }

        if (!gpsEnabled && !networkEnabled) {
            return;
        }

        Location location = getLocationOrNull();
        if (location != null) {
            sendLocationToListener(location);
            return;
        }

        if (gpsEnabled)
            locationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER,
                    0l,
                    0.0f,
                    locationListenerGps
            );
        if (networkEnabled)
            locationManager.requestLocationUpdates(
                    LocationManager.NETWORK_PROVIDER,
                    0l,
                    0.0f,
                    locationListenerNetwork
            );

        new Timer().schedule(this, 20000);
    }

    protected LocationListener locationListenerGps = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            LastKnowLocation.this.onLocationChanged(location);
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
        }

        @Override
        public void onProviderEnabled(String provider) {
        }

        @Override
        public void onProviderDisabled(String provider) {
        }
    };

    protected LocationListener locationListenerNetwork = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            LastKnowLocation.this.onLocationChanged(location);
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
        }

        @Override
        public void onProviderEnabled(String provider) {
        }

        @Override
        public void onProviderDisabled(String provider) {
        }
    };

    protected void onLocationChanged(Location location) {
        if (location == null) {
            return;
        }

        this.cancel();

        locationManager.removeUpdates(locationListenerGps);
        locationManager.removeUpdates(locationListenerNetwork);

        sendLocationToListener(location);
    }

    protected void sendLocationToListener(final Location location) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                locationManager.removeUpdates(locationListenerGps);
                locationManager.removeUpdates(locationListenerNetwork);
                listener.gotLastKnowLocation(location);
            }
        });
    }

    protected Location getLocationOrNull() {
        Location locationGps = null;
        Location locationNetwork = null;

        if (gpsEnabled)
            locationGps = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if (networkEnabled)
            locationNetwork = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

        if (locationGps != null && locationNetwork != null) {
            if (locationGps.getTime() >= locationNetwork.getTime()) {
                return locationGps;
            } else {
                return locationNetwork;
            }
        } else if (locationGps != null) {
            return locationGps;
        } else if (locationNetwork != null) {
            return locationNetwork;
        } else {
            return null;
        }
    }

    @Override
    public void run() {
        Location location = getLocationOrNull();
        if (location != null) {
            sendLocationToListener(location);
        }
    }
}
