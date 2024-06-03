package com.springboot.example.redis.redisdemo;

import redis.clients.jedis.Jedis;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class JedisSample {
    public static void main(String[] args) {
        // 1 connection 连接，通过指定ip和端口号
        Jedis jedis = new Jedis("8.130.44.112", 6379);

        // 2 指定访问服务器密码
        jedis.auth("123456");

        //  3 获得了Jedis客户端，可以像jdbc一样访问redis
        System.out.println(jedis.ping());

        // keys
        Set<String> keys = jedis.keys("*");
        System.out.println(keys);

        // string相关操作
        jedis.set("k3","hello-jedis");
        System.out.println(jedis.get("k3"));
        System.out.println(jedis.ttl("k3"));

        // list相关操作
        jedis.lpush("list","11","22","33");
        List<String> list = jedis.lrange("list", 0, -1);
        for (String s : list) {
            System.out.println(s);
        }
        System.out.println(jedis.rpop("list"));
        System.out.println(jedis.lpop("list"));

        // hash相关操作
        jedis.hset("hset1","k1","v1");
        Map<String,String> hash = new HashMap<>();
        hash.put("k1","1");
        hash.put("k2","2");
        hash.put("k3","3");
        jedis.hmset("hset2",hash);
        System.out.println(jedis.hmget("hset2","k1","k3","k2"));
        System.out.println(jedis.hget("hset1", "k1"));
        System.out.println(jedis.hexists("hset2","k2"));
        System.out.println(jedis.hkeys("hset2"));

        // set相关操作
        jedis.sadd("set1","1","2","3");
        jedis.sadd("set2","4");
        System.out.println(jedis.smembers("set1"));
        System.out.println(jedis.scard("set1"));
        System.out.println(jedis.spop("set1"));
        jedis.smove("set1","set2","1");
        System.out.println(jedis.smembers("set1"));
        System.out.println(jedis.smembers("set2"));
        System.out.println(jedis.sinter("set1", "set2"));  // 交集
        System.out.println(jedis.sunion("set1","set2"));   // 并集

        // zset相关操作
        jedis.zadd("zset1",100,"v1");
        jedis.zadd("zset1",80,"v2");
        jedis.zadd("zset1",60,"v3");

        List<String> zset1 = jedis.zrange("zset1", 0, -1);
        for (String s : zset1) {
            System.out.println(s);
        }
        List<String> zset11 = jedis.zrevrange("zset1", 0, -1);
        for (String s : zset11) {
            System.out.println(s);
        }
    }
}
