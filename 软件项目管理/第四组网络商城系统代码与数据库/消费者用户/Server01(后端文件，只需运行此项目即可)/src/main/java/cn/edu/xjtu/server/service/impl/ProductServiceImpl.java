package cn.edu.xjtu.server.service.impl;

import cn.edu.xjtu.server.pojo.OrderItem;
import cn.edu.xjtu.server.pojo.Product;
import cn.edu.xjtu.server.mapper.ProductMapper;
import cn.edu.xjtu.server.pojo.StockOperation;
import cn.edu.xjtu.server.service.ProductService;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.swagger.models.auth.In;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Service
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements ProductService {

    @Override
    @Transactional
    public Boolean updateStock(List<OrderItem> orderItemList,Integer operation) {
        String operator = operation.equals(StockOperation.ADD) ? "+" : "-";
        ArrayList<String> ids = new ArrayList<>();
        for (OrderItem orderItem : orderItemList) {
            System.out.println(orderItem);
            UpdateWrapper<Product> wrapper = new UpdateWrapper<>();
            wrapper.setSql("`stock`=`stock`"+operator+""+orderItem.getCount()).eq("id",orderItem.getProduct());
            baseMapper.update(new Product(),wrapper);
            if (baseMapper.selectById(orderItem.getProduct()).getStock()<0) {
                ids.add(orderItem.getProduct().toString());
            }
            System.out.println(ids);
        }
        if (ids.size()>0){
            String msg = "库存不足的货物ID为："+String.join(",",ids)+"!!";
            throw new RuntimeException(msg);
        }
        return true;
    }
}
