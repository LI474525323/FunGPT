package com.gtp.demo.lisenter;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.date.DateTime;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gtp.demo.bean.GPTData;
import com.gtp.demo.mapper.GPTDataMapper;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

@Component
public class KeyExpiredListenForMessageListener extends ServiceImpl<GPTDataMapper, GPTData> implements MessageListener {

    @Resource
    public StringRedisTemplate stringRedisTemplate;  // 集成了redis模块

    /**
     * key过期时间监听
     * @param message
     * @param bytes
     */
    @Override
    public void onMessage(Message message, byte[] bytes) {
        String expiredKey = new String(message.getBody(), StandardCharsets.UTF_8);
        String key = expiredKey + "_2";
        Map<Object, Object> entries = stringRedisTemplate.opsForHash().entries(key);
        GPTData gptData = BeanUtil.mapToBean(entries, GPTData.class, false, new CopyOptions());
        System.out.println("过期时间到");
        System.out.println(entries);
        save(gptData);
        stringRedisTemplate.delete(key);
//        System.out.println(expiredKey + "-value:  " + v);
    }



}
