/**
 * 
 */
package com.github.ansell.abstractserviceloader.test;

import java.util.ServiceConfigurationError;

import org.junit.Assert;
import org.junit.Test;

import com.github.ansell.abstractserviceloader.AbstractServiceLoader;
import com.github.ansell.abstractserviceloader.AbstractUniqueServiceLoader;

/**
 * Additional tests for the methods that are modified in {@link AbstractUniqueServiceLoader}.
 * 
 * The service loader implementations using this test must extend
 * {@link AbstractUniqueServiceLoader}.
 * 
 * @author Peter Ansell p_ansell@yahoo.com
 */
public abstract class AbstractUniqueServiceLoaderTest<K, S> extends AbstractServiceLoaderTest<K, S>
{
    /**
     * Returns a new implementation of a service that can be duplicated with the given key as its
     * identifier.
     * <p>
     * NOTE: This will be called multiple times within a test, so the implementor must be sure that
     * the service used to test this capability can be instantiated multiple times.
     * 
     * @param key
     *            A new service with the given duplicate key, in order to test the uniqueness
     *            constraints in the add and getUnique methods.
     * @return An implementation of the service that has the given key as its identifier.
     */
    public abstract S getNewDuplicateService(K key);
    
    /**
     * Overrides the abstract {@link #getNewService()} method to use the result of
     * {@link #getExpectedKeyForNewService()} as the key for a call to
     * {@link #getNewDuplicateService(Object)}.
     * 
     * @return A new service that can be used to test the service loader.
     */
    @Override
    public final S getNewService()
    {
        return this.getNewDuplicateService(this.getExpectedKeyForNewService());
    }
    
    /**
     * Overrides the abstract {@link #getNewServiceLoader()} method to use the result of the
     * {@link #getNewUniqueServiceLoader()} method.
     * 
     * @return A new implementation of {@link AbstractUniqueServiceLoader<K, S>} for each
     *         invocation.
     */
    @Override
    public final AbstractServiceLoader<K, S> getNewServiceLoader()
    {
        return this.getNewUniqueServiceLoader();
    }
    
    /**
     * 
     * @return A new implementation of {@link AbstractUniqueServiceLoader<K, S>} for each
     *         invocation.
     */
    public abstract AbstractUniqueServiceLoader<K, S> getNewUniqueServiceLoader();
    
    /**
     * Test method for
     * {@link com.github.ansell.abstractserviceloader.AbstractUniqueServiceLoader#add(java.lang.Object)}
     * .
     */
    @Override
    @Test
    public final void testAddDuplicate()
    {
        final AbstractUniqueServiceLoader<K, S> serviceLoader = this.getNewUniqueServiceLoader();
        
        final K nextKey = this.getExpectedKeyForNewService();
        
        final S nextService = this.getNewDuplicateService(nextKey);
        
        serviceLoader.add(nextService);
        
        Assert.assertTrue(serviceLoader.has(nextKey));
        
        try
        {
            serviceLoader.add(nextService);
            Assert.fail("Did not receive expected exception");
        }
        catch(final ServiceConfigurationError sce)
        {
            Assert.assertTrue(sce.getMessage().contains("Found a duplicate service"));
        }
    }
    
    /**
     * Test method for
     * {@link com.github.ansell.abstractserviceloader.AbstractUniqueServiceLoader#add(java.lang.Object)}
     * .
     */
    @Test
    public final void testAddUniqueSingle()
    {
        final AbstractUniqueServiceLoader<K, S> serviceLoader = this.getNewUniqueServiceLoader();
        
        final K nextKey = this.getExpectedKeyForNewService();
        
        final S nextService = this.getNewDuplicateService(nextKey);
        
        serviceLoader.add(nextService);
        
        Assert.assertTrue(serviceLoader.has(nextKey));
    }
    
    /**
     * Test method for
     * {@link com.github.ansell.abstractserviceloader.AbstractUniqueServiceLoader#getUnique(java.lang.Object)}
     * .
     */
    @Test
    public final void testGetUnique()
    {
        // NOTE: There does not seem to be any simple method of testing the getUnique
        // implementation, as the add method is overridden to provide uniqueness.
        
        final AbstractUniqueServiceLoader<K, S> serviceLoader = this.getNewUniqueServiceLoader();
        
        final K nextKey = this.getExpectedKeyForNewService();
        
        final S nextService = this.getNewDuplicateService(nextKey);
        
        serviceLoader.add(nextService);
        
        Assert.assertTrue(serviceLoader.has(nextKey));
        
        final S unique = serviceLoader.getUnique(nextKey);
        
        Assert.assertNotNull("Found a null service for the given key", unique);
    }
    
}
