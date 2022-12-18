package cn.edu.xjtu.server.mapper;

import cn.edu.xjtu.server.pojo.Drivers;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 张剑歆
 * @since 2022-07-14
 */
@Repository
public interface DriversMapper extends BaseMapper<Drivers> {


    Integer adaptCar(String province);
}
