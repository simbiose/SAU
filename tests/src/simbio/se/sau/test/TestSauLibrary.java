/**
 * 
 */
package simbio.se.sau.test;

import static simbio.se.sau.iterable.Range.range;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import simbio.se.sau.json.JsonUtils;
import simbio.se.sau.log.SimbiLog;
import simbio.se.sau.persistense.PreferencesHelper;
import simbio.se.sau.utilities.KeyKeyMap;
import simbio.se.sau.utilities.NullOrEmpty;
import android.test.AndroidTestCase;

import com.google.gson.reflect.TypeToken;

/**
 * A normal test class, for more details {@link AndroidTestCase}
 * 
 * @author Ademar Alves de Oliveira ademar111190@gmail.com
 * @date 2013-aŭg-19 00:57:49
 */
public class TestSauLibrary extends AndroidTestCase {

	/**
	 * A normal test method, for more details {@link AndroidTestCase}
	 */
	public final void testSomething() {
		// test shared preferences
		Object[] param1 = { "1", 2, null, 4.6f, new Object[] {} };
		String param1Json = JsonUtils.toJson(param1);
		String param1JsonValidate = "[\"1\",2,null,4.6,[]]";

		Foo foo1 = new Foo();
		Foo foo2 = (Foo) JsonUtils.fromJsonOrNull("{\"valueInt\":128,\"\":-67.87f,\"valueString\":\"FooT\",\"valuesDouble\":[1.2,1.4]}", Foo.class);
		Foo fooDifferent = new Foo();
		fooDifferent.randomize();
		Type type = new TypeToken<ArrayList<Foo>>() {
		}.getType();

		PreferencesHelper preferences = new PreferencesHelper(getContext());
		String keyBooleanTrue = "keyBooleanTrue";
		String keyBooleanFalse = "keyBooleanFalse";
		String keyFloat = "keyFloat";
		String keyInt = "keyInt";
		String keyLong = "keyLong";
		String keyString = "keyString";
		String keyObject = "keyObject";
		String keyArray = "keyArray";
		String keyMockBoolean = "keyMockBoolean";
		String keyMockFloat = "keyMockFloat";
		String keyMockInt = "keyMockInt";
		String keyMockLong = "keyMockLong";
		String keyMockString = "keyMockString";
		String keyMockObject = "keyMockObject";
		String keyMockArray = "keyMockArray";
		float floatPreferencesValue = 78632.767f;
		int intPreferencesValue = 879839;
		long longPreferencesValue = 93039902332l;
		String stringPreferencesValue = "Quanto teste é esse oO";
		Foo objectPreferencesValue = new Foo();
		ArrayList<Foo> arrayListPreferencesValue = new ArrayList<Foo>();
		ArrayList<Foo> arrayListDifferent = new ArrayList<Foo>();
		Foo foo;
		for (int i : range(3)) {
			foo = new Foo();
			foo.valueInt = i;
			arrayListPreferencesValue.add(foo);
		}
		for (int i : range(-7)) {
			foo = new Foo();
			foo.valueInt = i;
			arrayListDifferent.add(foo);
		}

		preferences.put(keyBooleanTrue, true);
		preferences.put(keyBooleanFalse, false);
		preferences.put(keyFloat, floatPreferencesValue);
		preferences.put(keyInt, intPreferencesValue);
		preferences.put(keyLong, longPreferencesValue);
		preferences.put(keyString, stringPreferencesValue);
		preferences.put(keyObject, objectPreferencesValue);
		preferences.put(keyArray, arrayListPreferencesValue);

		Foo objectPreferencesValueValidateWithDef = (Foo) preferences.getObject(keyObject, Foo.class, objectPreferencesValue);
		Foo objectPreferencesValueValidateWithDefDifferent = (Foo) preferences.getObject(keyObject, Foo.class, fooDifferent);
		Foo objectPreferencesValueValidateWithoutDef = (Foo) preferences.getObjectOrNull(keyObject, Foo.class);
		Foo objectPreferencesValueValidateWithDefMock = (Foo) preferences.getObject(keyMockObject, Foo.class, objectPreferencesValue);
		Foo objectPreferencesValueValidateWithDefDifferentMock = (Foo) preferences.getObject(keyMockObject, Foo.class, fooDifferent);
		Foo objectPreferencesValueValidateWithoutDefMock = (Foo) preferences.getObjectOrNull(keyMockObject, Foo.class);
		String stringPreferencesValueValidateWithDef = preferences.getString(keyString, stringPreferencesValue);
		String stringPreferencesValueValidateWithDefEmpty = preferences.getStringOrEmpty(keyString);
		String stringPreferencesValueValidateWithoutDef = preferences.getStringOrNull(keyString);
		String stringPreferencesValueValidateWithDefMock = preferences.getString(keyMockString, stringPreferencesValue);
		String stringPreferencesValueValidateWithDefEmptyMock = preferences.getStringOrEmpty(keyMockString);
		String stringPreferencesValueValidateWithoutDefMock = preferences.getStringOrNull(keyMockString);
		float floatPreferencesValueValidate = preferences.getFloatOrZero(keyFloat);
		int intPreferencesValueValidate = preferences.getIntOrZero(keyInt);
		long longPreferencesValueValidate = preferences.getLongOrZero(keyLong);
		float floatPreferencesValueValidateMock = preferences.getFloatOrZero(keyMockFloat);
		int intPreferencesValueValidateMock = preferences.getIntOrZero(keyMockInt);
		long longPreferencesValueValidateMock = preferences.getLongOrZero(keyMockLong);
		boolean booleanPreferencesValueValidateTrue = preferences.getBooleanOrFalse(keyBooleanTrue);
		boolean booleanPreferencesValueValidateFalse = preferences.getBooleanOrTrue(keyBooleanFalse);
		boolean booleanPreferencesValueValidateTrueMock = preferences.getBooleanOrFalse(keyMockBoolean);
		boolean booleanPreferencesValueValidateFalseMock = preferences.getBooleanOrTrue(keyMockBoolean);
		@SuppressWarnings("unchecked")
		ArrayList<Foo> arrayListPreferencesValueValidateWithDef = (ArrayList<Foo>) preferences.getObject(keyArray, type, arrayListPreferencesValue);
		@SuppressWarnings("unchecked")
		ArrayList<Foo> arrayListPreferencesValueValidateWithDefDifferent = (ArrayList<Foo>) preferences.getObject(keyArray, type, arrayListDifferent);
		@SuppressWarnings("unchecked")
		ArrayList<Foo> arrayListPreferencesValueValidateWithoutDef = (ArrayList<Foo>) preferences.getObjectOrNull(keyArray, type);
		@SuppressWarnings("unchecked")
		ArrayList<Foo> arrayListPreferencesValueValidateWithDefMock = (ArrayList<Foo>) preferences.getObject(keyMockArray, type, arrayListPreferencesValue);
		@SuppressWarnings("unchecked")
		ArrayList<Foo> arrayListPreferencesValueValidateWithDefDifferentMock = (ArrayList<Foo>) preferences.getObject(keyMockArray, type, arrayListDifferent);
		@SuppressWarnings("unchecked")
		ArrayList<Foo> arrayListPreferencesValueValidateWithoutDefMock = (ArrayList<Foo>) preferences.getObjectOrNull(keyMockArray, type);

		assertEquals(param1JsonValidate, param1Json);
		assertEquals(foo1.valueFloat, foo2.valueFloat);
		assertEquals(foo1.valueInt, foo2.valueInt);
		assertEquals(foo1.valueString, foo2.valueString);
		assertEquals(foo1.valuesDouble[0], foo2.valuesDouble[0]);
		assertEquals(foo1.valuesDouble[1], foo2.valuesDouble[1]);
		assertEquals(objectPreferencesValueValidateWithDef.valueFloat, objectPreferencesValue.valueFloat);
		assertEquals(objectPreferencesValueValidateWithDef.valueInt, objectPreferencesValue.valueInt);
		assertEquals(objectPreferencesValueValidateWithDef.valueString, objectPreferencesValue.valueString);
		assertEquals(objectPreferencesValueValidateWithDef.valuesDouble[0], objectPreferencesValue.valuesDouble[0]);
		assertEquals(objectPreferencesValueValidateWithDef.valuesDouble[1], objectPreferencesValue.valuesDouble[1]);
		assertEquals(objectPreferencesValueValidateWithDefDifferent.valueFloat, objectPreferencesValue.valueFloat);
		assertEquals(objectPreferencesValueValidateWithDefDifferent.valueInt, objectPreferencesValue.valueInt);
		assertEquals(objectPreferencesValueValidateWithDefDifferent.valueString, objectPreferencesValue.valueString);
		assertEquals(objectPreferencesValueValidateWithDefDifferent.valuesDouble[0], objectPreferencesValue.valuesDouble[0]);
		assertEquals(objectPreferencesValueValidateWithDefDifferent.valuesDouble[1], objectPreferencesValue.valuesDouble[1]);
		assertEquals(objectPreferencesValueValidateWithoutDef.valueFloat, objectPreferencesValue.valueFloat);
		assertEquals(objectPreferencesValueValidateWithoutDef.valueInt, objectPreferencesValue.valueInt);
		assertEquals(objectPreferencesValueValidateWithoutDef.valueString, objectPreferencesValue.valueString);
		assertEquals(objectPreferencesValueValidateWithoutDef.valuesDouble[0], objectPreferencesValue.valuesDouble[0]);
		assertEquals(objectPreferencesValueValidateWithoutDef.valuesDouble[1], objectPreferencesValue.valuesDouble[1]);
		assertEquals(objectPreferencesValueValidateWithDefMock.valueFloat, objectPreferencesValue.valueFloat);
		assertEquals(objectPreferencesValueValidateWithDefMock.valueInt, objectPreferencesValue.valueInt);
		assertEquals(objectPreferencesValueValidateWithDefMock.valueString, objectPreferencesValue.valueString);
		assertEquals(objectPreferencesValueValidateWithDefMock.valuesDouble[0], objectPreferencesValue.valuesDouble[0]);
		assertEquals(objectPreferencesValueValidateWithDefMock.valuesDouble[1], objectPreferencesValue.valuesDouble[1]);
		assertEquals(objectPreferencesValueValidateWithDefDifferentMock.valueFloat, fooDifferent.valueFloat);
		assertEquals(objectPreferencesValueValidateWithDefDifferentMock.valueInt, fooDifferent.valueInt);
		assertEquals(objectPreferencesValueValidateWithDefDifferentMock.valueString, fooDifferent.valueString);
		assertEquals(objectPreferencesValueValidateWithDefDifferentMock.valuesDouble[0], fooDifferent.valuesDouble[0]);
		assertEquals(objectPreferencesValueValidateWithDefDifferentMock.valuesDouble[1], fooDifferent.valuesDouble[1]);
		assertNull(objectPreferencesValueValidateWithoutDefMock);
		assertEquals(stringPreferencesValueValidateWithDef, stringPreferencesValue);
		assertEquals(stringPreferencesValueValidateWithDefEmpty, stringPreferencesValue);
		assertEquals(stringPreferencesValueValidateWithoutDef, stringPreferencesValue);
		assertEquals(stringPreferencesValueValidateWithDefMock, stringPreferencesValue);
		assertEquals(stringPreferencesValueValidateWithDefEmptyMock, "");
		assertNull(stringPreferencesValueValidateWithoutDefMock);
		assertEquals(floatPreferencesValueValidate, floatPreferencesValue);
		assertEquals(intPreferencesValueValidate, intPreferencesValue);
		assertEquals(longPreferencesValueValidate, longPreferencesValue);
		assertEquals(floatPreferencesValueValidateMock, 0.0f);
		assertEquals(intPreferencesValueValidateMock, 0);
		assertEquals(longPreferencesValueValidateMock, 0l);
		assertTrue(booleanPreferencesValueValidateTrue);
		assertTrue(booleanPreferencesValueValidateFalseMock);
		assertFalse(booleanPreferencesValueValidateFalse);
		assertFalse(booleanPreferencesValueValidateTrueMock);
		assertTrue(assertFooArrayList(arrayListPreferencesValue, arrayListPreferencesValueValidateWithDef));
		assertTrue(assertFooArrayList(arrayListPreferencesValue, arrayListPreferencesValueValidateWithDefDifferent));
		assertTrue(assertFooArrayList(arrayListPreferencesValue, arrayListPreferencesValueValidateWithoutDef));
		assertTrue(assertFooArrayList(arrayListPreferencesValue, arrayListPreferencesValueValidateWithDefMock));
		assertTrue(assertFooArrayList(arrayListDifferent, arrayListPreferencesValueValidateWithDefDifferentMock));
		assertTrue(assertFooArrayList(arrayListPreferencesValueValidateWithoutDefMock, null));

		// test prints
		SimbiLog.print(param1, foo1, foo2);
		SimbiLog.print("Message from Assert");
		SimbiLog.printException(new Exception("A mock exception to test printException method"));
		SimbiLog.printException(new Exception());
		SimbiLog.log(this, param1, foo1, null, foo2);
		SimbiLog.log(null, param1, foo1, null, foo2);
		SimbiLog.log(1, param1, foo1, null, foo2);
		SimbiLog.print(arrayListPreferencesValue);
		SimbiLog.print(arrayListPreferencesValueValidateWithoutDef);
		SimbiLog.print();
		SimbiLog.here();
		SimbiLog.printText(param1, foo1, foo2, 6, 7.9f, "Text");

		// test Null Or Empty
		ArrayList<Foo> fooArrayList = new ArrayList<Foo>();
		HashSet<Foo> fooHashSet = new HashSet<Foo>();
		HashMap<Foo, Foo> fooHashMap = new HashMap<Foo, Foo>();
		Foo[] foosEmpty = new Foo[0];
		Foo[] foosNotEmpty = new Foo[1];
		int[] intsEmpty = new int[0];
		int[] intsNotEmpty = new int[1];
		String string = new String();
		Foo fooNullOrEmpty = new Foo();

		assertTrue(NullOrEmpty.verify(null));
		assertTrue(NullOrEmpty.verify(fooArrayList));
		assertTrue(NullOrEmpty.verify(fooHashSet));
		assertTrue(NullOrEmpty.verify(fooHashMap));
		assertTrue(NullOrEmpty.verify(foosEmpty));
		assertTrue(NullOrEmpty.verify(intsEmpty));
		assertTrue(NullOrEmpty.verify(string));

		fooArrayList.add(fooNullOrEmpty);
		fooHashSet.add(fooNullOrEmpty);
		fooHashMap.put(fooNullOrEmpty, fooNullOrEmpty);
		string = new String("Foo");

		assertFalse(NullOrEmpty.verify(fooArrayList));
		assertFalse(NullOrEmpty.verify(fooHashSet));
		assertFalse(NullOrEmpty.verify(fooHashMap));
		assertFalse(NullOrEmpty.verify(foosNotEmpty));
		assertFalse(NullOrEmpty.verify(intsNotEmpty));
		assertFalse(NullOrEmpty.verify(string));
		assertFalse(NullOrEmpty.verify(fooNullOrEmpty));

		// assert key key
		String key1 = "Key 1";
		int key2 = 2;
		Foo key3 = new Foo();
		KeyKeyMap keyKeyMap = new KeyKeyMap();
		keyKeyMap.put(key1, key2);

		assertEquals(keyKeyMap.get(key1), key2);
		assertNull(keyKeyMap.get(key3));

		keyKeyMap.put(key1, key3);
		assertEquals(keyKeyMap.get(key1), key3);
		assertEquals(keyKeyMap.get(key3), key1);
		assertEquals(keyKeyMap.get(key2), key1);
		assertNull(keyKeyMap.get(null));

		keyKeyMap.put(null, key3);
		assertEquals(keyKeyMap.get(null), key3);
		assertNull(keyKeyMap.get(key3));
	}

