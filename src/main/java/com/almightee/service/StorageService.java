package com.almightee.service;

import com.almightee.service.util.PictureBO;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface StorageService {
    public void init();
    public String store(String author, MultipartFile file);
    public PictureBO load(String author, String filename);
    public void deleteAll();
}
