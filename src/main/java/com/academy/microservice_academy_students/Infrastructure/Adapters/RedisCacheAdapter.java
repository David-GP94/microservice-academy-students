package com.academy.microservice_academy_students.Infrastructure.Adapters;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import com.academy.microservice_academy_students.Domain.Entities.Student;
import com.academy.microservice_academy_students.application.Ports.Out.ICachePort;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class RedisCacheAdapter implements ICachePort
{
    private final RedisTemplate<String, Student> redisTemplate;

    @Override
    public void saveToCache(Long id, Student student) {
        redisTemplate.opsForValue().set("student:" + id, student, 1, TimeUnit.HOURS);
    }

    @Override
    public Optional<Student> findByIdFromCache(Long id) {
        Student student = redisTemplate.opsForValue().get("student:" + id);
        return Optional.ofNullable(student);
    }

    @Override
    public void deleteFromCache(Long id) {
        redisTemplate.delete("student:" + id);
    }

}
