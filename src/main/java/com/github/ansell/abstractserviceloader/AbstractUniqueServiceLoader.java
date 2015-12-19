/**
 * 
 */
package com.github.ansell.abstractserviceloader;

import java.util.Collection;
import java.util.Collections;
import java.util.ServiceConfigurationError;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

/**
 * An extension of the {@link AbstractServiceLoader} class to require the keys for services to be
 * unique.
 * 
 * @author Peter Ansell p_ansell@yahoo.com
 */
public abstract class AbstractUniqueServiceLoader<K, S> extends AbstractServiceLoader<K, S>
{
	private static final long serialVersionUID = -4759708909467622927L;

	/**
     * @param serviceClass
     */
    public AbstractUniqueServiceLoader(final Class<S> serviceClass,
			final Function<S, K> keyLambda)
    {
        super(serviceClass, keyLambda);
    }
    
    /**
     * @param serviceClass
     * @param classLoader
     */
    public AbstractUniqueServiceLoader(final Class<S> serviceClass, final ClassLoader classLoader,
			final Function<S, K> keyLambda)
    {
        super(serviceClass, classLoader, keyLambda);
    }
    
    /**
     * Extends the contract for the {@link AbstractServiceLoader#add(Object)} method to require that
     * services have unique keys.
     * 
     * @throws ServiceConfigurationError
     *             If another service is defined with the same key as the service to be added.
     */
    @Override
    public void add(final S service)
    {
        if(service == null)
        {
            throw new NullPointerException("Service must not be null");
        }
        
        final K key = this.getKey(service);
        
        if(key == null)
        {
            throw new NullPointerException("Key for service must not be null");
        }
        
        Collection<S> set = this.services.get(key);
        
        if(set == null)
        {
            set = Collections.newSetFromMap(new ConcurrentHashMap<S, Boolean>(2, 0.75f, 2));
            
            final Collection<S> existingSet = this.services.putIfAbsent(key, set);
            
            if(existingSet != null)
            {
                this.log.error("Failing due to a duplicate service with key: {} for class {}", key, service);
                throw new ServiceConfigurationError("Found a duplicate service with key: " + key + " for class: "
                        + service);
            }
        }
        else
        {
            this.log.error("Failing due to a duplicate service with key: {} for class {}", key, service);
            throw new ServiceConfigurationError("Found a duplicate service with key: " + key + " for class: " + service);
        }
        
        set.add(service);
        
        if(this.log.isDebugEnabled())
        {
            this.log.debug("Added service with key: {}", key);
        }
    }
    
    /**
     * 
     * @param key
     *            The key for the service to get.
     * @return A single service, or null if there is no service registered with that key.
     * @throws ServiceConfigurationError
     *             If there were multiple services registered with the given key.
     */
    public S getUnique(final K key)
    {
        final Collection<S> results = this.services.get(key);
        
        if(results == null || results.isEmpty())
        {
            return null;
        }
        else if(results.size() == 1)
        {
            return results.iterator().next();
        }
        
        this.log.error("Failing due to a duplicate service with key: {}", key);
        throw new ServiceConfigurationError("Found a duplicate service with key: " + key);
    }
}
