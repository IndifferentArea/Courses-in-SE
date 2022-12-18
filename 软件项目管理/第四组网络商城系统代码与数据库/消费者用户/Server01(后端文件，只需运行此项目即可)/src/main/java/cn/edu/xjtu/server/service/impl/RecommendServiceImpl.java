package cn.edu.xjtu.server.service.impl;

import cn.edu.xjtu.server.pojo.Recommend;
import cn.edu.xjtu.server.mapper.RecommendMapper;
import cn.edu.xjtu.server.service.RecommendService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Service
public class RecommendServiceImpl extends ServiceImpl<RecommendMapper, Recommend> implements RecommendService {

    @Override
    public void updateRecommend(List<Integer> prods,String userID) {
        Recommend recommend = baseMapper.selectById(userID);
        Integer ptr = recommend.getPointer();
        ArrayList<String> strings = null;
        if (recommend.getProducts().equals(""))
            strings = new ArrayList<>();
        else
            strings = new ArrayList<>(Arrays.asList(recommend.getProducts().split(",")));
        int start = 0;
        if (prods.size()>7)
            start = prods.size() - 7;
        for (int i = start; i <prods.size(); i++) {
            if (strings.size()<7)
                strings.add(prods.get(i).toString());
            else
                strings.set(ptr,prods.get(i).toString());
            ptr = (ptr+1) % 7;
        }
        recommend.setPointer(ptr);
        recommend.setProducts(String.join(",",strings));
        baseMapper.updateById(recommend);
    }
}
