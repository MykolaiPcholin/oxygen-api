package com.example.oxygen.service;

import com.example.oxygen.entity.User;
import com.example.oxygen.model.UserDTO;
import com.example.oxygen.model.mapper.UserMapper;
import com.example.oxygen.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public List<User> getUsers() {

        return userRepository.findAll();
    }

    public User saveUser(UserDTO userDTO) {

        return userRepository.save(userMapper.toEntity(userDTO));
    }
}
