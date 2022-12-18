package cn.edu.xjtu.server.controller;

import cn.edu.xjtu.server.pojo.Product;
import cn.edu.xjtu.server.result.RestResult;
import cn.edu.xjtu.server.service.ProductService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


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

    @ApiOperation(value = "商品的分页查询")
    @GetMapping("/page/{current}/{limit}")
    public Object findPage(
            @ApiParam(name = "current", value = "当前页", required = true)
            @PathVariable int current,
            @ApiParam(name = "limit", value = "每页的记录数", required = true)
            @PathVariable int limit) {
        Map<String, Object> data = productService.findPage(current, limit);
        return data;
    }

    @ApiOperation(value = "商品的删除")
    @DeleteMapping("/delete/{id}")
    public String delete(
            @ApiParam(name = "id", value = "商品id", required = true)
            @PathVariable int id) {
        boolean flag = productService.removeById(id);
        if (flag)
            return "success";
        else
            return "fail";
    }

    @ApiOperation(value = "商品的修改")
    @PostMapping("/update")
    public String update(@RequestBody Product product ) {
        Product flag = productService.findById(product.getId());
        if (flag == null) {
            productService.save(product);
            return "success";
        } else {
            return "fail";
        }
    }

    @ApiOperation(value = "根据类别进行商品的增加")
    @PostMapping("/add")
    public String add(@RequestBody Product product) {
        Product flag = productService.findByCategory(product.getCategory());
        if (flag == null) {
            productService.save(product);
            return "success";
        } else {
            return "fail";
        }
    }

    @ApiOperation(value = "根据商品名称进行查询")
    @GetMapping("/getByName/{name}")
    public RestResult getByName(
            @ApiParam(name = "name", value = "商品名称", required = true)
            @PathVariable String name) {
        // 创建条件构造器
        QueryWrapper<Product> wrapper = new QueryWrapper<>();
        wrapper.eq("name", name);
        Product flag = productService.getOne(wrapper);
        if (flag != null)
            return RestResult.ok().data("name", flag);
        else
            return RestResult.error();
    }

    @ApiOperation(value = "根据商品类别进行查询")
    @GetMapping("/getByCategory/{category}")
    public RestResult getByCategory(
            @ApiParam(name = "category", value = "商品类别", required = true)
            @PathVariable int category) {
        // 创建条件构造器
        QueryWrapper<Product> wrapper = new QueryWrapper<>();
        wrapper.eq("category", category);
        Product flag = productService.getOne(wrapper);
        if (flag != null)
            return RestResult.ok().data("name", flag);
        else
            return RestResult.error();
    }
}

