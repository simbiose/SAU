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

import android.database.Cursor;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.HashMap;

import simbio.se.sau.API;
import simbio.se.sau.log.SimbiLog;
import simbio.se.shiva.Shiva;

/**
 * A lot of utilities to do {@link Shiva} more easy on android
 *
 * @author Ademar Alves de Oliveira (ademar111190@gmail.com)
 * @date Nov 26, 2013 3:36:23 PM
 * @since {@link API#Version_3_0_0}
 */
public class ShivaAndroidUtils {

    /**
     * A type map to conver {@link Cursor} to java {@link Object}
     *
     * @author Ademar Alves de Oliveira (ademar111190@gmail.com)
     * @date Nov 27, 2013 11:39:44 AM
     * @since {@link API#Version_3_0_0}
     */
    public enum TypeMap {
        INTEGER, STRING, FLOAT, LONG, DOUBLE, SHORT;
    }

    /**
     * the hash map to be used with {@link TypeMap}
     */
    private static HashMap<Type, TypeMap> javaTypeSqlType;

    /**
     * @return the javaTypeSqlType an {@link HashMap} mapping the Java types with sqlite types
     */
    public static HashMap<Type, TypeMap> getJavaTypeSqlType() {
        if (javaTypeSqlType == null) {
            javaTypeSqlType = new HashMap<Type, TypeMap>();

            javaTypeSqlType.put(Integer.TYPE, TypeMap.INTEGER);
            javaTypeSqlType.put(Float.TYPE, TypeMap.FLOAT);
            javaTypeSqlType.put(Long.TYPE, TypeMap.LONG);
            javaTypeSqlType.put(Double.TYPE, TypeMap.DOUBLE);
            javaTypeSqlType.put(Short.TYPE, TypeMap.SHORT);
        }
        return javaTypeSqlType;
    }

    /**
     * @param clazz  the {@link Class} to be used as model
     * @param cursor the {@link Cursor} with data from sql
     * @return the {@link Object} instance from clazz variable with {@link Cursor} values
     * @since {@link API#Version_3_0_0}
     */
    public static Object getObjectFromCursor(Class<?> clazz, Cursor cursor) {
        Object object;
        try {
            object = clazz.newInstance();
        } catch (Exception e) {
            SimbiLog.printException(e);
            return null;
        }

        String[] columnsNames = cursor.getColumnNames();
        Field field;
        TypeMap typeMap;
        for (String key : columnsNames) {
            try {
                field = clazz.getDeclaredField(key);
                field.setAccessible(true);

                typeMap = getJavaTypeSqlType().get(field.getType());
                if (typeMap == null)
                    typeMap = TypeMap.STRING;

                switch (typeMap) {
                    case INTEGER:
                        field.set(object, cursor.getInt(cursor.getColumnIndex(key)));
                        break;
                    case FLOAT:
                        field.set(object, cursor.getFloat(cursor.getColumnIndex(key)));
                        break;
                    case LONG:
                        field.set(object, cursor.getLong(cursor.getColumnIndex(key)));
                        break;
                    case DOUBLE:
                        field.set(object, cursor.getDouble(cursor.getColumnIndex(key)));
                        break;
                    case SHORT:
                        field.set(object, cursor.getShort(cursor.getColumnIndex(key)));
                        break;
                    case STRING:
                        field.set(object, cursor.getString(cursor.getColumnIndex(key)));
                        break;
                }
            } catch (Exception e) {
                SimbiLog.printException(e);
            }
        }
        return object;
    }
}
