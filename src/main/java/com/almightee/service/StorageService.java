package com.almightee.service;

import com.almightee.service.util.PictureBO;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface StorageService {
    public void init();
    public void store(MultipartFile file);
    public PictureBO load(String username, String filename);
}
