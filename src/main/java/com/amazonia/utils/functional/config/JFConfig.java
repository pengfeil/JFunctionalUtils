package com.amazonia.utils.functional.config;

import java.lang.reflect.Method;
import java.util.HashMap;

/**
 * This is a Container for all configurations
 * 
 * @author pengfeil
 * 
 */
public class JFConfig {
	public static class Key {
		public String classPath;
		public String methodName;

		public Key(String classPath, String methodName) {
			this.classPath = classPath;
			this.methodName = methodName;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result
					+ ((classPath == null) ? 0 : classPath.hashCode());
			result = prime * result
					+ ((methodName == null) ? 0 : methodName.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Key other = (Key) obj;
			if (classPath == null) {
				if (other.classPath != null)
					return false;
			} else if (!classPath.equals(other.classPath))
				return false;
			if (methodName == null) {
				if (other.methodName != null)
					return false;
			} else if (!methodName.equals(other.methodName))
				return false;
			return true;
		}
	}

	private HashMap<Key, Method> methodMap = new HashMap<Key, Method>();

	public void registeMethod(Key key, Method m) {
		methodMap.put(key, m);
	}

	public Method getMethodByKey(Key key) {
		return methodMap.get(key);
	}

	public void clear() {
		methodMap.clear();
	}
}
