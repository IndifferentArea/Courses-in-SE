package cn.edu.xjtu.server.mapper;

import cn.edu.xjtu.server.pojo.Order;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface OrderMapper extends BaseMapper<Order> {
    Order queryOrderDetails(@Param("order_id") Integer id);
}
