package com.amazonia.utils.functional;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import com.amazonia.utils.functional.config.JFConfig;
import com.amazonia.utils.functional.config.JFConfig.Key;
import com.amazonia.utils.functional.config.JFInitializer;
import com.amazonia.utils.functional.exceptions.UnregisteredException;

/**
 * This class is intend to provider some sort of forks functionality to Java
 * Fork methods will produce a batch of mid-results and the join method will
 * merge the mid-results into final result
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
	 * @param forkMethod
	 * @param args
	 * @return Join result
	 * @throws ExecutionException
	 * @throws InterruptedException
	 */
	public Object fork(Object obj, String forkMethod, String joinMethod,
			Object[] args) throws InterruptedException, ExecutionException {
		JFConfig.Key forkKey = new Key(obj.getClass().getName(), forkMethod);
		JFConfig.Key joinKey = new Key(obj.getClass().getName(), joinMethod);
		Method mFork = JFInitializer.getInstance().getConfig()
				.getMethodByKey(forkKey);
		Method mJoin = JFInitializer.getInstance().getConfig()
				.getMethodByKey(joinKey);
		if (mFork != null && mJoin != null) {
			int forkNum = JFInitializer.getInstance().getConfig()
					.getForkNumByKey(forkKey);
			ArrayList<Future<Object>> results = doFork(obj, args, mFork,
					forkNum);
			Object[] forkResults = getForkResults(results);
			return pool
					.submit(new JForkCallable(obj, mJoin,
							new Object[] { forkResults })).get();
		} else {
			throw new UnregisteredException(forkMethod + " and " + joinMethod
					+ " have not been registered");
		}
	}

	private Object[] getForkResults(ArrayList<Future<Object>> results)
			throws InterruptedException, ExecutionException {
		Object[] objs = new Object[results.size()];
		for (int i = 0; i < objs.length; i++) {
			objs[i] = results.get(i).get();
		}
		return objs;
	}

	private ArrayList<Future<Object>> doFork(Object obj, Object[] args,
			Method m, int forkNum) {
		ArrayList<Future<Object>> results = new ArrayList<>();
		for (int i = 0; i < forkNum; i++) {
			results.add(pool.submit(new JForkCallable(obj, m, args)));
		}
		return results;
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
