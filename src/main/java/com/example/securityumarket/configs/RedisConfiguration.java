package com.example.securityumarket.configs;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;

import java.time.Duration;

import static org.springframework.data.redis.connection.jedis.JedisClientConfiguration.*;

@Configuration
@EnableCaching
public class RedisConfiguration {

    @Value("${redis.hostname}")
    private String host;

    @Value("${redis.port}")
    private int port;

    @Value("${redis.username}")
    private String username;

    @Value("${redis.password}")
    private String password;

    @Bean
    public JedisConnectionFactory jedisConnectionFactory() {
        RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration();
        configuration.setHostName(host);
        configuration.setPort(port);
        configuration.setUsername(username);
        configuration.setPassword(password);
        return new JedisConnectionFactory(configuration);
    }

    @Bean
    public <F, S> RedisTemplate<F, S> redisTemplate() {
        final RedisTemplate<F, S> redisTemplate = new RedisTemplate<>();

        redisTemplate.setConnectionFactory(jedisConnectionFactory());
        redisTemplate.setDefaultSerializer(new GenericJackson2JsonRedisSerializer());
        redisTemplate.afterPropertiesSet();

        return redisTemplate;
    }


}
/*
REDIS_HOSTNAME=redis-11872.c55.eu-central-1-1.ec2.cloud.redislabs.com
REDIS_PORT=11872
REDIS_USERNAME=default
REDIS_PASSWORD=dhwXmkiIMwrxbDApO35pm7fGc8PIg2ia
 */
