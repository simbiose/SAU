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
 * Created by Ademar Alves de Oliveira <ademar111190@gmail.com>
 *
 * @date 5/1/14.
 * @since {@link simbio.se.sau.API#Version_4_0_0}
 */
public class Geometry extends AbstractGeoLocationModel {

    protected String locationType;
    protected LatLng location;
    protected LocationBundle viewport;
    protected LocationBundle bounds;

    /**
     * @param jsonObject the {@link org.json.JSONObject} with data
     * @since {@link simbio.se.sau.API#Version_4_0_0}
     */
    public Geometry(JSONObject jsonObject) {
        if (jsonObject != null) {
            locationType = jsonObject.optString("location_type");
            location = new LatLng(jsonObject.optJSONObject("location_type"));
            viewport = new LocationBundle(jsonObject.optJSONObject("viewport"));
            bounds = new LocationBundle(jsonObject.optJSONObject("bounds"));
        }
    }

    /**
     * @return the Location Type
     * @since {@link simbio.se.sau.API#Version_4_0_0}
     */
    public String getLocationType() {
        return locationType;
    }

    /**
     * @param locationType a {@link java.lang.String} with the name of location type
     * @since {@link simbio.se.sau.API#Version_4_0_0}
     */
    public void setLocationType(String locationType) {
        this.locationType = locationType;
    }

    /**
     * @return the {@link LatLng} of location
     * @since {@link simbio.se.sau.API#Version_4_0_0}
     */
    public LatLng getLocation() {
        return location;
    }

    /**
     * @param location the {@link simbio.se.sau.model.location.LatLng} to be set
     * @since {@link simbio.se.sau.API#Version_4_0_0}
     */
    public void setLocation(LatLng location) {
        this.location = location;
    }

    /**
     * @return the {@link LocationBundle} of view port
     * @since {@link simbio.se.sau.API#Version_4_0_0}
     */
    public LocationBundle getViewport() {
        return viewport;
    }

    /**
     * @param viewport the {@link LocationBundle} to be set
     * @since {@link simbio.se.sau.API#Version_4_0_0}
     */
    public void setViewport(LocationBundle viewport) {
        this.viewport = viewport;
    }

    /**
     * @return the {@link LocationBundle} of bounds
     * @since {@link simbio.se.sau.API#Version_4_0_0}
     */
    public LocationBundle getBounds() {
        return bounds;
    }

    /**
     * @param bounds the {@link LocationBundle} to be set
     * @since {@link simbio.se.sau.API#Version_4_0_0}
     */
    public void setBounds(LocationBundle bounds) {
        this.bounds = bounds;
    }
}
