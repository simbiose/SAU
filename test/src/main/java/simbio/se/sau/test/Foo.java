/**
 * 
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
	public double[] valuesDouble = { 1.2, 1.4 };

	/**
	 * Randomize values to change from standard
	 */
	public void randomize() {
		Random random = new Random();
		valueInt = random.nextInt();
		valueFloat = random.nextFloat();
		valuesDouble = new double[] { random.nextDouble(), random.nextDouble() };
		valueString = "FooT_" + random;
	}

}
