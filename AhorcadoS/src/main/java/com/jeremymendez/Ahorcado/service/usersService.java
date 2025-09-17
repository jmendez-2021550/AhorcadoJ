package com.jeremymendez.Ahorcado.service;

import com.jeremymendez.Ahorcado.model.users;
import java.util.List;

public interface usersService {
    List<users> getAllUsers();
    users getUserById(Integer user_id);
    users saveUser(users user);
    users updateUser(Integer user_id, users user);
    void deleteUser(Integer user_id);
}
