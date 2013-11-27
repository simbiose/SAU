/**
 * 
 */
package simbio.se.sau.utilities;

import java.util.AbstractMap;
import java.util.Collection;
import java.util.Deque;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Queue;
import java.util.Set;

import simbio.se.sau.API;
import android.annotation.TargetApi;
import android.os.Build;

/**
 * A class to validate if an item is null or empty
 * 
 * @author Ademar Alves de Oliveira (ademar111190@gmail.com)
 * @date Nov 27, 2013 12:18:11 PM
 * @since {@link API#Version_3_0_0}
 */
public class NullOrEmpty {

	/**
	 * Verify if an {@link Object} is null or empty
	 * 
	 * @param object
	 *            the {@link Object} to be analized
	 * @return <code>true</code> if {@link Object} is <code>null</code> or empty, <code>false</code> if it is not <code>null</code> and not empty, or if the {@link Object} is the object instance is not verifiable like integers or other like it
	 */
	@TargetApi(Build.VERSION_CODES.GINGERBREAD)
	public static boolean verify(Object object) {
		if (object == null)
			return true;

		if (object instanceof String)
			return ((String) object).isEmpty();
		else if (object instanceof Collection<?>)
			return ((Collection<?>) object).isEmpty();
		else if (object instanceof Set<?>)
			return ((Set<?>) object).isEmpty();
		else if (object instanceof AbstractMap<?, ?>)
			return ((AbstractMap<?, ?>) object).isEmpty();
		else if (object instanceof Iterator<?>)
			return !((Iterator<?>) object).hasNext();
		else if (object instanceof Enumeration<?>)
			return !((Enumeration<?>) object).hasMoreElements();
		else if (object instanceof Deque<?>)
			return ((Deque<?>) object).isEmpty();
		else if (object instanceof Queue<?>)
			return ((Queue<?>) object).isEmpty();
		else if (object instanceof Object[])
			return ((Object[]) object).length <= 0;
		else if (object instanceof int[])
			return ((int[]) object).length <= 0;
		else if (object instanceof long[])
			return ((long[]) object).length <= 0;
		else if (object instanceof float[])
			return ((float[]) object).length <= 0;
		else if (object instanceof double[])
			return ((double[]) object).length <= 0;
		else if (object instanceof char[])
			return ((char[]) object).length <= 0;
		else if (object instanceof boolean[])
			return ((boolean[]) object).length <= 0;
		else if (object instanceof byte[])
			return ((byte[]) object).length <= 0;
		else if (object instanceof short[])
			return ((short[]) object).length <= 0;
		else
			return false;
	}

}
