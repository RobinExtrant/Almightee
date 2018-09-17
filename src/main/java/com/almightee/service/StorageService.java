package com.almightee.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface StorageService {
    public void init();
    public void store(MultipartFile file);
    public Resource load(String username, String filename);
}
