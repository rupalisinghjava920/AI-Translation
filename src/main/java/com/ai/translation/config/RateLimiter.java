package com.ai.translation.config;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class RateLimiter {

    private Map<String, Integer> requestCount = new ConcurrentHashMap<>();

    public boolean allow(String user) {
        requestCount.put(user, requestCount.getOrDefault(user, 0) + 1);
        return requestCount.get(user) <= 100;
    }
}