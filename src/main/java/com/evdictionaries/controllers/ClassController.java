package com.evdictionaries.controllers;

import com.evdictionaries.models.Class;
import com.evdictionaries.models.User;
import com.evdictionaries.payload.request.ClassRequest;
import com.evdictionaries.payload.response.BaseResponse;
import com.evdictionaries.payload.response.ClassResponse;
import com.evdictionaries.payload.response.MessageResponse;
import com.evdictionaries.payload.response.SubjectsResponse;
import com.evdictionaries.repository.ClassRepository;
import com.evdictionaries.repository.SubjectsRepository;
import com.evdictionaries.service.impl.ClassService;
import com.evdictionaries.service.impl.SubjectsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class ClassController {

    @Autowired
    SubjectsRepository subjectsRepository;

    @Autowired
    ClassRepository classRepository;

    @Autowired
    ClassService classService;

    @PostMapping(value = "/InsertClass")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> createClass(@RequestBody ClassRequest classRequest) {
        classService.createClass(classRequest);
        return ResponseEntity.ok(new MessageResponse((long)HttpStatus.OK.value(),HttpStatus.OK.getReasonPhrase(),"Thêm Lớp Học Thành Công!"));
    }

    @PutMapping(value = "/UpdateClass/{id_class}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity updateClass(@RequestBody ClassRequest classRequest, @PathVariable("id_class") Long id) {
        classRequest.setId(id);
        classService.createClass(classRequest);
        return ResponseEntity.ok(new MessageResponse((long)HttpStatus.OK.value(),HttpStatus.OK.getReasonPhrase(),"Sửa Đổi Thông Tin Lớp Học Thành Công!"));
    }

    @DeleteMapping(value = "/DeleteClass")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity deleteClass(@RequestBody Long[] ids) {
        classService.deleteClassById(ids);
        return ResponseEntity.ok(new MessageResponse((long)HttpStatus.OK.value(),HttpStatus.OK.getReasonPhrase(),"Xóa Lớp Học Thành Công!"));
    }

    @GetMapping(value = "/GetListClass")
    @PreAuthorize("hasRole('ADMIN')")
    public BaseResponse listClassByProfileId(@RequestParam("profile_id") long id) {
        User user = new User();
        user.setId(id);
        List<ClassResponse> objectClassRespons = new ArrayList<>();
        List<Class> listClass = classRepository.findAllByUserId(user);
        for (int i= 0; i< listClass.size();i++){
            ClassResponse classResponse = new ClassResponse();
            List<SubjectsResponse>subjects = new ArrayList<>();
            classResponse.setId(listClass.get(i).getId());
            classResponse.setCode(listClass.get(i).getCode());
            classResponse.setDescription(listClass.get(i).getDescription());
            classResponse.setName(listClass.get(i).getName());
            Class aClass = new Class();
            aClass.setId(listClass.get(i).getId());
            for (int j=0;j<subjectsRepository.findAllByClassId(aClass).size();j++){
                subjects.add(new SubjectsResponse(subjectsRepository.findAllByClassId(aClass).get(j).getId(),subjectsRepository.findAllByClassId(aClass).get(j).getCode(),subjectsRepository.findAllByClassId(aClass).get(j).getName(),subjectsRepository.findAllByClassId(aClass).get(j).getDescription(),subjectsRepository.findAllByClassId(aClass).get(j).getClassEntity().getId()));
            }
            classResponse.setSubjects(subjects);
            objectClassRespons.add(classResponse);
        }
        return new BaseResponse(HttpStatus.OK,"Truy Cập Thành Công!",null, (List<ClassResponse>)objectClassRespons);
    }
}
