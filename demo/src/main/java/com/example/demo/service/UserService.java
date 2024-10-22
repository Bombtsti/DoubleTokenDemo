package com.example.demo.service;

import com.example.demo.constant.TokenConstant;
import com.example.demo.mapper.UserMapper;
import com.example.demo.pojo.User;
import com.example.demo.utils.JWTUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import com.example.demo.result.Result;



import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserDetailService userDetailService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private RedisTemplate redisTemplate;


    public Result<?> getAllUser(){
//        return userMapper.getAllUser();
        User z1 = new User("z1", "12456");
        User z2 = new User("z2", "sadad");
        User z3 = new User("z3", "asdsdfg");
        List<User> users = new ArrayList<>();
        users.add(z1);
        users.add(z2);
        users.add(z3);
        return Result.ok(users);
    }

    public Result<?> login(User user) {

        Authentication authenticationToken = new UsernamePasswordAuthenticationToken(user.getUsername(),user.getPassword(),null);
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        if (Objects.isNull(authenticate)) {
            throw new RuntimeException("登陆失败");
        }

        UserDetails userDetail = userDetailService.loadUserByUsername(user.getUsername());
        String accessToken = JWTUtil.getJwtToken(userDetail.getUsername(), TokenConstant.ACCESS_TOKEN_EXPIRATION_TIME);
        String refreshToken = JWTUtil.getJwtToken(userDetail.getUsername(), TokenConstant.REFRESH_TOKEN_EXPIRATION_TIME);

        redisTemplate.opsForValue().set(userDetail.getUsername()+TokenConstant.REFRESH_TOKEN_START_TIME, String.valueOf(System.currentTimeMillis()), TokenConstant.REFRESH_TOKEN_EXPIRATION_TIME, TimeUnit.MILLISECONDS);

        Map<String,Object> map = new HashMap<>();
        map.put(TokenConstant.ACCESS_TOKEN, accessToken);
        map.put(TokenConstant.REFRESH_TOKEN, refreshToken);
        map.put("userInfo", userDetail);
        return Result.ok(map);
    }

    public Result<?> refreshToken(String refreshToken) {
        Map<String,Object> map = new HashMap<>();
        String username = JWTUtil.getUsername(refreshToken);
        String accessToken = JWTUtil.getJwtToken(username,TokenConstant.ACCESS_TOKEN_EXPIRATION_TIME);

        String refreshTokenStr = (String) redisTemplate.opsForValue().get(username+TokenConstant.REFRESH_TOKEN_START_TIME);
        if(StringUtils.isBlank(refreshTokenStr)){
            return Result.fail(map);
        }
        long refreshTokenStartTime = Long.parseLong(refreshTokenStr);

        if(refreshTokenStartTime+TokenConstant.REFRESH_TOKEN_EXPIRATION_TIME < System.currentTimeMillis()){
            return Result.forbidden(map);
        } else if(refreshTokenStartTime+TokenConstant.REFRESH_TOKEN_EXPIRATION_TIME-System.currentTimeMillis()<=TokenConstant.ACCESS_TOKEN_EXPIRATION_TIME){
            refreshToken = JWTUtil.getJwtToken(username,TokenConstant.REFRESH_TOKEN_EXPIRATION_TIME);
            redisTemplate.opsForValue().set(username+TokenConstant.REFRESH_TOKEN_START_TIME , String.valueOf(System.currentTimeMillis()), TokenConstant.REFRESH_TOKEN_EXPIRATION_TIME, TimeUnit.MILLISECONDS);
        }

        map.put(TokenConstant.ACCESS_TOKEN,accessToken);
        map.put(TokenConstant.REFRESH_TOKEN,refreshToken);
        return Result.ok(map);
    }
}
