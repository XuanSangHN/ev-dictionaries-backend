package com.evdictionaries.converter;

import com.evdictionaries.models.Subjects;
import com.evdictionaries.payload.request.SubjectsRequest;
import org.springframework.stereotype.Component;

@Component
public class SubjectsConverter {
    public Subjects toEntity(SubjectsRequest subjectsRequest) {
        Subjects subjects = new Subjects();
        subjects.setCode(subjectsRequest.getCode());
        subjects.setName(subjectsRequest.getName());
        subjects.setDescription(subjectsRequest.getDescription());
        return subjects;
    }

    public SubjectsRequest toDto(Subjects subjects) {
        SubjectsRequest subjectsRequest = new SubjectsRequest();
        if (subjects.getId() != null) {
            subjectsRequest.setId(subjects.getId());
        }
        subjectsRequest.setCode(subjects.getCode());
        subjectsRequest.setName(subjects.getName());
        subjectsRequest.setDescription(subjects.getDescription());
        subjectsRequest.setCreatedBy(subjects.getCreatedBy());
        subjectsRequest.setCreatedDate(subjects.getCreatedDate());
        subjectsRequest.setModifiedBy(subjects.getModifiedBy());
        subjectsRequest.setModifiedDate(subjects.getModifiedDate());
        return subjectsRequest;
    }

    public Subjects toEntity(SubjectsRequest subjectsRequest, Subjects subjects) {
        subjects.setCode(subjectsRequest.getCode());
        subjects.setName(subjectsRequest.getName());
        subjects.setDescription(subjectsRequest.getDescription());
        return subjects;
    }
}
