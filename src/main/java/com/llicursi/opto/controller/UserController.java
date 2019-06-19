package com.llicursi.opto.controller;

import com.llicursi.opto.domainobject.UserDO;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class UserController {

    @PostMapping("/register")
    public void save(@RequestBody UserDO userDO){

    }
}
