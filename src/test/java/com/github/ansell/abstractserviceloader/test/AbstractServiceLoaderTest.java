/**
 * 
 */
package com.github.ansell.abstractserviceloader.test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.github.ansell.abstractserviceloader.AbstractServiceLoader;

/**
 * Abstract test for AbstractServiceLoader implementations.
 * 
 * @author Peter Ansell p_ansell@yahoo.com
 * 
 * @param <K>
 *            The key used by the implementation of AbstractServiceLoader.
 * @param <S>
 *            The service class used by the implementation of AbstractServiceLoader.
 */
public abstract class AbstractServiceLoaderTest<K, S>
{
    /**
     * 
     * @return A new implementation of {@link AbstractServiceLoader<K, S>} for each invocation.
     */
    public abstract AbstractServiceLoader<K, S> getNewServiceLoader();
    
    /**
     * 
     * @return An implementation of the service whose service loader is under test, which at least
     *         implements support for getting the key, based on the internal, protected method K
     *         getKey(S) in AbstractServiceLoader.
     */
    public abstract S getNewService();
    
    /**
     * 
     * @return The expected number of services that will be initially loaded after calls to
     *         {@link #getNewServiceLoader()}.
     */
    public abstract int getExpectedInitialServiceCount();
    
    /**
     * Test method for
     * {@link com.github.ansell.abstractserviceloader.AbstractServiceLoader#add(java.lang.Object)}.
     */
    @Test
    public final void testAdd()
    {
        AbstractServiceLoader<K, S> serviceLoader = getNewServiceLoader();
        
        serviceLoader.clear();
        
        S service = getNewService();
        
        serviceLoader.add(service);
        
        Assert.assertEquals(1, serviceLoader.getAll().size());
    }
    
    /**
     * Test method for {@link com.github.ansell.abstractserviceloader.AbstractServiceLoader#clear()}
     * .
     */
    @Test
    public final void testClear()
    {
        AbstractServiceLoader<K, S> serviceLoader = getNewServiceLoader();
        
        int originalCount = serviceLoader.getAll().size();
        
        Assert.assertEquals("Did not find the expected number of initial services", getExpectedInitialServiceCount(),
                originalCount);
        
        serviceLoader.clear();
        
        int afterClearCountInitial = serviceLoader.getAll().size();
        
        Assert.assertEquals("Clear did not remove all initial services", 0, afterClearCountInitial);
        
        S service = getNewService();
        
        serviceLoader.add(service);
        
        Assert.assertEquals(1, serviceLoader.getAll().size());
        
        serviceLoader.clear();
        
        int afterClearCountManual = serviceLoader.getAll().size();
        
        Assert.assertEquals("Clear did not remove all manually added services", 0, afterClearCountManual);
        
    }
    
    /**
     * Test method for
     * {@link com.github.ansell.abstractserviceloader.AbstractServiceLoader#get(java.lang.Object)}.
     */
    @Test
    public final void testGet()
    {
        fail("Not yet implemented"); // TODO
    }
    
    /**
     * Test method for
     * {@link com.github.ansell.abstractserviceloader.AbstractServiceLoader#getAll()}.
     */
    @Test
    public final void testGetAll()
    {
        fail("Not yet implemented"); // TODO
    }
    
    /**
     * Test method for
     * {@link com.github.ansell.abstractserviceloader.AbstractServiceLoader#getKey(java.lang.Object)}
     * .
     */
    @Test
    public final void testGetKey()
    {
        fail("Not yet implemented"); // TODO
    }
    
    /**
     * Test method for
     * {@link com.github.ansell.abstractserviceloader.AbstractServiceLoader#getKeys()}.
     */
    @Test
    public final void testGetKeys()
    {
        fail("Not yet implemented"); // TODO
    }
    
    /**
     * Test method for
     * {@link com.github.ansell.abstractserviceloader.AbstractServiceLoader#has(java.lang.Object)}.
     */
    @Test
    public final void testHas()
    {
        fail("Not yet implemented"); // TODO
    }
    
    /**
     * Test method for
     * {@link com.github.ansell.abstractserviceloader.AbstractServiceLoader#removeByKey(java.lang.Object)}
     * .
     */
    @Test
    public final void testRemoveByKey()
    {
        fail("Not yet implemented"); // TODO
    }
    
    /**
     * Test method for
     * {@link com.github.ansell.abstractserviceloader.AbstractServiceLoader#remove(java.lang.Object)}
     * .
     */
    @Test
    public final void testRemove()
    {
        fail("Not yet implemented"); // TODO
    }
    
}
