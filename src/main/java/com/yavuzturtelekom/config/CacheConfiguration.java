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
            cm.createCache(com.yavuzturtelekom.domain.DovizKur.class.getName(), jcacheConfiguration);
            cm.createCache(com.yavuzturtelekom.domain.Personel.class.getName(), jcacheConfiguration);
            cm.createCache(com.yavuzturtelekom.domain.Personel.class.getName() + ".ekips", jcacheConfiguration);
            cm.createCache(com.yavuzturtelekom.domain.Ekip.class.getName(), jcacheConfiguration);
            cm.createCache(com.yavuzturtelekom.domain.Ekip.class.getName() + ".hakedis", jcacheConfiguration);
            cm.createCache(com.yavuzturtelekom.domain.Ekip.class.getName() + ".stokTakips", jcacheConfiguration);
            cm.createCache(com.yavuzturtelekom.domain.Ekip.class.getName() + ".ekipPersonels", jcacheConfiguration);
            cm.createCache(com.yavuzturtelekom.domain.StokTakip.class.getName(), jcacheConfiguration);
            cm.createCache(com.yavuzturtelekom.domain.Unvan.class.getName(), jcacheConfiguration);
            cm.createCache(com.yavuzturtelekom.domain.Depo.class.getName(), jcacheConfiguration);
            cm.createCache(com.yavuzturtelekom.domain.Depo.class.getName() + ".stokTakips", jcacheConfiguration);
            cm.createCache(com.yavuzturtelekom.domain.Birim.class.getName(), jcacheConfiguration);
            cm.createCache(com.yavuzturtelekom.domain.ProjeTuru.class.getName(), jcacheConfiguration);
            cm.createCache(com.yavuzturtelekom.domain.DefterTuru.class.getName(), jcacheConfiguration);
            cm.createCache(com.yavuzturtelekom.domain.ZimmetTuru.class.getName(), jcacheConfiguration);
            cm.createCache(com.yavuzturtelekom.domain.MalzemeGrubu.class.getName(), jcacheConfiguration);
            cm.createCache(com.yavuzturtelekom.domain.MalzemeGrubu.class.getName() + ".malzemeListesis", jcacheConfiguration);
            cm.createCache(com.yavuzturtelekom.domain.Malzeme.class.getName(), jcacheConfiguration);
            cm.createCache(com.yavuzturtelekom.domain.Malzeme.class.getName() + ".stokTakips", jcacheConfiguration);
            cm.createCache(com.yavuzturtelekom.domain.Malzeme.class.getName() + ".grups", jcacheConfiguration);
            cm.createCache(com.yavuzturtelekom.domain.Poz.class.getName(), jcacheConfiguration);
            cm.createCache(com.yavuzturtelekom.domain.Poz.class.getName() + ".hakedisDetays", jcacheConfiguration);
            cm.createCache(com.yavuzturtelekom.domain.Departman.class.getName(), jcacheConfiguration);
            cm.createCache(com.yavuzturtelekom.domain.Bolge.class.getName(), jcacheConfiguration);
            cm.createCache(com.yavuzturtelekom.domain.Santral.class.getName(), jcacheConfiguration);
            cm.createCache(com.yavuzturtelekom.domain.Marka.class.getName(), jcacheConfiguration);
            cm.createCache(com.yavuzturtelekom.domain.Model.class.getName(), jcacheConfiguration);
            cm.createCache(com.yavuzturtelekom.domain.Proje.class.getName(), jcacheConfiguration);
            cm.createCache(com.yavuzturtelekom.domain.Proje.class.getName() + ".hakedis", jcacheConfiguration);
            cm.createCache(com.yavuzturtelekom.domain.Hakedis.class.getName(), jcacheConfiguration);
            cm.createCache(com.yavuzturtelekom.domain.Hakedis.class.getName() + ".hakedisDetays", jcacheConfiguration);
            cm.createCache(com.yavuzturtelekom.domain.HakedisDetay.class.getName(), jcacheConfiguration);
            cm.createCache(com.yavuzturtelekom.domain.PersonelZimmet.class.getName(), jcacheConfiguration);
            cm.createCache(com.yavuzturtelekom.domain.Arac.class.getName(), jcacheConfiguration);
            cm.createCache(com.yavuzturtelekom.domain.PersonelArac.class.getName(), jcacheConfiguration);
            cm.createCache(com.yavuzturtelekom.domain.PersonelIzin.class.getName(), jcacheConfiguration);
            cm.createCache(com.yavuzturtelekom.domain.PersonelOdeme.class.getName(), jcacheConfiguration);
            // jhipster-needle-ehcache-add-entry
        };
    }
}
