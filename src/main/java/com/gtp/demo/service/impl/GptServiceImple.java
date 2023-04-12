package com.gtp.demo.service.impl;

import cn.hutool.core.lang.Console;
import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.gtp.demo.service.GptService;
import org.springframework.stereotype.Service;

@Service
public class GptServiceImple implements GptService {
    @Override
    public String getGPTData(String msg) throws Exception {
        System.out.println("开发发送请求");
        String ip = "http://10.242.138.32:7860/predict";
        JSONArray json = JSONUtil.createArray();
        String result = HttpRequest.post(ip + "?user_msg=" + msg)
                .body(json.toString())
                .execute().body();
        JSONObject jsonObject = JSONUtil.parseObj(result);
        Console.log(jsonObject.toStringPretty());
        return jsonObject.get("response").toString();
    }
}