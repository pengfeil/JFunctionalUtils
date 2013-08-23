package com.amazonia.utils.functional.config;

import org.junit.Assert;
import org.junit.Test;

import com.amazonia.utils.functional.exceptions.UninitializedException;

public class JFInitializerTest {
	@Test
	public void testRegister() throws SecurityException, ClassNotFoundException {
		JFInitializer.getInstance().initialize(null);
		JFInitializer.getInstance().registerFork(AnnotationHandlerTest.class);
	}

	@Test
	public void testSingleton() {
		Assert.assertEquals(JFInitializer.getInstance(),
				JFInitializer.getInstance());
	}

	@Test(expected = UninitializedException.class)
	public void testUnInitialized() {
		JFInitializer.getInstance().clear();
		Assert.assertFalse(JFInitializer.getInstance().isInitialized());
		JFInitializer.getInstance().getConfig();
	}
}
