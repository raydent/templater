package com.example.templater.service;

import com.example.templater.model.Temp_Full;
import com.example.templater.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface IUserService extends UserDetailsService {
    User getUserById(Integer id);
    User getUserByName(String name);
    boolean saveUser(User user);
    void saveUserUnsafe(User user);
    void deleteUserById(Integer id);
    List<Temp_Full> getTemplatesListByName(String name);
    
}