	/**
	 * Only a helper method
	 * 
	 * @param fooA
	 *            {@link ArrayList} of {@link Foo} objects to compare
	 * @param fooB
	 *            {@link ArrayList} of {@link Foo} objects to compare
	 * @return <code>true</code> if the list are equals, <code>false</code> otherside
	 */
	private boolean assertFooArrayList(ArrayList<Foo> fooA, ArrayList<Foo> fooB) {
		if (fooA == null && fooB == null)
			return true;
		if ((fooA == null && fooB != null) || (fooA != null && fooB == null))
			return false;
		if (fooA.size() != fooB.size())
			return false;
		Foo fa;
		Foo fb;
		for (int i : range(fooA.size())) {
			fa = fooA.get(i);
			fb = fooB.get(i);
			if (fa == null && fb == null)
				return true;
			if ((fa == null && fb != null) || (fa != null && fb == null))
				return false;
			if (fa.valueFloat != fb.valueFloat)
				return false;
			if (fa.valueInt != fb.valueInt)
				return false;
			if (fa.valueString == null && fb.valueString == null)
				return true;
			if ((fa.valueString == null && fb.valueString != null) || (fa.valueString != null && fb.valueString == null))
				return false;
			if (!fa.valueString.equalsIgnoreCase(fb.valueString))
				return false;
			if (fa.valuesDouble == null && fb.valuesDouble == null)
				return true;
			if ((fa.valuesDouble == null && fb.valuesDouble != null) || (fa.valuesDouble != null && fb.valuesDouble == null))
				return false;
			if (fa.valuesDouble.length != fb.valuesDouble.length)
				return false;
			for (int j : range(fa.valuesDouble.length))
				if (fa.valuesDouble[j] != fb.valuesDouble[j])
					return false;
		}
		return true;
	}
}
