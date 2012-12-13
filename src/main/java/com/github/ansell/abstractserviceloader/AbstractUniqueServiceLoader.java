/**
 * 
 */
package com.github.ansell.abstractserviceloader;

import java.util.Collection;
import java.util.Collections;
import java.util.ServiceConfigurationError;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Peter Ansell p_ansell@yahoo.com
 * 
 */
public abstract class AbstractUniqueServiceLoader<K, S> extends AbstractServiceLoader<K, S>
{
    
    /**
     * @param serviceClass
     */
    public AbstractUniqueServiceLoader(Class<S> serviceClass)
    {
        super(serviceClass);
        // TODO Auto-generated constructor stub
    }
    
    /**
     * @param serviceClass
     * @param classLoader
     */
    public AbstractUniqueServiceLoader(Class<S> serviceClass, ClassLoader classLoader)
    {
        super(serviceClass, classLoader);
        // TODO Auto-generated constructor stub
    }
    
    @Override
    public void add(final S service)
    {
        if(service == null)
        {
            throw new NullPointerException("Service must not be null");
        }
        
        final K key = this.getKey(service);
        
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
    
    public S getUnique(K key)
    {
        Collection<S> results = this.services.get(key);
        
        if(results.isEmpty())
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
