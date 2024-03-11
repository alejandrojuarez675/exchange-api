package com.alejua.domain;

import java.time.LocalDateTime;

public class Cache<T> {
    private static final Long TTL_DEFAULT = 10L;
    private final T data;
    private LocalDateTime lastUpdate = LocalDateTime.now();

    private Long ttl = Cache.TTL_DEFAULT;

    public Cache(T data) {
        this.data = data;
    }

    public Cache(T data, LocalDateTime lastUpdate) {
        this.data = data;
        this.lastUpdate = lastUpdate;
    }

    public Cache(T data, Long ttl) {
        this.data = data;
        this.ttl = ttl;
    }

    public Cache(T data, LocalDateTime lastUpdate, Long ttl) {
        this.data = data;
        this.lastUpdate = lastUpdate;
        this.ttl = ttl;
    }

    public Boolean isAlive() {
        return lastUpdate.isAfter(LocalDateTime.now().minusMinutes(ttl));
    }

    public T getData() {
        return data;
    }

    public LocalDateTime getLastUpdate() {
        return lastUpdate;
    }
}
