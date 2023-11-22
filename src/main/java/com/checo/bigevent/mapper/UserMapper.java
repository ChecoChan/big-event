package com.checo.bigevent.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.checo.bigevent.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface UserMapper extends BaseMapper<User> {
}
