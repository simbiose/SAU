/**
 * 
 */
package simbio.se.sau.network;

import simbio.se.sau.API;

import com.loopj.android.http.AsyncHttpClient;

/**
 * Just provide singleton access to {@link AsyncHttpClient}
 * 
 * @author Ademar Alves de Oliveira (ademar111190@gmail.com)
 * @date Nov 29, 2013 1:06:16 PM
 * @since {@link API#Version_3_0_0}
 */
public class SauHttpClient extends AsyncHttpClient {

	/**
	 * Singleton
	 */
	private static SauHttpClient sauHttpClient;

	/**
	 * @return the singleton {@link SauHttpClient} instance
	 * @since {@link API#Version_3_0_0}
	 */
	public static SauHttpClient getSauHttpClient() {
		if (sauHttpClient == null)
			sauHttpClient = new SauHttpClient();
		return sauHttpClient;
	}

}
