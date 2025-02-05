package com.cloudstore.config;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
public class RateLimitConfig {

    @Value("${rate.limit.capacity:100}") // Default capacity is 100 requests
    private int capacity;

    @Value("${rate.limit.time:1}") // Default time window is 1 minute
    private int time;

    @Bean
    public Bucket rateLimitBucket() {
        Bandwidth limit = Bandwidth.classic(capacity, Refill.intervally(capacity, Duration.ofMinutes(time)));
        return Bucket.builder()
                .addLimit(limit)
                .build();
    }
}