package com.xlydbb.myblog.service;

import com.xlydbb.myblog.pojo.User;

public interface UserService {
    User checkUser(String username, String password);
}
