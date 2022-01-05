package com.evdictionaries.service.impl;

import com.evdictionaries.converter.SubjectsConverter;
import com.evdictionaries.models.Subjects;
import com.evdictionaries.payload.request.SubjectsRequest;
import com.evdictionaries.repository.SubjectsRepository;
import com.evdictionaries.service.ISubjectsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SubjectsService implements ISubjectsService {

    @Autowired
    public SubjectsRepository subjectsRepository;

    @Autowired
    public SubjectsConverter subjectsConverter;

    @Override
    public SubjectsRequest createOrUpdateSubjects(SubjectsRequest subjectsRequest) {
        Subjects subjects = new Subjects();
        if (subjectsRequest.getId() != null) {
            Subjects subjectsEntityOld = subjectsRepository.findOne(subjectsRequest.getId());
            subjects = subjectsConverter.toEntity(subjectsRequest, subjectsEntityOld);
        } else {
            subjects = subjectsConverter.toEntity(subjectsRequest);
        }
        subjects = subjectsRepository.save(subjects);
        return subjectsConverter.toDto(subjects);
    }

    @Override
    public void deleteSubjectsById(Long[] ids) {
        for (Long idNational : ids) {
            subjectsRepository.delete(idNational);
        }
    }

    @Override
    public List<SubjectsRequest> getSubjectsAll() {
        List<SubjectsRequest> subjectsRequests = new ArrayList<>();
        List<Subjects> subjectsEntities = subjectsRepository.findAll();
        for (Subjects subjects : subjectsEntities) {
            SubjectsRequest classRequest = subjectsConverter.toDto(subjects);
            subjectsRequests.add(classRequest);
        }
        return subjectsRequests;
    }
}
