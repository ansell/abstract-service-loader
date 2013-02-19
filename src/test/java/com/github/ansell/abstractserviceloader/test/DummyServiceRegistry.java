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
    
    /**
     * 
     */
    public DummyServiceRegistry()
    {
        super(DummyService.class);
    }
    
    /**
     * @param classLoader
     *            A specific class loader to use with {@link ServiceLoader#load(Class, ClassLoader)}
     *            .
     */
    public DummyServiceRegistry(ClassLoader classLoader)
    {
        super(DummyService.class, classLoader);
    }
    
    @Override
    protected String getKey(DummyService service)
    {
        return service.getDummyKey();
    }
    
}
