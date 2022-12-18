package cn.edu.xjtu.server.service.impl;

import cn.edu.xjtu.server.pojo.OrderItem;
import cn.edu.xjtu.server.mapper.OrderItemMapper;
import cn.edu.xjtu.server.service.OrderItemService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 张剑歆
 * @since 2022-07-14
 */
@Service
public class OrderItemServiceImpl extends ServiceImpl<OrderItemMapper, OrderItem> implements OrderItemService {

}
