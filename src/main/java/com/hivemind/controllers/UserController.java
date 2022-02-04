package com.hivemind.controllers;


import com.hivemind.services.UserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transaction")
public class UserController {

    private final UserService userService;
}