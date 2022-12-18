package cn.edu.xjtu.server.service;

import cn.edu.xjtu.server.pojo.Recommend;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 张剑歆
 * @since 2022-11-01
 */
public interface RecommendService extends IService<Recommend> {
    public void updateRecommend(List<Integer> prods,String userID);

}
