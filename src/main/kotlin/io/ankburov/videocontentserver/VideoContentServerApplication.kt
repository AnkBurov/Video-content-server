package io.ankburov.videocontentserver

import org.apache.ignite.cache.CacheMode
import org.apache.ignite.cache.spring.SpringCacheManager
import org.apache.ignite.configuration.CacheConfiguration
import org.apache.ignite.configuration.IgniteConfiguration
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cache.Cache
import org.springframework.cache.CacheManager
import org.springframework.context.annotation.Bean
import org.springframework.core.io.ClassPathResource
import org.springframework.http.MediaType.TEXT_HTML
import org.springframework.web.reactive.function.server.ServerResponse.ok
import org.springframework.web.reactive.function.server.router
import org.apache.ignite.spi.discovery.tcp.ipfinder.multicast.TcpDiscoveryMulticastIpFinder
import org.apache.ignite.spi.discovery.tcp.TcpDiscoverySpi
import javax.cache.expiry.CreatedExpiryPolicy
import javax.cache.expiry.Duration


@SpringBootApplication
class VideoContentServerApplication {

    private val indexHtml = ClassPathResource("static/index.html")

    @Bean
    fun mainRouter() = router {
        GET("/") {
            ok().contentType(TEXT_HTML).syncBody(indexHtml)
        }
    }

    @Bean
    fun igniteConfiguration(): IgniteConfiguration {
        val spi = TcpDiscoverySpi()
        val ipFinder = TcpDiscoveryMulticastIpFinder()
        ipFinder.multicastGroup = "228.10.10.157"
        spi.ipFinder = ipFinder

        return IgniteConfiguration().apply {
            discoverySpi = spi
            isPeerClassLoadingEnabled = true
        }
    }

    @Bean
    fun cacheManager(igniteConfiguration: IgniteConfiguration): CacheManager {
        val springCacheManager = SpringCacheManager()
        springCacheManager.configuration = igniteConfiguration
        val cacheConfiguration = CacheConfiguration<Any, Any>()
        cacheConfiguration.setExpiryPolicyFactory(CreatedExpiryPolicy.factoryOf(Duration.ONE_MINUTE))
        cacheConfiguration.cacheMode = CacheMode.PARTITIONED
        cacheConfiguration.backups = 3
        springCacheManager.dynamicCacheConfiguration = cacheConfiguration
        return springCacheManager
    }

    @Bean
    fun fileCache(cacheManager: CacheManager): Cache {
        return cacheManager.getCache("file-cache")!!
    }
}

fun main(args: Array<String>) {
    runApplication<VideoContentServerApplication>(*args)
}
