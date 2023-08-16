package com.example.easybbsweb.aspect;

import com.example.easybbsweb.anotation.IdentifiesCheck;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Component;

import javax.swing.plaf.metal.MetalTheme;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

@Aspect
@Component
@Slf4j
public class IdentifiesCheckAspect {
    @Resource
    RedisTemplate<String, String> redisTemplate;

    @Pointcut("@annotation(com.example.easybbsweb.anotation.IdentifiesCheck)")
    void pt() {
    }

    ;

    @Around("pt()")
    public Object IdentifiesCheck(ProceedingJoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        IdentifiesCheck annotation = signature.getMethod().getAnnotation(IdentifiesCheck.class);
        String prefix = annotation.value(); // 获取前缀
        Object[] args = joinPoint.getArgs();
        String key = prefix + "identifies";
        String identifies = (String) args[args.length - 1]; // token
        ReentrantLock lock = new ReentrantLock();
        String val;
        try {
            lock.lock();
            val = redisTemplate.opsForValue().get(key);
            log.debug("尝试删除锁");
            String script = "if redis.call('exists' , KEYS[1])==0 " +
                    " then return 0 " +//==0表示不存在，锁空闲，不删除锁
                    " elseif redis.call('get' , KEYS[1])== ARGV[1] " +//有锁且是自己的锁（是不是自己的通过uuid）
                    " then return redis.call('del' , KEYS[1]) " + //删除自己的锁
                    " else return 0 end";//锁不是自己的，不删除

            redisTemplate.execute(new DefaultRedisScript<Boolean>(script, Boolean.class),
                    List.of("lock"), identifies);
        } finally {
            lock.unlock();
        }
        Object proceed = null;
        if (identifies.equals(val)) {
            try {
                proceed = joinPoint.proceed();
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }
        return proceed;
    }
}
