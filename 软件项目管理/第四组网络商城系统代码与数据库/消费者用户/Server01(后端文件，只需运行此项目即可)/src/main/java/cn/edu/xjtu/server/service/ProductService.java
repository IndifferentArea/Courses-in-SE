package cn.edu.xjtu.server.service;

import cn.edu.xjtu.server.pojo.OrderItem;
import cn.edu.xjtu.server.pojo.Product;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;


public interface ProductService extends IService<Product> {
    Boolean updateStock(List<OrderItem> orderItemList,Integer operation);
}
