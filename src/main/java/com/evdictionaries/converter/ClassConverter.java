package com.evdictionaries.converter;

import com.evdictionaries.models.Class;
import com.evdictionaries.models.User;
import com.evdictionaries.payload.request.ClassRequest;
import org.springframework.stereotype.Component;

@Component
public class ClassConverter {
    public Class toEntity(ClassRequest classRequest) {
        Class classEntity = new Class();
        classEntity.setCode(classRequest.getCode());
        classEntity.setName(classRequest.getName());
        User user = new User();
        user.setId(classRequest.getProfile_id());
        classEntity.setUser(user);
        classEntity.setDescription(classRequest.getDescription());
        return classEntity;
    }

    public ClassRequest toDto(Class classEntity) {
        ClassRequest classRequest = new ClassRequest();
        if (classEntity.getId() != null) {
            classRequest.setId(classEntity.getId());
        }
        classRequest.setCode(classEntity.getCode());
        classRequest.setName(classEntity.getName());
        classRequest.setDescription(classEntity.getDescription());
        classRequest.setCreatedBy(classEntity.getCreatedBy());
        classRequest.setCreatedDate(classEntity.getCreatedDate());
        classRequest.setModifiedBy(classEntity.getModifiedBy());
        classRequest.setModifiedDate(classEntity.getModifiedDate());
        return classRequest;
    }

    public Class toEntity(ClassRequest classRequest, Class classEntity) {
        User user = new User();
        user.setId(classRequest.getProfile_id());
        classEntity.setUser(user);
        classEntity.setCode(classRequest.getCode());
        classEntity.setName(classRequest.getName());
        classEntity.setDescription(classRequest.getDescription());
        return classEntity;
    }
}
