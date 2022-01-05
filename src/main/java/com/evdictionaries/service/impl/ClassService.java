package com.evdictionaries.service.impl;

import com.evdictionaries.converter.ClassConverter;
import com.evdictionaries.models.Class;
import com.evdictionaries.payload.request.ClassRequest;
import com.evdictionaries.repository.ClassRepository;
import com.evdictionaries.service.IClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ClassService implements IClassService {
    @Autowired
    public ClassRepository classRepository;

    @Autowired
    public ClassConverter classConverter;

    @Override
    public ClassRequest createClass(ClassRequest classRequest) {
        Class classEntity = new Class();
        if (classRequest.getId() != null) {
            Class nationalEntityOld = classRepository.findOne(classRequest.getId());
            classEntity = classConverter.toEntity(classRequest, nationalEntityOld);
        } else {
            classEntity = classConverter.toEntity(classRequest);
        }
        classEntity = classRepository.save(classEntity);
        return classConverter.toDto(classEntity);
    }

    @Override
    public void deleteClassById(Long[] ids) {
        for (Long idNational : ids) {
            classRepository.delete(idNational);
        }
    }

    @Override
    public List<ClassRequest> getClassAll() {
        List<ClassRequest> classRequests = new ArrayList<>();
        List<Class> nationalEntities = classRepository.findAll();
        for (Class nationalEntity : nationalEntities) {
            ClassRequest classRequest = classConverter.toDto(nationalEntity);
            classRequests.add(classRequest);
        }
        return classRequests;
    }
}
