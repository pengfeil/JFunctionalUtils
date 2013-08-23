package com.amazonia.utils.functional.config;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

/**
 * For handling the annotation for JFunctionalUtils
 * 
 * @author pengfeil
 * 
 */
public class AnnotationHandler {
	private Method[] methods;

	/**
	 * constructor of AnnotationHandler
	 * 
	 * @param packagePath
	 *            package to handle
	 * @throws ClassNotFoundException
	 * @throws SecurityException
	 * @throws URISyntaxException
	 */
	public AnnotationHandler(Class<?> cls) throws SecurityException,
			ClassNotFoundException {
		methods = cls.getMethods();
	}

	/**
	 * return the methods marked by Fork annotation
	 * 
	 * @return
	 */
	public List<Method> getMethodsByAnnotation(Class<? extends Annotation> type) {
		ArrayList<Method> list = new ArrayList<Method>();
		for (Method m : methods) {
			Annotation ano = m.getAnnotation(type);
			if (ano != null) {
				list.add(m);
			}
		}
		return list;
	}
}
