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
package simbio.se.sau.network.client;

import android.content.Context;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.entity.StringEntity;
import org.json.JSONObject;

import simbio.se.sau.API;
import simbio.se.sau.network.cache.CacheManager;

/**
 * Extends it to access the network in asyn mode. More details see {@link AsyncHttpClient} and
 * {@link AsyncHttpResponseHandler}
 *
 * @author Ademar Alves de Oliveira (ademar111190@gmail.com)
 * @date Nov 29, 2013 1:06:16 PM
 * @since {@link API#Version_3_0_0}
 */
public class SauHttpClient<Delegate extends SauHttpDelegate>
        extends AsyncHttpResponseHandler
        implements Runnable {

    private static AsyncHttpClient sharedAsyncHttpClient;
    protected static CacheManager cacheManager;

    private boolean usesSharedCliente;
    private AsyncHttpClient asyncHttpClient;
    private Delegate delegate;
    protected String cachedKey;
    protected Runnable postRunnable;
    protected Thread thread;

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
        if (cacheManager == null)
            cacheManager = new CacheManager(context);

        this.usesSharedCliente = shouldUsesSharedCliente;
        this.delegate = delegate;
        this.thread = new Thread(this);

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
        getDelegate().onRequestSuccess(this, response);
    }

    @Override
    public void onFailure(Throwable error, String content) {
        getDelegate().onRequestFail(this, error, content);
    }

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
     * Make a GET request
     *
     * @param url           the Url to make the request
     * @param requestParams the {@link RequestParams}
     * @since {@link API#Version_3_1_3}
     */
    public void get(final String url, final RequestParams requestParams) {
        cachedKey = cacheManager.makeKey(url, requestParams);
        postRunnable = new Runnable() {
            @Override
            public void run() {
                asyncHttpClient.get(url, requestParams, SauHttpClient.this);
            }
        };
        thread.start();
    }

    /**
     * Make a POST request
     *
     * @param url        the Url to make the request
     * @param context    the {@link Context} to codify the json
     * @param jsonParams the {@link JSONObject} to be codified
     * @since {@link API#Version_3_1_3}
     */
    public void post(final String url, final Context context, final JSONObject jsonParams) {
        cachedKey = cacheManager.makeKey(url, jsonParams);
        postRunnable = new Runnable() {
            @Override
            public void run() {
                try {
                    getAsyncHttpClient().post(
                            context,
                            url,
                            new StringEntity(jsonParams.toString()),
                            "application/json",
                            SauHttpClient.this
                    );
                } catch (Exception e) {
                    onFailure(e.getCause(), "");
                }
            }
        };
        thread.start();
    }

    //----------------------------------------------------------------------------------------------
    // Runnable
    //----------------------------------------------------------------------------------------------

    @Override
    public void run() {
        if (cachedKey != null) {
            String cached = cacheManager.getStringOrNull(cachedKey);
            if (cached != null)
                getDelegate().onRequestCached(this, cached);
        }
        if (postRunnable != null)
            postRunnable.run();
    }
}
