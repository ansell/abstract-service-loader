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
    @Override
    public String getDummyKey()
    {
        return "DummyServiceImpl";
    }
}
