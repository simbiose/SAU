package simbio.se.sau.network.service;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Ademar Alves de Oliveira <ademar111190@gmail.com>
 *
 * @date 01-May-2014.
 * @since {@link simbio.se.sau.API#Version_4_0_0}
 */
public abstract class NetworkJsonService extends NetworkService {

    /**
     * @param context the {@link android.content.Context} to be used
     * @since {@link simbio.se.sau.API#Version_4_0_0}
     */
    public NetworkJsonService(Context context) {
        super(context);
    }

    //----------------------------------------------------------------------------------------------
    // Network Service Methods
    //----------------------------------------------------------------------------------------------

    /**
     * Called when request has been returned with success
     *
     * @param jsonObject  a {@link org.json.JSONObject} with returned data
     * @param requestCode the request code send when called the method
     * @since {@link simbio.se.sau.API#Version_4_0_0}
     */
    protected abstract void proccessResponseForRequest(JSONObject jsonObject, int requestCode);

    /**
     * Called when request has a cache
     *
     * @param jsonObject  a {@link org.json.JSONObject} with cache saved
     * @param requestCode the request code send when called the method
     * @since {@link simbio.se.sau.API#Version_4_0_0}
     */
    protected abstract void proccessCacheForRequest(JSONObject jsonObject, int requestCode);

    //----------------------------------------------------------------------------------------------
    // Network Service Methods
    //----------------------------------------------------------------------------------------------

    @Override
    protected void proccessResponseForRequest(String respose, int requestCode) {
        JSONObject jsonObject;
        try {
            jsonObject = new JSONObject(respose);
        } catch (JSONException e) {
            proccessFailForRequest(requestCode, e, respose);
            return;
        }
        proccessResponseForRequest(jsonObject, requestCode);
    }

    @Override
    protected void proccessCacheForRequest(String respose, int requestCode) {
        JSONObject jsonObject;
        try {
            jsonObject = new JSONObject(respose);
        } catch (JSONException e) {
            return;
        }
        proccessCacheForRequest(jsonObject, requestCode);
    }
}
