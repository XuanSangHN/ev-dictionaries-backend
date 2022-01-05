package com.evdictionaries.controllers;

import com.evdictionaries.payload.request.ClassRequest;
import com.evdictionaries.payload.response.ClassResponse;
import com.evdictionaries.service.impl.ClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/v1")
public class ClassController {
    @Autowired
    ClassService classService;

    @Autowired
    ClassResponse classResponse;

    @PostMapping(value = "class")
    public ResponseEntity<?> createClass(@RequestBody ClassRequest classRequest) {
        return ResponseEntity.ok(classService.createClass(classRequest));
    }

    @PutMapping(value = "class/{id}")
    public ClassRequest updateClass(@RequestBody ClassRequest classRequest, @PathVariable("id") Long id) {
        classRequest.setId(id);
        return classService.createClass(classRequest);
    }

    @DeleteMapping(value = "class")
    public void delete(@RequestBody Long[] ids) {
        classService.deleteClassById(ids);
    }

    @GetMapping(value = "class")
    public ClassResponse showNClass() {
//        classResponse.setData(classService.getClassAll());
        return classResponse;
    }
}
