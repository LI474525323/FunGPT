package com.gtp.demo.bean;

import lombok.Data;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Data  // 自动封装
public class User {
    private String userName;
    private String password;
    private int time;
    private String message;
    private List<List<String>> lsMessage = new ArrayList<>();
}
