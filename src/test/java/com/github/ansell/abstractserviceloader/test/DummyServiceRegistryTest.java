/**
 * 
 */
package com.github.ansell.abstractserviceloader.test;

import com.github.ansell.abstractserviceloader.AbstractServiceLoader;

/**
 * 
 * @author Peter Ansell p_ansell@yahoo.com
 */
public class DummyServiceRegistryTest extends AbstractServiceLoaderTest<String, DummyService>
{
    @Override
    public AbstractServiceLoader<String, DummyService> getNewServiceLoader()
    {
        return new DummyServiceRegistry();
    }
    
    @Override
    public DummyService getNewService()
    {
        return new DummyServiceImpl();
    }
    
    @Override
    public int getExpectedInitialServiceCount()
    {
        return 1;
    }
}
