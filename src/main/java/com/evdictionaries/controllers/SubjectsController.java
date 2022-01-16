package com.evdictionaries.controllers;

import com.evdictionaries.models.Class;
import com.evdictionaries.models.Subjects;
import com.evdictionaries.payload.request.SubjectsRequest;
import com.evdictionaries.payload.response.BaseResponse;
import com.evdictionaries.payload.response.ClassResponse;
import com.evdictionaries.payload.response.MessageResponse;
import com.evdictionaries.payload.response.SubjectsResponse;
import com.evdictionaries.repository.SubjectsRepository;
import com.evdictionaries.service.impl.SubjectsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class SubjectsController {
    @Autowired
    SubjectsService subjectsService;

    @Autowired
    SubjectsRepository subjectsRepository;

    @PostMapping(value = "/InsertSubjects")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> createClass(@RequestBody SubjectsRequest subjectsRequest) {
        subjectsService.createOrUpdateSubjects(subjectsRequest);
        return ResponseEntity.ok(new MessageResponse((long) HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), "Thêm Lớp Học Thành Công!"));
    }

    @PutMapping(value = "UpdateSubjects/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateClass(@RequestBody SubjectsRequest subjectsRequest, @PathVariable("id") Long id) {
        subjectsRequest.setId(id);
        return ResponseEntity.ok(subjectsService.createOrUpdateSubjects(subjectsRequest));
    }

    @DeleteMapping(value = "DeleteSubjects")
    @PreAuthorize("hasRole('ADMIN')")
    public void delete(@RequestBody Long[] ids) {
        subjectsService.deleteSubjectsById(ids);
    }

    @GetMapping(value = "/GetListSubjects")
    @PreAuthorize("hasRole('ADMIN')")
    public BaseResponse listClassByProfileId(@RequestParam("class_id") long id) {
        Class aClass = new Class();
        aClass.setId(id);
        List<Subjects> subjectsList = subjectsRepository.findAllByClassId(aClass);
        List<SubjectsResponse> subjectsResponses = new ArrayList<>();
        for (int i=0 ; i<subjectsList.size();i++){
            subjectsResponses.add(new SubjectsResponse(subjectsList.get(i).getId(),subjectsList.get(i).getCode(),subjectsList.get(i).getName(),subjectsList.get(i).getDescription(),subjectsList.get(i).getClassEntity().getId()));
        }
        return new BaseResponse(HttpStatus.OK, "Truy Cập Thành Công!", null, subjectsResponses);

    }
}
