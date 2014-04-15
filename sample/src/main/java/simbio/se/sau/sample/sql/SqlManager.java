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

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import simbio.se.sau.database.DatabaseDelegate;
import simbio.se.sau.database.DatabaseHelper;

/**
 * You need extend the {@link DatabaseHelper} and the method
 * {@link DatabaseHelper#getModelsToBeCreatedOnDatabase()} is very important
 *
 * @author Ademar Alves de Oliveira (ademar111190@gmail.com)
 * @date Nov 26, 2013 5:18:34 PM
 */
public class SqlManager extends DatabaseHelper {

    public SqlManager(Context context, DatabaseDelegate delegate) {
        super(context, delegate, "sauExample.sqlite", 1);
    }

    @Override
    protected List<Class<?>> getModelsToBeCreatedOnDatabase() {
        List<Class<?>> clazzez = new ArrayList<Class<?>>();
        clazzez.add(SqlFooModel.class);
        return clazzez;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

}
