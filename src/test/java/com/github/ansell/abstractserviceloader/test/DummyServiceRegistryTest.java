/**
 * 
 */
package com.github.ansell.abstractserviceloader.test;

import java.util.Arrays;
import java.util.Collection;

import com.github.ansell.abstractserviceloader.AbstractServiceLoader;

/**
 * 
 * @author Peter Ansell p_ansell@yahoo.com
 */
public class DummyServiceRegistryTest extends AbstractServiceLoaderTest<String, DummyService>
{
    @Override
    public int getExpectedInitialServiceCount()
    {
        return 1;
    }
    
    @Override
    public Collection<String> getExpectedInitialServiceKeys()
    {
        return Arrays.asList("DummyServiceImpl");
    }
    
    @Override
    public String getExpectedKeyForNewService()
    {
        return "ManuallyCreatedDummyServiceKey";
    }
    
    @Override
    public DummyService getNewService()
    {
        return new DummyServiceImpl("ManuallyCreatedDummyServiceKey");
    }
    
    @Override
    public AbstractServiceLoader<String, DummyService> getNewServiceLoader()
    {
        return new DummyServiceRegistry();
    }
}
