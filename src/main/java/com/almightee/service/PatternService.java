package com.almightee.service;

import com.almightee.domain.Pattern;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

public interface PatternService {
    public Page<Pattern> retrieveAllPatterns(Pageable pageable);
    public Optional<Pattern> getPattern(Long id);
    public void deletePattern(Long id);
    public Page<Pattern> retrievePatternsByQuery(String query, Pageable pageable);
    public Pattern createPattern(Pattern pattern);
    public Pattern updatePattern(Pattern pattern);
    public void setPictureUrl(Long id, String url);
}
