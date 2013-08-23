package com.amazonia.utils.functional;

import java.lang.reflect.Method;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import com.amazonia.utils.functional.config.JFConfig;
import com.amazonia.utils.functional.config.JFConfig.Key;
import com.amazonia.utils.functional.config.JFInitializer;
import com.amazonia.utils.functional.exceptions.UnregisteredException;

/**
 * This class is intend to provider some sort of forks functionality to Java
 * 
 * @author pengfeil
 * 
 */
public class JForksRunner {
	private ExecutorService pool;

	public JForksRunner() {
		pool = Executors.newCachedThreadPool();
	}

	/**
	 * Convenience way to fork thread to do some computation. plan to provide
	 * fork/join for recursive support in the future.
	 * 
	 * @param obj
	 * @param methodName
	 * @param args
	 * @return
	 */
	public Future<Object> fork(Object obj, String methodName, Object[] args) {
		JFConfig.Key key = new Key(obj.getClass().getName(), methodName);
		Method m = JFInitializer.getInstance().getConfig().getMethodByKey(key);
		if (m != null) {
			return pool.submit(new JForkCallable(obj, m, args));
		}
		throw new UnregisteredException(methodName + " has not been registered");
	}

	public static class JForkCallable implements Callable<Object> {
		private Method method;
		private Object obj;
		private Object[] args;

		public JForkCallable(Object obj, Method method, Object[] args) {
			this.method = method;
			this.obj = obj;
			this.args = args;
		}

		@Override
		public Object call() throws Exception {
			return method.invoke(obj, args);
		}

	}
}
