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

import java.util.ArrayList;

import simbio.se.sau.model.location.GeoLocationModel;

/**
 * @author Ademar Alves de Oliveira ademar111190@gmail.com
 * @date 2014-may-1
 * @since {@link simbio.se.sau.API#Version_4_0_0}
 */
public interface GeoLocationListener {

    /**
     * @param geoLocationModels an {@link ArrayList} of {@link simbio.se.sau.model.location.GeoLocationModel} with
     *                          returned data
     * @since {@link simbio.se.sau.API#Version_4_0_0}
     */
    public void onGetGeoLocationSuccess(ArrayList<GeoLocationModel> geoLocationModels);

    /**
     * @param geoLocationModels an {@link ArrayList} of {@link simbio.se.sau.model.location.GeoLocationModel} with
     *                          cached data
     * @since {@link simbio.se.sau.API#Version_4_0_0}
     */
    public void onGetGeoLocationCached(ArrayList<GeoLocationModel> geoLocationModels);

    /**
     * @param throwable the {@link Throwable} occurred, can be <code>null</code>
     * @param response  a {@link String} with response
     * @since {@link simbio.se.sau.API#Version_4_0_0}
     */
    public void onGetGeoLocationFail(Throwable throwable, String response);

}
