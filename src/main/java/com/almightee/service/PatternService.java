package com.almightee.service;

import com.almightee.domain.Pattern;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface PatternService {
    public Page<Pattern> retrieveAllPatterns(Pageable pageable);
    public Optional<Pattern> getPattern(Long id);
    public Pattern savePattern(Pattern pattern);
    public void deletePattern(Long id);
    public Page<Pattern> retrievePatternsByQuery(String query, Pageable pageable);
}
