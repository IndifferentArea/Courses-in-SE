package cn.edu.xjtu.server.service;

import cn.edu.xjtu.server.pojo.Order;
import com.baomidou.mybatisplus.extension.service.IService;
import io.swagger.models.auth.In;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


public interface OrderService extends IService<Order> {
    public String createOrder(Order order);
    public Boolean eval(Integer OrderID);
    //List<T> list(Wrapper<T> queryWrapper);
    public Order queryDetailsByID(Integer ID);
    //修改用户的余额，使用事务管理。
    public Boolean advanceCharge(Integer ID,Double payment);
    public Boolean provinceSign(Integer ID);
    public Boolean spotSignAndRaisePrice(Integer ID);
    //检查缺损和退货并填好记录，完成尾款结算。返回Order的endStatus
    public Integer checkAndFinish(Integer ID);
    public Boolean orderReturn(Integer ID);
}
