package com.evdictionaries.controllers;

import com.evdictionaries.models.Role;
import com.evdictionaries.payload.response.BaseResponse;
import com.evdictionaries.payload.response.MessageResponse;
import com.evdictionaries.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class RoleController {
    @Autowired
    RoleRepository roleRepository;

    @GetMapping(value = "/GetListRoles")
    @PreAuthorize("hasRole('ADMIN') or hasRole('COLLABORATORS')")
    public BaseResponse listRole() {
        return new BaseResponse(HttpStatus.OK,"Truy Cập Thành Công!",null, roleRepository.findAll());
    }

    @PostMapping(value = "/InsertRoles")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> createRole(@RequestBody Role role) {
        roleRepository.save(role);
        return ResponseEntity.ok(new MessageResponse((long)HttpStatus.OK.value(),HttpStatus.OK.getReasonPhrase(),"Thêm Quyền Thành Công!"));
    }

    @PutMapping(value = "/UpdateRoles/{id_roles}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity updateRole(@RequestBody Role role, @PathVariable("id_roles") Long id) {
        role.setId(id);
        roleRepository.save(role);
        return ResponseEntity.ok(new MessageResponse((long)HttpStatus.OK.value(),HttpStatus.OK.getReasonPhrase(),"Sửa Đổi Thông Tin Quyền Thành Công!"));
    }

    @DeleteMapping(value = "/DeleteRoles")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity deleteRole(@RequestBody Long[] ids) {
        for (Long idRole : ids) {
            roleRepository.delete(idRole);
        }
        return ResponseEntity.ok(new MessageResponse((long)HttpStatus.OK.value(),HttpStatus.OK.getReasonPhrase(),"Xóa Quyền Thành Công!"));
    }
}
