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
    
    /**
     * 
     */
    public DummyUniqueServiceRegistry()
    {
        super(DummyService.class);
    }
    
    /**
     * @param classLoader
     *            A specific class loader to use with {@link ServiceLoader#load(Class, ClassLoader)}
     *            .
     */
    public DummyUniqueServiceRegistry(final ClassLoader classLoader)
    {
        super(DummyService.class, classLoader);
    }
    
    @Override
    public String getKey(final DummyService service)
    {
        return service.getDummyKey();
    }
    
}
