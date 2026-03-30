package org.agro.adapters.out.lock;

import lombok.RequiredArgsConstructor;
import org.agro.core.domain.exceptions.ServicoIndisponivelException;
import org.agro.core.ports.out.logging.LoggerPort;
import org.agro.core.ports.out.usecase.LockPort;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
@RequiredArgsConstructor
public class LockAdapter implements LockPort {

    private final StringRedisTemplate redisTemplate;
    private final LoggerPort logger;

    private static final String LOCK_PREFIX = "lock:operacao:";
    private static final Duration LOCK_TTL = Duration.ofSeconds(10);

    @Override
    public boolean tentarLock(String key) {
        try {
            Boolean acquired = redisTemplate
                    .opsForValue()
                    .setIfAbsent(LOCK_PREFIX + key, "locked", LOCK_TTL);

            return Boolean.TRUE.equals(acquired);

        } catch (Exception e) {
            logger.logError("Redis indisponível ao tentar adquirir lock: {}", key, e);
            throw new ServicoIndisponivelException("Serviço temporariamente indisponível.");
        }
    }

    @Override
    public void liberarLock(String key) {
        try {
            redisTemplate.delete(LOCK_PREFIX + key);
        } catch (Exception e) {
            logger.logError("Redis indisponível ao tentar liberar lock: {}", key, e);
        }
    }
}