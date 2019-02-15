package com.yavuzturtelekom.config;

import java.time.Duration;

import org.ehcache.config.builders.*;
import org.ehcache.jsr107.Eh107Configuration;

import io.github.jhipster.config.jcache.BeanClassLoaderAwareJCacheRegionFactory;
import io.github.jhipster.config.JHipsterProperties;

import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.cache.annotation.EnableCaching;
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
            cm.createCache(com.yavuzturtelekom.repository.UserRepository.USERS_BY_LOGIN_CACHE, jcacheConfiguration);
            cm.createCache(com.yavuzturtelekom.repository.UserRepository.USERS_BY_EMAIL_CACHE, jcacheConfiguration);
            cm.createCache(com.yavuzturtelekom.domain.User.class.getName(), jcacheConfiguration);
            cm.createCache(com.yavuzturtelekom.domain.Authority.class.getName(), jcacheConfiguration);
            cm.createCache(com.yavuzturtelekom.domain.User.class.getName() + ".authorities", jcacheConfiguration);
            cm.createCache(com.yavuzturtelekom.domain.Calisan.class.getName(), jcacheConfiguration);
            cm.createCache(com.yavuzturtelekom.domain.Calisan.class.getName() + ".ekips", jcacheConfiguration);
            cm.createCache(com.yavuzturtelekom.domain.Ekip.class.getName(), jcacheConfiguration);
            cm.createCache(com.yavuzturtelekom.domain.Ekip.class.getName() + ".iscilikEkips", jcacheConfiguration);
            cm.createCache(com.yavuzturtelekom.domain.Ekip.class.getName() + ".ekipMalzemeTakips", jcacheConfiguration);
            cm.createCache(com.yavuzturtelekom.domain.Ekip.class.getName() + ".ekipCalisans", jcacheConfiguration);
            cm.createCache(com.yavuzturtelekom.domain.MalzemeTakip.class.getName(), jcacheConfiguration);
            cm.createCache(com.yavuzturtelekom.domain.Unvan.class.getName(), jcacheConfiguration);
            cm.createCache(com.yavuzturtelekom.domain.Unvan.class.getName() + ".unvans", jcacheConfiguration);
            cm.createCache(com.yavuzturtelekom.domain.Birim.class.getName(), jcacheConfiguration);
            cm.createCache(com.yavuzturtelekom.domain.Birim.class.getName() + ".malzemeBirims", jcacheConfiguration);
            cm.createCache(com.yavuzturtelekom.domain.Birim.class.getName() + ".pozBirims", jcacheConfiguration);
            cm.createCache(com.yavuzturtelekom.domain.ProjeTuru.class.getName(), jcacheConfiguration);
            cm.createCache(com.yavuzturtelekom.domain.ProjeTuru.class.getName() + ".turus", jcacheConfiguration);
            cm.createCache(com.yavuzturtelekom.domain.MalzemeGrubu.class.getName(), jcacheConfiguration);
            cm.createCache(com.yavuzturtelekom.domain.MalzemeGrubu.class.getName() + ".malzemeListesis", jcacheConfiguration);
            cm.createCache(com.yavuzturtelekom.domain.Malzeme.class.getName(), jcacheConfiguration);
            cm.createCache(com.yavuzturtelekom.domain.Malzeme.class.getName() + ".malzemeMalzemeTakips", jcacheConfiguration);
            cm.createCache(com.yavuzturtelekom.domain.Malzeme.class.getName() + ".grups", jcacheConfiguration);
            cm.createCache(com.yavuzturtelekom.domain.Poz.class.getName(), jcacheConfiguration);
            cm.createCache(com.yavuzturtelekom.domain.Bolge.class.getName(), jcacheConfiguration);
            cm.createCache(com.yavuzturtelekom.domain.Bolge.class.getName() + ".projes", jcacheConfiguration);
            cm.createCache(com.yavuzturtelekom.domain.Proje.class.getName(), jcacheConfiguration);
            cm.createCache(com.yavuzturtelekom.domain.Proje.class.getName() + ".isciliks", jcacheConfiguration);
            cm.createCache(com.yavuzturtelekom.domain.Iscilik.class.getName(), jcacheConfiguration);
            cm.createCache(com.yavuzturtelekom.domain.Iscilik.class.getName() + ".isciliks", jcacheConfiguration);
            cm.createCache(com.yavuzturtelekom.domain.IscilikDetay.class.getName(), jcacheConfiguration);
            cm.createCache(com.yavuzturtelekom.domain.IscilikDetay.class.getName() + ".pozs", jcacheConfiguration);
            // jhipster-needle-ehcache-add-entry
        };
    }
}
