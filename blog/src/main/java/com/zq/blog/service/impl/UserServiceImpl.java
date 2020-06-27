package com.zq.blog.service.impl;


import com.zq.blog.dao.UserRepository;
import com.zq.blog.po.User;
import com.zq.blog.service.UserService;
import com.zq.blog.util.MD5Utils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserRepository userRepository;
    @Override
    public User checkUser(String username, String password) {
        User user=userRepository.findByUsernameAndPassword(username, MD5Utils.code(password));
        return user;
    }
}
