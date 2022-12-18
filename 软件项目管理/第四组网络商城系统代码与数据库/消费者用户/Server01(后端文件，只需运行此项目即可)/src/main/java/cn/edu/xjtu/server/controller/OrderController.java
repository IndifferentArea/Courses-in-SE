package cn.edu.xjtu.server.controller;


import cn.edu.xjtu.server.pojo.Order;
import cn.edu.xjtu.server.pojo.Product;
import cn.edu.xjtu.server.service.OrderService;
import cn.edu.xjtu.server.service.impl.OrderServiceImpl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Method;


@RestController
@RequestMapping("/order")
@Slf4j
public class OrderController {
    @Autowired
    private OrderService orderService;
    @GetMapping("/{action}/{id}")
    public Object distribute(@PathVariable("action")String action,
                             @PathVariable("id")Integer id) throws Exception {

        Method method = OrderServiceImpl.class.getDeclaredMethod(action, Integer.class);
        return method.invoke(orderService, id);
    }
    @GetMapping("/{action}/{user}/{pageNo}/{size}")
    public Object doPage(@PathVariable("action")String action,
                             @PathVariable("user")String user,
                         @PathVariable("pageNo")Integer pageNo,
                         @PathVariable("size")Integer size) throws Exception {

        Page<Order> orderPage = new Page<>(pageNo,size);
        return orderService.page(orderPage,new QueryWrapper<Order>().eq("user",user));
    }

    @GetMapping("/adminDoPage/{pageNo}/{size}/{completeSatus}/{destProvince}")
    public Object adminDoPage(@PathVariable("completeSatus")Integer completeSatus,
                         @PathVariable("destProvince")String destProvince,
                         @PathVariable("pageNo")Integer pageNo,
                         @PathVariable("size")Integer size) throws Exception {

        Page<Order> orderPage = new Page<>(pageNo,size);
        QueryWrapper<Order> wrapper = new QueryWrapper<Order>();
        if (completeSatus!=-1){
            wrapper.eq("complete_satus",completeSatus);
        }
        if (!destProvince.equals("none")){
            wrapper.eq("dest_province",destProvince);
        }
        return orderService.page(orderPage,wrapper);
    }

    @PostMapping("/create")
    public String create(@RequestBody Order order) throws Exception {
        return orderService.createOrder(order);
    }
    @GetMapping("/{action}/{id}/{payment}")
    public Boolean advancePay(@PathVariable("action")String action,
                             @PathVariable("id")Integer id,
                             @PathVariable("payment")Double payment) throws Exception {
        //false为钱不够
        return orderService.advanceCharge(id,payment);
    }


}

