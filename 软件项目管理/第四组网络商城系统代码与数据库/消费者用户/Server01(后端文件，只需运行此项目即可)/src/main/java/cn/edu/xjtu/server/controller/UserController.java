package cn.edu.xjtu.server.controller;


import cn.edu.xjtu.server.pojo.Admin;
import cn.edu.xjtu.server.pojo.Drivers;
import cn.edu.xjtu.server.pojo.Transprt;
import cn.edu.xjtu.server.pojo.User;
import cn.edu.xjtu.server.service.AdminService;
import cn.edu.xjtu.server.service.DriversService;
import cn.edu.xjtu.server.service.UserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;


@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    @PostMapping("/logIn")
    public User logIn(@RequestBody User user) throws Exception {
        return userService.getOne(new QueryWrapper<User>().eq("user_id",user.getUserId())
                .eq("user_pwd",user.getUserPwd()));
    }
    @GetMapping("/logIn/{id}")
    public User getById(@PathVariable("id")String id) throws Exception {
        return userService.getOne(new QueryWrapper<User>().eq("user_id",id));

    }
    @GetMapping("/refreshBalance/{id}")
    public BigDecimal refreshBalance(@PathVariable("id")String id) throws Exception {
        return userService.getOne(new QueryWrapper<User>().eq("user_id",id)).getUserBalance();

    }
}

