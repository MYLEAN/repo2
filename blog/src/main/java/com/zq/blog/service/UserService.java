package com.zq.blog.service;


import com.zq.blog.po.User;

public interface UserService {
    User checkUser(String username,String password);
}
