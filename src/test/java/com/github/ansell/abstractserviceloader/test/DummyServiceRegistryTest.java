/**
 * 
 */
package com.github.ansell.abstractserviceloader.test;

import java.util.Collections;
import java.util.Set;

import com.github.ansell.abstractserviceloader.AbstractServiceLoader;

/**
 * 
 * @author Peter Ansell p_ansell@yahoo.com
 */
public class DummyServiceRegistryTest extends
		AbstractServiceLoaderTest<String, DummyService> {
	@Override
	public int getExpectedInitialServiceCount() {
		return 1;
	}

	@Override
	public Set<String> getExpectedInitialServiceKeys() {
		return Collections.singleton("DummyServiceImpl");
	}

	@Override
	public String getExpectedKeyForNewService() {
		return "ManuallyCreatedDummyServiceKey";
	}

	@Override
	public DummyService getNewService() {
		return new DummyServiceImpl("ManuallyCreatedDummyServiceKey");
	}

	@Override
	public AbstractServiceLoader<String, DummyService> getNewServiceLoader() {
		return new DummyServiceRegistry(Thread.currentThread()
				.getContextClassLoader());
	}

	@Override
	public DummyService getNewServiceNullKey() {
		return new DummyServiceImpl(null);
	}
}
