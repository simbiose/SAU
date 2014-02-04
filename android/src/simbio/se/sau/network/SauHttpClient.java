/**
 * 
 */
package simbio.se.sau.network;

import org.apache.http.entity.StringEntity;
import org.json.JSONObject;

import simbio.se.sau.API;
import android.content.Context;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/**
 * Extends it to access the network in asyn mode. More details see {@link AsyncHttpClient} and {@link AsyncHttpResponseHandler}
 * 
 * @author Ademar Alves de Oliveira (ademar111190@gmail.com)
 * @date Nov 29, 2013 1:06:16 PM
 * @since {@link API#Version_3_0_0}
 */
public abstract class SauHttpClient extends AsyncHttpResponseHandler {

	/*
	 * Singleton
	 */
	protected static AsyncHttpClient asyncHttpClient;

	/*
	 * Class variables
	 */
	protected SauHttpDelegate delegate;

	/**
	 * @return the singleton {@link SauHttpClient} instance
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
	 * @param response
	 *            the {@link String} returned from server
	 * @since {@link API#Version_3_1_3}
	 */
	protected abstract void success(String response);

	/**
	 * @param requestParams
	 *            the {@link RequestParams}
	 * @since {@link API#Version_3_1_3}
	 */
	public void get(RequestParams requestParams) {
		asyncHttpClient.get(getUrl(), requestParams, this);
	}

	/**
	 * @param context
	 *            the {@link Context} to codify the json
	 * @param jsonParams
	 *            the {@link JSONObject} to be codified
	 * @since {@link API#Version_3_1_3}
	 */
	public void post(Context context, JSONObject jsonParams) {
		try {
			asyncHttpClient.post(context, getUrl(), new StringEntity(jsonParams.toString()), "application/json", this);
		} catch (Exception e) {
			onFailure(e.getCause(), "");
		}
	}

}
