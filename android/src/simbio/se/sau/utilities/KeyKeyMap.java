/**
 * 
 */
package simbio.se.sau.utilities;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import simbio.se.sau.API;

/**
 * A key key map, it means each object has a key and this key also is keyed by the bject
 * 
 * @author Ademar Alves de Oliveira (ademar111190@gmail.com)
 * @date Nov 27, 2013 1:05:49 PM
 * @since {@link API#Version_3_0_0}
 */
public class KeyKeyMap implements Map<Object, Object> {

	private HashMap<Object, Object> hashMapAasKey = new HashMap<Object, Object>();
	private HashMap<Object, Object> hashMapBasKey = new HashMap<Object, Object>();

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Map#clear()
	 */
	@Override
	public void clear() {
		hashMapAasKey.clear();
		hashMapBasKey.clear();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Map#containsKey(java.lang.Object)
	 */
	@Override
	public boolean containsKey(Object key) {
		return hashMapAasKey.containsKey(key) || hashMapBasKey.containsKey(key);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Map#containsValue(java.lang.Object)
	 */
	@Override
	public boolean containsValue(Object value) {
		return hashMapAasKey.containsValue(value) || hashMapBasKey.containsValue(value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Map#entrySet()
	 */
	@Override
	public Set<java.util.Map.Entry<Object, Object>> entrySet() {
		Set<java.util.Map.Entry<Object, Object>> set = hashMapAasKey.entrySet();
		set.addAll(hashMapBasKey.entrySet());
		return set;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Map#get(java.lang.Object)
	 */
	@Override
	public Object get(Object key) {
		Object object = hashMapAasKey.get(key);
		if (object == null)
			object = hashMapBasKey.get(key);
		return object;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Map#isEmpty()
	 */
	@Override
	public boolean isEmpty() {
		return hashMapAasKey.isEmpty() && hashMapBasKey.isEmpty();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Map#keySet()
	 */
	@Override
	public Set<Object> keySet() {
		Set<Object> set = hashMapAasKey.keySet();
		set.addAll(hashMapBasKey.keySet());
		return set;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Map#put(java.lang.Object, java.lang.Object)
	 */
	@Override
	public Object put(Object keyA, Object keyB) {
		Object object = get(keyA);
		if (object == null)
			object = get(keyB);
		hashMapAasKey.put(keyA, keyB);
		hashMapBasKey.put(keyB, keyA);
		return object;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Map#putAll(java.util.Map)
	 */
	@Override
	public void putAll(Map<? extends Object, ? extends Object> arg0) {
		hashMapAasKey.putAll(arg0);
		for (Object key : arg0.keySet())
			hashMapBasKey.put(arg0.get(key), key);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Map#remove(java.lang.Object)
	 */
	@Override
	public Object remove(Object key) {
		Object object = get(key);
		hashMapAasKey.remove(key);
		hashMapBasKey.remove(key);
		hashMapAasKey.remove(object);
		hashMapBasKey.remove(object);
		return object;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Map#size()
	 */
	@Override
	public int size() {
		return hashMapAasKey.size();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Map#values()
	 */
	@Override
	public Collection<Object> values() {
		Collection<Object> values = hashMapAasKey.values();
		values.addAll(hashMapBasKey.values());
		return values;
	}

}
