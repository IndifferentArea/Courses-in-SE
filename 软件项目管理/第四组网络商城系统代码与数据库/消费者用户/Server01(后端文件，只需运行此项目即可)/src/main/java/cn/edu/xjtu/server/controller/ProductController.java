package cn.edu.xjtu.server.controller;


import cn.edu.xjtu.server.pojo.Order;
import cn.edu.xjtu.server.pojo.Product;
import cn.edu.xjtu.server.service.ProductService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/product")
public class ProductController {
    @Autowired
    private ProductService productService;
    @GetMapping("/{pageNo}/{size}/{price}/{category}")
    public Object doProductPage(@PathVariable("pageNo")Integer pageNo,
                                @PathVariable("size")Integer size,
                                @PathVariable("price")Integer price,
                                @PathVariable("category")Integer category) throws Exception {
        Page<Product> orderPage = new Page<>(pageNo,size);
        QueryWrapper<Product> productQueryWrapper = new QueryWrapper<>();
        if (price!=-1)
            productQueryWrapper.between("price",price,price+29);
        if (category!=-1)
            productQueryWrapper.eq("category",category);

        return productService.page(orderPage,productQueryWrapper);
    }
    @GetMapping("/get/{id}")
    public Product oneProduct(@PathVariable("id")Integer Id) throws Exception {

        return productService.getById(Id);
    }



}

