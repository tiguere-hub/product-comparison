package com.meli.challenge.ProductComparisons.infrastructure.config;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;

/**
 * Enable Spring caching support
 * We can use a caching database here
 */
@Configuration
@EnableCaching
public class CacheConfig {
}
