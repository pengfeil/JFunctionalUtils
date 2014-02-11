package com.amazonia.utils.functional;

import java.util.concurrent.ExecutionException;

import junit.framework.Assert;

import org.junit.Test;

import com.amazonia.utils.functional.annotations.Fork;
import com.amazonia.utils.functional.annotations.Join;
import com.amazonia.utils.functional.config.JFInitializer;
import com.amazonia.utils.functional.exceptions.UnregisteredException;

public class JForksRunnerTest {
	@Fork(forkNumber = 10)
	public Long fork(long n) {
		System.out.println(Thread.currentThread().getName() + "-fork");
		long count = n;
		while (count-- > 0)
			;
		return n;
	}

	@Join
	public Long join(Object[] mid) {
		System.out.println(Thread.currentThread().getName() + "-join");
		long sum = 0;
		for (Object o : mid) {
			sum += Long.parseLong(o.toString());
		}
		return sum;
	}

	@Test
	public void testsubmitJob() throws SecurityException,
			ClassNotFoundException, InterruptedException, ExecutionException {
		JFInitializer.getInstance().initialize(null);
		JFInitializer.getInstance().register(JForksRunnerTest.class);
		JForksRunner jr = new JForksRunner();
		Object result = jr.fork(this, "fork", "join",
				new Object[] { 1000L * 1000 * 1000 });
		long re = Long.parseLong(result.toString());
		Assert.assertEquals(1000L * 1000 * 1000 * 10, re);
	}

	@Test(expected = UnregisteredException.class)
	public void testsubmitJobException() throws SecurityException,
			ClassNotFoundException, InterruptedException, ExecutionException {
		JFInitializer.getInstance().initialize(null);
		JForksRunner jr = new JForksRunner();
		Object result = jr.fork(this, "count", "join", new Object[] { 10 });
		System.out.println(result.toString());
	}
}
