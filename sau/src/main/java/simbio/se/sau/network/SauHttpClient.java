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
import simbio.se.sau.network.cache.CacheManager;
import simbio.se.sau.network.transformer.AbstractTransformer;

/**
 * Extends it to access the network in asyn mode. More details see {@link AsyncHttpClient} and
 * {@link AsyncHttpResponseHandler}
 *
 * @author Ademar Alves de Oliveira (ademar111190@gmail.com)
 * @date Nov 29, 2013 1:06:16 PM
 * @since {@link API#Version_3_0_0}
 */
public abstract class SauHttpClient<Delegate extends SauHttpDelegate> {

    private static AsyncHttpClient sharedAsyncHttpClient;

    private boolean usesSharedCliente;
    private AsyncHttpClient asyncHttpClient;
    private Delegate delegate;
    protected CacheManager cacheManager;

    //----------------------------------------------------------------------------------------------
    // Constructors
    //----------------------------------------------------------------------------------------------

    /**
     * @param context  the {@link Context} to create the cache manager
     * @param delegate A {@link SauHttpDelegate} to handle the returns
     * @since {@link API#Version_3_0_0}
     */
    public SauHttpClient(Context context, Delegate delegate) {
        this(context, delegate, true);
    }

    /**
     * @param context                 the {@link Context} to create the cache manager
     * @param delegate                A {@link SauHttpDelegate} to handle the returns
     * @param shouldUsesSharedCliente <code>true</code> if you want uses the shared
     *                                {@link AsyncHttpClient} <code>false</code> if you want use
     *                                your customized {@link AsyncHttpClient}
     * @since {@link API#Version_4_0_0}
     */
    public SauHttpClient(Context context, Delegate delegate, boolean shouldUsesSharedCliente) {
        if (sharedAsyncHttpClient == null)
            sharedAsyncHttpClient = new AsyncHttpClient();

        this.usesSharedCliente = shouldUsesSharedCliente;
        this.delegate = delegate;
        this.cacheManager = new CacheManager(context);

        if (shouldUsesSharedCliente()) {
            asyncHttpClient = new AsyncHttpClient();
            asyncHttpClient.setTimeout(getTimeout());
        }
    }

    //----------------------------------------------------------------------------------------------
    // Overrides
    //----------------------------------------------------------------------------------------------

    @Override
    public void onSuccess(String response) {
        success(response);
    }

    @Override
    public void onFailure(Throwable error, String content) {
        getDelegate().onRequestFail(this, error, content);
    }

    //----------------------------------------------------------------------------------------------
    // Abstracts
    //----------------------------------------------------------------------------------------------

    /**
     * @param response the {@link String} returned from server
     * @since {@link API#Version_3_1_3}
     */
    protected abstract void success(String response);

    /**
     * @param response the {@link String} returned from cache
     * @since {@link API#Version_4_0_0}
     */
    protected abstract void caches(String response);

    //----------------------------------------------------------------------------------------------
    // Customs
    //----------------------------------------------------------------------------------------------

    /**
     * @return the timeout
     * @since {@link API#Version_3_1_3}
     */
    public int getTimeout() {
        return 10000;
    }

    /**
     * @return <code>true</code> if should uses a custom {@link AsyncHttpClient}, returns <code>
     * false</code> is should uses the shared {@link AsyncHttpClient}
     * @since {@link API#Version_4_0_0}
     */
    public boolean shouldUsesSharedCliente() {
        return usesSharedCliente;
    }

    /**
     * @return the {@link AsyncHttpClient} to make requests
     * @since {@link API#Version_4_0_0}
     */
    protected AsyncHttpClient getAsyncHttpClient() {
        if (shouldUsesSharedCliente())
            return sharedAsyncHttpClient;
        else
            return asyncHttpClient;
    }

    /**
     * @return the extended {@link SauHttpDelegate} class to handle requests
     * @since {@link API#Version_4_0_0}
     */
    public Delegate getDelegate() {
        return delegate;
    }

    //----------------------------------------------------------------------------------------------
    // CRUD
    //----------------------------------------------------------------------------------------------

    /**
     * @param url           the Url to make the request
     * @param requestParams the {@link RequestParams}
     * @param transformer   an {@link AbstractTransformer} to handle the results
     * @since {@link API#Version_3_1_3}
     */
    public void get(String url, RequestParams requestParams, AbstractTransformer transformer) {
        asyncHttpClient.get(url, requestParams, transformer);
    }

    /**
     * @param context    the {@link Context} to codify the json
     * @param jsonParams the {@link JSONObject} to be codified
     * @since {@link API#Version_3_1_3}
     */
    public void post(Context context, JSONObject jsonParams) {
        try {
            getAsyncHttpClient().post(
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
