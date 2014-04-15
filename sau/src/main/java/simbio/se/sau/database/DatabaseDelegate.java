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
package simbio.se.sau.database;

import simbio.se.sau.API;

/**
 * @author Ademar Alves de Oliveira ademar111190@gmail.com
 * @date 2013-aŭg-14 23:57:59
 * @since {@link API#Version_3_0_0}
 */
public interface DatabaseDelegate {

    /**
     * This method will called when some error occurr on sql query
     *
     * @param databaseHelper the {@link DatabaseHelper} responsible
     * @param requestId      the request id
     * @param exception      the {@link Exception} occured
     * @since {@link API#Version_3_0_0}
     */
    public void onRequestFail(DatabaseHelper databaseHelper, int requestId, Exception exception);

    /**
     * This method will called when some error occurr on sql query
     *
     * @param databaseHelper the {@link DatabaseHelper} responsible
     * @param requestId      the request id
     * @param object         the {@link Object} returned or null if is a void request like insert
     * @since {@link API#Version_3_0_0}
     */
    public void onRequestSuccess(DatabaseHelper databaseHelper, int requestId, Object object);

}
