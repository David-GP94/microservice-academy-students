package com.academy.microservice_academy_students.Infrastructure.Config;

import com.academy.microservice_academy_students.Domain.Entities.Student;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {

    @Bean
    public RedisTemplate<String, Student> redisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, Student> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);
        
        // Configure key serializer
        template.setKeySerializer(new StringRedisSerializer());
        
        // Configure value serializer
        template.setValueSerializer(new Jackson2JsonRedisSerializer<>(Student.class));
        
        // Configure hash key serializer
        template.setHashKeySerializer(new StringRedisSerializer());
        
        // Configure hash value serializer
        template.setHashValueSerializer(new Jackson2JsonRedisSerializer<>(Student.class));
        
        template.afterPropertiesSet();
        return template;
    }
}
