package com.evdictionaries.service;

import com.evdictionaries.payload.request.ClassRequest;

import java.util.List;

public interface IClassService {
    ClassRequest createClass(ClassRequest nationalDTO);

    void deleteClassById(Long[] ids);

    List<ClassRequest> getClassAll();
}
