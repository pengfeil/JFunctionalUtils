package com.amazonia.utils.functional.config;

import java.lang.reflect.Method;
import java.net.URISyntaxException;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

import com.amazonia.utils.functional.annotations.Fork;
import com.amazonia.utils.functional.config.JFConfig.Key;
import com.amazonia.utils.functional.exceptions.UninitializedException;

/**
 * This is a initializer for JFunctioanl. This is Thread safe.
 * 
 * @author pengfeil
 * 
 */
public class JFInitializer {
	/**
	 * singleton instance
	 */
	private static JFInitializer instance;
	/**
	 * whether app has been initialized already
	 */
	private boolean hasInitialized;
	/**
	 * the configuration container
	 */
	private JFConfig config;

	/**
	 * mark as singleton
	 */
	protected JFInitializer() {
		hasInitialized = false;
	}

	/**
	 * construct instance of JFInitializer and make sure the instance is only
	 * single one even in multi-thread env.
	 * 
	 * @return
	 */
	public static JFInitializer getInstance() {
		if (instance == null) {
			synchronized (JFInitializer.class) {
				if (instance == null) {
					instance = new JFInitializer();
				}
			}
		}
		return instance;
	}

	/**
	 * @return whether app has been initialized already
	 */
	public boolean isInitialized() {
		return hasInitialized;
	}

	/**
	 * get the configuration
	 * 
	 * @return
	 */
	public JFConfig getConfig() {
		if (config == null) {
			throw new UninitializedException(
					"JFunctionalUtils has not been initialized yet!");
		}
		return config;
	}

	private ReentrantLock initializeLock = new ReentrantLock();

	/**
	 * Do initialization. Configuration has been supported now
	 * 
	 * @param packageName
	 *            package to read
	 * @throws URISyntaxException
	 */
	public void initialize(String configPath) {
		if (hasInitialized) {
			return;
		}
		initializeLock.lock();
		// do initialize stuff
		config = new JFConfig();
		initializeLock.unlock();
	}

	public void clear() {
		if (config != null) {
			initializeLock.lock();
			if (config != null) {
				// do clear stuff
				config.clear();
				hasInitialized = false;
				config = null;
				initializeLock.unlock();
			}
		}
	}

	/**
	 * register method in cls to be forked in mutiThread
	 * 
	 * @param cls
	 *            class type of Class
	 * @throws SecurityException
	 * @throws ClassNotFoundException
	 */
	public void registerFork(Class<?> cls) throws SecurityException,
			ClassNotFoundException {
		AnnotationHandler ah = new AnnotationHandler(cls);
		List<Method> methods = ah.getMethodsByAnnotation(Fork.class);
		for (Method m : methods) {
			getConfig().registeMethod(new Key(cls.getName(), m.getName()), m);
		}
	}
}
