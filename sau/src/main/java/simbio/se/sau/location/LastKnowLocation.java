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

import simbio.se.sau.exceptions.location.LocationProviderDisabled;
import simbio.se.sau.exceptions.location.NullLocationManager;

/**
 * Class to provide the last know location
 *
 * @author Ademar Alves de Oliveira ademar111190@gmail.com
 * @date 2014-apr-30
 * @since {@link simbio.se.sau.API#Version_4_0_0}
 */
public class LastKnowLocation {

    protected int timeToRetry = 1000;
    protected LastKnowLocationListener listener;
    protected LocationManager locationManager;
    protected Handler handler;
    protected boolean cancelRequest = false;

    /**
     * Default constructor
     *
     * @param listener the {@link LastKnowLocationListener} to handler the location
     * @date 2014-apr-30
     * @since {@link simbio.se.sau.API#Version_4_0_0}
     */
    public LastKnowLocation(LastKnowLocationListener listener) {
        this.listener = listener;
        this.handler = new Handler();
    }

    /**
     * Enqueue for get the location
     *
     * @param context the {@link Context} to access the {@link LocationManager}
     * @date 2014-apr-30
     * @since {@link simbio.se.sau.API#Version_4_0_0}
     */
    public void getLastKnowLocation(Context context) {
        if (listener == null) {
            return;
        }

        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        if (locationManager == null) {
            listener.couldNotGetLastKnowLocation(new NullLocationManager());
            return;
        }

        if (!isGpsEnabled() && !isNetworkEnabled()) {
            listener.couldNotGetLastKnowLocation(new LocationProviderDisabled());
            return;
        }

        Location location = getLocationOrNull();
        if (location != null) {
            sendLocationToListener(location);
            return;
        }

        locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                0l,
                0.0f,
                locationListenerGps
        );
        locationManager.requestLocationUpdates(
                LocationManager.NETWORK_PROVIDER,
                0l,
                0.0f,
                locationListenerNetwork
        );

        retry();
    }

    /**
     * How much time wait between the retry process
     *
     * @param timeToRetry the time in milliseconds
     * @date 2014-apr-30
     * @since {@link simbio.se.sau.API#Version_4_0_0}
     */
    public void setTimeToRetry(int timeToRetry) {
        this.timeToRetry = timeToRetry;
    }

    /**
     * @return how much time wait between the retry process
     * @date 2014-apr-30
     * @since {@link simbio.se.sau.API#Version_4_0_0}
     */
    public int getTimeToRetry() {
        return timeToRetry;
    }

    /**
     * Retry to get the last know location
     *
     * @date 2014-apr-30
     * @since {@link simbio.se.sau.API#Version_4_0_0}
     */
    protected void retry() {
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                if (cancelRequest)
                    return;

                Location location = getLocationOrNull();
                if (location != null)
                    sendLocationToListener(location);
                else
                    retry();
            }
        }, getTimeToRetry());
    }

    /**
     * {@link LocationListener} for Gps Provider
     *
     * @date 2014-apr-30
     * @since {@link simbio.se.sau.API#Version_4_0_0}
     */
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

    /**
     * {@link LocationListener} for Network Provider
     *
     * @date 2014-apr-30
     * @since {@link simbio.se.sau.API#Version_4_0_0}
     */
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

    /**
     * @return <code>true</code> if gps provider is enabled
     * @date 2014-apr-30
     * @since {@link simbio.se.sau.API#Version_4_0_0}
     */
    public boolean isGpsEnabled() {
        try {
            return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch (Exception ex) {
            return false;
        }
    }

    /**
     * @return <code>true</code> if network provider is enabled
     * @date 2014-apr-30
     * @since {@link simbio.se.sau.API#Version_4_0_0}
     */
    public boolean isNetworkEnabled() {
        try {
            return locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        } catch (Exception ex) {
            return false;
        }
    }

    /**
     * Retrieve location from listeners
     *
     * @param location the {@link Location} to send to listener
     * @date 2014-apr-30
     * @since {@link simbio.se.sau.API#Version_4_0_0}
     */
    protected void onLocationChanged(Location location) {
        if (location == null) {
            return;
        }

        this.cancel();

        locationManager.removeUpdates(locationListenerGps);
        locationManager.removeUpdates(locationListenerNetwork);

        sendLocationToListener(location);
    }

    /**
     * Send last know location to listener and ends the process
     *
     * @param location the {@link Location} to send to listener
     * @date 2014-apr-30
     * @since {@link simbio.se.sau.API#Version_4_0_0}
     */
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

    /**
     * Get Last know location or <code>null</code>
     *
     * @return the last know {@link Location} or <code>null</code>
     * @date 2014-apr-30
     * @since {@link simbio.se.sau.API#Version_4_0_0}
     */
    protected Location getLocationOrNull() {
        Location locationGps = null;
        Location locationNetwork = null;

        if (isGpsEnabled())
            locationGps = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if (isNetworkEnabled())
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

    /**
     * Cancel the process. It` not immediately
     *
     * @return the last know {@link Location} or <code>null</code>
     * @date 2014-apr-30
     * @since {@link simbio.se.sau.API#Version_4_0_0}
     */
    public void cancel() {
        cancelRequest = true;
    }

}
