/**
 * 
 */
package com.github.ansell.abstractserviceloader.test;

import java.util.Arrays;
import java.util.Collection;

import com.github.ansell.abstractserviceloader.AbstractUniqueServiceLoader;

/**
 * 
 * @author Peter Ansell p_ansell@yahoo.com
 */
public class DummyUniqueServiceRegistryTest extends
		AbstractUniqueServiceLoaderTest<String, DummyService> {
	@Override
	public int getExpectedInitialServiceCount() {
		return 1;
	}

	@Override
	public Collection<String> getExpectedInitialServiceKeys() {
		return Arrays.asList("DummyServiceImpl");
	}

	@Override
	public String getExpectedKeyForNewService() {
		return "ManuallyCreatedDummyUniqueServiceKey";
	}

	@Override
	public DummyService getNewDuplicateService(final String key) {
		return new DummyServiceImpl(key);
	}

	@Override
	public AbstractUniqueServiceLoader<String, DummyService> getNewUniqueServiceLoader() {
		return new DummyUniqueServiceRegistry(Thread.currentThread()
				.getContextClassLoader());
	}

	@Override
	public DummyService getNewServiceNullKey() {
		return new DummyServiceImpl(null);
	}
}
