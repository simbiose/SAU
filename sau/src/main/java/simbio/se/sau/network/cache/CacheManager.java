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
package simbio.se.sau.network.cache;

import android.content.Context;

import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import simbio.se.sau.persistense.PreferencesHelper;
import simbio.se.sau.API;

/**
 * Created by Ademar Alves de Oliveira <ademar111190@gmail.com>
 *
 * @date 4/24/14.
 */
public class CacheManager extends PreferencesHelper {

    /**
     * The default constructor
     *
     * @param context a {@link Context} needed to access the {@link PreferencesHelper} data.
     * @since {@link API#Version_4_0_0}
     */
    public CacheManager(Context context) {
        super(context);
        this.preferencesKey = "SauNetworkCache";
    }

    /**
     * Save the servers result
     *
     * @param key    the key
     * @param result the server results to be cached
     * @since {@link API#Version_4_0_0}
     */
    public void cache(String key, String result) {
        put(key, result);
    }

    /**
     * Retrieves the cached servers result
     *
     * @param key the key
     * @return the server results or <code>null</code> if have no cache
     * @since {@link API#Version_4_0_0}
     */
    public String retrieve(String key) {
        return getStringOrNull(key);
    }

    /**
     * Make a key using request info
     *
     * @param url the request url
     * @return the key using request info
     * @since {@link API#Version_4_0_0}
     */
    public String makeKey(String url) {
        return makeKey(url, null, null);
    }

    /**
     * Make a key using request info
     *
     * @param url           the request url
     * @param requestParams the {@link RequestParams} of request
     * @return the key using request info
     * @since {@link API#Version_4_0_0}
     */
    public String makeKey(String url, RequestParams requestParams) {
        return makeKey(url, requestParams, null);
    }

    /**
     * Make a key using request info
     *
     * @param url        the request url
     * @param jsonObject the {@link org.json.JSONObject} of request
     * @return the key using request info
     * @since {@link API#Version_4_0_0}
     */
    public String makeKey(String url, JSONObject jsonObject) {
        return makeKey(url, null, jsonObject);
    }

    /**
     * Make a key using request info
     *
     * @param url           the request url
     * @param requestParams the {@link RequestParams} of request
     * @param jsonObject    the {@link org.json.JSONObject} of request
     * @return the key using request info
     * @since {@link API#Version_4_0_0}
     */
    public String makeKey(String url, RequestParams requestParams, JSONObject jsonObject) {
        StringBuilder stringBuilder = new StringBuilder();
        if (url != null)
            stringBuilder.append(url);
        if (requestParams != null)
            stringBuilder.append(requestParams.toString());
        if (jsonObject != null)
            stringBuilder.append(jsonObject.toString());
        return stringBuilder.toString();
    }
}
