package cn.edu.xjtu.server.controller;


import cn.edu.xjtu.server.pojo.DefectRegisty;
import cn.edu.xjtu.server.pojo.Drivers;
import cn.edu.xjtu.server.pojo.ReturnRegisty;
import cn.edu.xjtu.server.pojo.Transprt;
import cn.edu.xjtu.server.service.DefectRegistyService;
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
@RequestMapping("/defect-registy")
public class DefectRegistyController {
    @Autowired
    private DefectRegistyService defectRegistyService;
    @GetMapping("/list")
    public List<DefectRegisty> listAll() throws Exception {
        return defectRegistyService.list(null);
    }
}

