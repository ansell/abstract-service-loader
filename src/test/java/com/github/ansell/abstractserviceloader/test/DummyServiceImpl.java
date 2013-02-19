/**
 * 
 */
package com.github.ansell.abstractserviceloader.test;

/**
 * Implementation of the DummyService interface used to test the AbstractServiceLoader class.
 * 
 * @author Peter Ansell p_ansell@yahoo.com
 */
public class DummyServiceImpl implements DummyService
{
    private final String key;
    
    public DummyServiceImpl()
    {
        this.key = "DummyServiceImpl";
    }
    
    public DummyServiceImpl(final String newKey)
    {
        this.key = newKey;
    }
    
    @Override
    public String getDummyKey()
    {
        return this.key;
    }
}
