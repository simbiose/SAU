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
 * Google Geo Location Model to follow Google Api return
 * <p/>
 * Created by Ademar Alves de Oliveira <ademar111190@gmail.com>
 *
 * @date 5/1/14.
 * @since {@link simbio.se.sau.API#Version_4_0_0}
 */
public class GeoLocationModel extends AbstractGeoLocationModel {

    protected String formattedAddress;
    protected Geometry geometry;
    protected ArrayList<String> types = new ArrayList<String>();
    protected ArrayList<AddressComponents> addressComponents = new ArrayList<AddressComponents>();

    /**
     * @param jsonObject the {@link org.json.JSONObject} with data
     * @since {@link simbio.se.sau.API#Version_4_0_0}
     */
    public GeoLocationModel(JSONObject jsonObject) {
        if (jsonObject != null) {
            formattedAddress = jsonObject.optString("formatted_address");
            geometry = new Geometry(jsonObject.optJSONObject("geometry"));

            JSONArray jsonArrayTypes = jsonObject.optJSONArray("formatted_address");
            if (jsonArrayTypes != null)
                for (int i = 0; i < jsonArrayTypes.length(); i++)
                    types.add(jsonArrayTypes.optString(i));

            JSONArray jsonArrayAddressComponents = jsonObject.optJSONArray("address_components");
            if (jsonArrayAddressComponents != null)
                for (int i = 0; i < jsonArrayAddressComponents.length(); i++)
                    addressComponents.add(new AddressComponents(
                            jsonArrayAddressComponents.optJSONObject(i)
                    ));
        }
    }

    /**
     * @return the formatted address following Google Maps standard
     * @since {@link simbio.se.sau.API#Version_4_0_0}
     */
    public String getFormattedAddress() {
        return formattedAddress;
    }

    /**
     * @param formattedAddress the formatted address to be set
     * @since {@link simbio.se.sau.API#Version_4_0_0}
     */
    public void setFormattedAddress(String formattedAddress) {
        this.formattedAddress = formattedAddress;
    }

    /**
     * @return the {@link Geometry}
     * @since {@link simbio.se.sau.API#Version_4_0_0}
     */
    public Geometry getGeometry() {
        return geometry;
    }

    /**
     * @param geometry the {@link simbio.se.sau.model.location.Geometry} to be set
     * @since {@link simbio.se.sau.API#Version_4_0_0}
     */
    public void setGeometry(Geometry geometry) {
        this.geometry = geometry;
    }

    /**
     * @return a {@link java.util.ArrayList} of types
     * @since {@link simbio.se.sau.API#Version_4_0_0}
     */
    public ArrayList<String> getTypes() {
        return types;
    }

    /**
     * @param types an {@link java.util.ArrayList} of types
     * @since {@link simbio.se.sau.API#Version_4_0_0}
     */
    public void setTypes(ArrayList<String> types) {
        this.types = types;
    }

    /**
     * @return a {@link java.util.ArrayList} of {@link AddressComponents}
     * @since {@link simbio.se.sau.API#Version_4_0_0}
     */
    public ArrayList<AddressComponents> getAddressComponents() {
        return addressComponents;
    }

    /**
     * @param addressComponents an {@link ArrayList} of {@link AddressComponents} to be set
     * @since {@link simbio.se.sau.API#Version_4_0_0}
     */
    public void setAddressComponents(ArrayList<AddressComponents> addressComponents) {
        this.addressComponents = addressComponents;
    }
}
