package com.amazonia.utils.functional.config;

import junit.framework.Assert;

import org.junit.Test;

import com.amazonia.utils.functional.annotations.Fork;

public class AnnotationHandlerTest {
	@Test
	@Fork
	public void testGetMethodsByAnnotation() throws SecurityException,
			ClassNotFoundException {
		AnnotationHandler ah = new AnnotationHandler(
				AnnotationHandlerTest.class);
		Assert.assertEquals(1, ah.getMethodsByAnnotation(Fork.class).size());
	}
}
