package com.amazonia.utils.functional;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import junit.framework.Assert;

import org.junit.Test;

import com.amazonia.utils.functional.annotations.Fork;
import com.amazonia.utils.functional.config.JFInitializer;
import com.amazonia.utils.functional.exceptions.UnregisteredException;

public class JForksRunnerTest {
	@Fork
	public Integer count(int initVal, int n) {
		System.out.println(Thread.currentThread().getName());
		if (n == 0)
			return initVal;
		try {
			Thread.sleep(n*100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return count(initVal * 2, n - 1);
	}

	@Test
	public void testsubmitJob() throws SecurityException,
			ClassNotFoundException, InterruptedException, ExecutionException {
		JFInitializer.getInstance().initialize(null);
		JFInitializer.getInstance().registerFork(JForksRunnerTest.class);
		JForksRunner jr = new JForksRunner();
		Future<Object> result = jr.fork(this, "count", new Object[] { 100,
				10 });
		jr.fork(this, "count", new Object[] { 100,
				10 });
		jr.fork(this, "count", new Object[] { 100,
				10 });
		jr.fork(this, "count", new Object[] { 100,
				10 });
		int re = Integer.parseInt(result.get().toString());
		Assert.assertEquals(count(100, 10).intValue(), re);
	}

	@Test(expected = UnregisteredException.class)
	public void testsubmitJobException() throws SecurityException,
			ClassNotFoundException, InterruptedException, ExecutionException {
		JFInitializer.getInstance().initialize(null);
		JForksRunner jr = new JForksRunner();
		Future<Object> result = jr.fork(this, "count", new Object[] { 100,
				10 });
		System.out.println(result.get().toString());
	}
}
