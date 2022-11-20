package com.example.demo.infrastructure.locks;

import com.example.demo.shared.exception.ConcurrentProcessingException;
import lombok.Setter;
import org.redisson.api.RMapCache;
import org.redisson.api.RedissonClient;
import org.redisson.client.codec.StringCodec;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

public abstract class BaseRedisLocker<K> {

  @Setter private String bucketKey;
  @Setter private long ttl;
  @Setter private String keyType;

  @Setter(onMethod = @__({@Autowired}))
  private RedissonClient client;

  private RMapCache<K, K> processingBucket;

  public void lock(K k) throws ConcurrentProcessingException {
    K result = this.processingBucket.putIfAbsent(k, k, ttl, TimeUnit.SECONDS);
    if (Objects.nonNull(result)) {
      throw new ConcurrentProcessingException(keyType + " " + k + " is processing by others");
    }
  }

  public K unlock(K k) {
    return this.processingBucket.remove(k);
  }

  protected void initBucket() {
    this.processingBucket = client.getMapCache(bucketKey, new StringCodec());
  }
}
