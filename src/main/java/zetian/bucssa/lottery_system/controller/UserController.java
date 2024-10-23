package zetian.bucssa.lottery_system.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import zetian.bucssa.lottery_system.domain.User;
import zetian.bucssa.lottery_system.service.UserService;

import java.sql.Timestamp;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/add")
    public String addUser(
            @RequestParam("buId") String buId,
            @RequestParam("name") String name,
            @RequestParam("email") String email,
            @RequestParam("phone") String phone) {
        User user = new User();
        user.setBuId(buId);
        user.setName(name);
        user.setEmail(email);
        user.setPhone(phone);
        user.setCreated_at(new java.sql.Timestamp(System.currentTimeMillis()));

        userService.addUser(user);
        return "User added successfully!";
    }

    @GetMapping("/all")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{buId}")
    public User getUserByBuId(@PathVariable String buId) {
        return userService.getUserByBuId(buId);
    }
}
