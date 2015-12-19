/**
 * 
 */
package com.github.ansell.abstractserviceloader.test;

import java.util.ServiceLoader;

import com.github.ansell.abstractserviceloader.AbstractUniqueServiceLoader;

/**
 * Dummy Service Registry for testing AbstractServiceLoader
 * 
 * @author Peter Ansell p_ansell@yahoo.com
 */
public class DummyUniqueServiceRegistry extends AbstractUniqueServiceLoader<String, DummyService>
{
	private static final long serialVersionUID = -2113679585718939159L;

	/**
     * 
     */
    public DummyUniqueServiceRegistry()
    {
        super(DummyService.class, s -> s.getDummyKey());
    }
    
    /**
     * @param classLoader
     *            A specific class loader to use with {@link ServiceLoader#load(Class, ClassLoader)}
     *            .
     */
    public DummyUniqueServiceRegistry(final ClassLoader classLoader)
    {
        super(DummyService.class, classLoader, s -> s.getDummyKey());
    }
}
