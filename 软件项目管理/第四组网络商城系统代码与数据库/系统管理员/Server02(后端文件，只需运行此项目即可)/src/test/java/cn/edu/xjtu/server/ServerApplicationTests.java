package cn.edu.xjtu.server;

import cn.edu.xjtu.server.mapper.OrderMapper;
import cn.edu.xjtu.server.mapper.ProductMapper;
import cn.edu.xjtu.server.mapper.TransprtMapper;
import cn.edu.xjtu.server.pojo.*;
import cn.edu.xjtu.server.service.*;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

@SpringBootTest
@RequiredArgsConstructor
class ServerApplicationTests {

    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private TransprtMapper transprtMapper;
    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private OrderService orderService;
    @Autowired
    private ProductService productService;
    @Autowired
    private AdminService adminService;
    @Autowired
    private DataSource dataSource;
    public static Integer orderID = 3;
    @Test
    void contextLoads() {
        Admin one = adminService.getOne(new QueryWrapper<Admin>().eq("id", "admin.getId()")
                .eq("pwd", "admin.getPwd()"));
        System.out.println(one);
    }
    @Test
    void dataSource(){
        System.out.println(dataSource.getClass().getName());
    }
    @Test
    void createOrder() {
        Order order = new Order(null,"上海","123456",
                null,null,new BigDecimal(135.8),null,
                null,null,null,null,null,null);
        OrderItem item1 = new OrderItem(null,6,2,new BigDecimal(46),null,null);
        OrderItem item2 = new OrderItem(null,7,2,new BigDecimal(89.8),null,null);
        List<OrderItem> orderItems = Arrays.asList(item1,item2);
        order.setItems(orderItems);
        orderService.createOrder(order);
    }

    @Test
    void eval() {
        orderService.eval(orderID);
    }

    @Test
    void queryDetailsByID() {
        Order order = orderService.queryDetailsByID(orderID);
        System.out.println(order);
    }

    @Test
    void advanceCharge() {
        Boolean aBoolean = orderService.advanceCharge(orderID, 50.0);
    }

    @Test
    void provinceSign() {
        Boolean aBoolean = orderService.provinceSign(orderID);

    }

    @Test
    void spotSignAndRaisePrice() {
        orderService.spotSignAndRaisePrice(orderID);
    }

    @Test
    void checkAndFinish() {
        Integer integer = orderService.checkAndFinish(orderID);
        System.out.println("==========="+integer);
    }

    @Test
    void orderReturn() {
        Boolean aBoolean = orderService.orderReturn(orderID);
        System.out.println(aBoolean);
    }

}
