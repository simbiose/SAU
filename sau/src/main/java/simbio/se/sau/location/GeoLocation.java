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

import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Locale;

import simbio.se.sau.exceptions.network.NetworkNullResponseException;
import simbio.se.sau.exceptions.network.NetworkStatusNotOkException;
import simbio.se.sau.model.location.GeoLocationModel;
import simbio.se.sau.network.service.NetworkJsonService;

/**
 * @author Ademar Alves de Oliveira ademar111190@gmail.com
 * @date 2014-may-1
 * @since {@link simbio.se.sau.API#Version_4_0_0}
 */
public class GeoLocation extends NetworkJsonService {

    protected final String URL = "http://maps.googleapis.com/maps/api/geocode/json";

    protected GeoLocationListener listener;

    /**
     * @param context  the {@link android.content.Context} to be used
     * @param listener the {@link GeoLocationListener} to handle data
     * @since {@link simbio.se.sau.API#Version_4_0_0}
     */
    public GeoLocation(Context context, GeoLocationListener listener) {
        super(context);
        this.listener = listener;
    }

    /**
     * @param location the {@link Location} to get GeoLocation
     * @since {@link simbio.se.sau.API#Version_4_0_0}
     */
    public void getGeoLocation(Location location) {
        if (listener == null || location == null) {
            return;
        }

        RequestParams requestParams = new RequestParams();
        requestParams.put("latlng", String.format(
                Locale.US,
                "%f,%f",
                location.getLatitude(),
                location.getLongitude()
        ));
        requestParams.put("sensor", "true");

        get(URL, requestParams, 0);
    }

    /**
     * @param jsonObject   the {@link JSONObject} with data
     * @param requestCode  the request code
     * @param proccessFail <code>true</code> if need inform listener about fails
     * @return an {@link ArrayList} of {@link simbio.se.sau.model.location.GeoLocationModel} or <code>null</code>
     * @since {@link simbio.se.sau.API#Version_4_0_0}
     */
    protected ArrayList<GeoLocationModel> getGoogleApiGeoLocationModelsOrNull(
            JSONObject jsonObject,
            int requestCode,
            boolean proccessFail
    ) {
        if (jsonObject.optString("status").equalsIgnoreCase("OK")) {
            JSONArray jsonArray = jsonObject.optJSONArray("results");
            if (jsonArray == null) {
                if (proccessFail)
                    proccessFailForRequest(
                            requestCode,
                            new NetworkNullResponseException(),
                            jsonObject.toString()
                    );
            } else {
                ArrayList<GeoLocationModel> googleApiGeoLocationModels =
                        new ArrayList<GeoLocationModel>();
                for (int i = 0; i < jsonArray.length(); i++)
                    googleApiGeoLocationModels.add(
                            new GeoLocationModel(jsonArray.optJSONObject(i))
                    );
                return googleApiGeoLocationModels;
            }
        } else if (proccessFail) {
            proccessFailForRequest(
                    requestCode,
                    new NetworkStatusNotOkException(),
                    jsonObject.optString("error_message")
            );
        }
        return null;
    }

    //----------------------------------------------------------------------------------------------
    // Network Json Service Methods
    //----------------------------------------------------------------------------------------------

    @Override
    protected void proccessResponseForRequest(JSONObject jsonObject, int requestCode) {
        final ArrayList<GeoLocationModel> googleApiGeoLocationModels =
                getGoogleApiGeoLocationModelsOrNull(jsonObject, requestCode, true);
        if (googleApiGeoLocationModels != null)
            getHandler().post(new Runnable() {
                @Override
                public void run() {
                    listener.onGetGeoLocationSuccess(googleApiGeoLocationModels);
                }
            });
    }

    @Override
    protected void proccessCacheForRequest(JSONObject jsonObject, int requestCode) {
        final ArrayList<GeoLocationModel> googleApiGeoLocationModels =
                getGoogleApiGeoLocationModelsOrNull(jsonObject, requestCode, false);
        if (googleApiGeoLocationModels != null)
            getHandler().post(new Runnable() {
                @Override
                public void run() {
                    listener.onGetGeoLocationCached(googleApiGeoLocationModels);
                }
            });
    }

    @Override
    protected void proccessFailForRequest(
            int requestCode,
            final Throwable throwable,
            final String content
    ) {
        getHandler().post(new Runnable() {
            @Override
            public void run() {
                listener.onGetGeoLocationFail(throwable, content);
            }
        });
    }
}
