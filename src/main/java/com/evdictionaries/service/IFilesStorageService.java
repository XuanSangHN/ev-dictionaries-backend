package com.evdictionaries.service;

import org.springframework.web.multipart.MultipartFile;

import org.springframework.core.io.Resource;

import java.nio.file.Path;
import java.util.stream.Stream;

public interface IFilesStorageService {
    void init();
    void uploadImgeProfile(MultipartFile file);
    Resource load(String filename);
    void deleteAll();
    Stream<Path> loadAll();
}
