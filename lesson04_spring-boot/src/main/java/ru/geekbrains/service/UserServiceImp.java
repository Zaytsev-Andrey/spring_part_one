package ru.geekbrains.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.geekbrains.controller.UserDto;
import ru.geekbrains.controller.UserListParam;
import ru.geekbrains.persist.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImp implements UserService {

    private UserRepository userRepository;

    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImp(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Page<UserDto> findWithFilter(UserListParam params) {
        Specification<User> specification = Specification.where(null);

        if (params.getUsernameFilter() != null && !params.getUsernameFilter().isBlank()) {
            specification = specification.and(UserSpecifications.usernamePrefix(params.getUsernameFilter()));
        }

        String sortColumn;
        if (params.getSortBy() != null && !params.getSortBy().isBlank()) {
            sortColumn = params.getSortBy();
        } else {
            sortColumn = "id";
        }

        Sort sort;
        if ("desc".equals(params.getSortOrder())) {
            sort = Sort.by(sortColumn).descending();
        } else {
            sort = Sort.by(sortColumn).ascending();
        }

        return userRepository.findAll(specification,
                PageRequest.of(Optional.ofNullable(params.getPage()).orElse(1) - 1,
                        Optional.ofNullable(params.getSize()).orElse(3),sort))
                .map(u -> new UserDto(u.getId(), u.getUsername(), u.getEmail()));
    }

    @Override
    public Optional<UserDto> findById(Long id) {
        return userRepository.findById(id)
                .map(u -> new UserDto(u.getId(), u.getUsername(), u.getEmail()));
    }

    @Override
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public void save(UserDto userDto) {
        User user = new User(userDto.getId(),
                userDto.getUsername(),
                passwordEncoder.encode(userDto.getPassword()),
                userDto.getEmail());
        userRepository.save(user);
    }

    @Override
    public List<UserDto> findAll() {
        return userRepository.findAll().stream()
                .map(u -> new UserDto(u.getId(), u.getUsername(), u.getEmail()))
                .collect(Collectors.toList());
    }
}
