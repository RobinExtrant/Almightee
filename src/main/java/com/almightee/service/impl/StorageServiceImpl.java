package com.almightee.service.impl;

import com.almightee.config.StorageProperties;
import com.almightee.service.StorageService;
import com.almightee.service.util.PictureBO;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileSystemUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
@Transactional
public class StorageServiceImpl implements StorageService {

    private final Logger log = LoggerFactory.getLogger(StorageServiceImpl.class);

    private final Path rootPath;
    private final String urlPrefix = "/api/pictures/";

    @Autowired
    public StorageServiceImpl(StorageProperties properties) {
        this.rootPath = Paths.get(properties.getLocation());
    }

    @Override
    public String store(String author, MultipartFile file) {
        String filename = StringUtils.cleanPath(file.getOriginalFilename());
        try {
            if (file.isEmpty()) {
                throw new RuntimeException("Failed to store empty file " + filename);
            }
            Path dir = this.rootPath.resolve(author);
            if (!Files.isDirectory(dir)) {
                Files.createDirectory(dir);
            }
            Files.copy(file.getInputStream(), dir.resolve(filename));
            return urlPrefix + author +"?picture_name=" + filename;
        } catch (IOException e) {
            throw new RuntimeException("Failed to store file " + filename, e);
        }
    }

    @Override
    public PictureBO load(String author, String filename) {
        try {
            Path file = rootPath.resolve(author + "/" + filename);
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
    public void deleteAll() {
        FileSystemUtils.deleteRecursively(rootPath.toFile());
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
