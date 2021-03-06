package com.jdbc.tool.test;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

/**  
 * @author chenwei  
 * @date 创建时间：2016年11月13日 下午3:57:58 
 * @version 1.0  
 */

public class KeyTest implements KeyHolder{
	private final List<Map<String, Object>> keyList;


	/**
	 * Create a new GeneratedKeyHolder with a default list.
	 */
	public KeyTest() {
		this.keyList = new LinkedList<Map<String, Object>>();
	}

	/**
	 * Create a new GeneratedKeyHolder with a given list.
	 * @param keyList a list to hold maps of keys
	 */
	public KeyTest(List<Map<String, Object>> keyList) {
		this.keyList = keyList;
	}


	public Number getKey() throws InvalidDataAccessApiUsageException, DataRetrievalFailureException {
		if (this.keyList.size() == 0) {
			return null;
		}
		if (this.keyList.size() > 1 || this.keyList.get(0).size() > 1) {
			throw new InvalidDataAccessApiUsageException(
					"The getKey method should only be used when a single key is returned.  " +
					"The current key entry contains multiple keys: " + this.keyList);
		}
		Iterator<Object> keyIter = this.keyList.get(0).values().iterator();
		if (keyIter.hasNext()) {
			Object key = keyIter.next();
			if (!(key instanceof Number)) {
				throw new DataRetrievalFailureException(
						"The generated key is not of a supported numeric type. " +
						"Unable to cast [" + (key != null ? key.getClass().getName() : null) +
						"] to [" + Number.class.getName() + "]");
			}
			return (Number) key;
		}
		else {
			throw new DataRetrievalFailureException("Unable to retrieve the generated key. " +
					"Check that the table has an identity column enabled.");
		}
	}

	public Map<String, Object> getKeys() throws InvalidDataAccessApiUsageException {
		if (this.keyList.size() == 0) {
			return null;
		}
		if (this.keyList.size() > 1)
			throw new InvalidDataAccessApiUsageException(
					"The getKeys method should only be used when keys for a single row are returned.  " +
					"The current key list contains keys for multiple rows: " + this.keyList);
		return this.keyList.get(0);
	}

	public List<Map<String, Object>> getKeyList() {
		return this.keyList;
	}

	
	public static void main(String [] args){
		KeyHolder keyHolder = new KeyTest();
		System.out.println(keyHolder.getKey().longValue());
	}
}

