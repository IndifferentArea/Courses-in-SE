package cn.edu.xjtu.server.mapper;

import cn.edu.xjtu.server.pojo.Drivers;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DriversMapper extends BaseMapper<Drivers> {


    Integer adaptCar(String province);
}
