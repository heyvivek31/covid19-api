package com.covid19.plasma.util;

import org.ehcache.event.CacheEvent;
import org.ehcache.event.CacheEventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CacheEventLogger implements CacheEventListener<Object, Object> {

    Logger logger = LoggerFactory.getLogger(CacheEventLogger.class);
    
    @Override
    public void onEvent(CacheEvent<?, ?> cacheEvent) {
        logger.info("Cache event "+ cacheEvent.getType() +" for item with key " + cacheEvent.getKey() + ". Old value = " + cacheEvent.getOldValue() 
                + " ,New value = " + cacheEvent.getNewValue());
    }
}
