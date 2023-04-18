package com.gtp.demo.controller;


import cn.hutool.core.date.TimeInterval;
import cn.hutool.core.lang.Console;
import cn.hutool.core.util.IdUtil;
import cn.hutool.http.server.HttpServerResponse;
import com.gtp.demo.bean.GPTData;
import com.gtp.demo.bean.Result;
import com.gtp.demo.bean.User;
import com.gtp.demo.interfaces.LimitRequest;
import com.gtp.demo.service.GptService;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.util.*;
import java.util.concurrent.TimeUnit;

//负责处理api接口
@RestController
public class GptController {

    final TimeInterval timer = new TimeInterval();
    @Resource
    private GptService mockService;
    @Resource
    private StringRedisTemplate stringRedisTemplate;

//    @RequestMapping("/welcome")
//    public ModelAndView login() throws Exception {
//        System.out.println("这是login");
//        return new ModelAndView("welcome");
//    }

//    @PostMapping("/user")
//    public String showData() {
//        String message = data.getMessage();
//        System.out.println(message);
//        return "welcome";
//    }

    @PostMapping("/welcome")
    @LimitRequest(count=2)
    public ModelAndView showData(HttpServletRequest request, Model model, HttpSession session) throws Exception {

        String question = request.getParameter("input");
//        System.out.println(question);
        timer.start("1");
        User user = (User)session.getAttribute("loginUser");
        String id = user.getUserName()+":"+user.getTime();
        user.setTime(user.getTime()+1);
        List<List<String>> lsMessage = user.getLsMessage();

        session.setAttribute("loginUser",user);
        String answer = mockService.getGPTData(question ,id, lsMessage);
        boolean add = lsMessage.add(new ArrayList<String>(Arrays.asList(question,answer)));
        if(add) {
            user.setLsMessage(lsMessage);
            System.out.println(lsMessage);
        }
        Console.log("Timer 1 took {} ms", timer.intervalMs("1"));
        user.setMessage((user.getMessage()==null?"":user.getMessage())+ "问题"+user.getTime()+": "+question
                +"\n" +"回答："+ answer +"\n"+"******************************************************"+"\n" );
        model.addAttribute("msg",user.getMessage());
        model.addAttribute("question",question);
//        System.out.println(answer);
        return new ModelAndView("welcome");
    }


    @RequestMapping("/test")
    @ResponseBody
    public Map<String,Object> redisTest() {
        stringRedisTemplate.opsForValue().set("myKey","myValue",20,TimeUnit.SECONDS);
        stringRedisTemplate.opsForValue().set("myKey_2","myValue");
        System.out.println("hello");
        String myKey = stringRedisTemplate.opsForValue().get("myKey");
        System.out.println(myKey);
        Map<String,Object> resultMap = new HashMap<String,Object>();
        resultMap.put("myKey","my");
        return resultMap;
    }

}