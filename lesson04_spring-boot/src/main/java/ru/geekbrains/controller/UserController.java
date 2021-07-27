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
import ru.geekbrains.service.UserService;

import javax.validation.Valid;

@Controller
@RequestMapping("/user")
public class UserController {

    private final Logger logger = LoggerFactory.getLogger(UserController.class);

    private final UserService service;

    @Autowired
    public UserController(UserService userService) {
        this.service = userService;
    }

    @GetMapping
    public String listPage(Model model, UserListParam userListParams) {
        logger.info("User list page requested");

        model.addAttribute("users", service.findWithFilter(userListParams));
        return "users";
    }

    @GetMapping("/new")
    public String newUserForm(Model model) {
        logger.info("Change user page requested");

        model.addAttribute("userDto", new UserDto());
        return "user_form";
    }

    @GetMapping("/{id}")
    public String editUser(@PathVariable("id") Long id, Model model) {
        logger.info("Editing user");

        UserDto currentUser = service.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found"));
        model.addAttribute("userDto", currentUser);

        return "user_form";
    }

    @DeleteMapping("/{id}")
    public String removeUser(@PathVariable("id") Long id, Model model) {
        logger.info("Deleting user");

        service.deleteById(id);

        return "redirect:/user";
    }

    @PostMapping
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