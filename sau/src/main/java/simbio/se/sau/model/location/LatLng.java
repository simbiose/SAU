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
package simbio.se.sau.model.location;

import org.json.JSONObject;

/**
 * A class to save latitude and longitude values
 * Note it is note the LatLng class of Google Maps Android API
 * <p/>
 * Created by Ademar Alves de Oliveira <ademar111190@gmail.com>
 *
 * @date 5/1/14.
 * @since {@link simbio.se.sau.API#Version_4_0_0}
 */
public class LatLng extends AbstractGeoLocationModel {

    protected double latitude = 0.0;
    protected double longitude = 0.0;

    /**
     * @param jsonObject the {@link JSONObject} with latitude and longitude information
     * @since {@link simbio.se.sau.API#Version_4_0_0}
     */
    public LatLng(JSONObject jsonObject) {
        if (jsonObject != null) {
            latitude = jsonObject.optDouble("lat", latitude);
            longitude = jsonObject.optDouble("lng", longitude);
        }
    }

    /**
     * @return the latitude
     * @since {@link simbio.se.sau.API#Version_4_0_0}
     */
    public double getLatitude() {
        return latitude;
    }

    /**
     * @param latitude the latitude
     * @since {@link simbio.se.sau.API#Version_4_0_0}
     */
    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    /**
     * @return the longitude
     * @since {@link simbio.se.sau.API#Version_4_0_0}
     */
    public double getLongitude() {
        return longitude;
    }

    /**
     * @param longitude the longitude
     * @since {@link simbio.se.sau.API#Version_4_0_0}
     */
    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
