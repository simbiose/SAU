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
package simbio.se.sau.sample.sql;

import java.util.Date;

import simbio.se.sau.model.IAbstractModel;

/**
 * The {@link IAbstractModel} implementation is necessary to be used on listview but not sql
 *
 * @author Ademar Alves de Oliveira (ademar111190@gmail.com)
 * @date Nov 26, 2013 5:22:52 PM
 */
public class SqlFooModel implements IAbstractModel {

    private long time;
    private String text;

    public SqlFooModel(String text) {
        this.text = text;
        this.time = new Date().getTime();
    }

    public SqlFooModel() {
    }

    public String getText() {
        return text;
    }

    public long getTime() {
        return time;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setTime(long time) {
        this.time = time;
    }

}
