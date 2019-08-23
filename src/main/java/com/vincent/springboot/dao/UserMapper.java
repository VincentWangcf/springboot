package com.vincent.springboot.dao;

import com.vincent.springboot.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper     //声明是一个Mapper,与springbootApplication中的@MapperScan二选一写上即可
@Repository
public interface UserMapper {
    User selectUserByName(String name);
     int insetUser(User user);

    void updateByUser(User user);
}
