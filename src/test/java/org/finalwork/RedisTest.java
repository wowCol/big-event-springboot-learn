package org.finalwork;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.util.concurrent.TimeUnit;

@SpringBootTest // 如果在测试类上添加了这个注解，那么将来单元测试方法执行之前，会先初始化Spring容器
public class RedisTest {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Test
    public void testSet() {
        // 往redis中存储一个键值对 StringRedisTemplate
        ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();

        // 存储值
        operations.set("username", "test");
        operations.set("id", "1", 15, TimeUnit.SECONDS);
    }

    @Test
    public void TestGet() {
        // 从Redis中获取一个键值对
        ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();

        System.out.println(operations.get("username"));
    }
}
