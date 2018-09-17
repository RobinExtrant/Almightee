package com.almightee.service.impl;

import com.almightee.service.StorageService;
import com.almightee.service.util.PictureBO;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
@Transactional
public class StorageServiceImpl implements StorageService {

    private final Logger log = LoggerFactory.getLogger(StorageServiceImpl.class);

    private Path rootPath;

    @Override
    public void store(MultipartFile file) {
        try {
            if (file.isEmpty()) {
                throw new RuntimeException("Failed to store empty file " + file.getOriginalFilename());
            }
            Files.copy(file.getInputStream(), this.rootPath.resolve(file.getOriginalFilename()));
        } catch (IOException e) {
            throw new RuntimeException("Failed to store file " + file.getOriginalFilename(), e);
        }
    }

    @Override
    public PictureBO load(String username, String filename) {
        try {
            Path file = rootPath.resolve(username + "/" + filename);
            PictureBO picture = new PictureBO(filename);
            picture.setContent(IOUtils.toByteArray(file.toUri()));
            return picture;
        }
        catch (MalformedURLException e) {
            throw new RuntimeException("Could not read file: " + filename, e);
        }
        catch (IOException e) {
            throw new RuntimeException("Unable to load the file: " + filename, e);
        }
    }

    @Override
    public void init() {
        try {
            Files.createDirectory(rootPath);
        } catch (IOException e) {
            throw new RuntimeException("Could not initialize storage", e);
        }
    }
}
