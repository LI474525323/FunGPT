package com.gtp.demo.controller;


import cn.hutool.core.date.TimeInterval;
import cn.hutool.core.lang.Console;
import com.gtp.demo.bean.GPTData;
import com.gtp.demo.bean.Result;
import com.gtp.demo.interfaces.LimitRequest;
import com.gtp.demo.service.GptService;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

//负责处理api接口
@RestController
public class GptController {
    public GPTData data = new GPTData();
    @Resource
    private GptService mockService;

    final TimeInterval timer = new TimeInterval();

    @RequestMapping("/welcome")
    public ModelAndView getMockData() throws Exception {
        return new ModelAndView("welcome");
    }

//    @PostMapping("/user")
//    public String showData() {
//        String message = data.getMessage();
//        System.out.println(message);
//        return "welcome";
//    }

    @PostMapping("/welcome")
    @LimitRequest(count=2)
    public ModelAndView showData(HttpServletRequest request, Model model) throws Exception {
        String question = request.getParameter("input");
        System.out.println(question);
        timer.start("1");
        data.setMessage(mockService.getGPTData(question));
        Console.log("Timer 1 took {} ms", timer.intervalMs("1"));
        String message = data.getMessage();
        model.addAttribute("msg",message);
        model.addAttribute("question",question);
        System.out.println(message);
        return new ModelAndView("welcome");
    }
}