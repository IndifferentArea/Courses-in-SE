package cn.edu.xjtu.server.controller;


import cn.edu.xjtu.server.pojo.Admin;
import cn.edu.xjtu.server.pojo.Drivers;
import cn.edu.xjtu.server.pojo.Transprt;
import cn.edu.xjtu.server.service.AdminService;
import cn.edu.xjtu.server.service.DriversService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private AdminService adminService;
    @PostMapping("/logIn")
    public Admin logIn(@RequestBody Admin admin) throws Exception {
        return adminService.getOne(new QueryWrapper<Admin>().eq("id",admin.getId())
                .eq("pwd",admin.getPwd()));
    }
}

