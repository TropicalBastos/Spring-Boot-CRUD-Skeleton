package com.example.demo.service;


import java.util.Collection;
import com.example.demo.model.User;

public interface UserService {

    Collection<User> findAll();

    User findOne(Integer id);
    
    User findByEmail(String email);

    User create(User user);

    User update(User user);

    void delete(Integer id);
    
    void save(User user);

}