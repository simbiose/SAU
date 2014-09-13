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
package simbio.se.sau.test;

import java.util.Random;

import simbio.se.sau.model.IAbstractModel;

/**
 * A class to simulate data on {@link TestSauLibrary}
 *
 * @author Ademar Alves de Oliveira ademar111190@gmail.com
 * @date 2013-aŭg-19 02:30:08
 */
public class Foo implements IAbstractModel {

    public int valueInt = 128;
    public float valueFloat = -67.87f;
    public String valueString = "FooT";
    public double[] valuesDouble = {1.2, 1.4};

    /**
     * Randomize values to change from standard
     */
    public void randomize() {
        Random random = new Random();
        valueInt = random.nextInt();
        valueFloat = random.nextFloat();
        valuesDouble = new double[]{random.nextDouble(), random.nextDouble()};
        valueString = "FooT_" + random;
    }

}
