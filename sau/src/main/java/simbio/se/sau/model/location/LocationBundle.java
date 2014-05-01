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
 * The Bundle to follow Google Api design
 * <p/>
 * Created by Ademar Alves de Oliveira <ademar111190@gmail.com>
 *
 * @date 5/1/14.
 * @since {@link simbio.se.sau.API#Version_4_0_0}
 */
public class LocationBundle extends AbstractGeoLocationModel {

    protected LatLng northeast;
    protected LatLng southwest;

    /**
     * @param jsonObject the {@link org.json.JSONObject} with the data
     * @since {@link simbio.se.sau.API#Version_4_0_0}
     */
    public LocationBundle(JSONObject jsonObject) {
        if (jsonObject != null) {
            northeast = new LatLng(jsonObject.optJSONObject("northeast"));
            southwest = new LatLng(jsonObject.optJSONObject("southwest"));
        }
    }

    /**
     * @return the {@link LatLng} of the Northeast
     * @since {@link simbio.se.sau.API#Version_4_0_0}
     */
    public LatLng getNortheast() {
        return northeast;
    }

    /**
     * @param northeast the Northeast {@link LatLng} to be set
     * @since {@link simbio.se.sau.API#Version_4_0_0}
     */
    public void setNortheast(LatLng northeast) {
        this.northeast = northeast;
    }

    /**
     * @return the {@link LatLng} of the Southwest
     * @since {@link simbio.se.sau.API#Version_4_0_0}
     */
    public LatLng getSouthwest() {
        return southwest;
    }

    /**
     * @param southwest the Southwest{@link LatLng} to be set
     * @since {@link simbio.se.sau.API#Version_4_0_0}
     */
    public void setSouthwest(LatLng southwest) {
        this.southwest = southwest;
    }
}
