package com.ghouse.service.mapper;

import com.ghouse.bean.User;
import org.apache.ibatis.annotations.Param;

/**
 * Created by godlikehzj on 2016/12/18.
 */
public interface UserMapper {
    public User getUserByMobile(@Param("mobile") String mobile);
    public User getUserByToken(@Param("token") String token);
    public void updateUserInfo(User user);
}
