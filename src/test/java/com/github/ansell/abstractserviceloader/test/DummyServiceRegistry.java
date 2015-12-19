/**
 * 
 */
package com.github.ansell.abstractserviceloader.test;

import java.util.ServiceLoader;

import com.github.ansell.abstractserviceloader.AbstractServiceLoader;

/**
 * Dummy Service Registry for testing AbstractServiceLoader
 * 
 * @author Peter Ansell p_ansell@yahoo.com
 */
public class DummyServiceRegistry extends AbstractServiceLoader<String, DummyService>
{
	private static final long serialVersionUID = 8517213328004036961L;

	/**
     * 
     */
    public DummyServiceRegistry()
    {
        super(DummyService.class, s -> s.getDummyKey());
    }
    
    /**
     * @param classLoader
     *            A specific class loader to use with {@link ServiceLoader#load(Class, ClassLoader)}
     *            .
     */
    public DummyServiceRegistry(final ClassLoader classLoader)
    {
        super(DummyService.class, classLoader, s -> s.getDummyKey());
    }
}
