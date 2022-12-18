package cn.edu.xjtu.server.controller;


import cn.edu.xjtu.server.pojo.Drivers;
import cn.edu.xjtu.server.pojo.Product;
import cn.edu.xjtu.server.pojo.Transprt;
import cn.edu.xjtu.server.service.DriversService;
import cn.edu.xjtu.server.service.TransprtService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/transprt")
public class TransprtController {
    @Autowired
    private TransprtService transprtService;
    @Autowired
    private DriversService driversService;
    @GetMapping("/list")
    public List<Transprt> listAll() throws Exception {
        return transprtService.list(null);
    }
    @GetMapping("/drivers/{province}")
    public List<Drivers> drivers(@PathVariable("province")String province) throws Exception {
        return driversService.list(new QueryWrapper<Drivers>().eq("province",province));
    }

}

