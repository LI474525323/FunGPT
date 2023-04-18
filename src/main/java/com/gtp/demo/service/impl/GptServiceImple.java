package com.gtp.demo.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gtp.demo.bean.GPTData;
import com.gtp.demo.mapper.GPTDataMapper;
import com.gtp.demo.service.GptService;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class GptServiceImple extends ServiceImpl<GPTDataMapper, GPTData> implements GptService {

    @Resource
    public StringRedisTemplate stringRedisTemplate;

    @Override
    public String getGPTData(String question, String uuid, List<List<String>> lsMessage) throws Exception {
        System.out.println("开始发送请求");
        String ip = "http://10.242.138.32:7860/predict2";
//        System.out.println("6666" + lsMessage.toString());
        // json数组，存放json对象
        JSONArray json = JSONUtil.createArray();
        // json对象
        JSONObject j = new JSONObject();
        j.accumulate("history", lsMessage);
        j.accumulate("user_msg",question);
        json.add(j);
        System.out.println(json.toString());
        // ip + "?user_msg=" + question + "*" + lsMessage.toString()
        String result = HttpRequest.post(ip)
                .body(json.toString())
                .execute().body();
        JSONObject jsonObject = JSONUtil.parseObj(result);
        // 获取JSON中的response
        String response = jsonObject.get("response").toString();
        // 获取JSON中的id
        String id = uuid;
        // 获取JSON中的date
        DateTime date = DateUtil.date();
        // 缓存到redis
        GPTData gptData = new GPTData(id, question, response, date);
        Map<String, Object> gptDataMap = BeanUtil.beanToMap(gptData,new HashMap<>(), CopyOptions.create()
                .setIgnoreNullValue(true)
                .setFieldValueEditor((fieldName,fieldValue)->fieldValue.toString()));

        stringRedisTemplate.opsForHash().putAll(id,gptDataMap);
        stringRedisTemplate.expire(id,30,TimeUnit.MINUTES);
        stringRedisTemplate.opsForHash().putAll(id+"_2",gptDataMap);
        // 保存到数据库
//        saveSessionData(id,question,response,date);
//        Console.log(jsonObject.toStringPretty());
        return response;
    }

    /**
     * 将数据保存到数据库
     * @param id
     * @param question
     * @param response
     */
    public void saveSessionData(String id,String question,String response, DateTime date) {
        GPTData gptData = new GPTData();
        gptData.setAnswer(response);
        gptData.setQuestion(question);
        gptData.setId(id);
        gptData.setCreateTime(date);
        save(gptData);
    }
}