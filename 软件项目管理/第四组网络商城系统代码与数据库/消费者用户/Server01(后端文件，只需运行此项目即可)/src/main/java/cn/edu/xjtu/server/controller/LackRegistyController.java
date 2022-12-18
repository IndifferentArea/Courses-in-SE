package cn.edu.xjtu.server.controller;


import cn.edu.xjtu.server.pojo.LackRegisty;
import cn.edu.xjtu.server.pojo.Product;
import cn.edu.xjtu.server.pojo.ReturnRegisty;
import cn.edu.xjtu.server.service.LackRegistyService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/lack-registy")
public class LackRegistyController {
    @Autowired
    private LackRegistyService lackRegistyService;
    @GetMapping("/{id}/{user}")
    public Boolean saveLack(@PathVariable("id")Integer Id,
                            @PathVariable("user")String user) throws Exception {

        if (lackRegistyService.getOne(new QueryWrapper<LackRegisty>().
                eq("product", Id).eq("user_id", user))!=null)
            return false;
        lackRegistyService.save(new LackRegisty(Id, 3, user));
        return true;
    }
    @GetMapping("/list")
    public List<LackRegisty> listAll() throws Exception {
        return lackRegistyService.list(null);
    }

}

