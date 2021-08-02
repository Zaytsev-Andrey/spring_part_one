package ru.geekbrains.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.geekbrains.persist.Role;
import ru.geekbrains.persist.RoleRepository;
import ru.geekbrains.service.RoleService;
import ru.geekbrains.service.UserService;

import javax.validation.Valid;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/user")
public class UserController {

    private final Logger logger = LoggerFactory.getLogger(UserController.class);

    private final UserService service;

    private final RoleService roleService;

    @Autowired
    public UserController(UserService userService, RoleService roleService) {
        this.service = userService;
        this.roleService = roleService;
    }

    @GetMapping
    public String listPage(Model model, UserListParam userListParams) {
        logger.info("User list page requested");

        model.addAttribute("users", service.findWithFilter(userListParams));
        return "users";
    }

    @GetMapping("/new")
    public String newUserForm(Model model, Principal principal) {
        logger.info("Change user page requested");

        UserDto userDto = new UserDto();
        Set<RoleDto> roles;
        if (principal != null) {
            roles = roleService.findAll().stream()
                    .map(role -> new RoleDto(role.getId(), role.getName()))
                    .collect(Collectors.toSet());
        } else {
            roles= roleService.findByDefaultRoleTrue().stream()
                    .map(role -> new RoleDto(role.getId(), role.getName()))
                    .collect(Collectors.toSet());
                userDto.setRoles(roles);
        }

        model.addAttribute("userDto", userDto);
        model.addAttribute("roles", roles);
        return "user_form";
    }

    @GetMapping("/{id}")
    public String editUser(@PathVariable("id") Long id, Model model) {
        logger.info("Editing user");

        UserDto currentUser = service.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found"));
        model.addAttribute("userDto", currentUser);
        model.addAttribute("roles", roleService.findAll().stream()
                .map(role -> new RoleDto(role.getId(), role.getName()))
                .collect(Collectors.toSet()));

        return "user_form";
    }

    @DeleteMapping("/{id}")
    public String removeUser(@PathVariable("id") Long id, Model model) {
        logger.info("Deleting user");

        service.deleteById(id);

        return "redirect:/user";
    }

    @PostMapping("/update")
    public String update(@Valid UserDto userDto, BindingResult result) {
        logger.info("Updating user");

        if (!userDto.getPassword().equals(userDto.getConfirmPassword())) {
            result.rejectValue("password", "", "Password and confirm password do not equals");
            return "user_form";
        }

        if (result.hasErrors()) {
            return "user_form";
        }

        service.save(userDto);
        return "redirect:/user";
    }

    @ExceptionHandler
    public ModelAndView notFoundExceptionHandler(NotFoundException e) {
        ModelAndView modelAndView = new ModelAndView("not_found");
        modelAndView.addObject("message", e.getMessage());
        modelAndView.setStatus(HttpStatus.NOT_FOUND);
        return modelAndView;
    }
}
