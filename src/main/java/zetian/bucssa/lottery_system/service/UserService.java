package zetian.bucssa.lottery_system.service;

import jakarta.annotation.PreDestroy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import zetian.bucssa.lottery_system.domain.User;
import zetian.bucssa.lottery_system.mapper.UserMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    private List<User> userBuffer = new ArrayList<>();
    private static final int BATCH_SIZE = 100;

    //仅在buffer池大于100时batch insert
    @Async
    public CompletableFuture<String> addUser(User user) {
        String redisKey = "user: " + user.getBuId();

        if(redisTemplate.opsForValue().get(redisKey) != null) {
            return CompletableFuture.completedFuture("User is already registered.");
        }

        synchronized (this) {
            userBuffer.add(user);

            if (userBuffer.size() >= BATCH_SIZE) {
                userMapper.insertManyUsers(new ArrayList<>(userBuffer));
                userBuffer.clear();
            }

            redisTemplate.opsForValue().set(redisKey, user.getBuId(), 60, TimeUnit.SECONDS);
        }
        return CompletableFuture.completedFuture("User added asynchronously.");
    }

    //定时5秒检查缓冲区
    @Scheduled(fixedRate = 5000)
    public synchronized void flushUsers() {
        if (!userBuffer.isEmpty()) {
            userMapper.insertManyUsers(new ArrayList<>(userBuffer));
            userBuffer.clear();
        }
    }

    // 程序关闭时，buffer写入db
    @PreDestroy
    public synchronized void onShutdown() {
        if (!userBuffer.isEmpty()) {
            userMapper.insertManyUsers(new ArrayList<>(userBuffer));
            userBuffer.clear();
        }
    }

    public User getUserByBuId(String buId) {
        return userMapper.getUserByBuId(buId);
    }

    public List<User> getAllUsers() {
        return userMapper.getAllUsers();
    }
}
