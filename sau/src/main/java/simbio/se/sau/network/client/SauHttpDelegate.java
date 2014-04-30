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
     * @param sauHttpClient the {@link SauHttpClient} that has managed the request
     * @param response      the {@link String} returned from server
     * @since {@link API#Version_3_1_3}
     */
    public abstract void onRequestSuccess(SauHttpClient sauHttpClient, String response);

    /**
     * @param sauHttpClient the {@link SauHttpClient} that has managed the request
     * @param response      the {@link String} returned from cache
     * @since {@link API#Version_4_0_0}
     */
    public abstract void onRequestCached(SauHttpClient sauHttpClient, String response);

    /**
     * Called when occurs an error on connection
     *
     * @param sauHttpClient the {@link SauHttpClient} that has managed the request
     * @param throwable     the {@link Throwable} of error
     * @param content       the {@link String} with error message
     * @since {@link API#Version_3_1_3}
     */
    public void onRequestFail(SauHttpClient sauHttpClient, Throwable throwable, String content);

}
