/**
 * 
 */
package simbio.se.sau.test;

import simbio.se.sau.json.JsonUtils;
import simbio.se.sau.log.SimbiLog;
import simbio.se.sau.persistense.PreferencesHelper;
import android.test.AndroidTestCase;

/**
 * @author Ademar Alves de Oliveira ademar111190@gmail.com
 * @date 2013-aŭg-19 00:57:49
 * 
 */
public class TestSauLibrary extends AndroidTestCase {

	public TestSauLibrary() {
		super();
	}

	public final void testSomething() {
		// Objects to tests
		Object[] param1 = { "1", 2, null, 4.6f, new Object[] {} };
		String param1Json = JsonUtils.toJson(param1);
		String param1JsonValidate = "[\"1\",2,null,4.6,[]]";

		Foo foo1 = new Foo();
		Foo foo2 = (Foo) JsonUtils.fromJsonOrNull("{\"valueInt\":128,\"\":-67.87f,\"valueString\":\"FooT\",\"valuesDouble\":[1.2,1.4]}", Foo.class);
		Foo fooDifferent = new Foo();
		fooDifferent.randomize();

		PreferencesHelper preferences = new PreferencesHelper(getContext());
		String keyBooleanTrue = "keyBooleanTrue";
		String keyBooleanFalse = "keyBooleanFalse";
		String keyFloat = "keyFloat";
		String keyInt = "keyInt";
		String keyLong = "keyLong";
		String keyString = "keyString";
		String keyObject = "keyObject";
		String keyMockBoolean = "keyMockBoolean";
		String keyMockFloat = "keyMockFloat";
		String keyMockInt = "keyMockInt";
		String keyMockLong = "keyMockLong";
		String keyMockString = "keyMockString";
		String keyMockObject = "keyMockObject";
		float floatPreferencesValue = 78632.767f;
		int intPreferencesValue = 879839;
		long longPreferencesValue = 93039902332l;
		String stringPreferencesValue = "Quanto teste é esse oO";
		Foo objectPreferencesValue = new Foo();

		preferences.put(keyBooleanTrue, true);
		preferences.put(keyBooleanFalse, false);
		preferences.put(keyFloat, floatPreferencesValue);
		preferences.put(keyInt, intPreferencesValue);
		preferences.put(keyLong, longPreferencesValue);
		preferences.put(keyString, stringPreferencesValue);
		preferences.put(keyObject, objectPreferencesValue);

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

		// assets
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

		// final prints "to test prints too"
		SimbiLog.print(param1, foo1, foo2);
		SimbiLog.print("Message from Assert");
		SimbiLog.printException(new Exception("A mock exception to test printException method"));
		SimbiLog.log(this, param1, foo1, null, foo2);
	}
}
