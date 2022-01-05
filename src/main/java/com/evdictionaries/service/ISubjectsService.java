package com.evdictionaries.service;

import com.evdictionaries.payload.request.SubjectsRequest;

import java.util.List;

public interface ISubjectsService {
    SubjectsRequest createOrUpdateSubjects(SubjectsRequest nationalDTO);

    void deleteSubjectsById(Long[] ids);

    List<SubjectsRequest> getSubjectsAll();
}
