package cn.edu.xjtu.server.service.impl;

import cn.edu.xjtu.server.pojo.Admin;
import cn.edu.xjtu.server.mapper.AdminMapper;
import cn.edu.xjtu.server.service.AdminService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;


@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements AdminService {

}
