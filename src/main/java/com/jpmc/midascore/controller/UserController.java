package com.jpmc.midascore.controller;

import com.jpmc.midascore.foundation.Balance;
import com.jpmc.midascore.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/balance")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping
    public Balance getBalance(@RequestParam Long userId){
        return userService.getBalance(userId);
    }
}
