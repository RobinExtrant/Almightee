package com.almightee.config;

import java.time.Duration;

import org.ehcache.config.builders.*;
import org.ehcache.jsr107.Eh107Configuration;

import io.github.jhipster.config.jcache.BeanClassLoaderAwareJCacheRegionFactory;
import io.github.jhipster.config.JHipsterProperties;

import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.serviceregistry.Registration;
import org.springframework.context.annotation.*;

@Configuration
@EnableCaching
public class CacheConfiguration {

    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        BeanClassLoaderAwareJCacheRegionFactory.setBeanClassLoader(this.getClass().getClassLoader());
        JHipsterProperties.Cache.Ehcache ehcache =
            jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(
            CacheConfigurationBuilder.newCacheConfigurationBuilder(Object.class, Object.class,
                ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(ehcache.getTimeToLiveSeconds())))
                .build());
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            cm.createCache(com.almightee.repository.UserRepository.USERS_BY_LOGIN_CACHE, jcacheConfiguration);
            cm.createCache(com.almightee.repository.UserRepository.USERS_BY_EMAIL_CACHE, jcacheConfiguration);
            cm.createCache(com.almightee.domain.User.class.getName(), jcacheConfiguration);
            cm.createCache(com.almightee.domain.Authority.class.getName(), jcacheConfiguration);
            cm.createCache(com.almightee.domain.User.class.getName() + ".authorities", jcacheConfiguration);
            cm.createCache(com.almightee.domain.Category.class.getName(), jcacheConfiguration);
            cm.createCache(com.almightee.domain.Product.class.getName(), jcacheConfiguration);
            cm.createCache(com.almightee.domain.Pattern.class.getName(), jcacheConfiguration);
            cm.createCache(com.almightee.domain.LineItem.class.getName(), jcacheConfiguration);
            cm.createCache(com.almightee.domain.Cart.class.getName(), jcacheConfiguration);
            cm.createCache(com.almightee.domain.Cart.class.getName() + ".lineItems", jcacheConfiguration);
            cm.createCache(com.almightee.domain.Command.class.getName(), jcacheConfiguration);
            cm.createCache(com.almightee.domain.Customer.class.getName(), jcacheConfiguration);
            cm.createCache(com.almightee.domain.Customer.class.getName() + ".commands", jcacheConfiguration);
            // jhipster-needle-ehcache-add-entry
        };
    }
}
