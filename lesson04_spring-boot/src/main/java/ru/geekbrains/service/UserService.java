package ru.geekbrains.service;

import org.springframework.data.domain.Page;
import ru.geekbrains.controller.UserDto;
import ru.geekbrains.controller.UserListParam;

import java.util.List;
import java.util.Optional;

public interface UserService {
    Page<UserDto> findWithFilter(UserListParam userListParam);

    Optional<UserDto> findById(Long id);

    void deleteById(Long id);

    void save(UserDto user);

    List<UserDto> findAll();
}
