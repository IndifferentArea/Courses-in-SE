package cn.edu.xjtu.server.controller;


import cn.edu.xjtu.server.mapper.ProductMapper;
import cn.edu.xjtu.server.mapper.RecommendMapper;
import cn.edu.xjtu.server.pojo.Product;
import cn.edu.xjtu.server.pojo.Recommend;
import cn.edu.xjtu.server.service.ProductService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@RestController
@RequestMapping("/recommend")
public class RecommendController {
    @Autowired
    private RecommendMapper recommendMapper;
    @Autowired
    private ProductMapper productMapper;
    @GetMapping("/{id}")
    public Map<String,Integer> getRecommend(@PathVariable("id")String id) throws Exception {
        Recommend recommend = recommendMapper.selectById(id);
        String[] products = recommend.getProducts().split(",");
        HashMap<String, Integer> map = new HashMap<>();
        if (products.length<4){
            map.put("price",-1);
            map.put("categories",-1);
        }else {
            List<Product> productList = productMapper.selectBatchIds(Arrays.stream(products).mapToInt(i -> Integer.parseInt(i)).boxed().collect(Collectors.toList()));
            BigDecimal avgPrice = new BigDecimal(0);
            HashMap<Integer, Integer> categoryMap = new HashMap<>();
            for (int i = 0; i < productList.size(); i++) {
                Product product = productList.get(i);
                avgPrice = product.getPrice().add(avgPrice);
                Integer category = product.getCategory();
                if (categoryMap.containsKey(category))
                    categoryMap.put(category,categoryMap.get(category)+1);
                else
                    categoryMap.put(category,1);
            }
            avgPrice = avgPrice.divide(new BigDecimal(productList.size()),2,BigDecimal.ROUND_HALF_UP);
            Integer recommendCategory = maxCategory(categoryMap,3);
            map.put("price",getRecommendPrice(avgPrice));
            map.put("categories",recommendCategory);
        }
        return map;
    }
    private Integer maxCategory(Map<Integer, Integer> categoryMap,Integer minCnt){
        int maxCategory = -1;
        int maxVal = 0;
        for (Map.Entry<Integer, Integer> integerIntegerEntry : categoryMap.entrySet()) {
            Integer key = integerIntegerEntry.getKey();
            Integer value = integerIntegerEntry.getValue();
            if (value<minCnt)
                continue;
            if (value>maxVal){
                maxCategory = key;
                maxVal = value;
            }
        }
        return maxCategory;
    }
    private Integer getRecommendPrice(BigDecimal avgPrice){
        if (avgPrice.compareTo(new BigDecimal(30))<=0)
            return 1;
        if (avgPrice.compareTo(new BigDecimal(60))<=0)
            return 31;
        if (avgPrice.compareTo(new BigDecimal(90))<=0)
            return 61;
        if (avgPrice.compareTo(new BigDecimal(120))<=0)
            return 91;
        return 91;
    }

}

