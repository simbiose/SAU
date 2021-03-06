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
package simbio.se.sau.adapters;

import java.util.ArrayList;

import simbio.se.sau.API;
import simbio.se.sau.model.IAbstractModel;

/**
 * A interface to use the {@link AbstractAdapter} class.
 *
 * @author Ademar Alves de Oliveira ademar111190@gmail.com
 * @date 2013-aŭg-14 23:52:06
 * @since {@link API#Version_1_0_0}
 */
public interface IAbstractAdapter<T extends IAbstractModel> {

    /**
     * this method need return the models used by {@link AbstractAdapter} class.
     *
     * @return the models needed to {@link AbstractAdapter}.
     * @since {@link API#Version_1_0_0}
     */
    public ArrayList<T> getAdapterModels();

}
