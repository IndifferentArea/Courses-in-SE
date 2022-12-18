package cn.edu.xjtu.server.service.impl;

import cn.edu.xjtu.server.mapper.*;
import cn.edu.xjtu.server.pojo.*;
import cn.edu.xjtu.server.service.*;
import cn.edu.xjtu.server.util.ProjectUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 张剑歆
 * @since 2022-07-14
 */
@Service
@RequiredArgsConstructor
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {
    @Autowired
    private OrderMapper orderMapper = baseMapper;
    @Autowired
    private OrderItemService orderItemService;
    @Autowired
    private DefectRegistyMapper defectRegistyMapper;
    @Autowired
    private DriversMapper driversMapper;
    @Autowired
    private TransprtMapper transprtMapper;
    @Autowired
    private ReturnRegistyMapper returnRegistyMapper;
    @Autowired
    private ProductService productService;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RecommendService recommendService;

    public String createOrder(Order order){
        String toReturn = "success";
        BigDecimal userBalance = userMapper.selectById(order.getUser()).getUserBalance();
        if (userBalance.compareTo(order.getShouldPay()) < 0)
            toReturn = "余额不足，请尽快充值后缴费";

        try {
            Boolean aBoolean = productService.updateStock(order.getItems(), StockOperation.SUBTRACT);
        } catch (Exception e) {

            return e.getMessage();
        }
        order.setCreateTime(new Date(System.currentTimeMillis()));
        orderMapper.insert(order);
        Integer id = order.getOrderId();
        ArrayList<Integer> prods = new ArrayList<>();
        for (OrderItem item : order.getItems()) {
            item.setOrderId(id);
            prods.add(item.getProduct());
        }

        orderItemService.saveBatch(order.getItems());

        recommendService.updateRecommend(prods,order.getUser());
        return toReturn;
    }

    @Override
    public Boolean eval(Integer orderID) {
        Order order = orderMapper.selectById(orderID);
        QueryWrapper<Transprt> wrapper = new QueryWrapper<>();
        wrapper.select("cost").eq("province",order.getDestProvince());
        BigDecimal cost = transprtMapper.selectOne(wrapper).getCost();
        order.setShouldPay(order.getShouldPay().add(cost));
        order.setCompleteSatus(CompleteStatus.EVALUATE);
        orderMapper.updateById(order);
        return true;
    }

    @Override
    public Order queryDetailsByID(Integer ID) {
        return orderMapper.queryOrderDetails(ID);
    }

    @Override
    //支付后配车，填入car_id
    public Boolean advanceCharge(Integer ID, Double payment) {
        BigDecimal toPay = new BigDecimal(payment);
        Order order = orderMapper.selectById(ID);
        User user = userMapper.selectById(order.getUser());
        BigDecimal userBalance = user.getUserBalance();
        if (userBalance.compareTo(toPay)<0)
            return false;
        user.setUserBalance(userBalance.subtract(toPay).setScale(2,BigDecimal.ROUND_HALF_UP));
        userMapper.updateById(user);
        Integer car = driversMapper.adaptCar(order.getDestProvince());
        order.setCarId(car);
        order.setCompleteSatus(CompleteStatus.ADVANCE_PAY);
        order.setActuallyPay(toPay);
        orderMapper.updateById(order);
        return true;
    }

    @Override
    public Boolean provinceSign(Integer ID) {
        Order order = orderMapper.selectById(ID);
        order.setProvinceSignTime(new Date(System.currentTimeMillis()));
        order.setCompleteSatus(CompleteStatus.PROVINCE_SIGN);
        orderMapper.updateById(order);
        return true;
    }

    @Override
    //可能会加路费
    public Boolean spotSignAndRaisePrice(Integer ID) {
        Order order = orderMapper.selectById(ID);
        order.setSpotSignTime(new Date(System.currentTimeMillis()));
        order.setCompleteSatus(CompleteStatus.SPOT_SIGN);
        boolean rand = ProjectUtils.rand();
        if (true)
            order.setShouldPay(new BigDecimal(10).add(order.getShouldPay()));
        orderMapper.updateById(order);
        return true;
    }

    @Override
    public Integer checkAndFinish(Integer ID) {
        Order order = orderMapper.queryOrderDetails(ID);
        String userID = order.getUser();
        UpdateWrapper<User> wrapper = new UpdateWrapper<>();
        if (order.getEndStatus().equals(EndStatus.RETURN)){
            //退货赔偿20元。
            order.setShouldPay(new BigDecimal(20));
            productService.updateStock(order.getItems(),StockOperation.ADD);
            returnRegistyMapper.insert(new ReturnRegisty(order.getOrderId(),false));
        }else if (ID%2==0){
            //损货
            List<OrderItem> items = order.getItems();
            Integer productID = items.get(0).getProduct();
            BigDecimal backPrice = productService.getById(productID).getPrice();
            defectRegistyMapper.insert(new DefectRegisty(ID,productID,1));
            order.setShouldPay(order.getShouldPay().subtract(backPrice).setScale(2,BigDecimal.ROUND_HALF_UP));
            order.setEndStatus(EndStatus.DEFECT);
        }
            //否则正常。
        wrapper.setSql("`user_balance`=`user_balance`-("+order.getShouldPay().subtract(order.getActuallyPay()).setScale(2,BigDecimal.ROUND_HALF_UP)+")")
                .eq("user_id",userID);
        userMapper.update(new User(),wrapper);
        order.setActuallyPay(order.getShouldPay());
        order.setCompleteSatus(CompleteStatus.CHECK_AND_FINISH);
        order.setFinishTime(new Date(System.currentTimeMillis()));
        orderMapper.updateById(order);
        return order.getEndStatus();
    }

    @Override
    public Boolean orderReturn(Integer ID) {
        Order order = orderMapper.queryOrderDetails(ID);
        Boolean val = false;
        String userID = order.getUser();
        Integer completeSatus = order.getCompleteSatus();
        order.setEndStatus(EndStatus.RETURN);
        if (Integer.compare(completeSatus,CompleteStatus.ADVANCE_PAY)<0){
            order.setCompleteSatus(CompleteStatus.CHECK_AND_FINISH);
            order.setFinishTime(new Date(System.currentTimeMillis()));
            val = true;
            returnRegistyMapper.insert(new ReturnRegisty(ID,true));
            productService.updateStock(order.getItems(),StockOperation.ADD);
            order.setShouldPay(new BigDecimal(0));
        }
        orderMapper.updateById(order);
        return val;
    }

}
