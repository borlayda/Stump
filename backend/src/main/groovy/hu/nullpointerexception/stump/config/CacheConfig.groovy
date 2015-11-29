package hu.nullpointerexception.stump.config

import com.google.code.ssm.CacheFactory
import com.google.code.ssm.aop.CacheBase
import com.google.code.ssm.config.DefaultAddressProvider
import com.google.code.ssm.providers.CacheConfiguration
import com.google.code.ssm.providers.xmemcached.MemcacheClientFactoryImpl
import com.google.code.ssm.spring.SSMCache
import com.google.code.ssm.spring.SSMCacheManager
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.cache.CacheManager
import org.springframework.cache.annotation.EnableCaching
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.DependsOn
import org.springframework.context.annotation.EnableAspectJAutoProxy
import org.springframework.context.annotation.ImportResource

/**
 * Created by Márton Tóth
 */
@Configuration
@ConfigurationProperties(prefix = "memcached")
@ImportResource("classpath:simplesm-context.xml")
@EnableCaching
class CacheConfig {

    //@Value("${host}")
    String address

    @Bean(name = "defaultCache")
    @Autowired
    @DependsOn("cacheBase")
    CacheFactory memcachedCacheFactory () {
        def cacheFactory = new CacheFactory()
        def c = new CacheConfiguration()
        c.consistentHashing = true
        c.useBinaryProtocol = true
        cacheFactory.cacheClientFactory = new MemcacheClientFactoryImpl()
        cacheFactory.addressProvider = new DefaultAddressProvider(address)
        cacheFactory.configuration = c;
        return cacheFactory
    }

    def createCache() {
        def cacheNames = new HashSet();
        cacheNames << "users"
        return cacheNames
    }


    @Bean
    CacheManager cacheManager(@Qualifier("defaultCache") CacheFactory defaultCache) {
        def cm = new SSMCacheManager()
        cm.caches = new HashSet<>()
        def cache = new SSMCache(defaultCache.cache, 300, false)
        cm.caches.add(cache)
        //cm.cacheNames.add("users")
        return cm
    }

}
