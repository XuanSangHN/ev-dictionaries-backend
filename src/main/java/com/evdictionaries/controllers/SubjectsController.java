package com.evdictionaries.controllers;

import com.evdictionaries.payload.request.SubjectsRequest;
import com.evdictionaries.service.impl.SubjectsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/v1")
public class SubjectsController {
    @Autowired
    SubjectsService subjectsService;

    @PostMapping(value = "subjects")
    public ResponseEntity<?> createClass(@RequestBody SubjectsRequest subjectsRequest) {
        return ResponseEntity.ok(subjectsService.createOrUpdateSubjects(subjectsRequest));
    }

    @PutMapping(value = "subjects/{id}")
    public ResponseEntity<?> updateClass(@RequestBody SubjectsRequest subjectsRequest, @PathVariable("id") Long id) {
        subjectsRequest.setId(id);
        return ResponseEntity.ok(subjectsService.createOrUpdateSubjects(subjectsRequest));
    }

    @DeleteMapping(value = "subjects")
    public void delete(@RequestBody Long[] ids) {
        subjectsService.deleteSubjectsById(ids);
    }
}
