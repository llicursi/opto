package com.llicursi.opto.controller;

import com.llicursi.opto.datatransferobject.mapper.UserMapper;
import com.llicursi.opto.datatransferobject.UserRegisterDTO;
import com.llicursi.opto.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("register")
public class RegisterController {

    private final UserService userService;

    @Autowired
    public RegisterController(final UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String test() {
       return "Ok";
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createUser(@RequestBody UserRegisterDTO userRegisterDTO) {
        UserMapper.convertToUserDTO(userService.create(UserMapper.convertToUserDO(userRegisterDTO)));
    }

}
