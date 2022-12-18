package cn.edu.xjtu.server.service.impl;

import cn.edu.xjtu.server.pojo.Admin;
import cn.edu.xjtu.server.mapper.AdminMapper;
import cn.edu.xjtu.server.service.AdminService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 张剑歆
 * @since 2022-07-17
 */
@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements AdminService {

}
