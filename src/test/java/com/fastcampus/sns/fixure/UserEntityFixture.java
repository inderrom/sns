package com.fastcampus.sns.fixure;

import com.fastcampus.sns.model.entity.UserEntity;

public class UserEntityFixture {

    public static UserEntity get(String userName, String password, Integer userId) {
        UserEntity entity = new UserEntity();
        entity.setId(userId);
        entity.setUserName(userName);
        return entity;
    }
}