package com.springboot.example.redis.redisdemo;

import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;

import java.util.List;
public class LettuceSample {

    public static void main(String[] args) {
        // 1 使用构建器链式编程来builder我们的RedisURI
        RedisURI uri = RedisURI.builder()
            .withHost("8.130.44.112")
            .withPort(6379)
            .withAuthentication("default", "123456")
            .build();
        // 2 连接客户端
        RedisClient redisClient = RedisClient.create(uri);
        // 得到一个连接对象
        StatefulRedisConnection<String, String> conn = redisClient.connect();

        // 3 创建操作的command, 通过conn创建
        RedisCommands<String, String> commands = conn.sync();

        // string
        commands.set("k1","v1");
        System.out.println("==========================="+commands.get("k1"));
        System.out.println("==========================="+commands.mget("k1","k2"));
        List<String> keys = commands.keys("*");
        for (String key : keys) {
            System.out.println("========================="+key);
        }

        // list
        commands.lpush("list01","1","2","3");
        List<String> list01 = commands.lrange("list01", 0, -1);
        for (String s : list01) {
            System.out.println("================"+s);
        }
        System.out.println("===================="+ commands.rpop("list01", 2));

        // hash
        commands.hset("hash","k1","v1");
        commands.hset("hash","k2","v2");
        commands.hset("hash","k3","v3");
        System.out.println("======================="+commands.hgetall("hash"));
        Boolean hexists = commands.hexists("hash", "v2");
        System.out.println("------"+hexists);

        // set
        commands.sadd("s1","1","2");
        System.out.println("=================================" + commands.smembers("s1"));
        System.out.println(commands.sismember("s1", "1"));
        System.out.println(commands.scard("s1"));

        // zset
        commands.zadd("a1",100,"v1");
        commands.zadd("a1",80,"v2");
        System.out.println(commands.zrange("a1", 0, -1));
        System.out.println("======================"+commands.zcount("a1", "90", "100"));

        // 4 各种关闭释放资源  先开后关
        conn.close();
        redisClient.shutdown();

    }
}
