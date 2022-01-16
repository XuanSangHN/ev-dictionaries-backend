package com.evdictionaries.controllers;

import com.evdictionaries.models.Class;
import com.evdictionaries.models.User;
import com.evdictionaries.payload.response.BaseResponse;
import com.evdictionaries.payload.response.ClassResponse;
import com.evdictionaries.payload.response.SubjectsResponse;
import com.evdictionaries.repository.RoleRepository;
import com.evdictionaries.repository.SubjectsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class RoleController {
    @Autowired
    RoleRepository roleRepository;

    @GetMapping(value = "/GetListRoles")
    public BaseResponse listRole() {
        return new BaseResponse(HttpStatus.OK,"Truy Cập Thành Công!",null, roleRepository.findAll());
    }
}
