package cn.edu.xjtu.server.service.impl;

import cn.edu.xjtu.server.pojo.OrderItem;
import cn.edu.xjtu.server.mapper.OrderItemMapper;
import cn.edu.xjtu.server.service.OrderItemService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;


@Service
public class OrderItemServiceImpl extends ServiceImpl<OrderItemMapper, OrderItem> implements OrderItemService {

}
