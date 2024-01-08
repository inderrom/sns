package com.fastcampus.sns.repository;

import com.fastcampus.sns.model.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.time.Duration;
import java.util.Optional;
// redis에 유저를 캐싱하고 그 캐시에서 다시 유저를 가져오는 역할을 하는 클래스

@Slf4j
@Repository
@RequiredArgsConstructor
public class UserCacheRepository {

    private final RedisTemplate<String, User> userRedisTemplate;
    //expire time을 걸어주면서 redis에 공간을 효율적으로 관리할 수 있다.
    private final static Duration USER_CACHE_TTL = Duration.ofDays(3);


    public void setUser(User user) {
        String key = getKey(user.getUsername());
        log.info("Set User to Redis {}({})", key, user);
        userRedisTemplate.opsForValue().set(key, user, USER_CACHE_TTL);
    }

    public Optional<User> getUser(String userName) {
        User data = userRedisTemplate.opsForValue().get(getKey(userName));
        log.info("Get User from Redis {}", data);
        return Optional.ofNullable(data);
    }

    private String getKey(String userName) {
        return "USER:" + userName;
    }
}