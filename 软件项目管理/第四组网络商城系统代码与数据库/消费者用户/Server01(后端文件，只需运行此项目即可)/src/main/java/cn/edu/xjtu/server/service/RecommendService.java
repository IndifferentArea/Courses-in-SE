package cn.edu.xjtu.server.service;

import cn.edu.xjtu.server.pojo.Recommend;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;


public interface RecommendService extends IService<Recommend> {
    public void updateRecommend(List<Integer> prods,String userID);

}
