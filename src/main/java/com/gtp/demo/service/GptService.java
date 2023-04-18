package com.gtp.demo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gtp.demo.bean.GPTData;

import java.util.List;

public interface GptService extends IService<GPTData> {
    String getGPTData(String msg,String uuid,List<List<String>> lsMessage) throws Exception;

}