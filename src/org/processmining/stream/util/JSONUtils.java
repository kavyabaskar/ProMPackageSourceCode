package org.processmining.stream.util;

import java.util.Map;
import java.util.Set;

import com.google.gson.Gson;
import com.google.gson.JsonElement;

public class JSONUtils {

	public static Object getValue(Set<Map.Entry<String, JsonElement>> entrySet, String key, Class<?> castTo) {
		Object value = null;
		for (Map.Entry<String, JsonElement> entry : entrySet) {
			if (entry.getKey().equals(key)) {
				Gson gson = new Gson();
				value = gson.fromJson(entry.getValue(), castTo);
				break;
			}
		}
		return value;
	}

}
