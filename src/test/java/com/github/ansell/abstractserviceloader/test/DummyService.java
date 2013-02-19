/**
 * 
 */
package com.github.ansell.abstractserviceloader.test;

/**
 * Interface for a dummy service that is used to test AbstractServiceLoader.
 * 
 * @author Peter Ansell p_ansell@yahoo.com
 * 
 */
public interface DummyService
{
    /**
     * 
     * @return A unique key for the given implementation of the DummyService interface.
     */
    String getDummyKey();
}
