package com.gtp.demo.config;

import com.gtp.demo.lisenter.KeyExpiredListenForMessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;

import javax.annotation.Resource;

@Configuration
public class RedisConfigForMessageListener {

    @Resource
    private ApplicationContext applicationContext;

    @Resource
    private RedisConnectionFactory connectionFactory;

    @Bean
    public KeyExpiredListenForMessageListener registerListener() {
        RedisMessageListenerContainer redisMessageListenerContainer = applicationContext.
                getBean(RedisMessageListenerContainer.class);
        redisMessageListenerContainer.setConnectionFactory(connectionFactory);
        KeyExpiredListenForMessageListener listener = new KeyExpiredListenForMessageListener();
        redisMessageListenerContainer.addMessageListener(listener,new PatternTopic("__keyevent@*__:expired"));
        return listener;
    }

}
