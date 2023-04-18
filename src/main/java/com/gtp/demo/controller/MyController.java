package com.gtp.demo.controller;


import cn.hutool.core.util.IdUtil;
import com.gtp.demo.bean.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.HttpSession;


@Controller
public class MyController {

    @GetMapping(value = {"/login"})
    public String login0() {
        return "login";
    }

    @PostMapping("/login")
    public String main(User user,HttpSession session, Model model) {
        System.out.println("123");
        if(!StringUtils.isEmpty(user.getUserName()) && "123456".equals(user.getPassword())) {
            // 把登录成功的用户保存起来
            session.setAttribute("loginUser",user);
            // 登录成功重定向到main.html; 重定向防止表单重复提交
            return "redirect:/main.html";
        } else {
            model.addAttribute("msg","账号密码错误");
            return "login";
        }
    }

    @GetMapping(value = {"/"})
    public String login() {
        return "name";
    }

    @PostMapping("/hi")
    public String gptFrame(User user,HttpSession session, Model model) {
        if(!StringUtils.isEmpty(user.getUserName())) {
            // 把登录成功的用户保存起来
            String uuid = IdUtil.simpleUUID();
            user.setUserName(user.getUserName() +":"+uuid);
            user.setTime(0);
            session.setAttribute("loginUser",user);
            // 登录成功重定向到main.html; 重定向防止表单重复提交
            return "redirect:/welcome.html";
        } else {
            model.addAttribute("msg","id不能为空");
            return "name";
        }
    }

    @GetMapping("/welcome.html")
    public String mainPage(HttpSession session, Model model) {
        if(session.getAttribute("loginUser") != null) {
            return "welcome";
        } else {
            model.addAttribute("msg","请重新登录");
            return "name";
        }
    }
}