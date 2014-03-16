/**
 * this file unlike the rest of the library is on the Apache license 2.0
 * because it was adapted from https://code.google.com/p/range-seek-bar/.
 * 
 *   Copyright 2013 Stephan Tittel (stephan.tittel@kom.tu-darmstadt.de)
 *   Copyright 2013 Ademar Alves de Oliveira (ademar111190@gmail.com)
 * 
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 * 
 *               http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package simbio.se.sau.view.enums;

import java.math.BigDecimal;

import simbio.se.sau.API;
import simbio.se.sau.view.RangeSeekBar;
import android.annotation.SuppressLint;

/**
 * Utility enumaration used to convert between Numbers and doubles.
 * 
 * @author Stephan Tittel (stephan.tittel@kom.tu-darmstadt.de)
 * @author Ademar Alves de Oliveira (ademar111190@gmail.com)
 * 
 * @date 2013-sep-30 02:44:01
 * 
 * 
 * @since {@link API#Version_2_0_0}
 */
public enum RangeSeekBarNumberType {
	LONG, DOUBLE, INTEGER, FLOAT, SHORT, BYTE, BIG_DECIMAL;

	/**
	 * @param value
	 *            a {@link Number} instance to be get your {@link RangeSeekBar} type
	 * @return the {@link RangeSeekBar} type of your {@link Number}
	 * @throws IllegalArgumentException
	 *             if you send a non supported {@link Number}
	 * @since {@link API#Version_2_0_0}
	 */
	public static <E extends Number> RangeSeekBarNumberType fromNumber(E value) throws IllegalArgumentException {
		if (value instanceof Long) {
			return LONG;
		}
		if (value instanceof Double) {
			return DOUBLE;
		}
		if (value instanceof Integer) {
			return INTEGER;
		}
		if (value instanceof Float) {
			return FLOAT;
		}
		if (value instanceof Short) {
			return SHORT;
		}
		if (value instanceof Byte) {
			return BYTE;
		}
		if (value instanceof BigDecimal) {
			return BIG_DECIMAL;
		}
		throw new IllegalArgumentException("Number class '" + value.getClass().getName() + "' is not supported");
	}

	/**
	 * @param value
	 *            to be converted to a {@link Number}
	 * @return a {@link Number} from value param
	 * @since {@link API#Version_2_0_0}
	 */
	@SuppressLint("UseValueOf")
	public Number toNumber(double value) {
		switch (this) {
		case LONG:
			return new Long((long) value);
		case DOUBLE:
			return value;
		case INTEGER:
			return new Integer((int) value);
		case FLOAT:
			return new Float(value);
		case SHORT:
			return new Short((short) value);
		case BYTE:
			return new Byte((byte) value);
		case BIG_DECIMAL:
			return new BigDecimal(value);
		}
		throw new InstantiationError("can't convert " + this + " to a Number object");
	}
}
