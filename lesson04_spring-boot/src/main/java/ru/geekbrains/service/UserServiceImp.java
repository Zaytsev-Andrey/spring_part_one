package ru.geekbrains.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.geekbrains.controller.RoleDto;
import ru.geekbrains.controller.UserDto;
import ru.geekbrains.controller.UserListParam;
import ru.geekbrains.persist.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;
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
                .map(user -> new UserDto(user.getId(), user.getUsername(), user.getEmail(), mapRolesDto(user)));
    }

    @Override
    public Optional<UserDto> findById(Long id) {
        return userRepository.findById(id)
                .map(user -> new UserDto(user.getId(), user.getUsername(), user.getEmail(), mapRolesDto(user)));
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
                userDto.getEmail(),
                userDto.getRoles().stream()
                        .map(roleDto -> new Role(roleDto.getId(), roleDto.getName()))
                        .collect(Collectors.toSet()));
        userRepository.save(user);
    }

    @Override
    public List<UserDto> findAll() {
        return userRepository.findAll().stream()
                .map(user -> new UserDto(user.getId(),
                        user.getUsername(),
                        user.getEmail(),
                        mapRolesDto(user)
                ))
                .collect(Collectors.toList());
    }

    private static Set<RoleDto> mapRolesDto(User user) {
        return user.getRoles().stream()
                .map(role -> new RoleDto(role.getId(), role.getName()))
                .collect(Collectors.toSet());

    }
}
