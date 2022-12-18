package cn.edu.xjtu.server.controller;


import cn.edu.xjtu.server.pojo.Drivers;
import cn.edu.xjtu.server.pojo.ReturnRegisty;
import cn.edu.xjtu.server.pojo.Transprt;
import cn.edu.xjtu.server.service.DriversService;
import cn.edu.xjtu.server.service.ReturnRegistyService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/return-registy")
public class ReturnRegistyController {
    @Autowired
    private ReturnRegistyService returnRegistyService;
    @GetMapping("/list")
    public List<ReturnRegisty> listAll() throws Exception {
        return returnRegistyService.list(null);
    }

}

