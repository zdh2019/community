package zhangdahu.community.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zhangdahu.community.mapper.UserMapper;
import zhangdahu.community.model.User;
import zhangdahu.community.model.UserExample;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;


    public void createOrUpdate(User user) {
        UserExample userExample = new UserExample();
        userExample.createCriteria()
                .andAccountIdEqualTo(user.getAccountId());
        List<User> users=userMapper.selectByExample(userExample);
        if(users.size()==0)
        {
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());
            userMapper.insert(user);
        }else{
            User dbuser=users.get(0);
            User updateUser=new User();
            updateUser.setName(user.getName());
            user.setGmtModified(System.currentTimeMillis());
            updateUser.setAvatarUrl(user.getAvatarUrl());
            updateUser.setToken(user.getToken());
            UserExample example = new UserExample();
            example.createCriteria()
                    .andIdEqualTo(dbuser.getId());
            userMapper.updateByExampleSelective(updateUser, example);
        }
    }
}
