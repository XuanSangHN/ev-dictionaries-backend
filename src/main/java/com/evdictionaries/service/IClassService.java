package com.evdictionaries.service;

import com.evdictionaries.models.Class;
import com.evdictionaries.payload.request.ClassRequest;

import java.util.List;

public interface IClassService {
    ClassRequest createClass(ClassRequest nationalDTO);

    void deleteClassById(Long[] ids);

    List<Class> getClassAll();
}
