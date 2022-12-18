package cn.edu.xjtu.server.service.impl;

import cn.edu.xjtu.server.pojo.User;
import cn.edu.xjtu.server.mapper.UserMapper;
import cn.edu.xjtu.server.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;


@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}
