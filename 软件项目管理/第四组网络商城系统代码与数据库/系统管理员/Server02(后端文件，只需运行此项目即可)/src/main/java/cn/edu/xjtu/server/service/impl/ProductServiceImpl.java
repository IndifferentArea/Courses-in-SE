package cn.edu.xjtu.server.service.impl;

import cn.edu.xjtu.server.pojo.OrderItem;
import cn.edu.xjtu.server.pojo.Product;
import cn.edu.xjtu.server.mapper.ProductMapper;
import cn.edu.xjtu.server.pojo.StockOperation;
import cn.edu.xjtu.server.service.ProductService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 张剑歆
 * @since 2022-07-14
 */
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
    @Override
    public Map<String, Object> findPage(int current, int limit) {
        //创建分页对象
        Page<Product> page = new Page<>(current,limit);
        //调用分页查询的办法
        baseMapper.selectPage(page, null);
        //获取表中的显示的数据
        List<Product> list = page.getRecords();
        //获取表中总记录数
        long total = page.getTotal();
        //获取分页总数
        long pages = page.getPages();
        //将分页信息数据封装在Map集合中
        Map<String, Object> map = new HashMap<>();
        map.put("rows",list);
        map.put("total",total);
        map.put("pages",pages);
        map.put("current",current);
        map.put("limit",limit);
        return map;
    }

    @Override
    public Product findByName(String name) {
        //创建条件构造器
        QueryWrapper<Product> wrapper = new QueryWrapper<>();
        wrapper.eq("name",name);
        return baseMapper.selectOne(wrapper);
    }

    @Override
    public Product findByCategory(Integer category){
        //创建条件构造器
        QueryWrapper<Product> wrapper = new QueryWrapper<>();
        wrapper.eq("category",category);
        return baseMapper.selectOne(wrapper);
    }

    @Override
    public Product findById(Integer id){
        //创建条件构造器
        QueryWrapper<Product> wrapper = new QueryWrapper<>();
        wrapper.eq("id",id);
        return baseMapper.selectOne(wrapper);
    }
}
