package ru.itmentor.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.itmentor.spring.boot_security.demo.models.Role;
import ru.itmentor.spring.boot_security.demo.models.User;
import ru.itmentor.spring.boot_security.demo.services.RoleService;
import ru.itmentor.spring.boot_security.demo.services.UserService;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final String REDIRECT = "redirect:/admin/users";
    private final UserService userService;
    private final RoleService roleService;

    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/users")
    public String getAllUsers(Model model) {
        List<User> users = userService.getAllUsers();
        model.addAttribute("users", users);
        return "users";
    }

    @GetMapping("/users/add")
    public String addUserForm(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("allRoles", roleService.getAllRoles());
        return "addUser";
    }

    @PostMapping("/addUser")
    public String addUser(@ModelAttribute User user, @RequestParam("selectedRoles") List<String> selectedRoles) {
        Set<Role> userRoles = selectedRoles.stream()
                .map(roleName -> {
                    Role role = roleService.findRoleByName(roleName);
                    return role;
                })
                .collect(Collectors.toSet());
        user.setRoles(userRoles);

        userService.saveUser(user);
        return REDIRECT;
    }

    @GetMapping("/editUser/{id}")
    public String showEditUserForm(@PathVariable("id") long id, Model model) {
        User user = userService.getUserById(id);
        model.addAttribute("user", user);
        return "editUser";
    }

    @PostMapping("/editUser/{id}")
    public String editUser(@ModelAttribute("user") User user) {
        userService.updateUser(user);
        return REDIRECT;
    }

    @PostMapping("/deleteUser/{id}")
    public String deleteUser(@PathVariable long id, Model model) {
        userService.deleteUserById(id);
        model.addAttribute("message", "Пользователь удален успешно");
        return REDIRECT;
    }
}
