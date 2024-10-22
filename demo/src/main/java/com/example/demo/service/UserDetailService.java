package com.example.demo.service;


import com.example.demo.mapper.UserMapper;
import com.example.demo.pojo.User;
import com.example.demo.pojo.UserDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailService implements UserDetailsService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        User user = userMapper.findByUsername(username);
        User user = new User("zlw", "$2a$10$m/4kcUo2LylsP4PKmFEFz.AcnV8DLtL/7krYxU7JcmqSPimnexd56");
        if(user==null){
            throw new UsernameNotFoundException("用户不存在");
        }else{
            return new UserDetail(user);
        }
    }
}
