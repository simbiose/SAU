/**
 * Copyright 2013-2014
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
package simbio.se.sau.network;

import android.content.Context;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.entity.StringEntity;
import org.json.JSONObject;

import simbio.se.sau.API;

/**
 * Extends it to access the network in asyn mode. More details see {@link AsyncHttpClient} and
 * {@link AsyncHttpResponseHandler}
 *
 * @author Ademar Alves de Oliveira (ademar111190@gmail.com)
 * @date Nov 29, 2013 1:06:16 PM
 * @since {@link API#Version_3_0_0}
 */
public abstract class SauHttpClient extends AsyncHttpResponseHandler {

    protected static AsyncHttpClient asyncHttpClient;
    protected SauHttpDelegate delegate;

    /**
     * @since {@link API#Version_3_0_0}
     */
    public SauHttpClient(SauHttpDelegate delegate) {
        if (asyncHttpClient == null) {
            asyncHttpClient = new AsyncHttpClient();
            asyncHttpClient.setTimeout(getTimeout());
        }
        this.delegate = delegate;
    }

    /**
     * @return the timeout
     * @since {@link API#Version_3_1_3}
     */
    public int getTimeout() {
        return 10000;
    }

    @Override
    public void onSuccess(String response) {
        success(response);
    }

    @Override
    public void onFailure(Throwable error, String content) {
        delegate.onFail(this, error, content);
    }

    /**
     * @return the URL to connection access
     * @since {@link API#Version_3_1_3}
     */
    public abstract String getUrl();

    /**
     * @param response the {@link String} returned from server
     * @since {@link API#Version_3_1_3}
     */
    protected abstract void success(String response);

    /**
     * @param requestParams the {@link RequestParams}
     * @since {@link API#Version_3_1_3}
     */
    public void get(RequestParams requestParams) {
        asyncHttpClient.get(getUrl(), requestParams, this);
    }

    /**
     * @param context    the {@link Context} to codify the json
     * @param jsonParams the {@link JSONObject} to be codified
     * @since {@link API#Version_3_1_3}
     */
    public void post(Context context, JSONObject jsonParams) {
        try {
            asyncHttpClient.post(
                    context,
                    getUrl(),
                    new StringEntity(jsonParams.toString()),
                    "application/json",
                    this
            );
        } catch (Exception e) {
            onFailure(e.getCause(), "");
        }
    }

}
