/**
 * 
 */
package simbio.se.sau.network;

import simbio.se.sau.API;

/**
 * The delegate to be used with {@link SauHttpClient}
 * 
 * @author Ademar Alves de Oliveira (ademar111190@gmail.com)
 * @date Feb 4, 2014 11:40:39 AM
 * @since {@link API#Version_3_1_3}
 */
public interface SauHttpDelegate {

	/**
	 * Called when occors an error on conection
	 * 
	 * @param sauHttpClient
	 *            the {@link SauHttpClient} that get the error
	 * @param throwable
	 *            the {@link Throwable} of error
	 * @param content
	 *            the {@link String} with error message
	 * @since {@link API#Version_3_1_3}
	 */
	public void onFail(SauHttpClient sauHttpClient, Throwable throwable, String content);

}
