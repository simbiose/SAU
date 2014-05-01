package simbio.se.sau.network.service;

import android.content.Context;
import android.os.Handler;

import com.loopj.android.http.RequestParams;

import java.util.HashMap;

import simbio.se.sau.exceptions.network.NetworkNullResponseException;
import simbio.se.sau.network.client.SauHttpClient;
import simbio.se.sau.network.client.SauHttpDelegate;

/**
 * Created by Ademar Alves de Oliveira <ademar111190@gmail.com>
 *
 * @date 01-May-2014.
 * @since {@link simbio.se.sau.API#Version_4_0_0}
 */
public abstract class NetworkService implements SauHttpDelegate {

    protected HashMap<SauHttpClient, Integer> clients = new HashMap<SauHttpClient, Integer>();
    protected Context context;
    protected Handler handler;

    /**
     * @param context the {@link android.content.Context} to be used
     * @since {@link simbio.se.sau.API#Version_4_0_0}
     */
    public NetworkService(Context context) {
        this.context = context;
        this.handler = new Handler();
    }

    //----------------------------------------------------------------------------------------------
    // Abstract Methods
    //----------------------------------------------------------------------------------------------

    /**
     * Called when request has been returned with success
     *
     * @param respose     a {@link java.lang.String} with returned data
     * @param requestCode the request code send when called the method
     * @since {@link simbio.se.sau.API#Version_4_0_0}
     */
    protected abstract void proccessResponseForRequest(String respose, int requestCode);

    /**
     * Called when request has a cache
     *
     * @param respose     a {@link java.lang.String} with cache saved
     * @param requestCode the request code send when called the method
     * @since {@link simbio.se.sau.API#Version_4_0_0}
     */
    protected abstract void proccessCacheForRequest(String respose, int requestCode);

    /**
     * Called when request fails
     *
     * @param requestCode the request code send when called the method
     * @param throwable   the {@link java.lang.Throwable} of the problem
     * @param content     an {@link java.lang.String} with error data, can be <code>null</code>
     * @since {@link simbio.se.sau.API#Version_4_0_0}
     */
    protected abstract void proccessFailForRequest(
            int requestCode,
            Throwable throwable,
            String content
    );

    //----------------------------------------------------------------------------------------------
    // Class methods
    //----------------------------------------------------------------------------------------------

    /**
     * @param sauHttpClient the {@link simbio.se.sau.network.client.SauHttpClient} of the request
     * @return the request code of {@link simbio.se.sau.network.client.SauHttpClient}
     */
    protected int getRequestCodeByClient(SauHttpClient sauHttpClient) {
        if (clients.containsKey(sauHttpClient))
            return clients.get(sauHttpClient);
        return 0;
    }

    /**
     * this method remove the {@link simbio.se.sau.network.client.SauHttpClient} of map
     *
     * @param sauHttpClient the {@link simbio.se.sau.network.client.SauHttpClient} of the request
     * @return the request code of {@link simbio.se.sau.network.client.SauHttpClient}
     */
    protected int getRequestCodeByClientAndClear(SauHttpClient sauHttpClient) {
        int ret = getRequestCodeByClient(sauHttpClient);
        clients.remove(sauHttpClient);
        return ret;
    }

    /**
     * @return a {@link android.os.Handler} to make jobs on UIThread
     */
    protected Handler getHandler() {
        return handler;
    }

    //----------------------------------------------------------------------------------------------
    // CRUD interface
    //----------------------------------------------------------------------------------------------

    /**
     * Make a GET request
     *
     * @param url           the Url to make the request
     * @param requestParams the {@link com.loopj.android.http.RequestParams}
     * @param requestCode   the request code to identify the correct answer to requests
     * @since {@link simbio.se.sau.API#Version_4_0_0}
     */
    protected void get(final String url, final RequestParams requestParams, int requestCode) {
        SauHttpClient sauHttpClient = new SauHttpClient<NetworkService>(context, this);
        clients.put(sauHttpClient, requestCode);
        sauHttpClient.get(url, requestParams);
    }

    //----------------------------------------------------------------------------------------------
    // Sau Http Client Listener
    //----------------------------------------------------------------------------------------------

    @Override
    public void onRequestSuccess(SauHttpClient sauHttpClient, String response) {
        if (response == null) {
            onRequestFail(sauHttpClient, new NetworkNullResponseException(), "");
            return;
        }
        proccessResponseForRequest(response, getRequestCodeByClientAndClear(sauHttpClient));
    }

    @Override
    public void onRequestCached(SauHttpClient sauHttpClient, String response) {
        if (response == null)
            return;
        proccessCacheForRequest(response, getRequestCodeByClient(sauHttpClient));
    }

    @Override
    public void onRequestFail(SauHttpClient sauHttpClient, Throwable throwable, String content) {
        proccessFailForRequest(getRequestCodeByClientAndClear(sauHttpClient), throwable, content);
    }
}
