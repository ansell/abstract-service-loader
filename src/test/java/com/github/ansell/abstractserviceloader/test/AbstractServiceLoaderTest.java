/**
 * 
 */
package com.github.ansell.abstractserviceloader.test;

import java.util.Collection;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

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
     * @return The expected number of services that will be initially loaded after calls to
     *         {@link #getNewServiceLoader()}.
     */
    public abstract int getExpectedInitialServiceCount();
    
    /**
     * 
     * @return A collection of keys for the services that will be enabled initially.
     */
    public abstract Collection<K> getExpectedInitialServiceKeys();
    
    /**
     * 
     * @return The expected key for all services returned by {@link #getNewService()}.
     */
    public abstract K getExpectedKeyForNewService();
    
    /**
     * This needs to consistently return new objects that all have the key which is returned by
     * {@link #getExpectedKeyForNewService()} for the current test.
     * 
     * At most one of these services will be used during each test.
     * 
     * @return A single implementation of a service whose service loader is under test, which at
     *         least implements support for getting the key, based on the internal, protected method
     *         K getKey(S) in AbstractServiceLoader.
     */
    public abstract S getNewService();
    
    /**
     * 
     * @return A new implementation of {@link AbstractServiceLoader<K, S>} for each invocation.
     */
    public abstract AbstractServiceLoader<K, S> getNewServiceLoader();
    
    /**
     * Test method for
     * {@link com.github.ansell.abstractserviceloader.AbstractServiceLoader#add(java.lang.Object)}.
     */
    @Test
    public final void testAdd()
    {
        final AbstractServiceLoader<K, S> serviceLoader = this.getNewServiceLoader();
        
        serviceLoader.clear();
        
        final S service = this.getNewService();
        
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
        final AbstractServiceLoader<K, S> serviceLoader = this.getNewServiceLoader();
        
        final int originalCount = serviceLoader.getAll().size();
        
        Assert.assertEquals("Did not find the expected number of initial services",
                this.getExpectedInitialServiceCount(), originalCount);
        
        serviceLoader.clear();
        
        final int afterClearCountInitial = serviceLoader.getAll().size();
        
        Assert.assertEquals("Clear did not remove all initial services", 0, afterClearCountInitial);
        
        final S service = this.getNewService();
        
        serviceLoader.add(service);
        
        Assert.assertEquals(1, serviceLoader.getAll().size());
        
        serviceLoader.clear();
        
        final int afterClearCountManual = serviceLoader.getAll().size();
        
        Assert.assertEquals("Clear did not remove all manually added services", 0, afterClearCountManual);
        
    }
    
    /**
     * Test method for
     * {@link com.github.ansell.abstractserviceloader.AbstractServiceLoader#get(java.lang.Object)}.
     */
    @Test
    public final void testGet()
    {
        final AbstractServiceLoader<K, S> serviceLoader = this.getNewServiceLoader();
        
        final Collection<K> expectedInitialServiceKeys = this.getExpectedInitialServiceKeys();
        
        Assert.assertNotNull("Expected initial service keys was null", expectedInitialServiceKeys);
        Assert.assertFalse("Expected initial service keys was empty", expectedInitialServiceKeys.isEmpty());
        
        for(final K nextInitialKey : expectedInitialServiceKeys)
        {
            final Collection<S> collection = serviceLoader.get(nextInitialKey);
            
            Assert.assertFalse(collection.isEmpty());
            
            for(final S nextService : collection)
            {
                Assert.assertNotNull(nextService);
            }
        }
    }
    
    /**
     * Test method for
     * {@link com.github.ansell.abstractserviceloader.AbstractServiceLoader#getAll()}.
     */
    @Test
    public final void testGetAll()
    {
        final AbstractServiceLoader<K, S> serviceLoader = this.getNewServiceLoader();
        
        final Collection<K> expectedInitialServiceKeys = this.getExpectedInitialServiceKeys();
        
        Assert.assertNotNull("Expected initial service keys was null", expectedInitialServiceKeys);
        Assert.assertFalse("Expected initial service keys was empty", expectedInitialServiceKeys.isEmpty());
        
        final Collection<S> allInitialServices = serviceLoader.getAll();
        
        Assert.assertEquals("Did not find the expected number of initial services",
                this.getExpectedInitialServiceCount(), allInitialServices.size());
        
        for(final S nextInitialService : allInitialServices)
        {
            final K key = serviceLoader.getKey(nextInitialService);
            
            Assert.assertNotNull("Initial service had a null key", key);
            
            Assert.assertTrue("Found unexpected initial service key: " + key.toString(),
                    expectedInitialServiceKeys.contains(key));
        }
        
        serviceLoader.clear();
        
        final int afterClearCountInitial = serviceLoader.getAll().size();
        
        Assert.assertEquals("Clear did not remove all initial services", 0, afterClearCountInitial);
        
        final S service = this.getNewService();
        
        serviceLoader.add(service);
        
        final Collection<S> manualServices = serviceLoader.getAll();
        
        Assert.assertEquals(1, manualServices.size());
        
        final K expectedKey = this.getExpectedKeyForNewService();
        
        Assert.assertNotNull("Expected key was null", expectedKey);
        
        Assert.assertEquals(expectedKey, serviceLoader.getKey(manualServices.iterator().next()));
        
        serviceLoader.clear();
        
        final int afterClearCountManual = serviceLoader.getAll().size();
        
        Assert.assertEquals("Clear did not remove all manually added services", 0, afterClearCountManual);
    }
    
    /**
     * Test method for
     * {@link com.github.ansell.abstractserviceloader.AbstractServiceLoader#getKey(java.lang.Object)}
     * .
     */
    @Test
    public final void testGetKey()
    {
        final AbstractServiceLoader<K, S> serviceLoader = this.getNewServiceLoader();
        
        final S service = this.getNewService();
        
        final K expectedKey = this.getExpectedKeyForNewService();
        
        Assert.assertNotNull("Expected key was null", expectedKey);
        
        Assert.assertEquals(expectedKey, serviceLoader.getKey(service));
    }
    
    /**
     * Test method for
     * {@link com.github.ansell.abstractserviceloader.AbstractServiceLoader#getKeys()}.
     */
    @Test
    public final void testGetKeys()
    {
        final AbstractServiceLoader<K, S> serviceLoader = this.getNewServiceLoader();
        
        final Collection<K> expectedInitialServiceKeys = this.getExpectedInitialServiceKeys();
        
        Assert.assertNotNull("Expected initial service keys was null", expectedInitialServiceKeys);
        Assert.assertFalse("Expected initial service keys was empty", expectedInitialServiceKeys.isEmpty());
        
        final Set<K> keys = serviceLoader.getKeys();
        
        Assert.assertEquals("Did not find the expected number of initial service keys", keys.size(),
                expectedInitialServiceKeys.size());
        
        for(final K nextExpectedKey : expectedInitialServiceKeys)
        {
            Assert.assertTrue("Could not find one of the expected keys in the service loader",
                    keys.contains(nextExpectedKey));
        }
        
        for(final K nextKey : keys)
        {
            Assert.assertTrue("Could not find one of the service keys in the collection of expected keys",
                    expectedInitialServiceKeys.contains(nextKey));
        }
    }
    
    /**
     * Test method for
     * {@link com.github.ansell.abstractserviceloader.AbstractServiceLoader#has(java.lang.Object)}.
     */
    @Test
    public final void testHas()
    {
        final AbstractServiceLoader<K, S> serviceLoader = this.getNewServiceLoader();
        
        final Collection<K> expectedInitialServiceKeys = this.getExpectedInitialServiceKeys();
        
        Assert.assertNotNull("Expected initial service keys was null", expectedInitialServiceKeys);
        Assert.assertFalse("Expected initial service keys was empty", expectedInitialServiceKeys.isEmpty());
        
        for(final K nextExpectedKey : expectedInitialServiceKeys)
        {
            Assert.assertTrue("Could not find one of the expected keys in the service loader",
                    serviceLoader.has(nextExpectedKey));
        }
        
        final Set<K> keys = serviceLoader.getKeys();
        
        Assert.assertEquals("Did not find the expected number of initial service keys", keys.size(),
                expectedInitialServiceKeys.size());
        
        for(final K nextKey : keys)
        {
            Assert.assertTrue("Could not find one of the service keys in the collection of expected keys",
                    serviceLoader.has(nextKey));
        }
    }
    
    /**
     * Test method for
     * {@link com.github.ansell.abstractserviceloader.AbstractServiceLoader#remove(java.lang.Object)}
     * .
     */
    @Test
    public final void testRemoveActual()
    {
        final AbstractServiceLoader<K, S> serviceLoader = this.getNewServiceLoader();
        
        final Set<K> keys = serviceLoader.getKeys();
        
        for(final K nextKey : keys)
        {
            Assert.assertTrue(serviceLoader.has(nextKey));
            Assert.assertTrue(serviceLoader.getKeys().contains(nextKey));
            
            final Collection<S> services = serviceLoader.get(nextKey);
            
            for(final S nextService : services)
            {
                serviceLoader.remove(nextService);
            }
            
            Assert.assertFalse(serviceLoader.has(nextKey));
            Assert.assertFalse(serviceLoader.getKeys().contains(nextKey));
        }
        
        Assert.assertTrue(serviceLoader.getKeys().isEmpty());
        Assert.assertTrue(serviceLoader.getAll().isEmpty());
    }
    
    /**
     * Test method for
     * {@link com.github.ansell.abstractserviceloader.AbstractServiceLoader#removeByKey(java.lang.Object)}
     * .
     */
    @Test
    public final void testRemoveByKeyActual()
    {
        final AbstractServiceLoader<K, S> serviceLoader = this.getNewServiceLoader();
        
        final Set<K> keys = serviceLoader.getKeys();
        
        for(final K nextKey : keys)
        {
            Assert.assertTrue(serviceLoader.has(nextKey));
            Assert.assertTrue(serviceLoader.getKeys().contains(nextKey));
            serviceLoader.removeByKey(nextKey);
            Assert.assertFalse(serviceLoader.has(nextKey));
            Assert.assertFalse(serviceLoader.getKeys().contains(nextKey));
        }
        
        Assert.assertTrue(serviceLoader.getKeys().isEmpty());
        Assert.assertTrue(serviceLoader.getAll().isEmpty());
    }
    
    /**
     * Test method for
     * {@link com.github.ansell.abstractserviceloader.AbstractServiceLoader#removeByKey(java.lang.Object)}
     * .
     */
    @Test
    public final void testRemoveByKeyExpected()
    {
        final AbstractServiceLoader<K, S> serviceLoader = this.getNewServiceLoader();
        
        final Collection<K> expectedInitialServiceKeys = this.getExpectedInitialServiceKeys();
        
        Assert.assertNotNull("Expected initial service keys was null", expectedInitialServiceKeys);
        Assert.assertFalse("Expected initial service keys was empty", expectedInitialServiceKeys.isEmpty());
        
        for(final K nextKey : expectedInitialServiceKeys)
        {
            Assert.assertTrue(serviceLoader.has(nextKey));
            Assert.assertTrue(serviceLoader.getKeys().contains(nextKey));
            serviceLoader.removeByKey(nextKey);
            Assert.assertFalse(serviceLoader.has(nextKey));
            Assert.assertFalse(serviceLoader.getKeys().contains(nextKey));
        }
        
        Assert.assertTrue(serviceLoader.getKeys().isEmpty());
        Assert.assertTrue(serviceLoader.getAll().isEmpty());
    }
    
    /**
     * Test method for
     * {@link com.github.ansell.abstractserviceloader.AbstractServiceLoader#remove(java.lang.Object)}
     * .
     */
    @Test
    public final void testRemoveExpected()
    {
        final AbstractServiceLoader<K, S> serviceLoader = this.getNewServiceLoader();
        
        final Collection<K> expectedInitialServiceKeys = this.getExpectedInitialServiceKeys();
        
        Assert.assertNotNull("Expected initial service keys was null", expectedInitialServiceKeys);
        Assert.assertFalse("Expected initial service keys was empty", expectedInitialServiceKeys.isEmpty());
        
        for(final K nextKey : expectedInitialServiceKeys)
        {
            Assert.assertTrue(serviceLoader.has(nextKey));
            Assert.assertTrue(serviceLoader.getKeys().contains(nextKey));
            
            final Collection<S> services = serviceLoader.get(nextKey);
            
            for(final S nextService : services)
            {
                serviceLoader.remove(nextService);
            }
            
            Assert.assertFalse(serviceLoader.has(nextKey));
            Assert.assertFalse(serviceLoader.getKeys().contains(nextKey));
        }
        
        Assert.assertTrue(serviceLoader.getKeys().isEmpty());
        Assert.assertTrue(serviceLoader.getAll().isEmpty());
    }
    
}
