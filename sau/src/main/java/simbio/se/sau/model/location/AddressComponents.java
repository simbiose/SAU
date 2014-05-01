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

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A class to save long name, short name and type values
 * Note it is note the LatLng class of Google Maps Android API
 * <p/>
 * Created by Ademar Alves de Oliveira <ademar111190@gmail.com>
 *
 * @date 5/1/14.
 * @since {@link simbio.se.sau.API#Version_4_0_0}
 */
public class AddressComponents extends AbstractGeoLocationModel {

    protected String longName;
    protected String shortName;
    protected ArrayList<String> types = new ArrayList<String>();

    /**
     * @param jsonObject the {@link org.json.JSONObject} with data
     * @since {@link simbio.se.sau.API#Version_4_0_0}
     */
    public AddressComponents(JSONObject jsonObject) {
        if (jsonObject != null) {
            longName = jsonObject.optString("long_name");
            shortName = jsonObject.optString("short_name");

            JSONArray jsonArrayTypes = jsonObject.optJSONArray("address_components");
            if (jsonArrayTypes != null)
                for (int i = 0; i < jsonArrayTypes.length(); i++)
                    types.add(jsonArrayTypes.optString(i));
        }
    }

    /**
     * @return the long name
     * @since {@link simbio.se.sau.API#Version_4_0_0}
     */
    public String getLongName() {
        return longName;
    }

    /**
     * @param longName the long name to be set
     * @since {@link simbio.se.sau.API#Version_4_0_0}
     */
    public void setLongName(String longName) {
        this.longName = longName;
    }

    /**
     * @return the short name
     * @since {@link simbio.se.sau.API#Version_4_0_0}
     */
    public String getShortName() {
        return shortName;
    }

    /**
     * @param shortName the short name to be set
     * @since {@link simbio.se.sau.API#Version_4_0_0}
     */
    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    /**
     * @return a list of types
     * @since {@link simbio.se.sau.API#Version_4_0_0}
     */
    public ArrayList<String> getTypes() {
        return types;
    }

    /**
     * @param types a list of types to be set
     * @since {@link simbio.se.sau.API#Version_4_0_0}
     */
    public void setTypes(ArrayList<String> types) {
        this.types = types;
    }
}
