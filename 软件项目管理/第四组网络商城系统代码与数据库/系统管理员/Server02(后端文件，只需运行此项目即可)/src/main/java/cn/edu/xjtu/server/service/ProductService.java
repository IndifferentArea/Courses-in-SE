package cn.edu.xjtu.server.service;

import cn.edu.xjtu.server.pojo.OrderItem;
import cn.edu.xjtu.server.pojo.Product;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 张剑歆
 * @since 2022-07-14
 */
public interface ProductService extends IService<Product> {
    Boolean updateStock(List<OrderItem> orderItemList,Integer operation);
    //分页查询商品
    Map<String, Object> findPage(int current, int limit);
    //根据名称查询商品
    Product findByName(String name);
    //根据类别添加商品
    Product findByCategory(Integer category);
    //根据id查询商品
    Product findById(Integer id);
}
