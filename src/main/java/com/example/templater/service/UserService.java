package com.example.templater.service;

import com.example.templater.model.Role;
import com.example.templater.model.User;
import com.example.templater.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.taglibs.authz.JspAuthorizeTag;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class UserService implements IUserService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        User user = userRepository.findUserByUsername(name);
        System.out.println("name = '" + name + "'");
        System.out.println("load = " + ((user == null) ? "null" : user.toString()));
        if (user == null) {
            throw new UsernameNotFoundException("Login or password is invalid");
        }
        return user;
    }

    @Override
    public User getUserById(Integer id) {
        return userRepository.findUserById(id);
    }

    @Override
    public User getUserByName(String name) {
        return userRepository.findUserByUsername(name);
    }

    @Override
    public boolean saveUser(User user) {
        User userFromDB = userRepository.findUserByUsername(user.getUsername());
        if (userFromDB != null) {
            return false;
        }
        user.setRoles(Collections.singleton(new Role(1L, "ROLE_USER")));
        user.setPassword(PasswordEncoderFactories.createDelegatingPasswordEncoder().encode(user.getPassword()));
        userRepository.save(user);
        return true;
    }

    @Override
    public void saveUserUnsafe(User user) {
        userRepository.save(user);
    }

    @Override
    public void deleteUserById(Integer id) {
        userRepository.deleteById(id);
    }
}
